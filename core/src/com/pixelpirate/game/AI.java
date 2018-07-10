package com.pixelpirate.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AI {
	public final int MAX_ENEMIES = 1;
	private final int AWARENESS_DISTANCE = 100; // [Pixels] Distance before stopping
	private final int SPAWN_DISTANCE = 200; 	// [Pixels] Spawn range offscreen

	//	Management
	private Main game;
	private SailingScreen sailingScreen;
	private CollisionHandler handler;
	
	//	Entity objects
	private Ship player;
	private EnemyShip[] enemies;
	
	private boolean boarded = false;

	AI(Main game, SailingScreen sailingScreen, CollisionHandler handler, Ship player) {
		this.game = game; 
		this.sailingScreen = sailingScreen;
		this.player = player;
		this.handler = handler;

		enemies = new EnemyShip[MAX_ENEMIES];

		//	Spawn initial enemies
		for(int i = 0; i < MAX_ENEMIES; i++) {
			int newX = MathUtils.random(SPAWN_DISTANCE * i * i) + Gdx.graphics.getWidth();
			enemies[i] = new EnemyShip(newX, i, new Rectangle(0,0, 200, 50), new Rectangle(20, 50, 160, 50));
		}
	}

	public void update() {
		checkPosition();
		for(int i = 0; i < MAX_ENEMIES; i++) {
			enemies[i].update(i);
		}
	}

	public void draw() {
		for(int i = 0; i < MAX_ENEMIES; i++) {
			enemies[i].draw();
		}
	}

	//	Reset enemies when offscreen
	private void checkPosition() {
		for(int i = 0; i < MAX_ENEMIES; i++) {
			if(enemies[i].position.x < -200) {
				reset(i);
			} else if(enemies[i].position.x < 200) {
				if(boarded != true) {
				game.getFade().FadeOut(game.meleeScreen, 5);
				}
				boarded = true;
			}
		}
	}

	//	Reset enemy state
	public void reset(int i) {
		boarded = false;
		
		enemies[i].position.x = MathUtils.random(SPAWN_DISTANCE) + Gdx.graphics.getWidth();
		enemies[i].health = game.sailingScreen.MAX_HEALTH;
		enemies[i].inCombat = false;
		enemies[i].isAlive = true;
		enemies[i].move = false;
		enemies[i].position.y = game.sailingScreen.ELEMENT_HEIGHT;
		enemies[i].lowerSails();
	}

	public EnemyShip getEnemy(int i) {
		return enemies[i];
	}

	public class EnemyShip {
		private final int REST_TIME = 1; // Time before next cannonball

		private int index;	//	Position in enemy list
		
		//	Type
		private String shipType = "basic";
		private int power = 15; // Canon damage plus treasure multiplier
		
		//	Class-specific objects
		private Vector2 position;
		private Collider collider;
		private Canon canon;
		private HealthBar healthbar;

		//	State
		protected boolean isFurled = false;
		public boolean inCombat = false;
		public boolean isAlive = true;
		public boolean move = true;
		public boolean underAttack;
		
		private int health;

		EnemyShip(int x, int index, Rectangle... boxes) {
			this.index = index;
			
			position = new Vector2(x, sailingScreen.ELEMENT_HEIGHT);
			healthbar = new HealthBar(game, game.getRenderer(), this, sailingScreen.MAX_HEALTH);
			collider = new Collider(game, this, handler, boxes);
			canon = new Canon(game, handler, "enemy", power);
			
			health = sailingScreen.MAX_HEALTH;
		}

		private void update(int i) {
			if(isAlive) {
				if(player.isAlive()) {
					checkDeath(i);
					if(getHealth() < 100) {
						underAttack = true;
					} else if(getHealth() == 100) {
						underAttack = false;
					}
					if(inCombat && underAttack) {
						Attack();
					}
				} else {
					move = true;
					lowerSails();
				}
				collider.update();
			} else { 
				dieAnimation();
			}
			
			canon.update();
			healthbar.update();
			
			//	Furl sails and lower speed at given distance away from player
			if(position.x < player.getPosition().x + 450 && player.isAlive()) {
				move = false;
			} else {
				move = true;
			}
			
			//	Move 
			if(move) {
				position.add(new Vector2(-0.3f * game.getSpeed() * (1-Gdx.graphics.getDeltaTime()), 0));
			} else if(player.isAlive()) {
				raiseSails();
				inCombat = true;
				if(!player.isFurled()) {
					position.add(new Vector2(-0.2f * game.getSpeed() * (1-Gdx.graphics.getDeltaTime()), 0));
				}
			}
		}

		private void draw() {
			canon.draw();

			//	Set shader variables
			Assets.Shaders.ship.begin();
			Assets.Shaders.ship.setUniformf("u_time", game.getYdelta() * 1.2f);
			Assets.Shaders.ship.end();

			Assets.Shaders.sails.begin();
			Assets.Shaders.sails.setUniformMatrix("u_projTrans", game.getCameraProjection());
			Assets.Shaders.sails.setUniformf("u_time", game.getYdelta() * 1.2f);
			Assets.Shaders.sails.end();

			if(isAlive)
				healthbar.draw();

			//	Draw player ship flipped horizontally [Note: 'hold' texture is not flipped around center to achieve adequate result]
			if(shipType == "basic") {
				game.getBatch().setShader(Assets.Shaders.ship);

				game.getBatch().begin();
				game.getBatch().draw(Assets.ShipParts.basicMast, position.x, position.y, Assets.ShipParts.basicMast.getWidth()/2, Assets.ShipParts.basicMast.getHeight()/2, Assets.ShipParts.basicMast.getWidth(), Assets.ShipParts.basicMast.getHeight(), 1, 1, 0, 0, 0, Assets.ShipParts.basicMast.getWidth(), Assets.ShipParts.basicMast.getHeight(), true, false); 
				game.getBatch().draw(Assets.ShipParts.basicHull,  position.x, position.y, Assets.ShipParts.basicHull.getWidth()/2, Assets.ShipParts.basicHull.getHeight()/2, Assets.ShipParts.basicHull.getWidth(), Assets.ShipParts.basicHull.getHeight(), 1, 1, 0, 0, 0, Assets.ShipParts.basicHull.getWidth(), Assets.ShipParts.basicHull.getHeight(), true, false);

				if(!isFurled) {
					game.getBatch().setShader(Assets.Shaders.sails);
					game.getBatch().draw(Assets.ShipParts.basicSails, position.x, position.y, Assets.ShipParts.basicSails.getWidth()/2, Assets.ShipParts.basicSails.getHeight()/2, Assets.ShipParts.basicSails.getWidth(), Assets.ShipParts.basicSails.getHeight(), 1, 1, 0, 0, 0, Assets.ShipParts.basicSails.getWidth(), Assets.ShipParts.basicSails.getHeight(), true, false); 
				} else {
					game.getBatch().draw(Assets.ShipParts.basicSailsFurled, position.x, position.y, Assets.ShipParts.basicSailsFurled.getWidth()/2, Assets.ShipParts.basicSailsFurled.getHeight()/2, Assets.ShipParts.basicSailsFurled.getWidth(), Assets.ShipParts.basicSailsFurled.getHeight(), 1, 1, 0, 0, 0, Assets.ShipParts.basicSailsFurled.getWidth(), Assets.ShipParts.basicSailsFurled.getHeight(), true, false); 
				}

				game.getBatch().setShader(Assets.Shaders.ship);
				game.getBatch().draw(Assets.ShipParts.basicHold, position.x, position.y, Assets.ShipParts.basicHold.getWidth()/2, Assets.ShipParts.basicHold.getHeight()/2, Assets.ShipParts.basicHold.getWidth() + 35, Assets.ShipParts.basicHold.getHeight(), 1, 1, 0, 0, 0, Assets.ShipParts.basicHold.getWidth(), Assets.ShipParts.basicHold.getHeight(), true, false);
				game.getBatch().end();
			}
		}

		/* Management methods */
		public void lowerSails() {
			isFurled = false;
		}

		private void raiseSails() {
			isFurled = true;
		}

		private boolean isFurled() {
			return isFurled;
		}

		public Vector2 getPosition() {
			return position;
		}

		public int getHealth() {
			return health;
		}

		public void setHealth(int amount) {
			health = amount;
		}

		public Collider getCollisionBox() {
			return collider;
		}

		//	Reduce health
		public void Hit(int damage) {
			if(health > 0)
				setHealth(getHealth() - damage);
		}

		public void checkDeath(int i) {
			if( health <= 0) {
				health = 0;	
				isAlive = false;
			}
		}

		//	Canon fire time limiter
		private float prevTime = 0;
		public void Attack() {
			if(game.getDelta() > prevTime + REST_TIME) {
				prevTime = game.getDelta();
				fireCanon();
			}
		}

		public void fireCanon() {
			canon.fire(position.x + 40, position.y + 40, new Vector2(-9, 6));
		}

		//	After animation transition to treasureScreen
		public void dieAnimation() {
			position.y -= 2;
			if(position.y < -40) {
				reset(index);
				game.treasureScreen.setTreasure(MathUtils.random(10), MathUtils.random(5), MathUtils.random(10));
				game.getFade().FadeOut(game.treasureScreen, 2);
			}
		}
	}
}

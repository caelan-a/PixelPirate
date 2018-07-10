package com.pixelpirate.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//@author Caelan Anderson
public class Ship {
	private final float SHIP_SPEED = 2.0f;
	
	//	Management
	private Main game;
	private SailingScreen sailingScreen;

	//	Class-specific objects
	private HealthBar healthBar;
	private Canon canon;
	private Collider collider;
	
	//	State
	private Vector2 position;
	private String shipType = "basic";
	
	private boolean isFurled = true;
	private boolean isAlive = true;
	private int health;
	
	Ship(Main game, SailingScreen sailingScreen, CollisionHandler handler) {
		this.game = game; 
		this.sailingScreen = sailingScreen;
		
		position = new Vector2(20, sailingScreen.ELEMENT_HEIGHT);
		healthBar = new HealthBar(game, game.getRenderer(), this, sailingScreen.MAX_HEALTH);
		collider = new Collider(game, this, handler,  new Rectangle(0,0, 200, 50), new Rectangle(20, 50, 100, 100));
		canon = new Canon(game, handler, "player", 30);
		
		health = sailingScreen.MAX_HEALTH;
	}
	
	public void update() {
		healthBar.update();
		canon.update();
		collider.update();
		
		checkDeath();
		if(!isAlive) {
			raiseSails();
			dieAnimation();
		}
	}
	
	public void draw() {
		//	Set shader variables
		Assets.Shaders.ship.begin();
		Assets.Shaders.ship.setUniformMatrix("u_projTrans", game.getShaderMatrix());
		Assets.Shaders.ship.setUniformf("u_time", game.getYdelta());
		Assets.Shaders.ship.end();
		
		Assets.Shaders.sails.begin();
		Assets.Shaders.sails.setUniformMatrix("u_projTrans", game.getCameraProjection());
		Assets.Shaders.sails.setUniformf("u_time", game.getYdelta());
		Assets.Shaders.sails.end();
		
		healthBar.draw();
		canon.draw();
		
		//	Draw modular parts based on shipType
		if(shipType == "basic") {
			game.getBatch().setShader(Assets.Shaders.ship);
			game.getBatch().begin();
			game.getBatch().draw(Assets.ShipParts.basicMast, position.x, position.y);
			game.getBatch().draw(Assets.ShipParts.basicHull, position.x, position.y);
			
			//	Change sails sprite
			if(!isFurled) {
				game.getBatch().setShader(Assets.Shaders.sails);
				game.getBatch().draw(Assets.ShipParts.basicSails, position.x, position.y);
			} else if(isFurled){
				game.getBatch().draw(Assets.ShipParts.basicSailsFurled, position.x, position.y);
			}
			
			game.getBatch().setShader(Assets.Shaders.ship);
			game.getBatch().draw(Assets.ShipParts.basicHold, position.x, position.y);
			game.getBatch().end();
		}
	}
	
	//	Management methods
	public void lowerSails() {
		isFurled = false;
		game.setSpeed(SHIP_SPEED);
	}
	
	public void raiseSails() {
		isFurled = true;
		game.setSpeed(1f);
	}
	
	public boolean isFurled() {
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
	
	public Canon getCanon() {
		return canon;
	}
	
	//	Reduces health
	public void Hit(int damage) {
		if(health > 0)
			setHealth(getHealth() - damage);
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	private void checkDeath() {
		if(getHealth() <= 0) {
			health = 0;
			isAlive = false;
		}
	}
	
	//	Reset state
	public void reset() {
		isAlive = true;
		health = game.sailingScreen.MAX_HEALTH;
		position.y = game.sailingScreen.ELEMENT_HEIGHT;
	}

	//	Upon death y decreases to point where scene is reset to main menu
	private void dieAnimation() {
		position.y -= 2;
		if(position.y < -40) {
			reset();
			position.y = 500; // Set to offscreen to avoid appearance during fadein+screenchange
			game.sailingScreen.restart();
		}
	}
}

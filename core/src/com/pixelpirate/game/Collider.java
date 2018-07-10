package com.pixelpirate.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelpirate.game.Canon.Canonball;

//@author Caelan Anderson
public class Collider {
	private Main game;
	
	//	Management
	private CollisionHandler handler;
	private Vector2 position;
	private SubBox[] collisionSubBoxes;
	private String owner;
	
	//	Owner object
	private Ship player;		
	private AI.EnemyShip enemy;
	
	//	Constructor for player ship
	Collider(Main game, Ship player, CollisionHandler handler,  Rectangle... boxes) {
		this.player = player;
		this.handler = handler;
		this.game = game;
		owner = "player";
		position = new Vector2();
		
		//	Store collision sub-boxes
		collisionSubBoxes = new SubBox[boxes.length];
		for(int i = 0; i < boxes.length; i++) {
			collisionSubBoxes[i] = new SubBox(boxes[i]);
		}

		//	Add sub-box array to handler list
		handler.addCollider(this);
	}
	
	//	Constructor for enemy ship
	Collider(Main game, AI.EnemyShip enemy, CollisionHandler handler, Rectangle... boxes) {
		this.enemy = enemy;
		this.handler = handler;
		this.game = game;
		owner = "enemy";
		position = new Vector2();
		
		//	Store collision sub-boxes
		collisionSubBoxes = new SubBox[boxes.length];
		for(int i = 0; i < boxes.length; i++) {
			collisionSubBoxes[i] = new SubBox(boxes[i]);
		}

		// Add sub-box array to handler list
		handler.addCollider(this);
	}
	
	public void update() {
		//	Update collisionbox to entity position
		if(owner == "player"){
			for(int i = 0; i < collisionSubBoxes.length; i++) {
				position.x = player.getPosition().x;
				position.y = player.getPosition().y;
				
				//	Update subbox position using sum of entity position and offset
				collisionSubBoxes[i].box.x = position.x + collisionSubBoxes[i].offset.x;
				collisionSubBoxes[i].box.y = position.y + collisionSubBoxes[i].offset.y;
				
				game.getRenderer().begin(ShapeType.Filled);
				game.getRenderer().rect(collisionSubBoxes[i].box.x, collisionSubBoxes[i].box.y, collisionSubBoxes[i].box.width, collisionSubBoxes[i].box.height);
				game.getRenderer().end();
			}
		} else 	if(owner == "enemy"){
			for(int i = 0; i < collisionSubBoxes.length; i++) {
				position.x = enemy.getPosition().x;
				position.y = enemy.getPosition().y;
				
				collisionSubBoxes[i].box.x = position.x + collisionSubBoxes[i].offset.x;
				collisionSubBoxes[i].box.y = position.y + collisionSubBoxes[i].offset.y;
				
				game.getRenderer().begin(ShapeType.Line);
				game.getRenderer().rect(collisionSubBoxes[i].box.x, collisionSubBoxes[i].box.y, collisionSubBoxes[i].box.width, collisionSubBoxes[i].box.height);
				game.getRenderer().end();
			}
		}
	}
	
	//	Collision Detector for canonball, destroys ball when return is true
	public boolean checkLocalCollision(Canonball ball) {
		for(int i = 0; i < collisionSubBoxes.length; i++) {
			if(collisionSubBoxes[i].box.contains(ball.position)) {
				if(ball.owner == "player" && owner == "enemy") {
					
					enemy.Hit(ball.damage);
					return true;
					
				} else if(ball.owner == "enemy" && owner == "player") {
					
					player.Hit(ball.damage);
					return true;
					
				}
			}
		}
		return false;
	}
	
	public class SubBox {
		public Rectangle box;
		public Vector2 offset;
		
		SubBox(Rectangle box) {
			this.box = box;
			offset = new Vector2(box.x, box.y);
		}
	}
}

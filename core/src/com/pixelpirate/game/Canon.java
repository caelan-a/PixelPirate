package com.pixelpirate.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

//@author Caelan Anderson
public class Canon{
	private final float SPEED = 10;
	private final float GRAVITY = -0.2f;
	
	//	Management
	private Main game;
	private CollisionHandler handler;
	
	//	Class-specific objects
	private List<Canonball> canonBalls;
	private String owner;
	private int power;					// Damage potential of cannonballs


	Canon(Main game, CollisionHandler handler, String owner, int power) {
		this.game = game;
		this.handler = handler;
		this.owner = owner;
		this.power = power;
		
		canonBalls = new ArrayList<Canonball>();
	}

	public void update() {
		for(int i = 0; i < canonBalls.size(); i++) {
			//	Apply gravity to cannonball velocity
			if(canonBalls.get(i).velocity.x > 3) {							//	Apply gravity before perpendicularity 
				canonBalls.get(i).velocity.add(new Vector2(0.0f, GRAVITY));	//	for east velocity
			} else if(canonBalls.get(i).velocity.x < -3) {					//	Apply gravity before perpendicularity
				canonBalls.get(i).velocity.add(new Vector2(0.0f, GRAVITY));	//	for west velocity
			}

			//	Add gravity adjusted velocity to canonball position
			canonBalls.get(i).position = canonBalls.get(i).position.add(canonBalls.get(i).velocity);
		}

		//	Seperate for loops to avoid iteration of removed canonball (ie from collision) [Find better method?]
		for(int i = 0; i < canonBalls.size(); i++) {
			canonBalls.get(i).checkCollision(i);
		}

		for(int i = 0; i < canonBalls.size(); i++) {
			canonBalls.get(i).checkPosition(i);
		}
	}

	public void draw() {
		game.getBatch().begin();
		game.getBatch().setShader(null);
		for(int i = 0; i < canonBalls.size(); i++) {
			game.getBatch().draw(Assets.canonball, canonBalls.get(i).position.x, canonBalls.get(i).position.y, 16, 16);
		}
		game.getBatch().end();
	}

	public void fire(float x, float y, Vector2 velocity) {
		canonBalls.add(new Canonball(new Vector2(x,y), velocity, owner, power));
	}

	public class Canonball {
		private final int OFFSCREEN_REMOVE = 50; //	In water
		
		public 	Vector2 position;
		private Vector2 velocity;
		public 	String owner;
		public int damage;

		Canonball(Vector2 initPos, Vector2 initVelocity, String owner, int damage) {
			this.owner = owner;
			this.damage = damage;
			
			velocity = initVelocity;
			position = initPos;
		}

		public void checkCollision(int i) {
			if(handler.checkCollision(this)) {
				canonBalls.remove(i);	
			}
		}

		//	Remove when offscreen
		public void checkPosition(int i) {
			if(position.y < OFFSCREEN_REMOVE) {
				if(position.y < 0) {
					canonBalls.remove(i);
				}
			}
		}
	}
}

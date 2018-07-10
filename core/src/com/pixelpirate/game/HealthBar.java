package com.pixelpirate.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

//	@author Caelan Anderson
public class HealthBar {
	private final int X_OFFSET = 30;
	private final int Y_OFFSET = 170;
	
	//	Management
	private Main game;
	private ShapeRenderer renderer;

	//	Entities
	private Ship player;
	private AI.EnemyShip enemy;
	
	//	State
	private String type;
	private Vector2 position;
	private int maxWidth;
	private int width;
	
	//	Constructor for player
	HealthBar(Main game, ShapeRenderer renderer, Ship player, int max) {
		this.game = game;
		this.renderer = renderer;
		this.player = player;
		
		type = "player";
		
		maxWidth = max;
		position = new Vector2(player.getPosition().x + X_OFFSET, player.getPosition().y + Y_OFFSET);
	}
	
	//	Constructor for enemy
	HealthBar(Main game, ShapeRenderer renderer, AI.EnemyShip enemy, int max) {
		this.game = game;
		this.renderer = renderer;
		this.enemy = enemy;
		
		type = "enemy";
		
		maxWidth = max;
		position = new Vector2(enemy.getPosition().x + X_OFFSET, enemy.getPosition().y + Y_OFFSET);
	}
	
	public void update() {
		if(type == "player") {
			position = new Vector2(player.getPosition().x + X_OFFSET, player.getPosition().y + Y_OFFSET);
			width = player.getHealth();
		} else if(type == "enemy") {
			position = new Vector2(enemy.getPosition().x + X_OFFSET, enemy.getPosition().y + Y_OFFSET);
			width = enemy.getHealth();
		}
	}
	
	public void draw() {
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.RED);
		renderer.rect(position.x, position.y, width, 10);
		renderer.end();
		
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.BLACK);
		renderer.rect(position.x, position.y, maxWidth, 10);
		renderer.end();
	}
}

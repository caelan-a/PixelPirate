package com.pixelpirate.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Island {
	private final float SPEED = -0.1f;	// Factor of 'Main' speed
	private final float SPAWN_DISTANCE = 1000; // In pixels
	
	private Main game;
	private Vector2 position;
	private Texture texture;
	
	Island(Main game, SailingScreen sailingScreen) {
		this.game = game;
		
		texture = Assets.island;
		position = new Vector2(10, sailingScreen.ELEMENT_HEIGHT);

	}

	public void update() {
		position.add(new Vector2(SPEED * game.getSpeed(), 0));
		checkPosition();
	}
	
	public void draw() {
		game.getBatch().begin();
		game.getBatch().draw(texture, position.x, position.y);
		game.getBatch().end();
	}
	
	//	Reset if position if entity is texture's width from screen
	private void checkPosition() {
		if(position.x < -texture.getWidth()) {
			reset();
		}
	}
	
	// Reset position off screen
	private void reset() {
		position.x = MathUtils.random(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()) + SPAWN_DISTANCE;
	}
}

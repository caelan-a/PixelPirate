package com.pixelpirate.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

//	@author Caelan Anderson
public class Clouds {
	private final int MAX_CLOUDS = 3;
	private final int MAX_SPACING = 100; // In pixels

	//	Management
	private Main game;
	private Cloud[] clouds;

	Clouds(Main game) {
		this.game = game;

		clouds = new Cloud[MAX_CLOUDS];

		//	Generating starting clouds based on amount [change from screen dependency]
		for(int i = 0; i < MAX_CLOUDS; i++) {
			clouds[i] = new Cloud(MathUtils.random(0.1f * i * Gdx.graphics.getWidth(), 2 * 0.8f * i * Gdx.graphics.getWidth()),  MathUtils.random(Gdx.graphics.getHeight() / 1.5f, Gdx.graphics.getHeight() - 50));
		}
	}
	public void update() {
		//	Adjust cloud speed if player stops moving
		for(int i = 0; i < MAX_CLOUDS; i++) {
			if(game.getSpeed() <= 1)
				clouds[i].position.x -= 0.2;
			else {
				clouds[i].position.x -= 0.6;
			}
		}
		checkPosition();
	}

	public void draw() {
		game.getBatch().setShader(null);

		for(int i = 0; i < MAX_CLOUDS; i++) {
			clouds[i].tex.bind(0);
			game.getBatch().begin();
			game.getBatch().draw(clouds[i].tex, clouds[i].position.x, clouds[i].position.y);
			game.getBatch().end();
		}
	}

	//	Reset cloud offscreen with random texture
	public void reset(int i) {
		clouds[i].position = new Vector2(MathUtils.random(MAX_SPACING) + Gdx.graphics.getWidth(),  MathUtils.random(Gdx.graphics.getHeight() / 1.5f, Gdx.graphics.getHeight() - 50));
		clouds[i].tex = Assets.clouds[MathUtils.random(8)];
	}

	//	Check if offscreen
	private void checkPosition() {
		for(int i = 0; i < MAX_CLOUDS; i++) {
			if(clouds[i].position.x < -250) {
				reset(i);
			}
		}
	}

	private class Cloud {
		private Vector2 position;
		private Texture tex; 

		Cloud(float x, float y) {
			position = new Vector2(x,y);
			tex = Assets.clouds[MathUtils.random(8)];
		}
	}
}

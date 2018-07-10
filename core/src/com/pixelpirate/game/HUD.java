package com.pixelpirate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;

public class HUD {
	private Main game;
	private Resources res;
	
	private Vector2 foodPosition;
	private Vector2 moneyPosition;
	private Vector2 peoplePosition;

	HUD(Main game, Resources res) {
		this.game = game;
		this.res = res;
		
		foodPosition = new Vector2(10, Gdx.graphics.getHeight() - 50);
		moneyPosition = new Vector2(10, Gdx.graphics.getHeight() - 110);
		peoplePosition = new Vector2(10, Gdx.graphics.getHeight() - 170);
	}
	
	public void draw() {
		game.getBatch().begin();
		game.getBatch().setShader(null);
		Assets.food.bind(0);
		game.getBatch().draw(Assets.food, foodPosition.x, foodPosition.y, 72, 48);
		game.getFont().draw(game.getBatch(), res.getFood() + "", foodPosition.x + 52, foodPosition.y + 30);
		game.getBatch().draw(Assets.money, moneyPosition.x, moneyPosition.y, 72, 48);
		game.getFont().draw(game.getBatch(), res.getMoney() + "K", moneyPosition.x + 42, moneyPosition.y + 30);
		game.getBatch().draw(Assets.people, peoplePosition.x, peoplePosition.y, 72, 48);
		game.getFont().draw(game.getBatch(), res.getPeople() + "", peoplePosition.x + 52, peoplePosition.y + 30);
		game.getBatch().end();
	}	
}

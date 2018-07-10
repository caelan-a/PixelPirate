package com.pixelpirate.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;

//	@author Caelan Anderson
public class ButtonList {
	private final int BUTTON_SPACING = 10;

	//	Format
	private int buttonWidth = 200;
	private int buttonHeight = 50;

	//	Management
	private Main game;
	private BitmapFont font;

	private List<Button> buttons;

	ButtonList(Main game, int startingX, int startingY, int buttonWidth, int buttonHeight, String firstContent) {
		this.game = game;
		this.buttonWidth = buttonWidth;
		this.buttonHeight = buttonHeight;

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenpixel_square.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 30;
		font = generator.generateFont(parameter);
		generator.dispose();

		buttons = new ArrayList<Button>();
		buttons.add(new Button(firstContent, new Rectangle(startingX, startingY, buttonWidth, buttonHeight)));
	}
	
	public void draw() {
		game.getBatch().begin();
		Assets.title.bind(0);
		game.getBatch().draw(Assets.title, 130, 400, 400, 60);
		for(int i = 0; i < getSize(); i++) {
			if(!getButton(i).isPressed)
				getButton(i).draw(Assets.brownButton[0]);
			else if(getButton(i).isPressed) {
				getButton(i).draw(Assets.brownButton[1]);
			}
		}
		game.getFont().draw(game.getBatch(), "Written by Caelan Anderson", 212, 50);
		game.getBatch().end();
	}

	//	Management
	public void createButton(String contents) {
		buttons.add(new Button(contents, new Rectangle(buttons.get(buttons.size()-1).bounds.x, buttons.get(buttons.size()-1).bounds.y - buttonHeight - BUTTON_SPACING, buttonWidth, buttonHeight)));
	}

	//	Object Retrieval
	public Button getButton(int i) {
		return buttons.get(i);
	}
	
	public int getSize() {
		return buttons.size();
	}
	
	//
	public class Button {
		public String contents;
		public Rectangle bounds;
		public boolean isPressed;

		Button(String contents, Rectangle bounds) {
			this.contents = contents;
			this.bounds = bounds;
		}

		public void draw(Texture tex) {
			game.getGlyphLayout().setText(font, contents);
			game.getBatch().draw(tex, bounds.x, bounds.y, bounds.width, bounds.height);
			font.draw(game.getBatch(), contents, bounds.x + (bounds.width / 2) - (game.getGlyphLayout().width / 2), bounds.y + 40);
		}
	}
}

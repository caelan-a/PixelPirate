package com.pixelpirate.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;
import com.pixelpirate.game.TreasureScreen.InputHandler;

//	@author Caelan Anderson
public class MainMenu implements Screen{
	
	//	Management
	private Main game;
	private InputProcessor InputHandler;
	private ButtonList buttonList;
	
	//	Aesthetics
	private Clouds clouds;
	private Water water;

	MainMenu(Main game) {
		this.game = game;
		
		//	Management
		InputHandler = new InputHandler();
		buttonList = new ButtonList(game, 220, 300, 200, 50, "Play"); // Creates list with inital button 
		
		//	Aesthetics
		water = new Water(game);
		clouds = new Clouds(game);
		
		//	Create buttons
		buttonList.createButton("Options");
		buttonList.createButton("Help");
		buttonList.createButton("Quit");
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(InputHandler);
		game.getFade().FadeIn(2);
	}

	@Override
	public void render(float delta) {
		update();
		draw();
		game.draw();
	}
	
	public void update() {
		game.update();
		clouds.update();
	}
	
	public void draw() {
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.getBatch().begin();
		Assets.sky.bind(0);
		game.getBatch().setShader(null);
		game.getBatch().draw(Assets.sky, 0, 120);
		game.getBatch().end();

		clouds.draw();
		water.render();
		
		buttonList.draw();
	}
	
	//	Button behaviour
	private void executeButton(int i) {
		if(i == 0) {
			game.sailingScreen.getPlayer().reset();
			game.getFade().FadeOut(game.sailingScreen, 2);
		} else if(i == 1) {
			game.setScreen(game.treasureScreen);
			dispose();
		} else if(i == 2) {
			
		} else if(i == 3) {
			Gdx.app.exit();
		}
	}
	
	//	Interface methods
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	//	Class-specific input
	public class InputHandler implements InputProcessor {
		
		
		@Override
		public boolean keyDown(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			for(int i = 0; i < buttonList.getSize(); i++) {
				if(buttonList.getButton(i).bounds.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
					buttonList.getButton(i).isPressed = true;
				} else {
					buttonList.getButton(i).isPressed = false;
				}
			}
			
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			for(int i = 0; i < buttonList.getSize(); i++) {
				if(buttonList.getButton(i).bounds.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
					executeButton(i);
				} else {
				buttonList.getButton(i).isPressed = false; // Reset button
				}
			}
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
	}
}
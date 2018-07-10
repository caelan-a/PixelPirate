package com.pixelpirate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

//	@author Caelan Anderson
public class MeleeScreen implements Screen {

	//	Management
	private Main game;	
	private InputProcessor InputHandler;
	
	MeleeScreen(Main game) {
		this.game = game;
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
	
	void update() {
		
	}
	
	void draw() {
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//	Draw Background
		game.getBatch().begin();
		Assets.treasureBg.bind(0);
		game.getBatch().setShader(null);
		game.getBatch().draw(Assets.treasureBg, 0, 120);
		game.getBatch().end();
	}

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
		// TODO Auto-generated method stub
		
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
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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

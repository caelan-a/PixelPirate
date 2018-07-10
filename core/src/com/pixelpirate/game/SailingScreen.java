package com.pixelpirate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

//@author Caelan Anderson
public class SailingScreen implements Screen {
	public final int MAX_HEALTH = 100;		 
	public final int ELEMENT_HEIGHT = 130;	 // In pixels
	
	private Main game;
	private InputProcessor InputHandler;			// Screen-specific input
	private CollisionHandler collisionHandler;		// Screen-specific collision handler
	
	private Ship 	player;
	private AI 		aiHandler; 
	private Water 	water; 
	private Clouds 	clouds;
	private HUD 	hud;			 
	private Island 	island;

	SailingScreen(Main game) {
		this.game = game;

		//	Management
		InputHandler = new InputHandler();
		collisionHandler = new CollisionHandler();
		
		//	Entities
		player 		= new Ship(game, this, collisionHandler);
		aiHandler	= new AI(game, this, collisionHandler, player);
		
		//	Aesthetics
		water 		= new Water(game);
		clouds 		= new Clouds(game);
		island 		= new Island(game, this);
		
		//	Overlay
		hud 		= new HUD(game, game.getResources());
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(InputHandler);
		game.getFade().FadeIn(10f);
	}

	@Override
	public void render(float delta) {
		update();
		draw();
		game.draw();
	}

	public void update() {
		input();
		
		if(!game.isPaused()) {
			game.update();
			island.update();
			player.update();
			aiHandler.update();
			clouds.update();
		}
	}
	
	public void restart() {
		//	Reset AI entities
		for(int i = 0; i < aiHandler.MAX_ENEMIES; i++)
			aiHandler.reset(i);
		
		game.getFade().FadeOut(game.mainMenu, 3);
	}

	public void draw() {
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//	Draw Background
		game.getBatch().begin();
		Assets.sky.bind(0);
		game.getBatch().setShader(null);
		game.getBatch().draw(Assets.sky, 0, 120);
		game.getBatch().end();

		//	Draw Objects
		island.draw();
		clouds.draw();
		player.draw();
		aiHandler.draw();
		water.render();
		hud.draw();
	}

	public void input() {
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			restart();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	//	Object retrieval
	public CollisionHandler getHandler() {
		return collisionHandler;
	}
	
	public Ship getPlayer() {
		return player;
	}
	
	//	Screen-specific touch input
	public class InputHandler implements InputProcessor {
		private final int CANON_DEADZONE = 200; // In pixels
	
		private Vector2 touchdownPos;
		private Vector2 touchupPos;
		private boolean isDragged;
		
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
			touchdownPos = new Vector2(screenX - 100, Gdx.graphics.getHeight() - screenY - 10);
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			touchupPos = new Vector2(screenX - 100, Gdx.graphics.getHeight() - screenY - 10);
			
			//	Fire cannon in section of screen only
			if(screenX > CANON_DEADZONE && player.isAlive())
				player.getCanon().fire(100, 120, touchupPos.nor().scl(10));
			
			//	Handle drag for raising and lowering sails
			if(touchdownPos.x < 200 && touchdownPos.y > touchupPos.y && isDragged == true) 
				player.lowerSails();

			if(touchdownPos.x < 200 && touchdownPos.y < touchupPos.y && isDragged == true) 
				player.raiseSails();
			
			isDragged = false;
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			isDragged = true;
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

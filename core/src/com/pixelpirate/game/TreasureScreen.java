package com.pixelpirate.game;

import org.w3c.dom.css.Rect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelpirate.game.SailingScreen.InputHandler;

//	@author Caelan Anderson
public class TreasureScreen implements Screen{
	
	//	Management
	private Main game;
	private InputProcessor InputHandler;
	
	//	Class-specifice objects
	private BitmapFont font;
	private Clouds clouds;
	
	//	Resources from treasure to award player
	private int tFood;
	private int tMoney;
	private int tPeople;
	private int tSpices;
	
	//	Button state
	private boolean isPressed;
	public Rectangle cButtonBox;
	
	TreasureScreen(Main game) {
		this.game = game;
		
		clouds = new Clouds(game);
		cButtonBox = new Rectangle(Gdx.graphics.getWidth() / 3 - 12, 100, 280, 70); // [Replace with button system]
		
		//	Text
		InputHandler = new InputHandler();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenpixel_square.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 30;
		font = generator.generateFont(parameter);
		generator.dispose();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(InputHandler);
		//game.getFade().FadeIn(2); // [Ugly transition]
	}

	@Override
	public void render(float delta) {
		draw();
		game.getFade().render();
	}
	
	public void draw() {
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.getBatch().begin();
		Assets.treasureBg.bind(0);
		game.getBatch().draw(Assets.treasureBg, 0, 0); // Background
		game.getBatch().end();
		
		//	Clouds
		clouds.update();
		clouds.draw();
		
		game.getBatch().begin();
		game.getBatch().draw(Assets.treasurePanel, 0, 0); // Panel
		
		//	Button animation
		if(!isPressed)
			game.getBatch().draw(Assets.brownButton[0], cButtonBox.x, cButtonBox.y, cButtonBox.width, cButtonBox.height);
		else
			game.getBatch().draw(Assets.brownButton[1], cButtonBox.x, cButtonBox.y, cButtonBox.width, cButtonBox.height);
	
		//	Draw amounts
		font.draw(game.getBatch(), "Continue", cButtonBox.x + 55, cButtonBox.y + 50);
		font.draw(game.getBatch(), "+" + tSpices, 135, 230);
		font.draw(game.getBatch(), "+" + tMoney + "k", 305, 250);
		font.draw(game.getBatch(), "+" + tFood, 485, 230);
		game.getBatch().end();
	
	}

	public void setTreasure(int food, int money, int spices) {
		tFood = food;
		tMoney = money;
		tSpices = spices;
	}
	
	public void Continue() {
		game.getResources().giveTreasure(tFood, tMoney, tSpices);
		game.getFade().FadeOut(game.sailingScreen, 2f);
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
	
	//	Screen-specific input
	public class InputHandler implements InputProcessor {
		
		InputHandler() {
		}
		
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
			if(cButtonBox.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
				isPressed = true;
			} else {
				isPressed = false;
			}
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			if(cButtonBox.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
				Continue();
			}
			isPressed = false;
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

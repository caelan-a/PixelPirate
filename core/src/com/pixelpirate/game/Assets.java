package com.pixelpirate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Assets {

	
	public static class ShipParts {
		public static Texture basicHull;
		public static Texture basicMast;
		public static Texture basicSails;
		public static Texture basicSailsFurled;
		public static Texture basicHold;
	}
	
	public static class Shaders {
		public static ShaderProgram water;
		public static ShaderProgram ship;
		public static ShaderProgram sails;
		public static ShaderProgram fade;
	}
	
	public static Texture sky;
	public static Texture water;
	public static Texture waterDis;
	
	public static Texture food;
	public static Texture money;
	public static Texture people;
	public static Texture spices;
	
	
	public static Texture[] clouds;
	
	public static Texture island;
	public static Texture canonball;
	
	public static Texture treasureBg;
	public static Texture treasurePanel;
	
	public static Texture[] continueButton;
	public static Texture[] brownButton;
	
	public static Texture title;

	public static Texture white;
	
	public static void loadAssets() {
		loadShaders();
		loadImages();
	}
	
	private static void loadShaders() {
		ShaderProgram.pedantic = false;
		
		Shaders.water = new ShaderProgram(Gdx.files.internal("shaders/v_water.glsl"),Gdx.files.internal("shaders/f_water.glsl"));
		System.out.println("Shader[water] status: " + Shaders.water.isCompiled() + "\n " + Shaders.water.getLog());
	
		Shaders.ship = new ShaderProgram(Gdx.files.internal("shaders/v_ship.glsl"),Gdx.files.internal("shaders/f_ship.glsl"));
		System.out.println("Shader[ship] status: " + Shaders.ship.isCompiled() + "\n " + Shaders.ship.getLog());	
	
		Shaders.sails = new ShaderProgram(Gdx.files.internal("shaders/v_sky.glsl"),Gdx.files.internal("shaders/f_sky.glsl"));
		System.out.println("Shader[sails] status: " + Shaders.sails.isCompiled() + "\n " + Shaders.sails.getLog());
		
		Shaders.fade = new ShaderProgram(Gdx.files.internal("shaders/v_fade.glsl"),Gdx.files.internal("shaders/f_fade.glsl"));
		System.out.println("Shader[fade] status: " + Shaders.fade.isCompiled() + "\n " + Shaders.fade.getLog());
	}
	
	private static void loadImages() {
		ShipParts.basicHull = new Texture(Gdx.files.internal("images/ship/basic_hull.png"));
		ShipParts.basicHold = new Texture(Gdx.files.internal("images/ship/basic_hold.png"));
		ShipParts.basicSails = new Texture(Gdx.files.internal("images/ship/basic_sails.png"));
		ShipParts.basicSailsFurled = new Texture(Gdx.files.internal("images/ship/basic_sailsfurled.png"));
		ShipParts.basicMast = new Texture(Gdx.files.internal("images/ship/basic_masts.png"));
	
		sky = new Texture(Gdx.files.internal("images/sky.png"));
		water = new Texture(Gdx.files.internal("images/water.png"));
		waterDis = new Texture(Gdx.files.internal("images/waterdisplacement.png"));
		
		clouds = new Texture[9];
		for(int i = 0; i < 9; i++) {
			clouds[i] = new Texture(Gdx.files.internal("images/clouds/cloud" + (i + 1) +".png"));
		}
		
		
		island = new Texture(Gdx.files.internal("images/hills1.png"));
		canonball = new Texture(Gdx.files.internal("images/canonball.png"));
		
		treasureBg = new Texture(Gdx.files.internal("images/treasure_bg.png"));
		treasurePanel = new Texture(Gdx.files.internal("images/treasure_panel.png"));
		
		//	Resources
		food = new Texture(Gdx.files.internal("images/icons/food.png"));
		money = new Texture(Gdx.files.internal("images/icons/money.png"));
		people = new Texture(Gdx.files.internal("images/icons/people.png"));
		spices = new Texture(Gdx.files.internal("images/icons/spices.png"));
		
		
		//	UI
		continueButton = new Texture[2];
		continueButton[0] = new Texture(Gdx.files.internal("images/continue_button.png"));
		continueButton[1] = new Texture(Gdx.files.internal("images/continue_button_pressed.png"));
		
		brownButton = new Texture[2];
		brownButton[0] = new Texture(Gdx.files.internal("images/buttonLong_brown.png"));
		brownButton[1] = new Texture(Gdx.files.internal("images/buttonLong_brown_pressed.png"));
		
		title = new Texture(Gdx.files.internal("images/title.png"));
		
		white = new Texture(Gdx.files.internal("images/white.png"));
	}
}
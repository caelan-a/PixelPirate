package com.pixelpirate.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

//@author Caelan Anderson
public class Main extends Game {
	
	//	Drawing Objects
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ShapeRenderer renderer; 	// Renderer for healthbar and collision box debugging
	private Matrix4 shaderMatrix; 

	//	Text-related objects
	private BitmapFont font;
	private GlyphLayout glyphLayout;
	
	//	Scenes
	public MainMenu mainMenu;
	public SailingScreen sailingScreen;
	public TreasureScreen treasureScreen;
	public MeleeScreen meleeScreen;
	
	//	Game
	private Resources resources;	// Player resources (eg Food, Money, Spices, People)
	private Fade fade;				// Handles fade in/out and subsequent screen changes
	
	//	Graphics assistance
	private float delta;		// Standard incremener from game start
	private float xDelta; 		// Independent time incrementer for x-axis moving entities, eg ship[sailing]/clouds
	private float yDelta; 		// Independent time incrementer for y-axis moving entities, eg ship[bobbing]/sails
	private float speed = 1f;	// Factor by which entities move to create moving effect based on player speed

	//	Game State
	private boolean isPaused = false;		
	private GameState gameState;			
	
	public enum GameState { 
		Running, 
		Treasure,
	}

	@Override
	public void create () {		
		gameState = GameState.Running;
		
		//	Administration
		Assets.loadAssets();
		renderer = new ShapeRenderer();
		batch = new SpriteBatch(); 
		camera = new OrthographicCamera(); 
		camera.setToOrtho(false, 480, 640);
		shaderMatrix = new Matrix4();
		
		//	Game
		fade = new Fade(this);
		resources = new Resources();

		//	Text
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenpixel_square.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		font = generator.generateFont(parameter);
		generator.dispose();
		glyphLayout = new GlyphLayout();
		
		//	Screens
		mainMenu = new MainMenu(this);
		sailingScreen = new SailingScreen(this);
		treasureScreen = new TreasureScreen(this);
		meleeScreen = new MeleeScreen(this);
	
		//	Set starting menu
		setScreen(mainMenu);
	}

	public void update() {
		camera.update();
		delta += Gdx.graphics.getDeltaTime();
		xDelta += 2 * speed * Gdx.graphics.getDeltaTime();
		yDelta += 2 * speed * Gdx.graphics.getDeltaTime();
	}
	
	public void draw() {
		fade.render(); // Used by any class to fade
	}

	// Object Retrieval
	public GameState getGameState() {
		return gameState;
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public float getXdelta() {
		return xDelta;
	}

	public float getYdelta() {
		return yDelta;
	}

	public float getDelta() {
		return delta;
	}

	public void setSpeed(float x) {
		speed = x;
	}

	public float getSpeed() {
		return speed;
	}

	public ShapeRenderer getRenderer() {
		return renderer;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public Matrix4 getCameraProjection() {
		return camera.combined;
	}

	public Matrix4 getShaderMatrix() {
		return shaderMatrix;
	}

	public Resources getResources() {
		return resources;
	}
	
	public BitmapFont getFont() {
		return font;
	}
	
	public GlyphLayout getGlyphLayout() {
		return glyphLayout;
	}
	
	public Fade getFade() {
		return fade;
	}
}

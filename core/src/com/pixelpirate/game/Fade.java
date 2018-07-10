package com.pixelpirate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

//	@author Caelan Anderson
public class Fade {
	/*	Concept: 
	 * White texture is drawn over all other textures, increasing alpha from 0 to max or decreasing from max to zero
	 * to produce fade effect. Sets time uniform in fade shader based on in-class incrementer (timeDelta)
	 */
	
	//	Management
	private Main game;
	
	//	Triggers
	public boolean doFade = false;		//	true on call to fade 
	private boolean isComplete = false;	//	true if fadeDelta >= maxTime
	public Screen screenToChange;
	
	private float fadeDelta;       //	Incrementer to check against maxTime
	private int fadeDirection = 1; //	FadeOut = 1, FadeIn = -1
	private float maxTime = 2f;    // 	Time taken to fade (adjusts speed accordingly)
	
	Fade(Main game) {
		this.game = game;
	}
	
	public void render() {
		if(doFade == true) {
			update();
			draw();
		}
	}
	
	private void update() {
		//	Increment delta in given direction based on maxTime to scale speed
		if(fadeDirection == 1)
			fadeDelta += Gdx.graphics.getDeltaTime() + 0.08/maxTime; 
		else if(fadeDirection == -1)
			fadeDelta -= Gdx.graphics.getDeltaTime() + 0.08/maxTime;

		//	Check if maxTime has been reached
		if(fadeDirection == 1 && fadeDelta > 1) 
			isComplete = true;
		else if(fadeDirection == -1 && fadeDelta <= 0)
			isComplete = true;
		
		//	Execute scene jump (if required), resetting variables and triggers
		if(isComplete)
			execute();
			
	}
	
	//	To be drawn after all other objects
	public void draw() {
		Assets.Shaders.fade.begin();
		Assets.Shaders.fade.setUniformMatrix("u_projTrans", game.getShaderMatrix());
		Assets.Shaders.fade.setUniformf("u_time", fadeDelta);
		Assets.Shaders.fade.end();
		
		game.getBatch().begin();
		game.getBatch().setShader(Assets.Shaders.fade);
		Assets.white.bind(0);
		game.getBatch().draw(Assets.white, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.getBatch().end();
	}
	
	//	Inhibits fade until next call, resetting active variables and triggers
	public void reset() {
		doFade = false;
		isComplete = false;
	}
	
	//	Increase alpha to 1
	public void FadeOut(Screen screen, float maxTime) {
		doFade = true;
		fadeDelta = 0;
		fadeDirection = 1;
		screenToChange = screen;
		this.maxTime = maxTime;
	}
	
	//	Decrease alpha to 0
	public void FadeIn(float maxTime) {
		doFade = true;
		fadeDelta = 1;
		fadeDirection = -1;
		this.maxTime = maxTime;
	}
	
	//	Called upon fade completion
	private void execute() {
		reset(); 
		
		//	For fade into different scene
		if(fadeDirection == 1) {
			game.setScreen(screenToChange);
			screenToChange.dispose();
		}
	}
}

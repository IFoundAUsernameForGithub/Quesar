package com.pet.quesar.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Colour;
import com.pet.quesar.ui.TextImage;

public class Options extends State{
	
	private TextImage Easy;
	private TextImage Medium;
	private TextImage Hard;
	private TextImage Unfair;
	private TextImage Back;
	private Colour red;
	private Colour green;
	
	private static int selected;
	
	protected Options(GSM gsm) {
		super(gsm);
		
		Easy = new TextImage(
				"easy",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + (Quesar.SIZE)*2, 
				(Quesar.SIZE/2)+1);
		
		Medium = new TextImage(
				"medium",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + Quesar.SIZE, 
				(Quesar.SIZE/2)+1);
		
		
		Hard = new TextImage(
				"hard",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 , 
				(Quesar.SIZE/2)+1);
		
		Unfair = new TextImage(
				"unfair",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - Quesar.SIZE, 
				(Quesar.SIZE/2)+1);
		
		Back = new TextImage(
				"back",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - (Quesar.SIZE*2), 
				(Quesar.SIZE/2)+1);
		
		if(Options.getSelected() == 0){
			Options.setSelected(1);
		}
		else{
			selected = Options.getSelected();
		}
		
		red = new Colour("red");
		green = new Colour("green");
		
	}
	
	public static int getSelected(){
		return selected;
	}
	public static void setSelected(int Selected){
		selected = Selected;
	}
	
	
	public void handleInput() {
		
		if(Gdx.input.justTouched()) {
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();

			gsm.getViewport().unproject(mouse);
			
			if(Easy.contains(mouse.x, mouse.y)) {
				LevelState.setMaxRects(5);
				LevelState.setAddTimer(1);
				LevelState.setScoreMultiplier(1);
				Options.setSelected(1);
				gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
			}
			else if(Medium.contains(mouse.x, mouse.y)){
				LevelState.setMaxRects(5);
				LevelState.setAddTimer(0.9f);
				LevelState.setScoreMultiplier(2);
				Options.setSelected(2);
				gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
			}
			else if(Hard.contains(mouse.x, mouse.y)){
				LevelState.setMaxRects(4);
				LevelState.setAddTimer(0.8f);
				LevelState.setScoreMultiplier(3);
				Options.setSelected(3);
				gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
			}
			else if(Unfair.contains(mouse.x, mouse.y)){
				LevelState.setMaxRects(4);
				LevelState.setAddTimer(0.7f);
				LevelState.setScoreMultiplier(4);
				Options.setSelected(4);
				gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
			}
			else if(Back.contains(mouse.x, mouse.y)){
				gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
			}
		}
		if(PlayState.getbackbutton()){
			gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
		}
	}

	
	public void update(float dt) {
		handleInput();
	}

	
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		if(selected == 1){
			sb.setColor(green.getColor());
			Easy.render(sb);
			sb.setColor(1, 1, 1, 1);
		}
		else {
			Easy.render(sb);
		}
		if(selected == 2){
			sb.setColor(green.getColor());
			Medium.render(sb);
			sb.setColor(1, 1, 1, 1);
		}
		else{
			Medium.render(sb);
		}
		if(selected == 3){
			sb.setColor(green.getColor());
			Hard.render(sb);
			sb.setColor(1, 1, 1, 1);
		}
		else{
			Hard.render(sb);
		}
		if(selected == 4){
			sb.setColor(green.getColor());
			Unfair.render(sb);
			sb.setColor(1, 1, 1, 1);
		}
		else{
			Unfair.render(sb);
		}
		sb.setColor(red.getColor());
		Back.render(sb);
		sb.setColor(1, 1, 1, 1);
		sb.end();
	}

}

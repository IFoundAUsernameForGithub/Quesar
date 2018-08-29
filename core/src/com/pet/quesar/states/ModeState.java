package com.pet.quesar.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Colour;
import com.pet.quesar.ui.TextImage;

public class ModeState extends State{

	private TextImage tutorial;
	private TextImage normal;
	private TextImage endless;
	private TextImage Back;
	private Colour red;
	
	public ModeState(GSM gsm){
		super(gsm);
		
		tutorial = new TextImage(
				"tutorial",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + (Quesar.SIZE*2),
				(Quesar.SIZE/2)+1);

		normal = new TextImage(
				"normal",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + (Quesar.SIZE*2)/3,
				(Quesar.SIZE/2)+1);
		
		endless = new TextImage(
				"endless",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - (Quesar.SIZE*2)/3,
				(Quesar.SIZE/2)+1);
		
		Back = new TextImage(
				"back",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - (Quesar.SIZE*2), 
				(Quesar.SIZE/2)+1);
		
		red = new Colour("red");
		
	}
	
	public void handleInput(){
		if(Gdx.input.justTouched()) {
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();
			
			gsm.getViewport().unproject(mouse);
			
			if(tutorial.contains(mouse.x, mouse.y)){
				gsm.set(new TransitionState(gsm, this, new TutorialState(gsm), Type.BLACK_FADE));
			}
			else if(normal.contains(mouse.x, mouse.y)){
				gsm.set(new TransitionState(gsm, this, new LevelState(gsm, 1, 0, false), Type.BLACK_FADE));
			}
			else if(endless.contains(mouse.x, mouse.y)){
				LevelState.setLifes(3);
				gsm.set(new TransitionState(gsm, this, new LevelState(gsm, 1, 0, true), Type.BLACK_FADE));
			}
			else if(Back.contains(mouse.x, mouse.y)){
				gsm.set(new TransitionState(gsm, this, new MenuState(gsm), Type.BLACK_FADE));
			}
		}
		if(PlayState.getbackbutton()){
			gsm.set(new TransitionState(gsm, this, new MenuState(gsm), Type.BLACK_FADE));
		}
		
	}
	public void update(float dt){ handleInput(); }
	public void render(SpriteBatch sb){
		
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		
		tutorial.render(sb);
		endless.render(sb);
		normal.render(sb);
		sb.setColor(red.getColor());
		Back.render(sb);
		sb.setColor(1, 1, 1, 1);
		sb.end();
		
	}
	
}

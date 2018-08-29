package com.pet.quesar.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Colour;
import com.pet.quesar.ui.TextImage;

public class PauseState extends State{

	private int Tempscore;
	
	private TextImage Resume;
	private TextImage Back;
	
	private Colour red;
	
	public PauseState(GSM gsm, boolean IsMoving, int Tempscore){
		
		super(gsm);
		
		this.Tempscore = Tempscore;
		
		Resume = new TextImage(
				"resume",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + Quesar.SIZE, 
				((Quesar.SIZE/3)*2)+1);
		
		Back = new TextImage(
				"menu",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - Quesar.SIZE, 
				((Quesar.SIZE/3)*2)+1);
		
		red = new Colour("red");
		
	}
	
	public void handleInput(){
		if(Gdx.input.justTouched()) {
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();
			
			gsm.getViewport().unproject(mouse);
			
			if(Resume.contains(mouse.x, mouse.y)){
				gsm.set(new TransitionState(gsm, this, new PlayState(gsm,Tempscore), Type.COUNTDOWN));
			}
			else if(Back.contains(mouse.x, mouse.y)){
				LevelState.setLevel(1);
				PlayState.setRects(null);
				LevelState.setLifes(0);
				LevelState.setGlobalTimer(0);
				gsm.set(new TransitionState(gsm, this, new MenuState(gsm), Type.BLACK_FADE));
			}
		}
		if(PlayState.getbackbutton()){
			gsm.set(new TransitionState(gsm, this, new PlayState(gsm,Tempscore), Type.COUNTDOWN));
		}
	}
	public void update(float dt){handleInput();}
	public void render(SpriteBatch sb){
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		Resume.render(sb);
		sb.setColor(red.getColor());
		Back.render(sb);
		sb.setColor(1, 1, 1, 1);
		sb.end();
	}
	
}


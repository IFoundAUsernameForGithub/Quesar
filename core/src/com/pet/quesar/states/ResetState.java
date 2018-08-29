package com.pet.quesar.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Colour;
import com.pet.quesar.ui.TextImage;

public class ResetState extends State{

	private TextImage youwill;
	private TextImage loseall;
	private TextImage yourdata;
	private TextImage yes;
	private TextImage no;
	private TextImage back;
	private Colour red;
	private Colour orange;
	private Colour green;
	
	public ResetState (GSM gsm){
		super(gsm);
		
		youwill = new TextImage(
				"you will",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + (Quesar.SIZE*3), 
				(Quesar.SIZE/2)+1);

		loseall = new TextImage(
				"lose all",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + (Quesar.SIZE*2), 
				(Quesar.SIZE/2)+1);

		yourdata = new TextImage(
				"your data",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + (Quesar.SIZE), 
				(Quesar.SIZE/2)+1);
		
		yes = new TextImage(
				"yes",
				Quesar.WIDTH / 4 ,
				Quesar.HEIGHT / 2 - (Quesar.SIZE), 
				(Quesar.SIZE/2)+1);
		
		no = new TextImage(
				"no",
				(Quesar.WIDTH / 4)*3,
				Quesar.HEIGHT / 2 - (Quesar.SIZE), 
				(Quesar.SIZE/2)+1);
		
		back = new TextImage(
				"back",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - (Quesar.SIZE*3), 
				(Quesar.SIZE/2)+1);
		
		red = new Colour("red");
		green = new Colour("green");
		orange = new Colour("orange");
		
	}
	
	public void handleInput(){
		if(Gdx.input.justTouched()) {
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();
			
			gsm.getViewport().unproject(mouse);
			
			if(yes.contains(mouse.x, mouse.y)){
				Quesar.ClearPreference();
				
				gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
			}
			else if(no.contains(mouse.x, mouse.y)){
				gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
			}
			else if(back.contains(mouse.x, mouse.y)){
				gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
			}
		}
		if(PlayState.getbackbutton()){
			gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
		}
	}
	public void update(float dt){ handleInput(); }
	public void render(SpriteBatch sb){
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		youwill.render(sb);
		loseall.render(sb);
		yourdata.render(sb);
		sb.setColor(green.getColor());
		yes.render(sb);
		sb.setColor(orange.getColor());
		no.render(sb);
		sb.setColor(red.getColor());
		back.render(sb);
		sb.setColor(1, 1, 1, 1);
		sb.end();
		
	}
}

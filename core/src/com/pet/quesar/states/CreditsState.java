package com.pet.quesar.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Colour;
import com.pet.quesar.ui.Graphic;
import com.pet.quesar.ui.TextImage;
public class CreditsState extends State{

	private Graphic Icon;
	private TextImage developedby;
	private TextImage AlessandroUlleri;
	private TextImage LucaUlleri;
	private TextImage back;
	private Colour red;
	
	public CreditsState(GSM gsm){
		super(gsm);

		Icon = new Graphic(
				Quesar.res.getAtlas("pack").findRegion("Icon"),
				Quesar.WIDTH/2,
				Quesar.HEIGHT/2 + Quesar.SIZE*2 + Quesar.SIZE/2,
				0.2f,
				0.2f,
				0,
				false);
		
		developedby = new TextImage(
				"developed by",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + (Quesar.SIZE),
				(Quesar.SIZE/4));
		
		AlessandroUlleri = new TextImage(
				"alessandro ulleri",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 ,
				(Quesar.SIZE/4));
		
		LucaUlleri = new TextImage(
				"luca ulleri",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - (Quesar.SIZE), 
				(Quesar.SIZE/4));
		
		back = new TextImage(
				"back",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - (Quesar.SIZE*2) - Quesar.SIZE/2, 
				(Quesar.SIZE/2)+1);
		
		red = new Colour("red");
		
	}
	
	public void handleInput(){
		if(Gdx.input.justTouched()){
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();
			
			gsm.getViewport().unproject(mouse);
			
			if(back.contains(mouse.x, mouse.y)){
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
		Icon.render(sb);
		AlessandroUlleri.render(sb);
		developedby.render(sb);
		LucaUlleri.render(sb);
		sb.setColor(red.getColor());
		back.render(sb);
		sb.setColor(1, 1, 1, 1);
		sb.end();
		
	}
	
}

package com.pet.quesar.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Graphic;
import com.pet.quesar.ui.TextImage;

public class StartState extends State{
	
	private Graphic Icon;
	private TextureRegion BackIcon;	
	private TextureRegion DoubleBackIcon;	
	private TextImage title;
	private TextImage TapTheCube;
	private TextImage ToStart;
	
	private float Width = 0.7f;
	private float DoubleWidth = 0.7f;
	private float Height = 0.7f;
	private float DoubleHeight = 0.7f;
	private float alpha = 1;
	private float Doublealpha = 1;
	private float timer = 0;
	private float doubletimer = 0;
	private float x;
	private float y;
	private float Tempx;
	private float DoubleTempx;
	private float Tempy;
	private float DoubleTempy;
	
	public StartState(GSM gsm){
		super(gsm);
		
		Icon = new Graphic(
				Quesar.res.getAtlas("pack").findRegion("Icon"),
				Quesar.WIDTH/2,
				Quesar.HEIGHT/2,
				0.7f,
				0.7f,
				0,
				false);
		
		BackIcon = Quesar.res.getAtlas("pack").findRegion("Icon");
		
		DoubleBackIcon = Quesar.res.getAtlas("pack").findRegion("Icon");
		
		title = new TextImage(
				Quesar.TITLE,
				Quesar.WIDTH/2,
				Quesar.HEIGHT/2 + Quesar.SIZE*3 - Quesar.SIZE/8,
				((Quesar.SIZE/3)*2)+1);
		
		TapTheCube = new TextImage(
				"tap the cube",
				Quesar.WIDTH/2,
				Quesar.HEIGHT/2 - Quesar.SIZE*2 - Quesar.SIZE*2/3,
				Quesar.SIZE/4);
		
		ToStart = new TextImage(
				"to start",
				Quesar.WIDTH/2,
				Quesar.HEIGHT/2 - Quesar.SIZE*2 - Quesar.SIZE,
				Quesar.SIZE/4);
		
	
		x = ((Quesar.WIDTH/2)-(BackIcon.getRegionWidth()*(Width)/2));
		y = ((Quesar.HEIGHT/2)-(BackIcon.getRegionHeight()*(Height)/2));
		Tempx = DoubleTempx = x;
		Tempy = DoubleTempy = y;
		
	}
	
	public void handleInput(){
		if(Gdx.input.justTouched()){
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();
			
			gsm.getViewport().unproject(mouse);
			
			if(Icon.contains(mouse.x, mouse.y)){
				gsm.set(new TransitionState(gsm, this, new MenuState(gsm), Type.BLACK_FADE));
			}
		}
	}
	public void update(float dt){
		handleInput();
		timer += dt;
		
		if(timer < 2){
			alpha = 1 - timer / 2;
			Tempx = x - x*(timer*0.62f);
			Tempy = y - y*(timer/(5.80f));
			Width = 0.7f + (timer / 2)*0.3f;
			Height = 0.7f + (timer / 2)*0.3f;
		}
		else {
			Tempx = x;
			Tempy = y;
			Width = 0.7f;
			Height = 0.7f;
			timer = 0;
		}
		
		doubletimer += dt;
		
		if(doubletimer < 1){
			Doublealpha = 1 - doubletimer / 1;
			DoubleTempx = x - x*(doubletimer*0.41f);
			DoubleTempy = y - y*(doubletimer/9);
			DoubleWidth = 0.7f + (doubletimer / 2)*0.2f;
			DoubleHeight = 0.7f + (doubletimer / 2)*0.2f;
		}
		else if(doubletimer < 2){
			DoubleTempx = x;
			DoubleTempy = y;
			DoubleWidth = 0.7f;
			DoubleHeight = 0.7f;
		}
		else {
			doubletimer = 0;
		}

	}
	public void render(SpriteBatch sb){
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		
		sb.setColor(1,1,1,Doublealpha);
		sb.draw(DoubleBackIcon, DoubleTempx, DoubleTempy,DoubleBackIcon.getRegionWidth()*DoubleWidth, DoubleBackIcon.getRegionHeight()*DoubleHeight);
		sb.setColor(1,1,1,1);
		Icon.render(sb);
		sb.setColor(1,1,1,alpha);
		sb.draw(BackIcon, Tempx, Tempy,BackIcon.getRegionWidth()*Width, BackIcon.getRegionHeight()*Height);
		ToStart.render(sb);
		TapTheCube.render(sb);
		
		sb.setColor(1,1,1,1);
		
		title.render(sb);
		
		
		
		sb.end();
	}

}

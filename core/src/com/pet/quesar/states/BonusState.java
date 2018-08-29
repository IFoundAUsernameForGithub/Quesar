package com.pet.quesar.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Graphic;
import com.pet.quesar.ui.Score;
import com.pet.quesar.ui.TextImage;

public class BonusState extends State{

	private TextImage BonusStage;
	
	private Score score;
	private Score Countdown;
	
	private int Tempscore;
	
	private ArrayList<Graphic> BonusRects;
	private int Width;
	private int Height;
	private int MaxWidth = 20*2;
	private int MaxHeight = 20*2;
	private int Size = 20*2;
	private int MaxBonusRects = 25;
	
	private final int MAX_FINGERS = 2;
	
	private float timer;
	private float Addtimer;
	private float PoppingTimer;
	
	private Sound pop;
	
	public BonusState(GSM gsm,int Tempscore){
		super(gsm);
		
		BonusRects = new ArrayList<Graphic>();
		
		BonusStage = new TextImage(
				"bonus stage",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT - Quesar.SIZE + (int)(Quesar.SIZE/1.4),
				(Quesar.SIZE/3)+1);
		
		if(LevelState.getIsEndless()){
			this.Tempscore = Tempscore;
		}
		else{
			this.Tempscore = (int)(LevelState.getGlobalTimer()*10);
		}
		
		score = new Score(Quesar.WIDTH / 2 - Quesar.SIZE, Quesar.HEIGHT - Quesar.SIZE, Tempscore, (Quesar.SIZE/2)+1);
		score.incrementScore(Tempscore);
		
		timer=5;
		Countdown = new Score((Quesar.WIDTH*5) / 6 , Quesar.HEIGHT - Quesar.SIZE, (int)(timer*10), (Quesar.SIZE/2)+1);
		Countdown.incrementScore(Tempscore);
		
		if(PlayState.getSoundOn()){
			pop = Gdx.audio.newSound(Gdx.files.internal("pop.wav"));
		}
		
	}
	
	public void AddRect(float dt) {
		Addtimer += dt;
		if(Addtimer >= 0.1){		
			if(BonusRects.size() < MaxBonusRects){
				BonusRects.add(new Graphic(
						Quesar.res.getAtlas("pack").findRegion("Yellow"),
						MathUtils.random(Size,Quesar.WIDTH - Size),
						MathUtils.random(Size,Quesar.HEIGHT - Quesar.SIZE*2 - Size),
						Width,
						Height,
						0,
						false));
			}
			Addtimer=0;
		}
		
	}
	
	public void PoppingRect (float dt){
		PoppingTimer += dt;
		if(PoppingTimer > 0.2){
			for(int k = 0; k < BonusRects.size(); k++){
				if(BonusRects.get(k).getWidth() < MaxWidth && BonusRects.get(k).getHeight() < MaxHeight ){
					BonusRects.get(k).setWidth(BonusRects.get(k).getWidth() + MaxWidth/5);
					BonusRects.get(k).setHeight(BonusRects.get(k).getHeight() + MaxHeight/5);
				}
				if(BonusRects.get(k).getWidth() >= MaxWidth && BonusRects.get(k).getHeight() >= MaxHeight ){
					BonusRects.get(k).setWidth(MaxWidth);
					BonusRects.get(k).setHeight(MaxHeight);
				}
			}
		}
	}
	
	
	public void handleInput(){
		for(int i = 0; i < MAX_FINGERS; i++) {
			if(Gdx.input.isTouched(i)){
				mouse.x = Gdx.input.getX(i);
				mouse.y = Gdx.input.getY(i);
				
				gsm.getViewport().unproject(mouse);
				
				if(BonusRects.size() < MaxBonusRects || !LevelState.getIsEndless()){
					for(int k = 0; k < BonusRects.size(); k++){
						if(BonusRects.get(k).contains(mouse.x, mouse.y)){
							if(LevelState.getIsEndless()){
								Tempscore+=(int)(1*LevelState.getScoreMultipier());
								score.incrementScore(Tempscore);
							}
							else {
								Tempscore-=(int)(1*LevelState.getScoreMultipier());
								if(Tempscore <=0){Tempscore=0;}
								score.incrementScore(Tempscore);
							}
							BonusRects.remove(k);
							if(PlayState.getSoundOn()){
								pop.play();
							}
						}
					}
				}
			}
		}
	}
	public void update(float dt){
		
		PoppingRect(dt);
		
		AddRect(dt);
		
		handleInput();
		
		timer -= dt;
		Countdown.incrementScore((int)(timer*10));
		Countdown.update(dt);
		
		score.update(dt);
		
		if (timer <= 0){
			if(!LevelState.getIsEndless()){LevelState.setGlobalTimer(((float)Tempscore)/10);}
			PlayState.setRects(null);
			if(LevelState.getLevel() > 5){
				if(!LevelState.getIsEndless()){LevelState.setGlobalTimer(((float)Tempscore)/10);}
				PlayState.setRects(null);
				gsm.set(new TransitionState(gsm, this, new ScoreState(gsm, (int)(Tempscore)), Type.BLACK_FADE));
			}
			else{
			gsm.set(new TransitionState(gsm, this, new LevelState(gsm, LevelState.getLevel(), Tempscore, LevelState.getIsEndless()), Type.BLACK_FADE));
			}
		}
	}
	public void render(SpriteBatch sb){
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		BonusStage.render(sb);
		score.render(sb);
		Countdown.render(sb);

		for(int i = 0; i < BonusRects.size(); i++){
			BonusRects.get(i).render(sb);
		}
		
		sb.end();
	}
}

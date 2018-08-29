package com.pet.quesar.states;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Graphic;
import com.pet.quesar.ui.TextImage;

public class LevelState extends State{
	
	private static int Lifes;
	
	private int Score;
	private TextImage RoundText;
	private TextImage ScoreText;
	private TextImage Text1;
	private TextImage Text2;
	private ArrayList<Graphic> Hearts;
	private float TransitionTimer;
	
	private static int RectsPopped=0;
	private static boolean isEndless=false;
	private static int Level=1;
	private static float GlobalTimer=0;
	
	private static float ScoreMultiplier=1;
	private static int maxRects=5;
	private static float AddTimer=1;
	

	protected LevelState(GSM gsm, int Round, int Score, boolean isEndless) {
		super(gsm);
		this.Score = Score;
		LevelState.setIsEndless(isEndless);
		
		RoundText = new TextImage(
				"round" + " " + Integer.toString(Round),
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + Quesar.SIZE*2, 
				(Quesar.SIZE/2)+1);
		
		Text1 = new TextImage(
				"current",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + Quesar.SIZE, 
				(Quesar.SIZE/2)+1);
		
		if(LevelState.getIsEndless() == true){
			Text2 = new TextImage(
					"score",
					Quesar.WIDTH / 2,
					Quesar.HEIGHT / 2 + Quesar.SIZE/5, 
					(Quesar.SIZE/2)+1);
		}
		else{
			Text2 = new TextImage(
					"time",
					Quesar.WIDTH / 2,
					Quesar.HEIGHT / 2 + Quesar.SIZE/5, 
					(Quesar.SIZE/2)+1);
		}
		
		ScoreText = new TextImage(
				Integer.toString(Score),
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - Quesar.SIZE, 
				Quesar.SIZE);
		if(LevelState.isEndless){
			Hearts = new ArrayList<Graphic>();
			for(int i = 0; i < 3; i++){
				if(i+1 <= LevelState.Lifes){
					Hearts.add(new Graphic(
							Quesar.res.getAtlas("pack").findRegion("Hearth"),
							((Quesar.WIDTH / 2)/3)*(i+2),
							Quesar.HEIGHT / 2 - Quesar.SIZE*2,
							1.5f,
							1.5f,
							0,
							false));
				}
				else{
					Hearts.add(new Graphic(
							Quesar.res.getAtlas("pack").findRegion("BlackHearth"),
							((Quesar.WIDTH / 2)/3)*(i+2),
							Quesar.HEIGHT / 2 - Quesar.SIZE*2,
							1.5f,
							1.5f,
							5,
							false));
				}
			}
		}
		
	}
	
	public static void setRectsPopped(int value){RectsPopped = value;}
	public static int getRectsPopped(){return RectsPopped;}
	
	public static void setLifes(int value){
		if(value != 0){
			LevelState.Lifes = Lifes + value;
		}
		else{
			LevelState.Lifes = 0;
		}
	}
	public static int getLifes(){return LevelState.Lifes;}
	
	public static void setLevel(int level){Level = level;}
	public static int getLevel(){return Level;}
	
	public static void setGlobalTimer(float Timer){LevelState.GlobalTimer = Timer;}
	public static float getGlobalTimer(){return GlobalTimer;}
	
	public static void setIsEndless(boolean IsNormal){LevelState.isEndless = IsNormal;}
	public static boolean getIsEndless(){return isEndless;}
	
	public static void setMaxRects(int MaxRects){LevelState.maxRects = MaxRects;}
	public static int getMaxRects(){return maxRects;}
	
	public static void setAddTimer(float AddTimer){LevelState.AddTimer = AddTimer;}
	public static float getAddTimer(){return AddTimer;}
	
	public static void setScoreMultiplier(float ScoreMultiplier){LevelState.ScoreMultiplier = ScoreMultiplier;}
	public static float getScoreMultipier(){return ScoreMultiplier;}

	
	public void handleInput() {}

	
	public void update(float dt) {
		TransitionTimer += dt;
		if(TransitionTimer > 2){
			PlayState.setIsPlaying(true);
			PlayState.setRects(new ArrayList<Graphic>());
			gsm.set(new TransitionState(gsm, this, new PlayState(gsm,Score),Type.POP_RECT));
		}
		if(LevelState.Lifes == 0 && TransitionTimer > 1 && isEndless){
			gsm.set(new TransitionState(gsm, this, new ScoreState(gsm,Score), Type.BLACK_FADE));
		}
		
	}

	
	public void render(SpriteBatch sb) {
		
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		ScoreText.render(sb);
		RoundText.render(sb);
		Text1.render(sb);
		Text2.render(sb);
		
		if(LevelState.isEndless){
			for(int i = 0; i < Hearts.size();i++){
				Hearts.get(i).render(sb);
			}
		}
		sb.end();
		
		
	}

	
	
}

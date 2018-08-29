package com.pet.quesar.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Colour;
import com.pet.quesar.ui.Graphic;
import com.pet.quesar.ui.Score;

public class PlayState extends State{

	private Graphic Pause;
	
	private int Width = 0;
	private int Height = 0;
	private int MaxWidth = 20*2;
	private int MaxHeight = 20*2;
	
	private static boolean backbutton=false;
	
	private boolean remove = false;
	private boolean Popp = false;
	
	private int Tempscore;
	private Score score;
	private float ScoreMultiplier;
	
	private final int MAX_FINGERS = 2;
	
	private float AddTimer = 0;
	private float MoveTimer = 0;
	private float GlobalTimer;
	private float PlayTimer;
	
	private static int RectangleOnScreen = 0;
	
	private int isRed;
	private int probRedRects;
	private int RedValue = 0;
	private int MaxRedOnScreen = 2;
	private int RedNumber1;
	private int RedNumber2;
	private float RedTimer1 = 0;
	private float RedTimer2 = 0;
	
	private boolean StopTime = false;
	private boolean MoreTime = false;
	private float StopTimer = 0;
	
	private int level;
	private int speed;
	
	private int RectsPopped;
	private int MaxRects;
	private int MaxRectsOnScreen;
	private float AddSeconds;
	private boolean IsMoving;
	
	private boolean isEndless;
	
	private int[] Randoms;
	private static ArrayList<Graphic> Rects;
	
	private Sound pop;
	private static boolean SoundOn = true;
	private static boolean isPlaying = true;
	
	private Colour red;
	private Colour green;
	
	public PlayState(GSM gsm, int Tempscore) {
		super(gsm);
		
		Quesar.showADS(false);
		
		Pause = new Graphic(
				Quesar.res.getAtlas("pack").findRegion("PauseButton"),
				Quesar.WIDTH - Quesar.SIZE/3,
				Quesar.HEIGHT - Quesar.SIZE/3,
				1, 
				1, 
				0,
				false);
		
		MaxRectsOnScreen = LevelState.getMaxRects();
		
		level = LevelState.getLevel();
		AddSeconds = LevelState.getAddTimer()/((float)Math.sqrt(level));
		
		Rects = PlayState.getRects();
		
		Randoms = new int[MaxRectsOnScreen*2];
		
		backbutton=false;
		
		ScoreMultiplier = LevelState.getScoreMultipier();
		GlobalTimer = LevelState.getGlobalTimer();
		isEndless = LevelState.getIsEndless();
		
		this.Tempscore = Tempscore;
		if(isEndless){
			score = new Score(Quesar.WIDTH / 2, Quesar.HEIGHT - Quesar.SIZE, Tempscore, Quesar.SIZE);
			score.setText(Integer.toString(Tempscore));
			score.incrementScore(Tempscore);
		}
		else{
			score = new Score(Quesar.WIDTH / 2, Quesar.HEIGHT - Quesar.SIZE, (int)(GlobalTimer*10), Quesar.SIZE);
			score.setText(Integer.toString((int)(GlobalTimer*10)));
			score.incrementScore((int)(GlobalTimer*10));
		}
		
		IsMoving = (level >= 4); 
		RectsPopped=0;
		MaxRects = level*5;
		probRedRects = 3;
		
		if (level >= 4) speed = (level)/2; else speed = 0;
		if(speed >= 5) speed = 5;
		
		if(Rects.size() > 0){
			for(int i = 0; i < Rects.size(); i++){
				if(Rects.get(i).getIsRed()!=0){
					RedValue++;
				}
			}
		}
		
		if(IsMoving == true)
		{
			for (int i = 0; i < Randoms.length; i++){
				Randoms[i] = MathUtils.random(-speed,speed);
			}
		}
		if(PlayState.getSoundOn()){
			SoundOn = true;
			pop = Gdx.audio.newSound(Gdx.files.internal("pop.wav"));
		}
		else{
			SoundOn = false;
		}
		
		red = new Colour("red");
		green = new Colour("green");
		
	}

	public static boolean getSoundOn(){return SoundOn;}
	public static void setSoundOn(boolean Sound){SoundOn = Sound;}
	
	public static ArrayList<Graphic> getRects(){return Rects;}
	public static void setRects(ArrayList<Graphic> Rect){Rects = Rect;}
	
	public static boolean getIsPlaying(){return isPlaying;}
	public static void setIsPlaying(boolean IsPlaying){PlayState.isPlaying = IsPlaying;}
	
	public static int getRectangleOnScreen(){return RectangleOnScreen;}
	public static void setRectangleOnScreen(int value){PlayState.RectangleOnScreen = value;}
	
	public static boolean getbackbutton(){return backbutton;}
	public static void setbackbutton(boolean a){backbutton = a;}
	
	public void AddRect(float dt) {
		AddTimer += dt;
		if(AddTimer >= AddSeconds){
			if(Rects.size() < MaxRectsOnScreen){
				
				if(RedValue < MaxRedOnScreen){
					if(MathUtils.random(0,probRedRects)==1){isRed = 1;RedValue++;}
					else if(MathUtils.random(0,probRedRects)==2){isRed = -1;RedValue++;}
					else{isRed = 0;}
					if(RedTimer1 == -1 && isRed != 0){
						RedTimer1 = 0;
					}
					else if(RedTimer2 == -1 && isRed != 0){
						isRed*=2;
						RedTimer2 = 0;
					}
				}
				else{
					isRed = 0;
				}
				
				Rects.add(new Graphic(
						Quesar.res.getAtlas("pack").findRegion("Yellow"),
						MathUtils.random(MaxWidth,Quesar.WIDTH - MaxWidth),
						MathUtils.random(MaxHeight,Quesar.HEIGHT - Quesar.SIZE*2 - MaxHeight),
						Width,
						Height,
						isRed,
						false));
				
				}
			AddTimer=0;
			Popp = true;
		}
	}
	
	public void CheckCollison(){
		
		for(int k = 0; k < Rects.size(); k++){
			if(Rects.get(k).getx() + Randoms[k] <= Rects.get(k).getWidth() || 
			   Rects.get(k).getx() + Randoms[k] >= Quesar.WIDTH - Rects.get(k).getWidth())
			{
				Randoms[k] = -Randoms[k];
			}
			if(Rects.get(k).gety() + Randoms[k + Rects.size()] <= Rects.get(k).getHeight() || 
			   Rects.get(k).gety() + Randoms[k + Rects.size()] >= Quesar.HEIGHT- Quesar.SIZE*2 - Rects.get(k).getHeight())
			{
				Randoms[k + Rects.size()] = -Randoms[k + Rects.size()];
			}
		}
		
		
		
	}
	
	public void MovingRect(float dt){
		
		if(IsMoving == true){
			MoveTimer += dt;
			if (MoveTimer >= 2){
				for (int i = 0; i < Randoms.length; i++){
					Randoms[i] = MathUtils.random(-speed,speed);
				}
				MoveTimer = 0;
			}
		
			for(int k = 0; k < Rects.size(); k++){
				Rects.get(k).setX(Rects.get(k).getx()+Randoms[k]);
				Rects.get(k).setY(Rects.get(k).gety()+Randoms[k + Rects.size()]);
			}
		}
	}
	
	public void RemoveOldReds(float dt){
		if(RedTimer1 >= 0){
			RedTimer1 += dt;
			if(RedTimer1 >= 1){
				RedTimer1 = -1;
				for(int i = 0; i < Rects.size(); i++){
					if(Rects.get(i).getIsRed()==1 || Rects.get(i).getIsRed()==-1){
						Rects.get(i).setRemove(true);
						remove = true;
						RedValue--;
					}
				}
			}
		}
		
		if(RedTimer2 >= 0){
			RedTimer2 += dt;
			if(RedTimer2 >= 1){
				RedTimer2 = -1;
				for(int i = 0; i < Rects.size(); i++){
					if(Rects.get(i).getIsRed()==2 || Rects.get(i).getIsRed()==-2){
						Rects.get(i).setRemove(true);
						remove = true;
						RedValue--;
					}
				}
			}
		}
		
		
	}
	
	public void RemoveRects(){
		int i = 0;
		for(int k = 0; k < Rects.size(); k++){
			if(Rects.get(k).getRemove()){
				if(Rects.get(k).getWidth() > 0 && Rects.get(k).getHeight() > 0 ){
					Rects.get(k).setWidth(Rects.get(k).getWidth() - MaxWidth/5);
					Rects.get(k).setHeight(Rects.get(k).getHeight() - MaxHeight/5);
				}
				if(Rects.get(k).getWidth() <= 0 && Rects.get(k).getHeight() <= 0 ){
					Rects.remove(k);
				}
			}
			else {
				i++;
			}
		}
		if(i == Rects.size()){remove = false;}
	}
	
	public void PoppingRect (){
		int i=0;
		for(int k = 0; k < Rects.size(); k++){
			if(!Rects.get(k).getRemove()){
				if(Rects.get(k).getWidth() < MaxWidth && Rects.get(k).getHeight() < MaxHeight){
					Rects.get(k).setWidth(Rects.get(k).getWidth() + MaxWidth/6);
					Rects.get(k).setHeight(Rects.get(k).getHeight() + MaxHeight/6);
				}
				if(Rects.get(k).getWidth() >= MaxWidth && Rects.get(k).getHeight() >= MaxHeight ){
					Rects.get(k).setWidth(MaxWidth);
					Rects.get(k).setHeight(MaxHeight);
					i++;
				}
			}
		}
		if(i == Rects.size()){Popp = false;}
	}
	
	public void handleInput() {
		for(int i = 0; i < MAX_FINGERS; i++) {
			if(Gdx.input.isTouched(i)){
				mouse.x = Gdx.input.getX(i);
				mouse.y = Gdx.input.getY(i);
				
				gsm.getViewport().unproject(mouse);
				
				if(Rects.size() < MaxRectsOnScreen || isEndless == false){
					for(int k = 0; k < Rects.size(); k++){
						if(Rects.get(k).contains(mouse.x, mouse.y) && !Rects.get(k).getRemove()){
							if(isEndless == true){
								Tempscore+=((int)(ScoreMultiplier));
								score.incrementScore(Tempscore);
							}
							
							RectsPopped++;
							Quesar.setPreferenceInt("TotRectsPopped", Quesar.getPreferenceInt("TotRectsPopped")+1);
							
							if(Rects.get(k).getIsRed()==1 || Rects.get(k).getIsRed()==2)
							{
								if(RedNumber1 == k){
									RedTimer1 = -1;
								}
								else if(RedNumber2 == k){
									RedTimer2 = -1;
								}
								
								RedValue--;
								StopTimer = 0;
								MoreTime = true;
								StopTime = false;
								if(isEndless){
									Quesar.showADS(true);
									LevelState.setLifes(-1);
									gsm.set(new TransitionState(gsm, this, new LevelState(gsm, level, Tempscore, isEndless), Type.BLACK_FADE));
								}
							}
							else if(Rects.get(k).getIsRed()==-1 || Rects.get(k).getIsRed()==-2)
							{
								if(RedNumber1 == k){
									RedTimer1 = -1;
								}
								else if(RedNumber2 == k){
									RedTimer2 = -1;
								}
								
								RedValue--;
								StopTimer = 0;
								StopTime = true;
								MoreTime = false;
								if(isEndless){
									ScoreMultiplier = LevelState.getScoreMultipier()*2;
								}
							}
							
							Rects.get(k).setRemove(true);
							remove = true;
							
							if(PlayState.getSoundOn()){
								pop.play();
							}
						}
					}
				}
			}
		}
		if(Gdx.input.justTouched() || backbutton){
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();
			cam.unproject(mouse);
			if(score.contains(mouse.x, mouse.y) || Pause.contains(mouse.x, mouse.y) || backbutton){
				PlayState.setRects(Rects);
				LevelState.setGlobalTimer(GlobalTimer);
				PlayState.setRectangleOnScreen(PlayState.Rects.size());
				LevelState.setRectsPopped(RectsPopped);
				if(LevelState.getIsEndless()){
					Quesar.showADS(true);
					gsm.set(new TransitionState(gsm, this, new PauseState(gsm, IsMoving, Tempscore), Type.BLACK_FADE));
				}
				else{
					Quesar.showADS(true);
					gsm.set(new TransitionState(gsm, this, new PauseState(gsm, IsMoving, (int)((LevelState.getGlobalTimer())*10)), Type.BLACK_FADE));
				}
			}
		}
	}

	public void update(float dt) {
		if(PlayState.getIsPlaying()){
			PlayTimer +=dt;
			if(PlayTimer >= 1){
				Quesar.setPreferenceInt("TimePlayed", Quesar.getPreferenceInt("TimePlayed")+1);
				PlayTimer=0;
			}
			
			handleInput();
			
			CheckCollison();
			
			MovingRect(dt);
			
			RemoveOldReds(dt);
			
			if(Popp){
				PoppingRect();
			}
			
			if(remove){
				RemoveRects();
			}
			
			if(StopTime){
				StopTimer += dt;
				if(StopTimer >= 1.5){
					
					if(isEndless){
						ScoreMultiplier = LevelState.getScoreMultipier();
					}
					
					StopTimer = -1;
					StopTime = false;
				}
			}
			
			if(MoreTime && !isEndless){
				StopTimer += dt;
				GlobalTimer +=dt;
				if(StopTimer >= 1.5){
					StopTimer = -1;
					MoreTime = false;
				}
			}
			
			if(isEndless == false && !StopTime){
				if((GlobalTimer*10) >= 9999){
					Quesar.showADS(true);
					gsm.set(new TransitionState(gsm, this, new ScoreState(gsm, (int)(GlobalTimer*10)), Type.BLACK_FADE));
				}
				GlobalTimer+=dt;
				score.incrementScore((int)(GlobalTimer*10));
			}
			
			AddRect(dt);
			
			score.update(dt);
			
			if(isEndless == true){
				if(Rects.size()>=MaxRectsOnScreen){
					Quesar.showADS(true);
					LevelState.setLifes(-1);
					for(int k = 0; k < Rects.size(); k++){
						Rects.get(k).setToRed();
					}
					gsm.set(new TransitionState(gsm, this, new LevelState(gsm, level, Tempscore, isEndless), Type.BLACK_FADE));
				}	
			}
			
			if(RectsPopped >= MaxRects && score.isDone()){
				level++;
				LevelState.setLevel(level);
				if(isEndless == false){
					LevelState.setGlobalTimer(GlobalTimer);
						if(level%5==0){
							Quesar.showADS(true);
							LevelState.setRectsPopped(0);
							gsm.set(new TransitionState(gsm, this, new BonusState(gsm, (int)(GlobalTimer*10)), Type.BLACK_FADE));
						}
						else{
							Quesar.showADS(true);
							LevelState.setRectsPopped(0);
							gsm.set(new TransitionState(gsm, this, new LevelState(gsm, level, (int)(GlobalTimer*10), isEndless), Type.BLACK_FADE));
						}
				}
				else{
					if(level%5==0){
						Quesar.showADS(true);
						LevelState.setRectsPopped(0);
						gsm.set(new TransitionState(gsm, this, new BonusState(gsm, Tempscore), Type.BLACK_FADE));
					}
					else{
						Quesar.showADS(true);
						LevelState.setRectsPopped(0);
						gsm.set(new TransitionState(gsm, this, new LevelState(gsm, level, Tempscore, isEndless), Type.BLACK_FADE));
					}
				}
			}
		}
		else {
			
			if(LevelState.getIsEndless()){
				score.incrementScore(Tempscore);}
			else{
				score.incrementScore((int)((LevelState.getGlobalTimer())*10));}
			score.update(dt);
			
			RectsPopped = LevelState.getRectsPopped();
			
			PlayState.setRects(PlayState.getRects());
			
			PoppingRect();
			
		}
		
	}

	public void render(SpriteBatch sb) {
		
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		
		if(StopTime){
			sb.setColor(green.getColor());
			score.render(sb);
			sb.setColor(1, 1, 1, 1);
		}
		else if(MoreTime){
			sb.setColor(red.getColor());
			score.render(sb);
			sb.setColor(1, 1, 1, 1);
		}
		else{
			score.render(sb);
		}
		
		for(int i = 0; i < PlayState.Rects.size(); i++){
			PlayState.Rects.get(i).render(sb);
		}
		
		Pause.render(sb);
		
		sb.end();
		
	}

}

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
import com.pet.quesar.ui.TextImage;

public class TutorialState extends State{
		
	private TextImage click;
	private TextImage click2;
	private Graphic RightArrow;
	private Graphic LeftArrow;
	
	private int done;
	private int added;
	
	private float normalTimer=0;
	
	private ArrayList<Graphic> rects;
	
	private int Width = 0;
	private int Height = 0;
	private int MaxWidth = 20*2;
	private int MaxHeight = 20*2;
	
	private int Score;
	private boolean Popp = false;
	
	private int Tempscore;
	private Score score;
	private float ScoreMultiplier;
	
	private final int MAX_FINGERS = 2;
	
	private float AddTimer = 0;
	private float GlobalTimer;
	
	private int RedNumber1;
	private float RedTimer1 = 0;
	
	private boolean StopTime = false;
	private boolean MoreTime = false;
	private float StopTimer = 0;
	
	private int MaxRectsOnScreen;
	
	private boolean isEndless;
	
	private Sound pop;
	
	private Colour red;
	private Colour green;
	
	public TutorialState(GSM gsm){
		
		super(gsm);
		
		RightArrow = new Graphic(Quesar.res.getAtlas("pack").findRegion("RightArrow"),
								Quesar.WIDTH/2 - Quesar.SIZE,
								Quesar.HEIGHT/2 - Quesar.SIZE,
								1,
								1,
								0,
								false);
		
		LeftArrow = new Graphic(Quesar.res.getAtlas("pack").findRegion("LeftArrow"),
							Quesar.WIDTH/2 + Quesar.SIZE,
							Quesar.HEIGHT/2 - Quesar.SIZE,
							1,
							1,
							0,
							false);
		
		click = new TextImage("tap or swipe the",Quesar.WIDTH/2,Quesar.HEIGHT/2,Quesar.SIZE/4);
		click2 = new TextImage("yellow squares",Quesar.WIDTH/2,Quesar.HEIGHT/2 - Quesar.SIZE/3,Quesar.SIZE/4);
		
		MaxRectsOnScreen = LevelState.getMaxRects();
		
		rects = new ArrayList<Graphic>();
		
		ScoreMultiplier = 1;
		GlobalTimer = 0;
		isEndless = LevelState.getIsEndless();
		
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
		
		done = 0;
		added = 0;
		
		if(PlayState.getSoundOn()){
			pop = Gdx.audio.newSound(Gdx.files.internal("pop.wav"));
		}
		
		red = new Colour("red");
		green = new Colour("green");
		
		
	}
	
	public void RemoveOldReds(float dt){
		RedTimer1 += dt;
		if(RedTimer1 >= 1){
			RedTimer1 = -1;
			for(int i = 0; i < rects.size(); i++){
				if(rects.get(i).getIsRed()==1 || rects.get(i).getIsRed()==2){
					rects.get(i).setRemove(true);
				}
			}
		}
	}
	
	public void RemoveRects(){
		for(int k = 0; k < rects.size(); k++){
			if(rects.get(k).getRemove()){
				if(rects.get(k).getWidth() > 0 && rects.get(k).getHeight() > 0 ){
					rects.get(k).setWidth(rects.get(k).getWidth() - MaxWidth/5);
					rects.get(k).setHeight(rects.get(k).getHeight() - MaxHeight/5);
				}
				if(rects.get(k).getWidth() <= 0 && rects.get(k).getHeight() <= 0 ){
					rects.remove(k);
				}
			}
		}
		
	}
	
	public void PoppingRect (){
		int i=0;
		for(int k = 0; k < rects.size(); k++){
			if(!rects.get(k).getRemove()){
				if(rects.get(k).getWidth() < MaxWidth && rects.get(k).getHeight() < MaxHeight ){
					rects.get(k).setWidth(rects.get(k).getWidth() + MaxWidth/6);
					rects.get(k).setHeight(rects.get(k).getHeight() + MaxHeight/6);
				}
				if(rects.get(k).getWidth() >= MaxWidth && rects.get(k).getHeight() >= MaxHeight ){
					rects.get(k).setWidth(MaxWidth);
					rects.get(k).setHeight(MaxHeight);
					i++;
				}
			}
		}
		if(i == rects.size()){Popp = false;}
	}
	
	public void AddRect(float dt, float x, float y,int color) {
		AddTimer += dt;
		if(AddTimer >= 1){
			if(rects.size() < MaxRectsOnScreen){
				
				rects.add(new Graphic(
						Quesar.res.getAtlas("pack").findRegion("Yellow"),
						x,
						y,
						Width,
						Height,
						color,
						false));	
				added++;		
				}
			AddTimer=0;
			Popp = true;
			
		}
	}
	
	
	public void handleInput(){
		for(int i = 0; i < MAX_FINGERS; i++) {
			if(Gdx.input.isTouched(i)){
				mouse.x = Gdx.input.getX(i);
				mouse.y = Gdx.input.getY(i);
				
				gsm.getViewport().unproject(mouse);
				
				if(rects.size() < MaxRectsOnScreen || isEndless == false){
					for(int k = 0; k < rects.size(); k++){
						if(rects.get(k).contains(mouse.x,mouse.y) && !rects.get(k).getRemove()){
							if(isEndless == true){
								Tempscore+=((int)(ScoreMultiplier));
								score.incrementScore(Tempscore);
							}
							
							if(rects.get(k).getIsRed()==1 || rects.get(k).getIsRed()==2)
							{
								if(RedNumber1 == k){
									RedTimer1 = -1;
								}
								if(done >=26 && done <= 36){
									added = 26;
									done = 26;
									for(int j = 0; j < rects.size(); j++){
										rects.get(j).setRemove(true);
									}
								}
								Score = 0;
								StopTimer = 0;
								MoreTime = true;
								StopTime = false;
								
							}
							else if(rects.get(k).getIsRed()==-1 || rects.get(k).getIsRed()==-2)
							{
								if(RedNumber1 == k){
									RedTimer1 = -1;
								}
								
								StopTimer = 0;
								StopTime = true;
								MoreTime = false;
								if(isEndless){
									ScoreMultiplier*=2;
								}
							}
							
							rects.get(k).setRemove(true);
							done++;
							
							if(done >=26 && done < 36){
								Score++;
							}
							
							if(PlayState.getSoundOn()){
								pop.play();
							}
						}
					}
				}
			}
		}
		if(PlayState.getbackbutton()){
			
			gsm.set(new TransitionState(gsm, this, new ModeState(gsm), Type.BLACK_FADE));
			
		}
	}
	public void update(float dt){
		handleInput();
		
		if(StopTime){
			StopTimer += dt;
			if(StopTimer >= 1.5){
				
				if(isEndless){
					ScoreMultiplier /= 2;
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
		
		if(added == 0){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,0);
		}
		
		if(added >=1 && added < 4 && done >=1 && done < 4){
			AddRect(dt, MathUtils.random(MaxWidth,Quesar.WIDTH - MaxWidth), MathUtils.random(MaxHeight,Quesar.HEIGHT - Quesar.SIZE*2 - MaxHeight),0);
		}
		
		if(added == 4 && done == 4){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,1);
		}
		
		if(added == 5 && done == 5){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,-1);
		}
		
		if(added >=6 && added < 9 && done >=6 && done < 9){
			AddRect(dt, MathUtils.random(MaxWidth,Quesar.WIDTH - MaxWidth), MathUtils.random(MaxHeight,Quesar.HEIGHT - Quesar.SIZE*2 - MaxHeight),0);
		}
		
		if(added == 9 && done == 9){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,0);
		}
		
		if(added == 10 && done == 10){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,0);
		}
		
		if(added == 11 && done == 11){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,0);
		}
		
		if(added >=12 && added <= 22 && done >=12 && done < 22){
			normalTimer+=dt;
			if(added < 22){
				AddRect(dt, MathUtils.random(MaxWidth,Quesar.WIDTH - MaxWidth), MathUtils.random(MaxHeight,Quesar.HEIGHT - Quesar.SIZE*2 - MaxHeight),0);
			}
			score.incrementScore((int)(normalTimer*10));
			score.update(dt);
		}
		
		if(added == 22 && done == 22){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,0);
		}
		
		if(added == 23 && done == 23){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,0);
			score.incrementScore(0);
			score.update(dt);
		}
		
		if(added == 24 && done == 24){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,0);
			score.update(dt);
		}
		
		if(added == 25 && done == 25){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,0);
			score.update(dt);
		}
		
		if(added >=26 && done >=26 && done < 36){
			
			AddRect(dt, MathUtils.random(MaxWidth,Quesar.WIDTH - MaxWidth), MathUtils.random(MaxHeight,Quesar.HEIGHT - Quesar.SIZE*2 - MaxHeight),MathUtils.random(-1,1));
			score.incrementScore(Score);
			score.update(dt);
			RemoveOldReds(dt);
			normalTimer = 0;
		}
		
		if(done == 36){
			normalTimer +=dt;
			for(int j = 0; j < rects.size(); j++){
				rects.get(j).setRemove(true);
			}
			if(normalTimer >= 1){
				added=37;
				done++;
			}
		}
		
		if(added == 36 && done == 36){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,0);
		}
		
		if(added == 37 && done == 37){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,0);
		}
		
		if(added == 38 && done == 38){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,0);
		}
		
		if(added == 39 && done == 39){
			AddRect(dt, Quesar.WIDTH/2, Quesar.HEIGHT/2 - Quesar.SIZE,0);
		}
		
		if(done == 40){
			gsm.set(new TransitionState(gsm, this, new ModeState(gsm), Type.BLACK_FADE));
		}
		
		if(Popp){
			PoppingRect ();
		}
		
		RemoveRects();
		
	}
	public void render(SpriteBatch sb){
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
		
		for(int i = 0; i < rects.size(); i++){
			rects.get(i).render(sb);
		}
		
		if(done == 0){
			click.render(sb);
			click2.render(sb);
			RightArrow.render(sb);
			LeftArrow.render(sb);
		}
		if(done == 4){
			click.setText("red squares have");
			click2.setText("negative effects");
			click.render(sb);
			click2.render(sb);
		}
		if(done == 5){
			click.setText("green squares have");
			click2.setText("positive effects");
			click.render(sb);
			click2.render(sb);
		}
		if(done == 9){
			click.setText("the game modes are");
			click2.setText("normal and endless");
			click.render(sb);
			click2.render(sb);
		}
		if(done == 10){
			click.setText("normal has 10");
			click2.setText("rounds with time");
			click.render(sb);
			click2.render(sb);
		}
		if(done == 11){
			click.setText("tap the squares as");
			click2.setText("fast as you can");
			click.render(sb);
			click2.render(sb);
		}
		if(done == 22){
			if(normalTimer >= 10 && normalTimer <= 12){
				click.setText("well done");
			}
			else{
				click.setText("great");
			}
			click.render(sb);
		}
		if(done == 23){
			click.setText("in endless you");
			click2.setText("have 3 lives");
			click.render(sb);
			click2.render(sb);
		}
		if(done == 24){
			click.setText("too many squares");
			click2.setText("remove a life");
			click.render(sb);
			click2.render(sb);
		}
		if(done == 25){
			click.setText("red squares take");
			click2.setText("off a life too");
			click.render(sb);
			click2.render(sb);
		}
		if(done == 36){
			click.setText("good");
			click.render(sb);
		}
		if(done == 37){
			click.setText("each 5 rounds there");
			click2.setText("is a bonus stage");
			click.render(sb);
			click2.render(sb);
		}
		if(done == 38){
			click.setText("in options you can");
			click2.setText("change difficulty");
			click.render(sb);
			click2.render(sb);
		}
		if(done == 39){
			click.setText("now you are ready");
			click2.setText("have fun");
			click.render(sb);
			click2.render(sb);
		}
		
		sb.end();
		
		
	}
	
}

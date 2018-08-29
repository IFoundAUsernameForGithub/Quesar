package com.pet.quesar.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Colour;
import com.pet.quesar.ui.TextImage;

public class ScoreState extends State{

	private TextImage image;
	private TextImage Text;
	private TextImage HighScore;
	private boolean isHighScore;
	
	private Colour green;

	
	public ScoreState(GSM gsm, int score) {
		super(gsm);
		
		if(!LevelState.getIsEndless()){
			isHighScore = HighScoreState.WriteHighScoreNormal(score);
		}
		
		if(LevelState.getIsEndless()){
			isHighScore = HighScoreState.WriteHighScoreEndless(score);
		}
		
		if(isHighScore){
			HighScore = new TextImage(
					"highscore",
					Quesar.WIDTH / 2,
					Quesar.HEIGHT / 2 + Quesar.SIZE*2, 
					(Quesar.SIZE/2)+1);
		}
		
		if(LevelState.getIsEndless() == true){
			Text = new TextImage(
					"score",
					Quesar.WIDTH / 2,
					Quesar.HEIGHT / 2 + Quesar.SIZE, 
					(Quesar.SIZE/2)+1);
		}
		else {
			Text = new TextImage(
					"time",
					Quesar.WIDTH / 2,
					Quesar.HEIGHT / 2 + Quesar.SIZE, 
					(Quesar.SIZE/2)+1);
		}
		
		image = new TextImage(
				Integer.toString(score),
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2,
				Quesar.SIZE);
		
		green = new Colour("green");
		
	}
	
	public void handleInput() {
		if(Gdx.input.justTouched()||PlayState.getbackbutton()) {
			LevelState.setLevel(1);
			LevelState.setGlobalTimer(0);
			PlayState.setRects(null);
			gsm.set(new TransitionState(gsm, this, new MenuState(gsm),Type.BLACK_FADE));
		}
	}
	
	public void update(float dt) { handleInput(); }
	
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		if(isHighScore)
		{
			sb.setColor(green.getColor());
			HighScore.render(sb);
			sb.setColor(1, 1, 1, 1);
		}
		
		Text.render(sb);
		image.render(sb);
		sb.end();
	}
}

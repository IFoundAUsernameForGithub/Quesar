package com.pet.quesar.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Colour;
import com.pet.quesar.ui.TextImage;

public class StatsState extends State{

	private TextImage Total1;
	private TextImage Total2;
	private TextImage TotSquaresText;
	private TextImage TotSquares;
	private TextImage TotalTimeText;
	private TextImage TotalTime;
	private TextImage back;
	private Colour red;
	private Colour green;
	
	public StatsState (GSM gsm){
		super(gsm);
		
		Total1 = new TextImage(
				"total",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + (Quesar.SIZE*3), 
				(Quesar.SIZE/2)+1);
		
		TotSquaresText = new TextImage(
				"squares",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + (Quesar.SIZE*2), 
				(Quesar.SIZE/2)+1);

		TotSquares = new TextImage(
				Integer.toString(Quesar.getPreferenceInt("TotRectsPopped")) + " sqs",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + (Quesar.SIZE), 
				(Quesar.SIZE/2)+1);

		Total2 = new TextImage(
				"total",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 , 
				(Quesar.SIZE/2)+1);
		
		TotalTimeText = new TextImage(
				"time",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - (Quesar.SIZE), 
				(Quesar.SIZE/2)+1);
		
		TotalTime = new TextImage(
				Integer.toString(Quesar.getPreferenceInt("TimePlayed")) + " sec",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - (Quesar.SIZE*2), 
				(Quesar.SIZE/2)+1);
		
		if(Quesar.getPreferenceInt("TimePlayed") >= 60){
			TotalTime = new TextImage(
					Integer.toString(Quesar.getPreferenceInt("TimePlayed")/60) + " min",
					Quesar.WIDTH / 2,
					Quesar.HEIGHT / 2 - (Quesar.SIZE*2), 
					(Quesar.SIZE/2)+1);
			if(Quesar.getPreferenceInt("TimePlayed") >= 3600){
				TotalTime = new TextImage(
						Integer.toString(Quesar.getPreferenceInt("TimePlayed")/3600) + " hrs",
						Quesar.WIDTH / 2,
						Quesar.HEIGHT / 2 - (Quesar.SIZE*2), 
						(Quesar.SIZE/2)+1);
				if(Quesar.getPreferenceInt("TimePlayed") >= 86400){
					TotalTime = new TextImage(
							Integer.toString(Quesar.getPreferenceInt("TimePlayed")/86400) + " days",
							Quesar.WIDTH / 2,
							Quesar.HEIGHT / 2 - (Quesar.SIZE*2), 
							(Quesar.SIZE/2)+1);
					if(Quesar.getPreferenceInt("TimePlayed") >= 604800){
						TotalTime = new TextImage(
								Integer.toString(Quesar.getPreferenceInt("TimePlayed")/604800) + " weeks",
								Quesar.WIDTH / 2,
								Quesar.HEIGHT / 2 - (Quesar.SIZE*2), 
								(Quesar.SIZE/2)+1);
					}
				}
			}
		}
		
		back = new TextImage(
				"back",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - (Quesar.SIZE*3), 
				(Quesar.SIZE/2)+1);
		
		red = new Colour("red");
		green = new Colour("green");
		
	}
	
	public void handleInput(){
		if(Gdx.input.justTouched()||PlayState.getbackbutton()) {
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();
			
			gsm.getViewport().unproject(mouse);
			
			if(back.contains(mouse.x, mouse.y)||PlayState.getbackbutton()){
				gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
			}
		}
	}
	public void update(float dt){ handleInput(); }
	public void render(SpriteBatch sb){
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		TotSquares.render(sb);
		TotalTime.render(sb);
		sb.setColor(green.getColor());
		TotSquaresText.render(sb);
		TotalTimeText.render(sb);
		Total1.render(sb);
		Total2.render(sb);
		sb.setColor(red.getColor());
		back.render(sb);
		sb.setColor(1, 1, 1, 1);
		sb.end();
		
	}
	
}

package com.pet.quesar.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Colour;
import com.pet.quesar.ui.TextImage;


public class MenuState extends State{
	
	private TextImage play;
	private TextImage options;
	private TextImage highscore;
	private TextImage credits;
	private TextImage back;
	private Colour red;
	private Colour green;
	
	public MenuState(GSM gsm) {
		super(gsm);
		
		
			play = new TextImage(
					"play",
					Quesar.WIDTH / 2,
					Quesar.HEIGHT / 2 + Quesar.SIZE*2,
					(Quesar.SIZE/2)+1);
			
			highscore = new TextImage(
					"highscore",
					Quesar.WIDTH / 2,
					Quesar.HEIGHT / 2 + Quesar.SIZE,
					(Quesar.SIZE/2)+1);
			
			credits = new TextImage(
					"credits",
					Quesar.WIDTH / 2,
					Quesar.HEIGHT / 2 - Quesar.SIZE,
					(Quesar.SIZE/2)+1);
			
			options = new TextImage(
					"options",
					Quesar.WIDTH / 2,
					Quesar.HEIGHT / 2 ,
					(Quesar.SIZE/2)+1);
			
			back = new TextImage(
					"back",
					Quesar.WIDTH / 2,
					Quesar.HEIGHT / 2 - Quesar.SIZE*2,
					(Quesar.SIZE/2)+1);
			
			LevelState.setLevel(1);
			
			red = new Colour("red");
			green = new Colour("green");
				
	}
	
	public void handleInput() {
		if(Gdx.input.justTouched()) {
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();

			gsm.getViewport().unproject(mouse);
			
			if(play.contains(mouse.x, mouse.y)) {
				gsm.set(new TransitionState(gsm, this, new ModeState(gsm), Type.BLACK_FADE));
			}
			else if(highscore.contains(mouse.x, mouse.y)){
				gsm.set(new TransitionState(gsm, this, new HighScoreState(gsm), Type.BLACK_FADE));
			}
			else if(credits.contains(mouse.x, mouse.y)){
				gsm.set(new TransitionState(gsm,this, new CreditsState(gsm), Type.BLACK_FADE));
			}
			else if(options.contains(mouse.x, mouse.y)){
				gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
			}
			else if(back.contains(mouse.x, mouse.y)){
				gsm.set(new TransitionState(gsm, this, new StartState(gsm), Type.BLACK_FADE));
			}
		}
		
		if(PlayState.getbackbutton()){
			gsm.set(new TransitionState(gsm, this, new StartState(gsm), Type.BLACK_FADE));
		}
		
		
	}

	public void update(float dt) {
		handleInput();
	}
	
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();

		highscore.render(sb);
		credits.render(sb);
		options.render(sb);
		sb.setColor(green.getColor());
		play.render(sb);
		sb.setColor(red.getColor());
		back.render(sb);
		sb.setColor(1, 1, 1, 1);
		sb.end();
	}
	
}

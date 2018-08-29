package com.pet.quesar.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Colour;
import com.pet.quesar.ui.TextImage;

public class OptionsState extends State{

	private TextImage Mode;
	private TextImage Sound;
	private TextImage Audio;
	private TextImage Back;
	private TextImage Reset;
	private static boolean SoundOn = true;
	private static boolean AudioOn = true;
	private String Text;
	private TextImage stats;
	private TextImage NoADS;
	private Colour red;
	private Colour green;
	
	public OptionsState (GSM gsm){
		super(gsm);
		
		Mode = new TextImage(
				"mode",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + (Quesar.SIZE*3), 
				(Quesar.SIZE/2)+1);
		if(OptionsState.getSoundOn()){Text = "on";}
		else{Text = "off";}
		Sound = new TextImage(
				"sound" + " " + Text,
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + (Quesar.SIZE*2), 
				(Quesar.SIZE/2)+1);
		if(OptionsState.getAudioOn()){Text = "on";}
		else{Text = "off";}
		Audio = new TextImage(
				"audio" + " " + Text,
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + (Quesar.SIZE), 
				(Quesar.SIZE/2)+1);
		
		Reset = new TextImage(
				"reset",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 , 
				(Quesar.SIZE/2)+1);
		
		stats = new TextImage(
				"stats",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - (Quesar.SIZE), 
				(Quesar.SIZE/2)+1);
		
		NoADS = new TextImage(
				"no ads",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - (Quesar.SIZE*2), 
				(Quesar.SIZE/2)+1);
		
		Back = new TextImage(
				"back",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - (Quesar.SIZE*3), 
				(Quesar.SIZE/2)+1);
		
		red = new Colour("red");
		green = new Colour("green");
		
	}
	
	public static boolean getSoundOn(){
		return SoundOn;
	}
	public static void setSoundOn(boolean Sound){
		SoundOn = Sound;
	}
	public static boolean getAudioOn(){
		return AudioOn;
	}
	public static void setAudioOn(boolean Audio){
		AudioOn = Audio;
	}
	
public void handleInput() {
		
		if(Gdx.input.justTouched()) {
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();
			
			gsm.getViewport().unproject(mouse);
			
			if(Mode.contains(mouse.x, mouse.y)) {
				gsm.set(new TransitionState(gsm, this, new Options(gsm), Type.BLACK_FADE));
			}
			else if(Sound.contains(mouse.x, mouse.y)){
				OptionsState.setSoundOn(!SoundOn);
				if(SoundOn){
					PlayState.setSoundOn(true);
				}
				else{
					PlayState.setSoundOn(false);
				}
				gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
			}
			else if(Audio.contains(mouse.x, mouse.y)){
				OptionsState.setAudioOn(!AudioOn);
				if(AudioOn){
					Quesar.SetMusic();
					Quesar.PlayMusic();
				}
				else{
					Quesar.StopMusic();
				}
				gsm.set(new TransitionState(gsm, this, new OptionsState(gsm), Type.BLACK_FADE));
			}
			else if(Reset.contains(mouse.x, mouse.y)) {
				gsm.set(new TransitionState(gsm, this, new ResetState(gsm), Type.BLACK_FADE));
			}
			else if(stats.contains(mouse.x, mouse.y)) {
				gsm.set(new TransitionState(gsm, this, new StatsState(gsm), Type.BLACK_FADE));
			}
			else if(NoADS.contains(mouse.x, mouse.y)) {
				if(Quesar.getNOADS()==false){
					//TODO app purchase
					Quesar.showADS(false);
					Quesar.setNOADS(true);
				}
			}
			else if(Back.contains(mouse.x, mouse.y)){
				gsm.set(new TransitionState(gsm, this, new MenuState(gsm), Type.BLACK_FADE));
			}
		}
		if(PlayState.getbackbutton()){
			gsm.set(new TransitionState(gsm, this, new MenuState(gsm), Type.BLACK_FADE));
		}
	}

	
	public void update(float dt) {
		handleInput();
	}

	
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		Mode.render(sb);
		Sound.render(sb);
		Audio.render(sb);
		Reset.render(sb);
		stats.render(sb);
		sb.setColor(green.getColor());
		NoADS.render(sb);
		sb.setColor(red.getColor());
		Back.render(sb);
		sb.setColor(1, 1, 1, 1);
		sb.end();
	}
}

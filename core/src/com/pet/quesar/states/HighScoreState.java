package com.pet.quesar.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pet.quesar.Quesar;
import com.pet.quesar.states.TransitionState.Type;
import com.pet.quesar.ui.Colour;
import com.pet.quesar.ui.TextImage;

public class HighScoreState extends State{
	
	private TextImage normal;
	private TextImage endless;
	private TextImage Back;
	private TextImage Normal1;
	private TextImage Normal2;
	private TextImage Normal3;
	private TextImage Endless1;
	private TextImage Endless2;
	private TextImage Endless3;
	private TextImage Uno1;
	private TextImage Due1;
	private TextImage Tre1;
	private TextImage Uno2;
	private TextImage Due2;
	private TextImage Tre2;
	
	private static int normal1 = 9999;
	private static int normal2 = 9999;
	private static int normal3 = 9999;
	private static int endless1 = 0;
	private static int endless2 = 0;
	private static int endless3 = 0;
	
	private Colour red;
	private Colour green;
	
	public HighScoreState(GSM gsm){
		super(gsm);
		
		normal = new TextImage(
				"normal",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 + Quesar.SIZE*3,
				(Quesar.SIZE/2)+1);
		
		Uno1 = new TextImage(
				"1",
				Quesar.WIDTH / 4,
				Quesar.HEIGHT / 2 + (int)((Quesar.SIZE*3)/4)*3,
				(Quesar.SIZE/2)+1);
		
		Normal1 = new TextImage(
				Integer.toString(normal1),
				(Quesar.WIDTH / 4)*3,
				Quesar.HEIGHT / 2 + (int)((Quesar.SIZE*3)/4)*3,
				(Quesar.SIZE/2)+1);
		
		Due1 = new TextImage(
				"2",
				Quesar.WIDTH / 4,
				Quesar.HEIGHT / 2 + (int)((Quesar.SIZE*3)/4)*2,
				(Quesar.SIZE/2)+1);
		
		Normal2 = new TextImage(
				Integer.toString(normal2),
				(Quesar.WIDTH / 4)*3,
				Quesar.HEIGHT / 2 + (int)((Quesar.SIZE*3)/4)*2,
				(Quesar.SIZE/2)+1);
		
		Tre1 = new TextImage(
				"3",
				Quesar.WIDTH / 4,
				Quesar.HEIGHT / 2 + (int)((Quesar.SIZE*3)/4),
				(Quesar.SIZE/2)+1);
		
		Normal3 = new TextImage(
				Integer.toString(normal3),
				(Quesar.WIDTH / 4)*3,
				Quesar.HEIGHT / 2 + (int)((Quesar.SIZE*3)/4),
				(Quesar.SIZE/2)+1);
		
		endless = new TextImage(
				"endless",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2,
				(Quesar.SIZE/2)+1);
		
		Uno2 = new TextImage(
				"1",
				Quesar.WIDTH / 4,
				Quesar.HEIGHT / 2 - (int)((Quesar.SIZE*3)/4),
				(Quesar.SIZE/2)+1);
		
		Endless1 = new TextImage(
				Integer.toString(endless1),
				(Quesar.WIDTH / 4)*3,
				Quesar.HEIGHT / 2 - (int)((Quesar.SIZE*3)/4),
				(Quesar.SIZE/2)+1);
		
		Due2 = new TextImage(
				"2",
				Quesar.WIDTH / 4,
				Quesar.HEIGHT / 2 - (int)((Quesar.SIZE*3)/4)*2,
				(Quesar.SIZE/2)+1);
		
		Endless2 = new TextImage(
				Integer.toString(endless2),
				(Quesar.WIDTH / 4)*3,
				Quesar.HEIGHT / 2 - (int)((Quesar.SIZE*3)/4)*2,
				(Quesar.SIZE/2)+1);
		
		Tre2 = new TextImage(
				"3",
				Quesar.WIDTH / 4,
				Quesar.HEIGHT / 2 - (int)((Quesar.SIZE*3)/4)*3,
				(Quesar.SIZE/2)+1);
		
		Endless3 = new TextImage(
				Integer.toString(endless3),
				(Quesar.WIDTH / 4)*3,
				Quesar.HEIGHT / 2 - (int)((Quesar.SIZE*3)/4)*3,
				(Quesar.SIZE/2)+1);
		
		Back = new TextImage(
				"back",
				Quesar.WIDTH / 2,
				Quesar.HEIGHT / 2 - (Quesar.SIZE*3), 
				(Quesar.SIZE/2)+1);
		
		red = new Colour("red");
		green = new Colour("green");
		
	}

	public static int getNormal1() {return normal1;}
	public static void setNormal1(int normal1) {
		HighScoreState.normal1 = normal1;
		Quesar.setPreferenceInt("normal1", HighScoreState.normal1);}

	public static int getNormal2() {return normal2;}
	public static void setNormal2(int normal2) {
		HighScoreState.normal2 = normal2;
		Quesar.setPreferenceInt("normal2", HighScoreState.normal2);}
	
	public static int getNormal3() {return normal3;}
	public static void setNormal3(int normal3) {
		HighScoreState.normal3 = normal3;
		Quesar.setPreferenceInt("normal3", HighScoreState.normal3);}
	
	public static int getEndless1() {return endless1;}
	public static void setEndless1(int endless1) {
		HighScoreState.endless1 = endless1;
		Quesar.setPreferenceInt("endless1", HighScoreState.endless1);}
	
	public static int getEndless2() {return endless2;}
	public static void setEndless2(int endless2) {
		HighScoreState.endless2 = endless2;
		Quesar.setPreferenceInt("endless2", HighScoreState.endless2);}
	
	public static int getEndless3() {return endless3;}
	public static void setEndless3(int endless3) {
		HighScoreState.endless3 = endless3;
		Quesar.setPreferenceInt("endless3", HighScoreState.endless3);}


	public static boolean WriteHighScoreNormal(int score){
		if(score <= HighScoreState.getNormal1()){
			HighScoreState.setNormal3(HighScoreState.getNormal2());
			HighScoreState.setNormal2(HighScoreState.getNormal1());
			HighScoreState.setNormal1(score);
			return true;
		}
		else if(score <= HighScoreState.getNormal2()){
			HighScoreState.setNormal3(HighScoreState.getNormal2());
			HighScoreState.setNormal2(score);
			return true;
		}
		else if(score <= HighScoreState.getNormal3()){
			HighScoreState.setNormal3(score);
			return true;
		}
		return false;
	}
	
	public static boolean WriteHighScoreEndless(int score){
		if(score >= HighScoreState.getEndless1()){
			HighScoreState.setEndless3(HighScoreState.getEndless2());
			HighScoreState.setEndless2(HighScoreState.getEndless1());
			HighScoreState.setEndless1(score);
			return true;
		}
		else if(score >= HighScoreState.getEndless2()){
			HighScoreState.setEndless3(HighScoreState.getEndless2());
			HighScoreState.setEndless2(score);
			return true;
		}
		else if(score >= HighScoreState.getEndless3()){
			HighScoreState.setEndless3(score);
			return true;
		}
		return false;
	}

	
	public void handleInput(){
		if(Gdx.input.justTouched()) {
				mouse.x = Gdx.input.getX();
				mouse.y = Gdx.input.getY();
				
				gsm.getViewport().unproject(mouse);
				
			if(Back.contains(mouse.x, mouse.y)||PlayState.getbackbutton()){
				gsm.set(new TransitionState(gsm, this, new MenuState(gsm), Type.BLACK_FADE));
			}
		}
		if(PlayState.getbackbutton()){
			gsm.set(new TransitionState(gsm, this, new MenuState(gsm), Type.BLACK_FADE));
		}
	}
	public void update(float dt){handleInput();};
	public void render(SpriteBatch sb){
		
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		
		Uno1.render(sb);
		Uno2.render(sb);
		Due1.render(sb);
		Due2.render(sb);
		Tre1.render(sb);
		Tre2.render(sb);
		Normal1.render(sb);
		Normal2.render(sb);
		Normal3.render(sb);
		Endless1.render(sb);
		Endless2.render(sb);
		Endless3.render(sb);
		sb.setColor(green.getColor());
		normal.render(sb);
		endless.render(sb);
		sb.setColor(red.getColor());
		Back.render(sb);
		sb.setColor(1, 1, 1, 1);
		sb.end();
		
	}

	
}

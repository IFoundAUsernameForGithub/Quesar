package com.pet.quesar.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pet.quesar.Quesar;
import com.pet.quesar.ui.Colour;
import com.pet.quesar.ui.Score;

public class TransitionState extends State{

	public enum Type {
		BLACK_FADE,
		POP_RECT,
		COUNTDOWN
	};
	
	private State prev;
	private State next;
	private Type type;
	
	private TextureRegion dark;
	
	// black fade
	private float maxTime;
	private float timer;
	private float alpha;
	
	private float Width;
	private float Height;
	private float TempX;
	private float TempY;
	private float rotation;
	
	private Score Number;
	private int score=3;
	private float dtimer;
	
	private Colour blue;
	
	public TransitionState(GSM gsm, State prev, State next, Type type) {
		
		super(gsm);
		
		this.prev = prev;
		this.next = next;
		this.type = type;
		
		dark = Quesar.res.getAtlas("pack").findRegion("Green");
		
		if(type == Type.BLACK_FADE) {
			maxTime = 1;
		}
		else if(type == Type.POP_RECT){
			maxTime = 1;
			Width = 1;
			Height = 1;
			TempX = Quesar.WIDTH/2;
			TempY = Quesar.HEIGHT/2;
		}
		else if(type == Type.COUNTDOWN){
			maxTime = 4;
			Number = new Score(Quesar.WIDTH/2,Quesar.HEIGHT/2,score,Quesar.SIZE);
			blue = new Colour("blue");
		}
		
	}
	
	public void handleInput() {}
	
	public void update(float dt) {
		timer += dt;
		
		if(type == Type.BLACK_FADE) {
			if(timer >= maxTime) {
				gsm.set(next);
			}
		}
		else if (type == Type.POP_RECT){
			Width = Quesar.WIDTH*alpha;
			Height = Quesar.HEIGHT*alpha;
			TempX = Quesar.WIDTH/2 - (Quesar.WIDTH/2)*alpha;
			TempY = Quesar.HEIGHT/2 - (Quesar.HEIGHT/2)*alpha;
			rotation = 180*alpha;
			if(timer >= maxTime) {
				gsm.set(next);
			}
		}
		else if(type == Type.COUNTDOWN){
			if(timer >= 1){
				dtimer += dt;
				PlayState.setIsPlaying(false);
				next.update(dt);
			}
			if (dtimer >= 1 && score >1){
				score--;
				Number.incrementScore(score);
				Number.update(dt);
				dtimer = 0;
			}
			if(timer >= maxTime){
				PlayState.setIsPlaying(true);
				gsm.set(next);
			}
		}
		
		
	}
	
	public void render(SpriteBatch sb) {
		
		if(type == Type.BLACK_FADE) {
			if(timer <= maxTime / 2) {
				alpha = timer / (maxTime / 2);
				prev.render(sb);
			}
			else {
				alpha = (1 + maxTime)-(timer / (maxTime / 2));
				Quesar.setbackground(true);
				next.render(sb);
			}
			sb.setColor(0, 0, 0, alpha);
			sb.setProjectionMatrix(cam.combined);
			sb.begin();
			sb.draw(dark, 0, 0, Quesar.WIDTH, Quesar.HEIGHT);
			sb.end();
			sb.setColor(1, 1, 1, 1);
		}
		else if(type == Type.POP_RECT){
			if(timer <= maxTime / 2) {
				alpha = timer / (maxTime / 2);
				prev.render(sb);
			}
			else {
				alpha = (1 + maxTime)-(timer / (maxTime / 2));
				Quesar.setbackground(false);
				next.render(sb);
			}
			sb.setColor(0, 0, 0, 1);
			sb.setProjectionMatrix(cam.combined);
			sb.begin();
			sb.draw(dark, TempX, TempY, Quesar.WIDTH/2, Quesar.HEIGHT/2, Width, Height, 1, 1, rotation);
			sb.end();
			sb.setColor(1, 1, 1, 1);
		}
		else if (type == Type.COUNTDOWN){
			if(timer <= maxTime / 8) {
				alpha = timer / (maxTime / 8);
				prev.render(sb);
			}
			else if (timer <= maxTime){
				if(timer <= maxTime / 4)
				alpha = (1 + maxTime/4)-(timer / (maxTime / 8));
				Quesar.setbackground(false);
				next.render(sb);
			}
			sb.setColor(0, 0, 0, alpha);
			sb.setProjectionMatrix(cam.combined);
			sb.begin();
			sb.draw(dark, 0, 0, Quesar.WIDTH, Quesar.HEIGHT);
			if(timer >= maxTime / 4){
				sb.setColor(blue.getColor());
				Number.render(sb);
			}
			sb.end();
			sb.setColor(1, 1, 1, 1);
		}
		
	}
	
}

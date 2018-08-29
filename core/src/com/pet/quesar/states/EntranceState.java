package com.pet.quesar.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pet.quesar.Quesar;

public class EntranceState extends State{
	
	private State next;
	
	private TextureRegion dark;
	
	// black fade
	private float maxTime;
	private float timer;
	private float alpha;
	
	public EntranceState(GSM gsm, State next) {
		super(gsm);
		
		this.next = next;
		
		dark = Quesar.res.getAtlas("pack").findRegion("Green");
		
		maxTime = 1;
		
	}

	public void handleInput() {
		
	}

	public void update(float dt) {
		timer += dt;
		
		if(timer >= maxTime) {
			gsm.set(next);
		}
		
	}

	public void render(SpriteBatch sb) {
		
			if(timer <= maxTime) {
				alpha = (maxTime)-(timer / (maxTime));
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
		
}

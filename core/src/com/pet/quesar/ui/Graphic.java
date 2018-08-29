package com.pet.quesar.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pet.quesar.Quesar;

public class Graphic extends Box{
	
	private TextureRegion image;
	
	public Graphic(TextureRegion image, float x, float y, float Width, float Height, int IsRed, boolean remove) {
		this.image = image;
		this.x = x;
		this.y = y;
		width = image.getRegionWidth()*Width;
		height = image.getRegionHeight()*Height;
		isRed = IsRed;
		if(isRed == 1 || isRed == 2){
			this.image = Quesar.res.getAtlas("pack").findRegion("Red");
		}
		else if (isRed == -1 || isRed == -2){
			this.image = Quesar.res.getAtlas("pack").findRegion("Green");
		}
		else if (isRed == 5){
			this.image = Quesar.res.getAtlas("pack").findRegion("BlackHearth");
		}
		
		Remove = remove;
	}
	
	public void setX (float x){
		this.x = x;
		
	}
	
	public void setY (float y) {
		this.y = y;
	}
	
	public void setWidth(float Width){
		this.width = Width;
	}
	
	public void setHeight(float Height) {
		this.height = Height;
	}
	
	public void setImage(TextureRegion ima){
		this.image = ima;
	}
	
	public void setRemove(boolean remove){
		this.Remove = remove;
	}
	
	public void setToRed(){
		this.image = Quesar.res.getAtlas("pack").findRegion("Red");
	}
	
	
	public void render(SpriteBatch sb) {
		sb.draw(image, x - width / 2, y - height / 2, width, height);
	}

}

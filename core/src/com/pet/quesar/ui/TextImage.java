package com.pet.quesar.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pet.quesar.Quesar;

public class TextImage extends Box{
	
	private TextureRegion[][] fontSheet;
	private String text;
	private int size=50;
	private int Size;
	
	public TextImage(String text, float x, float y, int Size) {
		
		this.text = text;
		this.x = x;
		this.y = y;
		
		this.Size = Size;
		setText(text);
		
		TextureRegion sheet =
			Quesar.res.getAtlas("pack").findRegion("FontSheet");		
		fontSheet = sheet.split(size, size);
		
	}
	
	public void setText(String text) {
		this.text = text;
		width = Size * text.length();
		height = Size;
	}
	
	public void render(SpriteBatch sb) {
		for(int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if(c >= 'a' && c <= 'z') {
				c -= 'a';
			}
			else if(c >= '0' && c <= '9') {
				c -= '0';
				c += 27;
			}
			else if(c == ' '){
				c = 26;
			}
			int index = (int) c;
			int row = index / fontSheet[0].length;
			int col = index % fontSheet[0].length;
			sb.draw(
				fontSheet[row][col],
				x - width / 2 + Size * i,
				y - height / 2,
				Size,
				Size
			);
		}
	}

}

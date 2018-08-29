package com.pet.quesar.ui;

import com.badlogic.gdx.graphics.Color;

public class Colour {

	private Color color;
	
	public Colour (String name){
		
		color = new Color(1,1,1,1);
		
		if(name == "red"){
			color.set(0.9608f, 0.1647f, 0.1490f, 1);
		}
		else if(name == "orange"){
			color.set(1, 0.5843f, 0.2353f, 1);
		}
		else if(name == "yellow"){
			color.set(0.9333f, 0.8627f, 0.2039f, 1);
		}
		else if(name == "green"){
			color.set(0.0667f, 0.8784f, 0.3412f, 1);
		}
		else if(name == "blue"){
			color.set(0.2706f, 0.4824f, 0.9098f, 1);
		}
		else if(name == "purple"){
			color.set(0.7373f, 0.1647f, 1, 1);
		}
	}
	
	public Color getColor(){
		return color;
	}
}

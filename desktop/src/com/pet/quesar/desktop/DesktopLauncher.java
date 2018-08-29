package com.pet.quesar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pet.quesar.Quesar;
import com.pet.quesar.handler.AdsController;

public class DesktopLauncher implements AdsController{
	
	private static DesktopLauncher application;
	
	public static void main (String[] arg) {
		
		if(application == null){
			application = new DesktopLauncher();
		}
	
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = Quesar.WIDTH;
		config.height = Quesar.HEIGHT;
		config.title = Quesar.TITLE;
		
		new LwjglApplication(new Quesar(application), config);
	}


	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		
	}
}

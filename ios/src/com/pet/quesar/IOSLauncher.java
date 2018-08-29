package com.pet.quesar;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.pet.quesar.handler.AdsController;

public class IOSLauncher extends IOSApplication.Delegate implements AdsController {
	
	private static IOSLauncher application;
	
    @Override
    protected IOSApplication createApplication() {
    	
    	if(application == null){
			application = new IOSLauncher();
		}
    	
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        return new IOSApplication(new Quesar(application), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		
	}
}
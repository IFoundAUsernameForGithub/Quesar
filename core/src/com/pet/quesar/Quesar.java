package com.pet.quesar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pet.quesar.handler.AdsController;
import com.pet.quesar.handler.Content;
import com.pet.quesar.states.EntranceState;
import com.pet.quesar.states.GSM;
import com.pet.quesar.states.HighScoreState;
import com.pet.quesar.states.PlayState;
import com.pet.quesar.states.StartState;
import com.pet.quesar.ui.Graphic;

public class Quesar extends ApplicationAdapter implements InputProcessor{

	public static final String TITLE = "quesar";
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final int SIZE = 100;
	
	public static Content res;
	
	private SpriteBatch sb;
	private GSM gsm;
	
	private static boolean backmove=true;
	private ShapeRenderer sr;
	private static Graphic back;
	private int x = Quesar.WIDTH/2;
	private int y = Quesar.HEIGHT/2;
	private int width = 2;
	private int height = 2;
	private int speedX = 1;
	private int speedY = 1;
	private float r;
	private float g;
	private float b;
	private int color;
	
	private float volume=0;
	
	private static Music bg;
	private static boolean isPlaying;
	
	private static Preferences pref;
	
	private OrthographicCamera cam;
	Viewport viewport;
	
	private static AdsController request;
	private static boolean NOADS = false;
	
	public static void showADS(boolean a){
		if(NOADS == false){
			request.showAds(a);
		}
	}
	
	public static void setNOADS(boolean a){
		NOADS = a;
	}
	
	public static boolean getNOADS(){
		return NOADS;
	}
	
	
	public Quesar (AdsController handler){
		request = handler;
	}
	
	public void create () {
		
		Quesar.showADS(true);
		
		bg = Gdx.audio.newMusic(Gdx.files.internal("bg.wav"));
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		//catch back button
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		
		res = new Content();
		res.loadAtlas("pack.pack", "pack");
		
		sr = new ShapeRenderer();
		sb = new SpriteBatch();
		
		cam = new OrthographicCamera();
		viewport = new FitViewport(Quesar.WIDTH,Quesar.HEIGHT,cam);
		viewport.apply();
		cam.setToOrtho(false, Quesar.WIDTH, Quesar.HEIGHT);
		
		gsm = new GSM(viewport);
		gsm.push(new EntranceState(gsm, new StartState(gsm)));
		
		bg.setLooping(true);
		Quesar.setIsPlaying(true);
		bg.play();
		bg.setVolume(0);
		back = new Graphic(Quesar.res.getAtlas("pack").findRegion("Background"), x, y, width, height, 0, false);
		
		
		
		pref = Gdx.app.getPreferences("My Preferences");
		
		if(Quesar.getPreferenceInt("normal1")==0){Quesar.setPreferenceInt("normal1",9999);}
		HighScoreState.setNormal1(Quesar.getPreferenceInt("normal1"));
		if(Quesar.getPreferenceInt("normal2")==0){Quesar.setPreferenceInt("normal2",9999);}
		HighScoreState.setNormal2(Quesar.getPreferenceInt("normal2"));
		if(Quesar.getPreferenceInt("normal3")==0){Quesar.setPreferenceInt("normal3",9999);}
		HighScoreState.setNormal3(Quesar.getPreferenceInt("normal3"));
		HighScoreState.setEndless1(Quesar.getPreferenceInt("endless1"));
		HighScoreState.setEndless2(Quesar.getPreferenceInt("endless2"));
		HighScoreState.setEndless3(Quesar.getPreferenceInt("endless3"));
		
		/*
		 * IN APP PURCHASE
		 * 
		if(PurchaseSystem.hasManager()){
			PurchaseManagerConfig config = new PurchaseManagerConfig();
			//config.addOffer();
			config.addStoreParam(PurchaseManagerConfig.STORE_NAME_ANDROID_GOOGLE, "<Google key>");
			PurchaseSystem.purchase("");
			PurchaseSystem.purchaseRestore();;
		}
		
		* IN APP PURCHASE
		*/
		
	}
	
	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
		cam.setToOrtho(false, Quesar.WIDTH, Quesar.HEIGHT);
		
	}
	
	public static int getPreferenceInt(String key){
		return pref.getInteger(key);
	}
	public static void setPreferenceInt(String key, int value){
		pref.remove(key);
		pref.putInteger(key, value);
		pref.flush();
	}
	public static void ClearPreference(){
		pref.remove("normal1");
		pref.remove("normal2");
		pref.remove("normal3");
		pref.remove("endless1");
		pref.remove("endless2");
		pref.remove("endless3");
		pref.remove("TotRectsPopped");
		pref.remove("TimePlayed");
		Quesar.setPreferenceInt("normal1",9999);
		Quesar.setPreferenceInt("normal2",9999);
		Quesar.setPreferenceInt("normal3",9999);
		Quesar.setPreferenceInt("endless1",0);
		Quesar.setPreferenceInt("endless2",0);
		Quesar.setPreferenceInt("endless3",0);
		Quesar.setPreferenceInt("TotRectsPopped", 0);
		Quesar.setPreferenceInt("TimePlayed", 0);
		HighScoreState.setEndless1(Quesar.getPreferenceInt("endless1"));
		HighScoreState.setEndless2(Quesar.getPreferenceInt("endless2"));
		HighScoreState.setEndless3(Quesar.getPreferenceInt("endless3"));
		HighScoreState.setNormal1(Quesar.getPreferenceInt("normal1"));
		HighScoreState.setNormal2(Quesar.getPreferenceInt("normal2"));
		HighScoreState.setNormal3(Quesar.getPreferenceInt("normal3"));
		pref.flush();
	}
	
	public static void setIsPlaying(boolean play){
		isPlaying = play;
	}
	public static boolean isPlaying(){
		return isPlaying;
	}
	
	public static void PlayMusic(){
		Quesar.setIsPlaying(true);
		bg.play();
	}
	
	public static void SetMusic(){
		Quesar.bg.setVolume(0);
	}
	
	public static void StopMusic(){
		Quesar.setIsPlaying(false);
		bg.stop();
	}
	
	public static void setbackground(boolean a){
		backmove = a;
		if(a){back.setImage(Quesar.res.getAtlas("pack").findRegion("Background"));}
		else{back.setImage(Quesar.res.getAtlas("pack").findRegion("BackgroundPlayState"));}
	}

	public void update(float dt) {
		x+=speedX;
		y+=speedY;
		volume = bg.getVolume();
		if(Quesar.isPlaying && volume < 1){
			volume += dt/2.5f;
			if(volume > 1){
				volume = 1;
			}
			bg.setVolume(volume);
		}
		
		if(x >= Quesar.WIDTH || x <= 0){
			speedX = -speedX;
		}
		if(y >= Quesar.HEIGHT || y <= 0){
			speedY = -speedY;
		}
		if(backmove){
		back.setX(x);
		back.setY(y);
		}
		
		color++;
		if((int)(color/100)>=0 && (int)(color/100) < 1){
			r = 1;
			g = (((float)color)/100);
			b = 0;
		}
		else if((int)(color/100)>=1 && (int)(color/100) < 2){
			r = 2 - (((float)color)/100);
			g = 1;
			b = 0;
		}
		else if((int)(color/100)>=2 && (int)(color/100) < 3){
			r = 0;
			g = 1;
			b = (((float)color)/100) - 2;
		}
		else if((int)(color/100)>=3 && (int)(color/100) < 4){
			r = 0;
			g = 4 - (((float)color)/100);
			b = 1;
		}
		else if((int)(color/100)>=4 && (int)(color/100) < 5){
			r = (((float)color)/100) - 4;
			g = 0;
			b = 1;
		}
		else if((int)(color/100)>=5 && (int)(color/100) < 6){
			r = 1;
			g = 0;
			b = 6 - (((float)color)/100);
		}
		else if(color >= 600){
			color = 0;
		}
	}
	
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeType.Filled);
		sr.setColor(r, g, b, 1);
		sr.rect(0, 0, Quesar.WIDTH, Quesar.HEIGHT);
		sr.end();
		
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		back.render(sb);
		update(Gdx.graphics.getDeltaTime());
		sb.end();
	
	
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);
	
	}

	@Override
	public boolean keyDown(int keycode) {
			if(keycode == Keys.BACK) {
	            PlayState.setbackbutton(true);
	        }
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.BACK) {
            PlayState.setbackbutton(false);
        }
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}

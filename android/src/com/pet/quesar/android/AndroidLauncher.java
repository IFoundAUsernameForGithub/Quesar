package com.pet.quesar.android;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.vending.billing.IInAppBillingService;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.pet.quesar.Quesar;
import com.pet.quesar.handler.AdsController;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class AndroidLauncher extends AndroidApplication implements AdsController {
	
	public final int BILLING_RESULT_OK = 0;
	IInAppBillingService mService;
	ServiceConnection mServiceConn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name){
			mService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service){
			mService = IInAppBillingService.Stub.asInterface(service);
		}
	};;
	String inappid =  "premiumUpgrade";
	
	protected AdView adView;
	
	private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;

    protected Handler handler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_ADS:
                {
                    adView.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE_ADS:
                {
                    adView.setVisibility(View.GONE);
                    break;
                }
            }
            return true;
        }
    });
	
    
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent serviceIntent =
			      new Intent("com.android.vending.billing.InAppBillingService.BIND");
			  serviceIntent.setPackage("com.android.vending");
			  bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
		
		ArrayList<String> skuList = new ArrayList<String> ();
		skuList.add("premiumUpgrade");
		skuList.add("gas");
		Bundle querySkus = new Bundle();
		querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
		
		try {
			final Bundle skuDetails = mService.getSkuDetails(3,
					   getPackageName(), "inapp", querySkus);
		
		
			Button purchaseBtn = (Button) findViewById(0x7f08);
			purchaseBtn.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				Thread thread = new Thread(){
					@Override
					public void run(){
						try {
							int response = skuDetails.getInt("RESPONSE_CODE");
							if(response == 0){
								ArrayList<String> responseList = skuDetails.getStringArrayList("DETAILS_LIST");
								
								for(String thisResponse : responseList){
									JSONObject object = new JSONObject(thisResponse);
									String sku = object.getString("productId");
									String price = object.getString("price");
									
									if(sku.equals(inappid)){
										System.out.println("price " + price);
										Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(), sku, "inapp", "bGoa+V7g/yq/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
										PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
										startIntentSenderForResult(
												pendingIntent.getIntentSender(), 1001,
												new Intent(), Integer.valueOf(0),
												Integer.valueOf(0), Integer.valueOf(0));
									}
								}
							}
						}
						catch(RemoteException e){
							e.printStackTrace();
						} catch(JSONException e){
							e.printStackTrace();
						} catch(SendIntentException e){
							e.printStackTrace();
						}
					}
				};
				
			}
		});
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		//inAppPurchase FINISHED
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
	    config.useAccelerometer = false;
	    config.useCompass = false;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		
		// Create a RelativeLayout as the main layout and add the gameView.
        RelativeLayout mainLayout = new RelativeLayout(this);
        
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mainLayout.setLayoutParams(params);
        
        View gameView = initializeForView(new Quesar(this),config);
        
        
		// Create and load the AdView.
        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        // Add adView to the bottom of the screen.
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
        			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        adView.setLayoutParams(adParams);
        adView.setBackgroundColor(Color.TRANSPARENT);
        
        mainLayout.addView(gameView);
        mainLayout.addView(adView);
        
        setContentView(mainLayout);
        
        adView.loadAd(new AdRequest.Builder()
		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
        
        /* To interact with the game
         * Gdx.app.postRunnable(new Runnable(){
         * 	@Override
         * 	public void run(){
         * 		game.callMethod();
         * 	}
         * })
         */
        
	}
	
	@Override
    public void showAds(boolean show) {
       handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
    }
	
	public void getOwnedItems(){
		Bundle ownedItems;
		try{
			ownedItems = mService.getPurchases(3, getPackageName(), "inapp", null);
			int response = ownedItems.getInt("RESPONSE_CODE");
			if(response == 0){
				ArrayList<String> ownedSkus =
						ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
				
				ArrayList<String> purchaseDataList =
						ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
				
				ArrayList<String> signatureList =
						ownedItems.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
				
				String continuationToken =
						ownedItems.getString("INAPP_CONTINUATION_TOKEN");
			
				for(int i = 0; i < purchaseDataList.size(); ++i){
					String purchaseData = purchaseDataList.get(i);
					String signature = signatureList.get(i);
					String sku = ownedSkus.get(i);
					
					//do something with this purchase information
					// e.g. display the updated list of products owned by user
					
				}
				
				// if continuationToken !+ null, call getPurchases again
				// and pass in the token to retrieve more items
			
			}
		} catch (RemoteException e){
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == 1001){
			int responseCode = data.getIntExtra("RESPOnSE_CODE",  0);
			String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
			String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");
			
			if(resultCode == RESULT_OK){
				try {
					JSONObject jo = new JSONObject(purchaseData);
					String sku = jo.getString(inappid);
					Toast.makeText(
							AndroidLauncher.this,
							"You have bought the" + sku
									+ ". Excellent choice!",
							Toast.LENGTH_LONG).show();
				} catch(JSONException e){
					System.out.println("Failed to parse purchase data.");
					e.printStackTrace();
				}
			}
		}
	}
	
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		if(mServiceConn != null){
			unbindService(mServiceConn);
		}
	}
	//IN APP PURCHASE
}

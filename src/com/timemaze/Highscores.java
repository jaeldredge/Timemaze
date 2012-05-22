package com.timemaze;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.view.WindowManager;


public class Highscores extends Activity {
	
	private static final String TAG = TimemazeActivity.class.getSimpleName();

	int easyHighscore;
	int mediumHighscore;
	int hardHighscore;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	
    	 // No Title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //making full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	Log.d(TAG, "Highscores: onCreate start");
    	setContentView(R.layout.highscore);
    	Log.d(TAG, "highscores");
    	
    	
    	
    	
    	Bundle extras = getIntent().getExtras(); 
    	//get values
    	if(extras !=null)
    	{
    		easyHighscore = extras.getInt("easHig");
    		mediumHighscore = extras.getInt("medHig");
    		hardHighscore = extras.getInt("harHig");
    	}
    	else{
    		easyHighscore = 0;
    		mediumHighscore = 0;
    		hardHighscore = 0;
    	}
    	
    	//write out scores
    	TextView e = (TextView) findViewById(R.id.easyValue);
    	e.setText(""+easyHighscore+"");
    	
    	TextView m = (TextView) findViewById(R.id.mediumValue);
    	m.setText(""+mediumHighscore+"");
    	
    	TextView h = (TextView) findViewById(R.id.hardValue);
    	h.setText(""+hardHighscore+"");
    	
    	//go back
    	Button back = (Button) findViewById(R.id.backtooptions);
        
    	back.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {

    			//ends and restarts
    			//Intent intent = new Intent();
    			//returns with 3,4, or 5 depending  on size;
    			setResult(6);
    			finish();
            	
    		}
        });
        
    }
    
    
    
    @Override
     protected void onDestroy() {
      Log.d(TAG, "Highscores: Destroying...");
      super.onDestroy();
     }
     
     @Override
     protected void onStop() {
      Log.d(TAG, "Game: Stopping...");
      super.onStop();
     }

}
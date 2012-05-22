package com.timemaze;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.view.WindowManager;





public class Options extends Activity {
	
	private static final String TAG = TimemazeActivity.class.getSimpleName();
	int size;
	int startTime;
	int easyTime;
	int mediumTime;
	int hardTime;
	
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	
    	 // No Title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //making full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	
    	
    	
    	
    	
    	Bundle extras = getIntent().getExtras(); 
    	//int size;
    	//getting values from previous activity
    	if(extras !=null)
    	{
    		//adjust by two because of how it displays
    		size = extras.getInt("size");
    		easyTime = extras.getInt("easTim")-2;
    		mediumTime = extras.getInt("medTim")-2;
    		hardTime = extras.getInt("harTim")-2;
    		
    	}
    	else{
    		size = 0;
    		easyTime = 15;
    		mediumTime = 20;
    		hardTime = 25;
    		
    	}
    	
    	
    	displayMenu();
        
    }
    
    //do all the display stuff
    public void displayMenu(){
    	Log.d(TAG, "Options: onCreate start");
    	setContentView(R.layout.options);
    	Log.d(TAG, "options");
    	final TextView time = (TextView) findViewById(R.id.time);
    	
    	final Button easy = (Button) findViewById(R.id.easy);
    	final Button medium = (Button) findViewById(R.id.medium);
    	final Button hard = (Button) findViewById(R.id.hard);
    	if(size == 1){
    		easy.setBackgroundColor(Color.YELLOW);
    		medium.setBackgroundColor(Color.GRAY);
    		hard.setBackgroundColor(Color.GRAY);
    		time.setText(" "+easyTime+" ");
    	} else if(size == 2){
    		easy.setBackgroundColor(Color.GRAY);
    		medium.setBackgroundColor(Color.YELLOW);
    		hard.setBackgroundColor(Color.GRAY);
    		time.setText(" "+mediumTime+" ");
    	} else {
    		easy.setBackgroundColor(Color.GRAY);
    		medium.setBackgroundColor(Color.GRAY);
    		hard.setBackgroundColor(Color.YELLOW);
    		time.setText(" "+hardTime+" ");
    	}
    	
    	easy.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {

    			size = 1;
    			easy.setBackgroundColor(Color.YELLOW);
        		medium.setBackgroundColor(Color.GRAY);
        		hard.setBackgroundColor(Color.GRAY);
        		time.setText(" "+easyTime+" ");
    		}
        });
    	
    	medium.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {

    			size = 2;
    			easy.setBackgroundColor(Color.GRAY);
        		medium.setBackgroundColor(Color.YELLOW);
        		hard.setBackgroundColor(Color.GRAY);
        		time.setText(" "+mediumTime+" ");
    		}
        });
    	
    	hard.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {

    			size = 3;
    			easy.setBackgroundColor(Color.GRAY);
        		medium.setBackgroundColor(Color.GRAY);
        		hard.setBackgroundColor(Color.YELLOW);
        		time.setText(" "+hardTime+" ");
    		}
        });
    	
    	Button backtomenu = (Button) findViewById(R.id.backtomenu);
        
    	backtomenu.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {

    			//ends and restarts
    			Intent intent = new Intent();
    			intent.putExtra("easTim",easyTime+2);
            	intent.putExtra("medTim",mediumTime+2);
            	intent.putExtra("harTim",hardTime+2);
            	intent.putExtra("size",size);
    			//returns with 3,4, or 5 depending  on size;//now just 3
    			setResult(3, intent);
    			finish();
            	
    		}
        });
    	

    	
    	Button plus = (Button) findViewById(R.id.plus);
    	Button minus = (Button) findViewById(R.id.minus);
    	plus.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			if(easyTime < 60 && size == 1){
    				easyTime ++;
    				time.setText(" "+easyTime+" ");
    			}
    			if(mediumTime < 60 && size == 2){
    				mediumTime ++;
    				time.setText(" "+mediumTime+" ");
    			}
    			if(hardTime < 60 && size ==3){
    				hardTime ++;
    				time.setText(" "+hardTime+" ");
    			}
    		}
        });
    	minus.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			if(easyTime > 5 && size == 1){
    				easyTime --;
    				time.setText(" "+easyTime+" ");
    			}
    			if(mediumTime > 5 && size == 2){
    				mediumTime --;
    				time.setText(" "+mediumTime+" ");
    			}
    			if(hardTime > 5 && size ==3){
    				hardTime --;
    				time.setText(" "+hardTime+" ");
    			}
    		}
        });
    }
    
    
    //when returning, in this case, from highscores
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
    	displayMenu();
    }
    
    
    @Override
     protected void onDestroy() {
      Log.d(TAG, "Options: Destroying...");
      super.onDestroy();
     }
     
     @Override
     protected void onStop() {
      Log.d(TAG, "Game: Stopping...");
      super.onStop();
     }

}
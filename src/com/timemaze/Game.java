package com.timemaze;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class Game extends Activity {
	
	private static final String TAG = TimemazeActivity.class.getSimpleName();
	boolean end;
	maingame current;
	
	public Game(){
		Log.d(TAG, "Game: constructor");
	}
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	end = false;
    	
    	 // No Title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //making full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	Log.d(TAG, "Game: onCreate start");
    	
    	Log.d(TAG, "Game: onCreate a");
    	Bundle extras = getIntent().getExtras(); 
    	int mazesCompleted;
    	int maxtime;
    	int size;
    	int easyTime;
    	int mediumTime;
    	int hardTime;
    	if(extras !=null)
    	{
    		mazesCompleted = extras.getInt("mazesCompleted");
    		//maxtime = extras.getInt("maxtime");
    		size = extras.getInt("size");
    		easyTime = extras.getInt("easTim");
    		mediumTime = extras.getInt("medTim");
    		hardTime = extras.getInt("harTim");
    	}
    	else{
    		mazesCompleted = 0;
    		//maxtime = 42;
    		size = 2;
    		easyTime = 17;
    		mediumTime = 22;
    		hardTime = 27;
    		
    	}
    	
    	//assign maxtime
    	switch(size){
    		case 1:
    			maxtime = easyTime;
    			break;
    		case 2:
    			maxtime = mediumTime;
    			break;
    		case 3:
    			maxtime = hardTime;
    			break;
    		default:
    			maxtime = 15;
    			break;
    	}
    	
    	
    	current = new maingame(this,mazesCompleted,size);
    	Log.d(TAG, "Game: onCreate b");
    	setContentView(current);
    	Log.d(TAG, "Game: View added");
    	
    	Log.d(TAG, "Game: onCreate end");
    	
    	//countdown timer
    	
    	/*if(maxtime == 500*mazesCompleted){
    		Intent intent = new Intent();
			setResult(1, intent);
			current.shutDown();
			finish();
    	}*/
    	new CountDownTimer(maxtime*1000-1000*mazesCompleted, 1000) {

   	     public void onTick(long millisUntilFinished) {
   	    	 
   	    	current.drawTime(millisUntilFinished);
   	        // c.drawText("Time Left: \n" + millisUntilFinished / 1000,0,height/10,blue);
   	     }

   	     public void onFinish() {
   	    	 
   	    	//current.drawEndTime();
   	    	Log.d(TAG, "Game: timeout finished");
			Intent intent = new Intent();
			setResult(0, intent);
			current.shutDown();
			finish();

   	     }
   	  }.start();
    	
    	
    	
    	//setContentView(R.layout.maze);
        //Log.d(TAG, "maze layout");
        //Button finishedmaze = (Button) findViewById(R.id.finished);
        //finishedmaze.setVisibility(4);
        //current.setClickable(true);
        current.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent m) {
                // Perform action on click
            	if(!end){
            		Log.d(TAG, "Game: Click");
            		current.onTouchEvent(m);
            		if(current.isSolved()){
            			Log.d(TAG, "Game: finished");
            			Intent intent = new Intent();
            			setResult(1, intent);
            			
            			//onStop();
            			//onDestroy();
            			finish();
            			end = true;
            		}
            		
            	}
            	return true;
            }
        });
        
    }
    
    
    @Override
     protected void onDestroy() {
      Log.d(TAG, "Game: Destroying...");
      super.onDestroy();
      
     }
     
     @Override
     protected void onStop() {
      Log.d(TAG, "Game: Stopping...");
      super.onStop();
     }

}
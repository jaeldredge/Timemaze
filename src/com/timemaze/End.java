package com.timemaze;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.view.WindowManager;


public class End extends Activity {
	int mazesCompleted;
	int easyHighscore;
	int mediumHighscore;
	int hardHighscore;
	int size;
	int isHighscore;
	
	private static final String TAG = TimemazeActivity.class.getSimpleName();
	
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
    	
    	if(extras !=null)
    	{
    		mazesCompleted = extras.getInt("mazesCompleted");
    		easyHighscore = extras.getInt("easHig");
    		mediumHighscore = extras.getInt("medHig");
    		hardHighscore = extras.getInt("harHig");
    		size = extras.getInt("size");
    		isHighscore = extras.getInt("isHighscore");
    	}
    	else{
    		mazesCompleted = 0;
    		easyHighscore = 0;
    		mediumHighscore = 0;
    		hardHighscore = 0;
    		size = 1;
    		isHighscore = 0;
    	}
    
    	
    	
    	displayEnd();
    	
        
    }
    
    //when returning, in this case, from highscores
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
    	displayEnd();
    }
    
    public void displayEnd(){
    	Log.d(TAG, "End: onCreate start");
    	setContentView(R.layout.end);
    	Log.d(TAG, "end");
    	
    	TextView score = (TextView) findViewById(R.id.score);
    	score.setText(""+mazesCompleted+"");
    	
    	TextView t = (TextView) findViewById(R.id.console);
    	switch (mazesCompleted){
    		case 0:
    			t.setTextSize(28);
    	    	t.setText("No mazes solved.\nIf its to fast for you, try increasing the starting time.");
    			break;
    		case 1:
    			t.setText("Come on, you can do better than a mere");
    			break;
    		case 2:
    		case 3:
    		case 4:
    		case 5:
    			t.setText("Good job,\nbet you can do better.");
    			break;
    		case 6:
    		case 7:
    		case 8:
    		case 9:
    		case 10:
    			t.setText("Impressive, see if you can set a new highscore.");
    			break;
    		default:
    			break;
    			
    	}

    	
    	if(isHighscore == 1){
    		t.setText("Congragulations,\nyou set a new highscore of");
    	}
    	Log.d(TAG,"end set text");
    	
    	Button playgame = (Button) findViewById(R.id.menu);
        
        	playgame.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {

            	//ends and restarts
        			Intent intent = new Intent();
        			setResult(2, intent);
        			finish();
            	
            }
        });
    	
        	Button viewHighscores = (Button) findViewById(R.id.highscores);
        	
        	viewHighscores.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {

        			//ends and restarts
        			Intent intent = new Intent(v.getContext(), Highscores.class);
        			intent.putExtra("easHig",easyHighscore);
                	intent.putExtra("medHig",mediumHighscore);
                	intent.putExtra("harHig",hardHighscore);
        			startActivityForResult(intent,0);
                	
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
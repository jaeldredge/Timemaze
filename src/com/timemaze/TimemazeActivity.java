package com.timemaze;
//This application is  copyrighted by James Eldredge
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.view.WindowManager;


public class TimemazeActivity extends Activity {
	
	private static final String TAG = TimemazeActivity.class.getSimpleName();
	int mazesCompleted;
	int size;//easy = 1,medium = 2 , hard = 3;
	//int maxtime;//15,25,35
	int easyHighscore;
	int mediumHighscore;
	int hardHighscore;
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
        
        
        easyHighscore = 0;
        mediumHighscore = 0;
        hardHighscore = 0;
        size = 2;
        easyTime = 12;
        mediumTime = 17;
        hardTime = 22;
        
      	//maxtime = 15;
      	
        
        startGame();
        
        
        
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data){
    	//Intent myIntent = new Intent(this, Game.class);
        //startActivityForResult(myIntent, 0);
    	//Log.d(TAG, "END: EEEEEEEEENNNNNNNNNNDDDDDDDDD");
    	
    	
    	switch(resultCode){
    		case 1:
    			nextLevel();
    			break;
    		case 0:
    			//endGame();
    			int isHighscore = 0;
    			if(size == 1 && mazesCompleted > easyHighscore){
    				easyHighscore = mazesCompleted;
    				isHighscore = 1;
    			}
    			if(size == 2 && mazesCompleted > mediumHighscore){
    				mediumHighscore = mazesCompleted;
    				isHighscore = 1;
    			}
    			if(size == 3 && mazesCompleted > hardHighscore){
    				hardHighscore = mazesCompleted;
    				isHighscore = 1;
    			}
    			Intent myIntent = new Intent(getBaseContext(), End.class);
    			myIntent.putExtra("size",size);
    			myIntent.putExtra("easHig",easyHighscore);
            	myIntent.putExtra("medHig",mediumHighscore);
            	myIntent.putExtra("harHig",hardHighscore);
            	myIntent.putExtra("mazesCompleted",mazesCompleted);
            	myIntent.putExtra("isHighscore",isHighscore);
            	startActivityForResult(myIntent, 0);
            	break;
    		case 2:
    			startGame();
    			break;
    			//from options menu
    			//were separate cases until learned how to use intents
    		case 3:
    		case 4:	
    		case 5:
    			Bundle extras = data.getExtras(); 
    			if(extras !=null)
    	    	{
    	    		size = extras.getInt("size");
    	    		easyTime = extras.getInt("easTim");
    	    		mediumTime = extras.getInt("medTim");
    	    		hardTime = extras.getInt("harTim");
    	    		
    	    	}
    	    	else{
    	    		size = 0;
    	    		easyTime = 17;
    	    		mediumTime = 22;
    	    		hardTime = 27;
    	    		
    	    	}
    			startGame();
    			break;
    			//from Highscores
    		case 6:
    			startGame();
    			break;
    		default:
    			//restartGame();
    				break;
    	}
    	
    	
    	
    	
    	
    	
    	
    	//random();
    }
    
    //trying to restart the game
    public void startGame(){
    	setContentView(R.layout.main);
        Log.d(TAG, "main layout");

        //rules text
        TextView rules = (TextView) findViewById(R.id.rules);
        rules.setText(Html.fromHtml("<font color=red>"+ "Here are the rules:"+ "<br />"+
            	"1. Drag a line from the "+"</font>"+ "<font color=yellow>"+"yellow"+"</font>"+ "<font color=red>"+" dot to the"+"</font>"+"<font color=blue>"+" blue"+"</font>"+"<font color=red>"+" dot."+"<br />"+
            	"2. Do it before the clock ticks down to zero."+"<br />"+
            	"3. The alloted time per maze will get progressively shorter."+ "<br />"+"<br />"+
            	"</font>"+"<font color=#FF8C00>"+"See how many you can complete in a row."+ "</font>"));
        
        
        //options code
        Button options = (Button) findViewById(R.id.optionsMenu);
        options.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(v.getContext(), Options.class);
            	myIntent.putExtra("size",size);
            	myIntent.putExtra("easTim",easyTime);
            	myIntent.putExtra("medTim",mediumTime);
            	myIntent.putExtra("harTim",hardTime);
            	Log.d(TAG,"e "+easyHighscore + " m "+mediumHighscore +" h "+hardHighscore + "argh");
                startActivityForResult(myIntent, 0);
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
        
       
        
        //end options code
        Button playgame = (Button) findViewById(R.id.play);
        playgame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	
            	//startgame();
            	//transition();
            	//Intent launchGame = new Intent(v.getContext(),Game.class);
            	//startActivity(launchGame);
            	// game = new Game();
            	//game.onCreate();
            	//if(game.currentmaingame.isSolved())
            		//game.onDestroy();
            	mazesCompleted = 0;
            	
            	Intent myIntent = new Intent(v.getContext(), Game.class);
            	myIntent.putExtra("mazesCompleted",mazesCompleted);
            	myIntent.putExtra("easTim",easyTime);
            	myIntent.putExtra("medTim",mediumTime);
            	myIntent.putExtra("harTim",hardTime);
            	//myIntent.putExtra("maxtime",maxtime);
            	myIntent.putExtra("size",size);
                startActivityForResult(myIntent, 0);
            	Log.d(TAG, "END: EEEEEEEEENNNNNNNNNNDDDDDDDDD");
            }
        });
    }
    
    public void endGame(){
    	setContentView(R.layout.end);
    	Log.d(TAG, "end");
    	
    	
    	Button playgame = (Button) findViewById(R.id.menu);
        
        	playgame.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {

            	//ends and restarts
        			return;
            	
            }
        });

    }

    
    public void nextLevel(){
    	
    	setContentView(R.layout.transition);
    	Log.d(TAG, "transition");
    	
    	TextView encouragement = (TextView) findViewById(R.id.transition);
    	Random rand = new Random();
    	int messageid = rand.nextInt(14);
    	
    	if(mazesCompleted == 0){
    		encouragement.setText("Great!\nFor each succesive maze, you have one less second.");
    	}
    	else{
    		if(mazesCompleted == 1){
    			encouragement.setText("Fantastic!\nSee how many you can complete in a row.");
    		}
    		else{
    			switch(messageid){
    			case 1:
    				encouragement.setText("Amazing!\nSee if you can keep going.");
    				break;
    			case 2:
    				encouragement.setText("Never before have I seen such maze solving prowess.");
    				break;
    			case 3:
    				encouragement.setText("Magnificent!\n I am awestruck.");
    				break;
    			case 4:
    				encouragement.setText("Incredible! You are a great maze solver.");
    				break;
    			case 5:
    				encouragement.setText("Magnificent!\nWonderful!\nTerrific!");
    				break;
    			case 6:
    				encouragement.setText("Way to go!\nKeep it up.");
    				break;
    			case 7:
    				encouragement.setText("Splendid!\nYou have prodigous talent.");
    				break;
    			case 8:
    				encouragement.setText("Awesome,\nget ready for the next maze!");
    				break;
    			case 9:
    				encouragement.setText("You have won my approval!!!");
    				break;
    			case 10:
    				encouragement.setText("Flat out amazing.");
    				break;
    			case 11:
    				encouragement.setText("Hands down, superbly done.");
    				break;
    			case 12:
    				encouragement.setText("What wonderful work!");
    				break;
    			case 13:
    				encouragement.setText("Your skill is unquestionable.");
    				break;
    				
    			}
    		}
    	}
    	
    	
    	Button playgame = (Button) findViewById(R.id.next);
        
        playgame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mazesCompleted ++;
            	Intent myIntent = new Intent(v.getContext(), Game.class);
            	myIntent.putExtra("mazesCompleted",mazesCompleted);
            	myIntent.putExtra("easTim",easyTime);
            	myIntent.putExtra("medTim",mediumTime);
            	myIntent.putExtra("harTim",hardTime);
            	//myIntent.putExtra("maxtime",maxtime);
            	myIntent.putExtra("size",size);
                startActivityForResult(myIntent, 0);
            	Log.d(TAG, "END: EEEEEEEEENNNNNNNNNNDDDDDDDDD");
            	//setContentView(R.layout.random);
            	
            }
        });
    	
    }
    
    
    /*void random(){
    	// SLEEP 2 SECONDS HERE ...
        Handler handler = new Handler(); 
        handler.postDelayed(new Runnable() { 
             public void run() { 
            	 setContentView(R.layout.random);
             } 
        }, 2000);
        setContentView(R.layout.random);
    }*/
    
    /*void startgame(){
    //code goes here
    	maingame current;
    	//do {
    		current = new maingame(this);
    		setContentView(current);
    		Log.d(TAG, "View added");
    		//while(!current.isSolved()){
  
 //   		}
   // 	} while(true);
    	
    }
    void transition(){
    	setContentView(R.layout.transition);
    	Log.d(TAG, "transition");
    }*/
    
    
    @Override
     protected void onDestroy() {
      Log.d(TAG, "Timemaze Destroying...");
      super.onDestroy();
     }
     
     @Override
     protected void onStop() {
      Log.d(TAG, "Stopping...");
      super.onStop();
     }

}
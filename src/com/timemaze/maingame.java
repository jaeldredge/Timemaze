package com.timemaze;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.Color;
import com.timemaze.maze.*;
import android.view.WindowManager;
import android.graphics.Paint;


public class maingame extends SurfaceView implements
  SurfaceHolder.Callback {

	private static final String TAG = maingame.class.getSimpleName();
	
	private MainThread thread;
	//private rand rand;
	private Maze maze;
	private boolean solved;
	private boolean movable;
	//private boolean timeout;
	
	Bitmap b;// = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
	Canvas c; //= new Canvas(b);
	
	//int displaywidth;
	//int displayheight;
	int mazesCompleted; 
	int sz;
	int mzdim;// holds dimensions of maze matrix.  # maze squares = (mzdim+1)/2, square
	
 public maingame(Context context,int completed,int size) {
  super(context);
  // adding the callback (this) to the surface holder to intercept events
  getHolder().addCallback(this);
  
  //create droid and load bitmap
  //rand = new rand(BitmapFactory.decodeResource(getResources(),R.drawable.rand),35,35);
  
  //create new maze
  //maze = new Maze(Bitmap.createBitmap(10,10, Bitmap.Config.ARGB_8888));
  //Log.d(TAG, "Width is " + getWidth() + "and height is " + getHeight());
  
  //create the game loop thread
  thread = new MainThread(getHolder(), this);
  movable = true;
  sz = size;
  // make the GamePanel focusable so it can handle events
  setFocusable(true);
  
  Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
  int displaywidth = display.getWidth();
  int displayheight = display.getHeight();
  b = Bitmap.createBitmap(displaywidth, displayheight, Bitmap.Config.ARGB_8888);
  c = new Canvas(b);
  
  mazesCompleted = completed;
  Log.d(TAG,"mazesCompleted set to "+mazesCompleted);
  
  prepareMaze();
  solved = false;
  //timeout = false;
  
 }

 //@Override
 public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

 }

 //@Override
 public void surfaceCreated(SurfaceHolder holder) {
	 // at this point the surface is created and
	 // we can safely start the game loop
	 thread.setRunning(true);
	 thread.start();
	 //c.drawColor(100);
	 //Log.d(TAG,"color");
 }
 
 //@Override
 public void surfaceDestroyed(SurfaceHolder holder) {
	 Log.d(TAG, "Surface is being destroyed");
	 //tell the thread to shut down and wait for it to finish
	 //this is a clean shutdown
	 boolean retry = true;
	 while (retry){
		 try{
			 thread.join();
			 retry = false;
		 } catch(InterruptedException e) {
			 //try again shutting down the thread
		 }
	 }
	 Log.d(TAG, "Thread was shut down cleanly");
 }
 
 //@Override
 public boolean onTouchEvent(MotionEvent event) {
	 /*if (event.getAction() == MotionEvent.ACTION_DOWN) {
	
		 //maze.touchdown((int)event.getX(),(int)event.getY()- ((int)c.getHeight())/5);
		 //Log.d(TAG, "height, y , adjusted y: " +c .getHeight()+", " + (int)event.getY()+ ", " + (int)event.getX()+", "+ ((int)event.getY()-c.getHeight()/5)+" ");
	 } */
	 
	 if (movable && (event.getAction() == MotionEvent.ACTION_MOVE||event.getAction() == MotionEvent.ACTION_DOWN)) {
		 //the gestures
		 int mzSize = (mzdim+1)/2;
		 Log.d(TAG, "mzSize is " + mzSize);
		 int xMove =(int)c.getWidth()/mzSize;
		 Log.d(TAG, "xMove is " + xMove);
		 int yMove =((int)c.getHeight()-(int)c.getHeight()/5)/(mzSize+2);
		 Log.d(TAG, "yMove is " + yMove);
		 int touchX = (int)event.getX();
		 Log.d(TAG, "touchX is " + touchX);
		 int touchY = (int)event.getY()-(int)c.getHeight()/5;
		 Log.d(TAG, "touchY is " + touchY);
		 int xTouchSquare = touchX/(xMove);
		 Log.d(TAG, "xTouchSquare is " + xTouchSquare);
		 int yTouchSquare = touchY/(yMove);
		 Log.d(TAG, "yTouchSquare is " + yTouchSquare);
		 int xIsSquare = maze.getX();
		 Log.d(TAG, "xIsSquare is " + xIsSquare);
		 int yIsSquare = maze.getY();
		 Log.d(TAG, "yIsSquare is " + yIsSquare);
		 int xIs =xIsSquare * xMove + xMove/2;
		 Log.d(TAG, "xIs is " + xIs);
		 int yIs =yIsSquare* yMove + yMove/2;
		 Log.d(TAG, "yIs is " + yIs);
		 if(xTouchSquare == xIsSquare && yTouchSquare != yIsSquare){
			 Log.d(TAG,"passed first text");
			 if(yTouchSquare < yIsSquare){
				for(int pos = yIs; pos/(yMove) >= yTouchSquare ; pos -=yMove){
					solved = maze.move((int)event.getX(),pos);
					 if(solved) {
						
						 //b = Bitmap.createBitmap(displaywidth, displayheight, Bitmap.Config.ARGB_8888);
						 //c = new Canvas(b);
						 //c.drawColor(Color.BLACK);
						 //prepareMaze();
						 solved = true;
						 thread.setRunning(false);
					 }
				}
			 }
			 else{
				 Log.d(TAG,"passed second text");
				 Log.d(TAG,"posSquare "+yIs/(yMove)+" yTouchSquare "+yTouchSquare);
				 for(int pos = yIs; pos/(yMove)  <= yTouchSquare ; pos +=yMove){
					 Log.d(TAG,"passed third text");
						solved = maze.move((int)event.getX(),pos);
						 if(solved) {
							
							 //b = Bitmap.createBitmap(displaywidth, displayheight, Bitmap.Config.ARGB_8888);
							 //c = new Canvas(b);
							 //c.drawColor(Color.BLACK);
							 //prepareMaze();
							 solved = true;
							 thread.setRunning(false);
						 }
				 }
			 }
		 }
			 if(xTouchSquare != xIsSquare && yTouchSquare == yIsSquare){
				 if(xTouchSquare < xIsSquare){
					for(int pos = xIs; pos/(xMove) >= xTouchSquare ; pos -=xMove){
						solved = maze.move(pos,(int)event.getY()-((int)c.getHeight())/5);
						 if(solved) {
							
							 //b = Bitmap.createBitmap(displaywidth, displayheight, Bitmap.Config.ARGB_8888);
							 //c = new Canvas(b);
							 //c.drawColor(Color.BLACK);
							 //prepareMaze();
							 solved = true;
							 thread.setRunning(false);
						 }
					}
				 }
				 else{
					 for(int pos = xIs; pos/(xMove) <= xTouchSquare ; pos +=xMove){
							solved = maze.move(pos,(int)event.getY()-((int)c.getHeight())/5);
							 if(solved) {
								
								 //b = Bitmap.createBitmap(displaywidth, displayheight, Bitmap.Config.ARGB_8888);
								 //c = new Canvas(b);
								 //c.drawColor(Color.BLACK);
								 //prepareMaze();
								 solved = true;
								 thread.setRunning(false);
							 }
					 }
				 }
			 }
	 	 
		 ///////old code
		 /*
		 solved = maze.move((int)event.getX(),(int)event.getY()-((int)c.getHeight())/5);
		 if(solved) {
			
			 //b = Bitmap.createBitmap(displaywidth, displayheight, Bitmap.Config.ARGB_8888);
			 //c = new Canvas(b);
			 //c.drawColor(Color.BLACK);
			 //prepareMaze();
			 solved = true;
			 thread.setRunning(false);
			 

		 }*/
		 
	 }
	 
	 if (event.getAction() == MotionEvent.ACTION_UP) {
		maze.liftoff();
	 }
	 
	 return true;
 }
 
 @Override
 protected void onDraw(Canvas canvas) {
	 //canvas = c;
	 
	 maze.updateScreen(c,c.getHeight());
	 //c.drawColor(Color.MAGENTA);
	 canvas.drawBitmap(b, 0,0, null);
	 //canvas.drawColor(Color.MAGENTA);
 }
 
 public void prepareMaze() {
	 //fills the canvas with black
	 c.drawColor(Color.BLACK);
	 //rand.draw(canvas);
	 
	 final int width = c.getWidth();
	 final int height = c.getHeight();
	 
	 //int mzdim;
	 switch(sz){
	 case 1:
		 mzdim = 9;
		 break;
	 case 2:
		 mzdim = 13;
		 break;
	 case 3:
		 mzdim = 17;
		 break;
	 default:
		 mzdim = 13;
			 break;
			 
	 }
		 
		 
	 maze = new Maze(Bitmap.createBitmap(c.getWidth(),c.getHeight()*4/5, Bitmap.Config.ARGB_8888),mzdim,mzdim);
	 
	 maze.drawOriginal(c, width, height);
	 
	 
	 //line
	 final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
	 p.setColor(Color.RED);
	 p.setStyle(Paint.Style.STROKE); 
	 final int lnsz = 4;
	 p.setStrokeWidth(2*lnsz/*1 /getResources().getDisplayMetrics().density*/);
	 c.drawLine(0, height/5 - 4*lnsz, width, height/5 - 4*lnsz, p);
	 c.drawLine(lnsz, 0, lnsz, height/5 - 4*lnsz, p);
	 c.drawLine(0, lnsz, width, lnsz, p);
	 c.drawLine(width-lnsz, 0, width-lnsz, height/5 - 4*lnsz, p);
	 c.drawLine(width/2, 0, width/2, height/5-4*lnsz, p);
	 //experimental timer code
	 Paint blue = new Paint(Paint.ANTI_ALIAS_FLAG);
	 blue.setColor(Color.BLUE);
	 blue.setStyle(Paint.Style.STROKE);
	 blue.setTextSize(27);
	 c.drawText("Mazes Completed: ",41*width/80,height/20,blue);
	 blue.setTextSize(80);
	 Log.d(TAG,"mazesCompleted is "+mazesCompleted);
	 c.drawText(""+mazesCompleted*1,9*width/14,height/7,blue);
	 
 }
 /*public boolean timeout(){
	 return timeout;
 }*/
 
 public boolean isSolved(){
	 return solved;
 }
 
 public void drawTime(long millisUntilFinished){
	 final int width = c.getWidth();
	 final int height = c.getHeight();
	 final int lnsz = 4;
	 final Rect r = new Rect(2*lnsz,2*lnsz,width/2-2*lnsz,height/5-6*lnsz);
	 final Paint blue = new Paint(Paint.ANTI_ALIAS_FLAG);
	 blue.setColor(Color.BLUE);
	 blue.setStyle(Paint.Style.STROKE);
	 blue.setTextSize(30);
	 final Paint black = new Paint(Paint.ANTI_ALIAS_FLAG);
	 black.setColor(Color.BLACK);
	 black.setStyle(Paint.Style.FILL);
	 blue.setTextSize(35);
   	 c.drawRect(r, black);
   	 c.drawText("Time Left: ",width/13,height/20,blue);
   	 blue.setTextSize(80);
   	 long outputTime = millisUntilFinished /1000 -1;
   	 if ((int)outputTime == 0){
   		 setMovable(false);
   	 }
   	 //long sec = millisUntilFinished /1000 - 10*outputTime;
   	 c.drawText(""+ outputTime ,width/7,height/7,blue);
 }
 
 public void shutDown(){
	 thread.setRunning(false);
 }
 
 public void setMovable(boolean value) {
	 movable = value;
 }
 
}

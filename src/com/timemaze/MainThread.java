package com.timemaze;

import android.util.Log;
import android.view.SurfaceHolder;
import android.graphics.Canvas;

public class MainThread extends Thread {
	
	private static final String TAG = MainThread.class.getSimpleName();
	
	private SurfaceHolder surfaceHolder;
	private maingame gamePanel;
	//flag to hold the game state
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public MainThread(SurfaceHolder surfaceHolder, maingame gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void run() {
		Canvas canvas;
		long tickCount = 0L;
		Log.d(TAG, "Starting game Loop");
		while(running) {
			canvas = null;
			tickCount++;
			//update game state
			//render state to the screen
			//try locking the canvas for the exclusive pixel editing on the surface
			try{
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					//update the game state
					//draw the canvas on the panel
					this .gamePanel.onDraw(canvas);
					
				}
			} finally {
				//in case of an exception the surface is not left in 
				//an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			} //end finally
		}
		Log.d(TAG,"Game loop executed "+ tickCount + " times");
	}
}
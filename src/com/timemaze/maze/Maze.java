package com.timemaze.maze;

import android.graphics.Bitmap;
import android.graphics.Canvas;
//import android.graphics.Rect;
//import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.Random;
import java.util.Stack;

import com.timemaze.maingame;
//import android.view.View;
import android.util.Log;


public class Maze {
	private static final String TAG = maingame.class.getSimpleName();
	Paint green = new Paint(Paint.ANTI_ALIAS_FLAG);
	Paint blue = new Paint(Paint.ANTI_ALIAS_FLAG);
	Paint yellow = new Paint(Paint.ANTI_ALIAS_FLAG);
	Paint yline = new Paint(Paint.ANTI_ALIAS_FLAG);
	Paint black = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Bitmap bitmap;
	private int lnsz = 4;
	
	private int matrix [][];
	private int mw; // matrix width
	private int mh; // matrix height
	private int start;//locations of entrances and exits
	private int end;
	//private boolean touched; //if yellow circle touched
	private int yx; //yellow x coordinate
	private int yy; //yellow y coordinate
	
	public Maze (Bitmap bitmap, int matwid,int mathei) {
		this.bitmap = bitmap;
		green.setColor(Color.GREEN);
		green.setStyle(Paint.Style.STROKE); 
		green.setStrokeWidth(2*lnsz/*1 /getResources().getDisplayMetrics().density*/);
		blue.setColor(Color.BLUE);
		blue.setStyle(Paint.Style.FILL); 
		//blue.setStrokeWidth(8/*1 /getResources().getDisplayMetrics().density*/);
		yellow.setColor(Color.YELLOW);
		yellow.setStyle(Paint.Style.FILL); 
		//yellow.setStrokeWidth(8/*1 /getResources().getDisplayMetrics().density*/);
		black.setColor(Color.BLACK);
		black.setStyle(Paint.Style.FILL);
		yline.setColor(Color.YELLOW);
		yline.setStyle(Paint.Style.STROKE); 
		yline.setStrokeWidth(4*lnsz/*1 /getResources().getDisplayMetrics().density*/);
		
		
		mw = matwid;//both must be odd
		mh = mathei;
		Log.d(TAG,"ZZZZZZZZ   "+ mh+" "+mathei+ " "+mw+" "+matwid);
		matrix = new int [mh][mw];
		generateMaze();
		//touched = false;
	}
	
	//called once to setup maze
	public void drawMaze() {
		Canvas c = new Canvas(bitmap);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		
		drawOutline(c,w,h);
		
		//draw branches
		
	}
	
	//called once to prepare maze
	public void drawOriginal(Canvas canvas, int width, int height) {
		drawMaze();
		canvas.drawBitmap(bitmap, 0, height/5, null);
	}
	
	public void drawOutline(Canvas c, int w, int h) {
		float a = mw+1; //width factor
		float b = (mh+1)/2+2;//height factor
		
		//draw vertical
		 c.drawLine(lnsz,h/b,lnsz,h-h/b,green);
		 c.drawLine(w-lnsz,h/b,w-lnsz,h-h/b,green);
		 
		 //draw horizontal
		 c.drawLine(0,h/b,start/2*2*w/a+lnsz,h/b,green);
		 c.drawLine((start+2)*w/a-lnsz,h/b,w,h/b,green);
		 
		 c.drawLine(0,h-h/b,end*w/a+lnsz,h-h/b,green);
		 c.drawLine((end+2)*w/a-lnsz,h-h/b,w,h-h/b,green);
	
		 //draw vertical entrance/exit
		 int sl = 0;//adjust for edges
		 int sr = 0;
		 int el = 0;
		 int er = 0;
		 if(start == 0)
			 sl = 1;
		 if(end == 0)
			 el = 1;
		 if(start == a)
			 sr = 1;
		 if(end == a)
			 er = 1;
		 c.drawLine(start*w/a+sl*lnsz, 0,start*w/a+sl*lnsz , h/b, green);
		 c.drawLine(end*w/a+el*lnsz, h-h/b,end*w/a+el*lnsz , h, green);
		 c.drawLine((start+2)*w/a-sr*lnsz, 0,(start+2)*w/a-sr*lnsz,h/b , green);
		 c.drawLine((end+2)*w/a-er*lnsz, h-h/b,(end+2)*w/a-er*lnsz , h, green);
		 
		 //draw horizontal entrance/exit
		 c.drawLine(start*w/a, lnsz,(start+2)*w/a , lnsz, green);
		 c.drawLine(end*w/a, h-lnsz,(end+2)*w/a , h-lnsz, green);
	
		 //drawing circles
		 c.drawCircle((start+1)*w/a, h/(2*b), h/(2*b), yellow);
		 c.drawCircle((end+1)*w/a, h-h/(2*b), h/(3*b), blue);
		 
		 yy = 0;
		 yx = start/2;
		 
		 //for(int bb = 0; bb < mh;bb ++){
		 //Log.d(TAG," "+matrix[bb][0]+" "+matrix[bb][1]+" "+matrix[bb][2]+" "+matrix[bb][3]+" "+matrix[bb][4]+" "+matrix[bb][5]+" "+matrix[bb][6]+" "+matrix[bb][7]+" "+matrix[bb][8]+" "+matrix[bb][9]+" "+matrix[bb][10]);
		 //}
		 
		 
		 for(int i = 0;i < mh; i ++) {
			 for(int j = 0; j < mw; j ++) {
				 if(matrix[i][j] == 0) {
					 if(j % 2 == 0){
						 //horizontal line
						c.drawLine(w*(j)/a-lnsz, (((i+1)/2)+1)*h/b,w*(j+2)/a+lnsz , (((i+1)/2)+1)*h/b, green);
					 } else {
						 //vertical line
						 c.drawLine(w*(j+1)/a, (((i)/2)+1)*h/b-lnsz, w*(j+1)/a, (((i)/2)+2)*h/b+lnsz, green);
					 }
					 
				 }
			 }
		 }
		
	}
	
	public void generateMaze() {
		//int w = 11;//must be odd
		//int h = 11;//must be odd
		//int m [] [] = new int [h] [w];
		for(int i = 0; i < mh; i ++){
			for(int j = 0; j < mw; j++){
				if(j % 2 == 1 || i %2 == 1) {
					matrix[i][j] = 0;
					if(j % 2 == 1 && i %2 == 1) {
						matrix[i][j] = 4;
					}
				}else{
					matrix[i][j] = 1;
				}
			}
		}
		
		//h,w
		//0 = line, 1 = unvisited, 2 = visited, 4 = no line
		Random rand = new Random();
		start = rand.nextInt((mw-1)/2)*2;
		end = rand.nextInt((mw-1)/2)*2;
		matrix[0][start] = 2;
		Stack<Integer> stack = new Stack<Integer>();
		
		int totalCells = (mw+1)/2*(mh+1)/2;
		int cellsVisited = 1;
		stack.push(start);
		
		while(cellsVisited < totalCells){
			int x = stack.peek()%mw;
			int y = stack.peek()/mw;
			matrix[y][x] = 2;
			int no = 0;
			int so = 0;
			int ea = 0;
			int we = 0;
			if(y>1 && matrix[y-2][x] == 1)
				no = 1;
			if(y<mh-2 && matrix[y+2][x] == 1)
				so = 1;
			if(x<mw-2 && matrix[y][x+2] == 1)
				ea = 1;
			if(x>1 && matrix[y][x-2] == 1)
				we = 1;
			if(no+ea+so+we == 0){
				stack.pop();
			} else {
			
				int dir = -1;
				while(dir == -1){
					int a = rand.nextInt(4);
					if(a == 0 && no == 1){
						dir = 0;
						matrix[y-1][x] = 4;
						y=y-2;
					}
					if(a == 1 && ea == 1){
						dir = 1;
						matrix[y][x+1] = 4;
						x = x+2;
					}
					if(a == 2 && so == 1){
						dir = 2;
						matrix[y+1][x] = 4;
						y = y+2;
					}
					if(a == 3 && we == 1){
						dir = 3;
						matrix[y][x-1] = 4;
						x = x-2;
					}
				}
				stack.push(mw*y + x);
				cellsVisited ++;
			}
			
		}
		
	
		
	}
	
	
	public void updateScreen(Canvas canvas, int height){
		//Log.d(TAG,"maze.updateScreen");
		canvas.drawBitmap(bitmap, 0, height/5, null);//redraw bitmap to sreen
	}
	
	/*public void touchdown(int x,int y){
		Log.d(TAG,"maze.touchdown");
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		int a = ((mw+1));
		int b = ((mh+1)/2+2);
		int altx = x/(w/(a/2));
		int alty = y/(h/b);
		Log.d(TAG,"y,h,b"+y+", "+h+","+b);

		Log.d(TAG,"ZZZ yx: " + yx +" yy: "+ yy + "  altx: "+ altx + "  alty: "+ alty);
		Log.d(TAG,"a: " + a +" b: "+ b + "  ");
		if(x > 0) {
			if( altx == yx && alty == yy) {//change
				touched = true;
			}
		} else 
			touched = false;
	}*/
	
	public boolean move(int x,int y){
		Log.d(TAG,"maze.move");
		//Log.d(TAG,"touched is now" + touched + " ");
		if(x > 0 ) { 
			Canvas c = new Canvas(bitmap);
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			int a = ((mw+1));
			int b = ((mh+1)/2+2);
			int altx = x/(w/(a/2));//attempted target
			int alty = y/(h/b);
			boolean move = false;
			
			/*
			Log.d(TAG,"x,y  ,  altx,alty + ("+yx+","+yy+")    ("+altx+","+alty+")");
			if((alty != 0 || (alty == 0 && yx == start/2)) && (alty != b-1 || (alty == b-1 && yx == end/2))){
				if(alty == yy && altx != yx){
					move = true;
					if(altx < yx){
						for(int pos = altx; pos < yx; pos ++){
							if(matrix[(alty + yy-2)][(pos + pos+1)] != 4) {
								move = false;
								break;
							}
						}
					}else{//altx > yx
						for(int pos = altx; pos > yx; pos --){
							if(matrix[(alty + yy-2)][(pos + pos-1)] != 4) {
								move = false;
								break;
							}
						}
					}
				}
				if(altx == yx){
					move = true;
					if(alty < yy){
						for(int pos = alty; pos < yy; pos ++){
							if(matrix[(pos + pos+1-2)][(altx+yx)] != 4) {
								move = false;
								break;
							}
						}
					}else{//alty > yy
						for(int pos = yy; pos > alty; pos ++){
							boolean notException = true;
							if((pos == 0 && alty > 0 && altx == start) ||(pos + 1 == b-1 && altx == end && pos == b-2) )
								notException = false;
							if(matrix[(pos + pos+1-2)][(altx+yx)] != 4 && notException) {
								move = false;
								break;
							}
						}
					}
				}
			}
			
			if(move){
				float dx = w*2/a;
				float dy = h/b;
				Log.d(TAG,"MOVE: yx" + yx +" yy: "+ yy + "  altx: "+ altx + "  alty: "+ alty);
				Log.d(TAG,"MOVE ARGH: a" + a +" b: "+ b );

				float ddy = dy/2;
				float ddx = dx/2;
				if(alty == yy && altx != yx){
					if(altx < yx){
						c.drawCircle((2*yx+1)*ddx+1, (2*yy+1)*ddy, h/(3*b), black);
						for(int pos = altx; pos < yx; pos ++){
							//altx =pos,yx = pos+1
							c.drawLine((2*(pos+1)+1)*ddx, (2*yy+1)*ddy, (2*pos+1)*ddx, (2*alty+1)*ddy, yline);
						}
						c.drawCircle((2*altx+1)*ddx+1, (2*alty+1)*ddy, h/(3*b)-1, yellow);
					}else{//altx > yx
						c.drawCircle((2*yx+1)*ddx+1, (2*yy+1)*ddy, h/(3*b), black);
						for(int pos = altx; pos > yx; pos --){
							//altx =pos,yx = pos-1
							c.drawLine((2*(pos-1)+1)*ddx, (2*yy+1)*ddy, (2*pos+1)*ddx, (2*alty+1)*ddy, yline);
						}
						c.drawCircle((2*altx+1)*ddx+1, (2*alty+1)*ddy, h/(3*b)-1, yellow);
					}
				}
				if(altx == yx){
					if(alty < yy){
						c.drawCircle((2*yx+1)*ddx+1, (2*yy+1)*ddy, h/(3*b), black);
						for(int pos = alty; pos < yy; pos ++){
							//alty =pos,yy = pos+1
							c.drawLine((2*yx+1)*ddx, (2*(pos+1)+1)*ddy, (2*altx+1)*ddx, (2*pos+1)*ddy, yline);
							}
						c.drawCircle((2*altx+1)*ddx+1, (2*alty+1)*ddy, h/(3*b)-1, yellow);
						}
					}else{//alty > yy
						c.drawCircle((2*yx+1)*ddx+1, (2*yy+1)*ddy, h/(3*b), black);
						for(int pos = alty; pos > yy; pos --){
							//alty =pos-1,yy = pos
							c.drawLine((2*yx+1)*ddx, (2*(pos-1)+1)*ddy, (2*altx+1)*ddx, (2*pos+1)*ddy, yline);
							}
						c.drawCircle((2*altx+1)*ddx+1, (2*alty+1)*ddy, h/(3*b)-1, yellow);
						}
					}
					move = false;
					yy = alty;
					yx = altx;
					if(yy == b-1)
						return true;
				}
				//idea move(oldx, old y, new x, new y) or equivelant w/o function call or passing other parameters
				
				return false;
			
			*/
			if(true) {//changed from touched
				//if within 1
				
				if(((altx == yx+1 || altx == yx-1) && alty == yy)||((alty == yy+1 || alty == yy-1) && altx == yx)) {
					//
					if((alty != 0 /*|| (alty == 0 && yx == start/2)*/) && (alty != b-1 || (alty == b-1 && yx == end/2))) {
						if(yy == b-1 || (yy == 1 && alty == 0)||alty > b-1 ||alty < 0 ||altx > a/2-1||altx < 0)
							move = false;
						else if(alty == 0 || alty == b-1 || yy == 0)
							move = true;
							//if no wall
						else
							if(matrix[(alty + yy-2)][(altx + yx)] == 4) {
								move = true;
								
						}
						
					}
				}
			}
		
		
			if(move){
				float dx = w*2/a;
				float dy = h/b;
				Log.d(TAG,"MOVE: yx" + yx +" yy: "+ yy + "  altx: "+ altx + "  alty: "+ alty);
				Log.d(TAG,"MOVE ARGH: a" + a +" b: "+ b );

				float ddy = dy/2;
				float ddx = dx/2;
				//refine
				//int dddx = (int)ddx;
				//int dddy = (int)ddy;
				//int r1 = 2*yx*dddx +2*lnsz;
				//int r2 = 2*yy*dddy+2*lnsz;
				//int r3 = (2*yx+2)*dddx-2*lnsz;
				//int r4 = (2*yy+2)*dddy-2*lnsz;
				//if(yy == 0)
				c.drawCircle((2*yx+1)*ddx+1, (2*yy+1)*ddy, h/(3*b), black);
				//else{
					//Rect r = new Rect(2*yx*dddx +2*lnsz,2*yy*dddy+2*lnsz,(2*yx+2)*dddx-2*lnsz,(2*yy+2)*dddy-2*lnsz);
					//Rect r = new Rect(r1,r2,r3,r4);
					//c.drawRect(r, black);
				//}
				if(alty == b-1)
					c.drawCircle((2*altx+1)*ddx+1, (2*alty+1)*ddy, h/(3*b)+4, black);
				c.drawLine((2*yx+1)*ddx, (2*yy+1)*ddy, (2*altx+1)*ddx, (2*alty+1)*ddy, yline);
				c.drawCircle((2*altx+1)*ddx+1, (2*alty+1)*ddy, h/(3*b), black);
				c.drawCircle((2*altx+1)*ddx+1, (2*alty+1)*ddy, h/(3*b)-1, yellow);
				
				move = false;
				yx = altx;
				yy = alty;
			}
			if(yy == b-1)
				return true;
		}
		return false;
	}
	
	public void liftoff(){ 
		Log.d(TAG,"maze.liftoff");
//			touched = false;
	}
	
	public int getX(){
		return yx;
	}
	public int getY(){
		return yy;
	}
}
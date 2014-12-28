package com.maches_man.adventure_of_maches_man;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Number {
	static final int Blue=0;
	static final int Cyan=1;
	static final int Green=2;
	static final int Gray=3;
	static final int Red=4;
	static final int Wite=5;
	static final int Yellow=6;

	final int default_width=35;
	final int default_height=50;
	int width;
	int height;
	int dis=0;

	Bitmap origin[]=new Bitmap[7];
	Bitmap nums[][]=new Bitmap[7][10];
	public Number(Resources res){
		origin[0]=LoadBitmap(res,R.drawable.num_blue);
		origin[1]=LoadBitmap(res,R.drawable.num_cyan);
		origin[2]=LoadBitmap(res,R.drawable.num_green);
		origin[3]=LoadBitmap(res,R.drawable.num_gray);
		origin[4]=LoadBitmap(res,R.drawable.num_red);
		origin[5]=LoadBitmap(res,R.drawable.num_white);
		origin[6]=LoadBitmap(res,R.drawable.num_yellow);
		for(int i=0;i<7;i++){
			for(int k=0;k<10;k++){
				nums[i][k]=Graphic.cutArea(origin[i], 0 + k * default_width, 0, default_width, default_height);
			}
			origin[i].recycle();
		}
		this.reset();
	}
	public Bitmap LoadBitmap(Resources res,int r){
        return BitmapFactory.decodeResource(res, r);
	}
	public void setSize(int width,int height){
		this.width=width;
		this.height=height;
	}
	public void setDistence(int dis){
		this.dis=dis;
	}
	public void drawNumberLeftStart(int first_num_x,int first_num_y,int num,int color,Canvas canvas,Paint paint){
		String tmp=String.valueOf(num);
		for(int i=0;i<tmp.length();i++){
			Graphic.drawPic(canvas, Graphic.bitSize(nums[color][Integer.valueOf(tmp.substring(i,i+1))],this.width, this.height), first_num_x+i*(this.width+dis), first_num_y, 0, 255, paint);
		}
	}
	public void drawNumberRightStart(int first_num_x,int first_num_y,int num,int color,Canvas canvas,Paint paint){
		String tmp=String.valueOf(num);
		for(int i=tmp.length()-1;i>=0;i--){
			Graphic.drawPic(canvas, Graphic.bitSize(nums[color][Integer.valueOf(tmp.substring(i,i+1))],this.width, this.height), first_num_x-(tmp.length()-i)*(this.width+dis), first_num_y, 0, 255, paint);
		}
	}
	public void reset(){
		this.width=this.default_width;
		this.height=this.default_height;
		this.dis=0;
	}
	public void recycle(){
		for(int i=0;i<7;i++){
			origin[i].recycle();
			for(int j=0;j<10;j++){
				nums[i][j].recycle();
			}
		}
	}
}

package com.maches_man.adventure_of_maches_man;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public class Botton {
	MainActivity activity;
	int x;//�Ϥ�������x�y��
	int y;//�Ϥ�������y�y��
	float width;//�������s���e
	float height;//�������s����
	Bitmap onBitmap;//���U���A���Ϥ�
	boolean isOn=false;//���U���A��true
	int key;
	public Botton(MainActivity activity, Bitmap onBitmap, int x, int y){
		this.activity=activity;
		//this.isOn=activity.backgroundsoundFlag;
		this.onBitmap=onBitmap;
		this.width=onBitmap.getWidth();
		this.height=onBitmap.getHeight();
		this.x=x;
		this.y=y;
	}

	public void drawBtm(Canvas canvas,Paint paint){//ø�s���s
		if(isOn)
			Graphic.drawPic(canvas,onBitmap,x,y,0,150,paint);
		else
            Graphic.drawPic(canvas,onBitmap,x,y,0,255,paint);
	}

	public void drawBtm(Canvas canvas,Paint paint,int x,int y){//ø�s���s
		move(x,y);
		drawBtm(canvas,paint);
	}
	
	
	public void setBottom(){//�������s���A
		this.isOn=!this.isOn;
	}
	public boolean getBottom(){
		return this.isOn;
	}
	
	public void setBottomTo(Boolean i){
		this.isOn=i;
	}
	public void move(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void setKey(int key){
		this.key=key;
	}
	public int getKey(){
		return key;
	}
	public Boolean isIn(double pointx,double pointy){//�P�_Ĳ����m
        double x=Coordinate.CoordinateX(this.x)-(this.width/2);
        double y=Coordinate.CoordinateY(this.y)-(this.height/2);
		if(pointx>=x&&pointx<=x+width&&      	pointy>=y&&pointy<=y+height)
			return true;
		return false;
	}
}

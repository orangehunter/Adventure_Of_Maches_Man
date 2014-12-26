package com.maches_man.adventure_of_maches_man;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Graphic {

	static Bitmap bitSize(Bitmap bf,int f,int g){//???ç¸®æ?
		int bw=0;
		int bh=0;
		float scaleWidth=0;
		float scaleHeight=0;
		// ????³è?ç¼©æ???atrix???
		Matrix matrix = new Matrix();
		while(scaleWidth<=0&&scaleHeight<=0){
			bw=bf.getWidth();
			bh=bf.getHeight();
			scaleWidth = Coordinate.CoordinateX(f) / bw;
			scaleHeight = Coordinate.CoordinateY(g)/ bh;
		}
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bit=Bitmap.createBitmap(bf, 0,0,bw,bh, matrix, true);//ç¸®æ????
		matrix.reset();
		//bf.recycle();//?·æ????
		//bf=null;

		return bit;
	}

	static void drawPic(Canvas canvas,Bitmap bit,int mid_x,int mid_y,float rot,int alpha,Paint paint){
		paint.setAntiAlias(true);
		paint.setAlpha(alpha);
		float x=Coordinate.CoordinateX(mid_x),y=Coordinate.CoordinateY(mid_y);
		if(rot%360==0)
			canvas.drawBitmap(bit, x-(bit.getWidth()/2), y-(bit.getHeight()/2), paint);
		else{
			Matrix matrix = new Matrix();
			matrix.preRotate(rot, (bit.getWidth()/2), (bit.getHeight()/2));
			matrix.postTranslate( x-(bit.getWidth()/2), y-(bit.getHeight()/2));
			canvas.drawBitmap(bit, matrix, paint);
			matrix.reset();
		}
		paint.reset();
	}

	static void drawLine(Canvas canvas,int color,int start_x,int start_y,int end_x,int end_y,int with,Paint paint){
		paint.setColor(color);																	//è¨­å?é¡??
		paint.setStrokeWidth(with);    //è¨­å?ç·?¯¬
		canvas.drawLine(Coordinate.CoordinateX(start_x), Coordinate.CoordinateY(start_y), Coordinate.CoordinateX(end_x),Coordinate.CoordinateY( end_y), paint);      //ç¹ªè£½?´ç?
		paint.reset();
	}
}

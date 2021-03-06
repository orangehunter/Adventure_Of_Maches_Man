package com.maches_man.adventure_of_maches_man;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint({ "ViewConstructor", "WrongCall", "ClickableViewAccessibility" })
public class TeachView extends SurfaceView
implements SurfaceHolder.Callback{
	//===============宣告======================
    Bitmap teach01;
    Bitmap teach02;
    Bitmap teach03;

    Botton nextbtm;

    int tflag = 0; //切換圖片用的FLAG

	//========================================
	SparseArray<PointF> mActivePointers=new SparseArray<PointF>();
	SparseArray<Integer> btn_pointer=new SparseArray<Integer>();
	Paint paint;			//畫筆的參考
	MainActivity activity;
	boolean deTouchJump=true;
	int pointerCount=0;

	public TeachView(MainActivity mainActivity) {
		super(mainActivity);
		this.activity = mainActivity;
		this.getHolder().addCallback(this);//設定生命周期回調接口的實現者


	}
    @Override
	public void surfaceCreated(SurfaceHolder holder) {
		paint = new Paint();//建立畫筆
		paint.setAntiAlias(true);//開啟抗鋸齒
		//=============圖片載入==================
        teach01=Graphic.LoadBitmap(activity.getResources(),R.drawable.t01,Constant.DEFULT_WITH,Constant.DEFULT_HIGHT,true);
        teach02=Graphic.LoadBitmap(activity.getResources(),R.drawable.t02,Constant.DEFULT_WITH,Constant.DEFULT_HIGHT,true);
        teach03=Graphic.LoadBitmap(activity.getResources(),R.drawable.t03,Constant.DEFULT_WITH,Constant.DEFULT_HIGHT,true);

		//=====================================

        tflag = 0;
		Constant.Flag=true;
		//=============螢幕刷新=================================================
		new Thread(){
			@SuppressLint("WrongCall")
			public void run()
			{
				while(Constant.Flag){
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					SurfaceHolder myholder=TeachView.this.getHolder();
					Canvas canvas = myholder.lockCanvas();//取得畫布
					onDraw(canvas);
					if(canvas != null){
						myholder.unlockCanvasAndPost(canvas);
					}
				}

			}
		}.start();
		//===========================================================================
	}
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {//重新定義的繪制方法
		if(canvas!=null){
			super.onDraw(canvas);
			canvas.clipRect(new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HIGHT));//只在螢幕範圍內繪制圖片
			canvas.drawColor(Color.WHITE);//界面設定為白色
			paint.setAntiAlias(true);	//開啟抗鋸齒
			//================================畫面繪製========================================

            if(tflag ==0) {
                Graphic.drawPic(canvas, teach01, 1280 / 2, 720 / 2, 0, 255, paint);
            }
            else if(tflag == 1){
                Graphic.drawPic(canvas, teach02, 1280 / 2, 720 / 2, 0, 255, paint);
            }else if(tflag == 2){
                Graphic.drawPic(canvas, teach03, 1280 / 2, 720 / 2, 0, 255, paint);
            }
            else if(tflag == 3){
                activity.changeView(1);

            }

			//===============================================================================
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){//觸控事件
		pointerCount = event.getPointerCount();

		// get pointer index from the event object
		int pointerIndex = event.getActionIndex();

		// get pointer ID
		int pointerId = event.getPointerId(pointerIndex);

		switch(event.getActionMasked())
		{
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN://按下
			PointF f = new PointF();
			f.x = event.getX(pointerIndex);
			f.y = event.getY(pointerIndex);
			mActivePointers.put(pointerId, f);

			break;
		case MotionEvent.ACTION_MOVE:  // a pointer was moved
			for (int size = event.getPointerCount(), i = 0; i < size; i++) {
				PointF point = mActivePointers.get(event.getPointerId(i));
				if (point != null) {
					point.x = event.getX(i);
					point.y = event.getY(i);
				}
			}
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_CANCEL: 
			mActivePointers.remove(pointerId);
			btn_pointer.remove(pointerId);
            tflag+=1;
			break;
		}

		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {

	}

	public void surfaceDestroyed(SurfaceHolder arg0) {//銷毀時被呼叫
        teach01.recycle();
        teach02.recycle();
        teach03.recycle();

        Constant.Flag=false;
	}


}
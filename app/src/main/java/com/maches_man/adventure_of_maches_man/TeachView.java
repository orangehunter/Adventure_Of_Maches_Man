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
	//===============�ŧi======================
    Bitmap teach01;
    Bitmap teach02;
    Bitmap teach03;

    Botton nextbtm;

    int tflag = 0; //�����Ϥ��Ϊ�FLAG

	//========================================
	SparseArray<PointF> mActivePointers=new SparseArray<PointF>();
	SparseArray<Integer> btn_pointer=new SparseArray<Integer>();
	Paint paint;			//�e�����Ѧ�
	MainActivity activity;
	boolean deTouchJump=true;
	int pointerCount=0;

	public TeachView(MainActivity mainActivity) {
		super(mainActivity);
		this.activity = mainActivity;
		this.getHolder().addCallback(this);//�]�w�ͩR�P���^�ձ��f����{��


	}
    @Override
	public void surfaceCreated(SurfaceHolder holder) {
		paint = new Paint();//�إߵe��
		paint.setAntiAlias(true);//�}�ҧܿ���
		//=============�Ϥ����J==================
        teach01=Graphic.LoadBitmap(activity.getResources(),R.drawable.t01,Constant.DEFULT_WITH,Constant.DEFULT_HIGHT,true);
        teach02=Graphic.LoadBitmap(activity.getResources(),R.drawable.t02,Constant.DEFULT_WITH,Constant.DEFULT_HIGHT,true);
        teach03=Graphic.LoadBitmap(activity.getResources(),R.drawable.t03,Constant.DEFULT_WITH,Constant.DEFULT_HIGHT,true);

		//=====================================

        tflag = 0;
		Constant.Flag=true;
		//=============�ù���s=================================================
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
					Canvas canvas = myholder.lockCanvas();//���o�e��
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
	protected void onDraw(Canvas canvas) {//���s�w�q��ø���k
		if(canvas!=null){
			super.onDraw(canvas);
			canvas.clipRect(new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HIGHT));//�u�b�ù��d��ø��Ϥ�
			canvas.drawColor(Color.WHITE);//�ɭ��]�w���զ�
			paint.setAntiAlias(true);	//�}�ҧܿ���
			//================================�e��ø�s========================================

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
	public boolean onTouchEvent(MotionEvent event){//Ĳ���ƥ�
		pointerCount = event.getPointerCount();

		// get pointer index from the event object
		int pointerIndex = event.getActionIndex();

		// get pointer ID
		int pointerId = event.getPointerId(pointerIndex);

		switch(event.getActionMasked())
		{
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN://���U
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

	public void surfaceDestroyed(SurfaceHolder arg0) {//�P���ɳQ�I�s
        teach01.recycle();
        Constant.Flag=false;
	}


}
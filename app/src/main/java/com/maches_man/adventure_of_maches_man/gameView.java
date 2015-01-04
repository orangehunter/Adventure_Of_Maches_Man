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

@SuppressLint({ "ViewConstructor", "WrongCall" })
public class gameView extends SurfaceView
        implements SurfaceHolder.Callback{
    //===============宣告======================
    Player player;
    Bitmap back;
    int back_x,back_y;
    //========================================
    SparseArray<PointF> pointers =new SparseArray<PointF>();
    SparseArray<Integer> btn_pointer=new SparseArray<Integer>();
    final int active_left=1;
    final int active_right=2;
    int pointerCount=0;
    Paint paint;			//畫筆的參考
    MainActivity activity;

    public gameView(MainActivity mainActivity) {
        super(mainActivity);
        this.activity = mainActivity;
        this.getHolder().addCallback(this);//設定生命周期回調接口的實現者
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        paint = new Paint();//建立畫筆
        paint.setAntiAlias(true);//開啟抗鋸齒
        //=============圖片載入==================
        player=new Player(activity);
        back=Graphic.bitSize(Graphic.LoadBitmap(activity.getResources(),R.drawable.bg_tmp3,false),2560,800);
        back_x=0;
        back_y=0;
        //=====================================
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
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    SurfaceHolder myholder= gameView.this.getHolder();
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
            Graphic.drawPic(canvas,back,(int)(back_x+1280/2),(int)(back_y+720/2),0,255,paint);
            player.drawPlayer(canvas,paint,0.25);
            if(pointers.size()==0){
                player.setStop();
            }
            for(int i=0;i<pointers.size();i++) {
                try {
                    if (pointers.get(i).x > Coordinate.CoordinateX(1280 / 2)) {
                        player.setWalk(player.face_right);
                        back_x -= 5;
                    }
                    if (pointers.get(i).x < Coordinate.CoordinateX(1280 / 2)) {
                        player.setWalk(player.face_left);
                        back_x += 5;
                    }
                }catch (NullPointerException e){
                    //Log.v("gameview",""+e);
                }
            }
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
                pointers.put(pointerId, f);

                break;
            case MotionEvent.ACTION_MOVE:  // a pointer was moved
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    PointF point = pointers.get(event.getPointerId(i));
                    if (point != null) {
                        point.x = event.getX(i);
                        point.y = event.getY(i);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                pointers.remove(pointerId);
                btn_pointer.remove(pointerId);
                break;
        }

        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {

    }

    public void surfaceDestroyed(SurfaceHolder arg0) {//銷毀時被呼叫
        player.recycle();
        back.recycle();
        Constant.Flag=false;
    }


}
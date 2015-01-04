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
    //===============�ŧi======================
    Player player;
    Bitmap back;
    int back_x,back_y;
    //========================================
    SparseArray<PointF> pointers =new SparseArray<PointF>();
    SparseArray<Integer> btn_pointer=new SparseArray<Integer>();
    final int active_left=1;
    final int active_right=2;
    int pointerCount=0;
    Paint paint;			//�e�����Ѧ�
    MainActivity activity;

    public gameView(MainActivity mainActivity) {
        super(mainActivity);
        this.activity = mainActivity;
        this.getHolder().addCallback(this);//�]�w�ͩR�P���^�ձ��f����{��
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        paint = new Paint();//�إߵe��
        paint.setAntiAlias(true);//�}�ҧܿ���
        //=============�Ϥ����J==================
        player=new Player(activity);
        back=Graphic.bitSize(Graphic.LoadBitmap(activity.getResources(),R.drawable.bg_tmp3,false),2560,800);
        back_x=0;
        back_y=0;
        //=====================================
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
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    SurfaceHolder myholder= gameView.this.getHolder();
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

    public void surfaceDestroyed(SurfaceHolder arg0) {//�P���ɳQ�I�s
        player.recycle();
        back.recycle();
        Constant.Flag=false;
    }


}
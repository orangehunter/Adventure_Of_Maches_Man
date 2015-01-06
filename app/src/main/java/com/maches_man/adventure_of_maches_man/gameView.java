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
    int player_gravity=0;
    Bitmap player_info;
    enemy_pic en_pic;
    Bitmap back;
    int back_x,back_y;
    boolean jump_flag=false;
    boolean squat_flag=false;
    int squat_counter=-1;
    //========================================
    SparseArray<point> pointers = new SparseArray<point>();
    SparseArray<Integer> walk =new SparseArray<Integer>();
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
        player_info=Graphic.bitSize(Graphic.LoadBitmap(activity.getResources(),R.drawable.player_info,false),1280,160);
        en_pic=new enemy_pic(activity);
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
            Graphic.drawPic(canvas,player_info,1280/2,80,0,255,paint);
            player.drawPlayer(canvas,paint,0.25);


            if (jump_flag){
                player.setJump();
                player.y-=player_gravity;
                player_gravity--;
                if (walk.get(0)!=null) {
                    player.setFace_flag(walk.get(0));
                    if (walk.get(0) == player.face_right) {
                        back_x -= 5;
                    }
                    if (walk.get(0) == player.face_left) {
                        back_x += 5;
                    }
                }
                if (player.y>=720/3*2){
                    jump_flag=false;
                    player_gravity=0;
                    player.y=720/3*2;
                }
            }else if (squat_flag) {
                player.setSquat();
                if(squat_counter==-1) {
                squat_counter=player.squat_time;
                }
                squat_counter--;
                if (squat_counter==0) {
                squat_flag=false;
                    squat_counter=-1;
                }
            }else{
                if (walk.get(0) == null) {
                    player.setStop();
                } else {
                    player.setWalk();
                    player.setFace_flag(walk.get(0));
                    if (walk.get(0) == player.face_right) {
                        back_x -= 5;
                    }
                    if (walk.get(0) == player.face_left) {
                        back_x += 5;
                    }
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
                point down = new point();
                down.x = event.getX(pointerIndex);
                down.y = event.getY(pointerIndex);
                down.down_x=down.x;
                down.down_y=down.y;
                if(down.x>Coordinate.CoordinateX(1280/2)){
                    walk.put(pointerId,player.face_right);
                }
                if(down.x<Coordinate.CoordinateX(1280/2)){
                    walk.put(pointerId,player.face_left);
                }
                pointers.put(pointerId, down);
                break;
            case MotionEvent.ACTION_MOVE:  // a pointer was moved
                int size = event.getPointerCount();
                for (int j = 0; j < size; j++) {
                    point move = pointers.get(event.getPointerId(j));
                    if (move != null) {
                        move.x = event.getX(j);
                        move.y = event.getY(j);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                PointF up = new PointF();
                up.x = event.getX(pointerIndex);
                up.y = event.getY(pointerIndex);
                if (pointers.get(pointerId).down_y-up.y>Coordinate.CoordinateY(100)&&jump_flag==false){
                    player_gravity=player.jump_power;
                    jump_flag=true;
                }
                if (up.y-pointers.get(pointerId).down_y>Coordinate.CoordinateY(100)&&squat_flag==false&&jump_flag==false){
                    squat_flag=true;
                }
                pointers.remove(pointerId);
                walk.remove(pointerId);
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
        player_info.recycle();
        en_pic.recycle();
        Constant.Flag=false;
    }


}
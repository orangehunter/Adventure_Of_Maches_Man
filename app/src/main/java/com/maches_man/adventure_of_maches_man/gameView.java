package com.maches_man.adventure_of_maches_man;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint({ "ViewConstructor", "WrongCall" })
public class gameView extends SurfaceView
        implements SurfaceHolder.Callback{
    //===============宣告======================
    int fack_timer=0;
    int enemy_atk_counter=0;
    Number num;
    static Player player;
    int player_gravity=0;
    Bitmap player_info;
    Bitmap player_arrow;
    int player_arrow_alpha=255;
    int player_arrow_alpha_flag=0;

    enemy_pic en_pic;
    int enemys=5;
    Enemy[]enemy;
    Bitmap enemy_arrow;
    int enemy_counter=0;
    int enemy_born_time=400;

    Bitmap back;

    int back_x,back_y;

    boolean jump_flag=false;
    boolean squat_flag=false;

    int squat_counter=-1;
    boolean rush_flag=false;

    int rush_face=0;
    int rush_counter=-1;
    int rush_cooler=0;

    int gv_score = 0;

    Bitmap botton_a;
    Botton attack_L,attack_R;
    boolean attack_flag=false;
    int attack_counter=-1;
    Bitmap botton_b;
    Botton skill_L,skill_R;
    Bitmap botton_b_lite;
    boolean super_skill;

    Bitmap[] tama_pic;
    int tamas=20;
    tama tama[];
    //========================================
    SparseArray<point> pointers = new SparseArray<point>();
    SparseArray<Integer> walk =new SparseArray<Integer>();
    SparseArray<rush_data> rush = new SparseArray<rush_data>();
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
        Resources rs=activity.getResources();
        num=new Number(rs);
        player=new Player(activity);
        player.weapon_flag=player.weapon_Gun;
        player_info=Graphic.bitSize(Graphic.LoadBitmap(rs,R.drawable.player_info,false),1280,160);
        player_arrow=Graphic.bitSize(Graphic.LoadBitmap(rs,R.drawable.player_arrow,false),128,174);

        en_pic=new enemy_pic(activity);
        enemy=new Enemy[enemys];
        for(int i=0;i<enemys;i++){
            enemy[i]=new Enemy(activity,en_pic);
            enemy[i].weapon_flag=enemy[i].weapon_Gun;
        }
        enemy_arrow=Graphic.bitSize(Graphic.LoadBitmap(rs,R.drawable.enemy_arrow,true),69,42);

        back=Graphic.bitSize(Graphic.LoadBitmap(rs,R.drawable.bg_tmp3,false),2560,800);
        back_x=0;
        back_y=0;

        botton_a=Graphic.bitSize(Graphic.LoadBitmap(rs,R.drawable.attack,true),146,146);
        attack_L =new Botton(activity,botton_a,90,640);
        attack_R=new Botton(activity,botton_a,1190,640);
        botton_b=Graphic.bitSize(Graphic.LoadBitmap(rs,R.drawable.b,true),125,125);
        skill_L =new Botton(activity,botton_b,250,650);
        skill_R =new Botton(activity,botton_b,1030,650);
        botton_b_lite=Graphic.bitSize(Graphic.LoadBitmap(rs,R.drawable.light,true),186,162);

        Bitmap tama_pic_org =Graphic.LoadBitmap(rs,R.drawable.ttama,true);
        tama_pic =new Bitmap[3];
        tama_pic[0]=Graphic.bitSize(Graphic.cutArea(tama_pic_org,0,0,20,20),20,20);
        tama_pic[1]=Graphic.bitSize(Graphic.cutArea(tama_pic_org,50,0,100,100),100,100);
        tama_pic[2]=Graphic.bitSize(Graphic.cutArea(tama_pic_org,0,50,20,20),20,20);
        tama_pic_org.recycle();
        tama=new tama[tamas];
        for (int i=0;i<tamas;i++){
            tama[i]=new tama(tama_pic);
        }
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
            fack_timer++;
            enemy_atk_counter++;
            if (fack_timer>=150){
                if (player.mp<player.mp_max) {
                    player.mp += 2;
                    if (player.mp>player.skill_mp_expend) {
                        skill_R.setBottomTo(false);
                        skill_L.setBottomTo(false);
                    }
                }
                fack_timer=0;
            }
            int hp_c;
            if (player.hp<=0){
                if (player.setDie()){
                    activity.changeView(3);
                }
            }
            if (player.hp<player.hp_max/3*1){
                hp_c=Color.RED;
            }else if (player.hp<player.hp_max/3*2){
                hp_c=Color.YELLOW;
            }else {
                hp_c=Color.GREEN;
            }
            Graphic.drawRect(canvas,hp_c,242,18,242+(int)(player.hp*(310/player.hp_max)),59,paint);


            Graphic.drawRect(canvas,Color.YELLOW,664,18,664+(int)(player.mp*((976-664)/player.mp_max)),59,paint);

            if (player.exp==player.exp_max){
                player.lv_up();
                for (int i=0;i<enemys;i++){
                    enemy[i].damage+=1;
                }
            }
            Graphic.drawRect(canvas,Color.BLUE,411,77,411+(int)(player.exp*((976-411)/player.exp_max)),118,paint);

            num.setSize(25,35);
            num.drawNumberLeftStart(254,97,player.lv,Number.Blue,canvas,paint);
            num.setSize(30,50);
            num.drawNumberRightStart(1265,97,gv_score,Number.Yellow,canvas,paint);

            attack_L.drawBtm(canvas,paint);
            attack_R.drawBtm(canvas,paint);


            skill_L.drawBtm(canvas,paint);
            skill_R.drawBtm(canvas,paint);

            for (int i=0;i<tamas;i++){
                tama[i].draw_tama(canvas,paint);
                tama[i].getX();
                if (tama[i].getFlag()) {
                    switch (tama[i].getStyle()) {
                        case 0:
                            for (int j=0;j<enemys;j++){
                                if (tama[i].getX()>enemy[j].getX(back_x)-20&&tama[i].getX()<enemy[j].getX(back_x)+20&&enemy[j].life_flag){

                                    if (tama[i].getY()>720/3*2-80){
                                        enemy[j].hp-=player.damage;
                                        enemy[j].setDamage();
                                        tama[i].stop();
                                        break;
                                    }
                                }
                            }
                            break;
                        case 1:
                            for (int j=0;j<enemys;j++){
                                if (tama[i].getX()>enemy[j].getX(back_x)-20&&tama[i].getX()<enemy[j].getX(back_x)+20&&enemy[j].life_flag){
                                    if (tama[i].getY()>720/3*2-80){
                                        enemy[j].hp-=player.exdamage;
                                        enemy[j].setDamage();
                                        tama[i].stop();
                                        break;
                                    }
                                }
                            }
                            break;
                        case 2:
                            if (tama[i].getX()>player.x-10&&tama[i].getX()<player.x+10){
                                if (player.y>720/3*2-20&&!rush_flag&&!squat_flag){
                                    player.hp-=4;
                                    player.setDamage();
                                }
                            }
                            break;
                    }
                }
            }

            enemy_born();
            enemy_control();
            for (int i=0;i<enemys;i++){
                if (enemy[i].drawEnemy(canvas,paint,0.25,back_x)){
                    player.exp++;
                }
            }

            Graphic.drawPic(canvas,player_info,1280/2,80,0,255,paint);
            player.drawPlayer(canvas,paint,0.25);

            player_control();

            arrow_control(canvas);
            //Log.v("timer",""+time);
        }
    }
    public void enemy_control(){
        for (int i=0;i<enemys;i++){
            if (enemy[i].life_flag){
                if (enemy[i].getX(back_x)>0&&enemy[i].getX(back_x)<1280){
                    if (enemy_atk_counter%40==0){
                        for (int k=0;k<tamas;k++){
                            if (!tama[k].getFlag()){
                                int ax,ay=0;
                                if (enemy[i].face_flag==player.face_left){
                                    ax=-20;
                                }else{
                                    ax=20;
                                }
                                tama[k].start(enemy[i].getX(back_x)+ax,enemy[i].y+ay,enemy[i].face_flag,2);
                                break;
                            }
                        }
                    }
                }

                if (enemy[i].hp<=0){
                    enemy[i].setDie();
                }

                enemy[i].setWalk();
                if (enemy[i].face_flag==enemy[i].face_left){
                    if (enemy[i].x>-1250) {
                        enemy[i].x -= player.walk_speed;
                    }else{
                        enemy[i].face_flag=enemy[i].face_right;
                    }
                }
                if (enemy[i].face_flag==enemy[i].face_right){
                    if (enemy[i].x<1250) {
                        enemy[i].x += player.walk_speed;
                    }else{
                        enemy[i].face_flag=enemy[i].face_left;
                    }
                }
                if (enemy[i].y<720/3*2&&enemy[i].life_flag){
                    enemy[i].y=Coordinate.AnalogSpeedMove(enemy[i].y,720/3*2);
                }
            }
        }
    }
    public void enemy_born(){
        int en_num=0;
        for (int i=0;i<enemys;i++){
            if (enemy[i].life_flag)
                en_num++;
        }
        enemy_counter--;
        if (en_num<enemys&&enemy_counter==-1){
            for (int i=0;i<enemys;i++){
                if (enemy[i].life_flag==false){
                    int side=(int)(Math.random()*2);
                    enemy[i].y=-100;
                    enemy[i].hp=enemy[i].hp_max;
                    enemy[i].die_flag=false;
                    if (side==0){
                        enemy[i].x=1250;
                    }else if (side==1){
                        enemy[i].x=-1250;
                    }
                    int c=enemy_born_time - player.lv*10;
                    if (c>0) {
                        enemy_counter = c;
                    }else{
                        enemy_counter=0;
                    }
                    Log.v("enemy_born",side+":"+i);
                    enemy[i].life_flag=true;
                    break;
                }
            }
        }
    }
    public void arrow_control(Canvas canvas){
        int en_l=0,en_r=0;
        for (int i=0;i<enemys;i++){
            if (enemy[i].life_flag){
                if (enemy[i].getX(back_x)>1280){
                    en_r++;
                }
                if (enemy[i].getX(back_x)<0){
                    en_l++;
                }
            }
        }
        if (en_l!=0){
            Graphic.drawPic(canvas,enemy_arrow,80,500,0,255,paint);
            num.setSize(20,20);
            num.drawNumberLeftStart(130,500,en_l,Number.Red,canvas,paint);
        }
        if (en_r!=0){
            Graphic.drawPic(canvas,enemy_arrow,1200,500,180,255,paint);
            num.setSize(20,20);
            num.drawNumberLeftStart(1150,500,en_r,Number.Red,canvas,paint);
        }

        player_arrow_alpha+=player_arrow_alpha_flag;
        if (player_arrow_alpha>250){
            player_arrow_alpha_flag=-5;
        }
        if (player_arrow_alpha<100){
            player_arrow_alpha_flag=5;
        }
        if (player.x==640){
            Graphic.drawPic(canvas,player_arrow,80,330,0,player_arrow_alpha,paint);
            Graphic.drawPic(canvas,player_arrow,1200,330,180,player_arrow_alpha,paint);
        }else{
            if (player.x>640)
                Graphic.drawPic(canvas,player_arrow,80,330,0,player_arrow_alpha,paint);
            if (player.x<640)
                Graphic.drawPic(canvas,player_arrow,1200,330,180,player_arrow_alpha,paint);
        }
    }
    public void player_control(){
        if (attack_flag){
            if (attack_counter==-1){
                attack_counter=player.attack_normal_coolTime;
            }
            attack_counter--;
            if (attack_counter==0){
                attack_flag=false;
                attack_R.setBottomTo(false);
                attack_L.setBottomTo(false);
                if (player.mp>player.skill_mp_expend) {
                    skill_R.setBottomTo(false);
                    skill_L.setBottomTo(false);
                }
                attack_counter=-1;
            }
        }

        if (rush_flag) {
            player.setRush();
            player_move(rush_face,player.walk_speed*2);
            if (rush_counter==-1){
                rush_counter=player.rush_time;
            }
            rush_counter--;
            if (rush_counter==0){
                rush_flag=false;
                rush_counter=-1;
                rush_cooler=player.rush_coolTime;
            }
        }else if (jump_flag){
            player.setJump();
            player.y-=player_gravity;
            player_gravity--;
            if (walk.get(0)!=null) {
                player.setFace_flag(walk.get(0));
                player_move(walk.get(0),player.walk_speed);
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
            if (rush_cooler!=0){
                rush_cooler--;
            }
            if (walk.get(0) == null) {
                player.setStop();
            } else {
                player.setWalk();
                player.setFace_flag(walk.get(0));
                player_move(walk.get(0),player.walk_speed);
            }
        }
    }
    public void player_move(int face,int sp){
        if (face== player.face_right) {
            if (back_x>=640){
                back_x=640;
                if (player.x<640-sp)
                    player.x+=sp;
                if (player.x==640-sp){
                    player.x=640;
                    back_x -= sp;
                }
            }else if (back_x<=-640){
                back_x=-640;
                if (player.x<=1280-50)
                    player.x+=sp;
            }else {
                back_x -= sp;
            }
        }
        if (face == player.face_left) {
            if (back_x<=-640) {
                back_x=-640;
                if (player.x > 640+sp)
                    player.x -= sp;
                if (player.x == 640+sp){
                    player.x=640;
                    back_x += sp;
                }
            }else if (back_x>=640) {
                back_x=640;
                if (player.x > 50)
                    player.x -= sp;
            }else{
                back_x += sp;
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
                if (attack_L.isIn(down.x,down.y)||attack_R.isIn(down.x,down.y)){
                    if (!attack_flag&&!rush_flag){
                        attack_flag=true;
                        attack_L.setBottomTo(true);
                        attack_R.setBottomTo(true);
                        for (int i=0;i<tamas;i++){
                            if (!tama[i].getFlag()){
                                int ax=0,ay;
                                if (player.face_flag==player.face_left){
                                    ax=-20;
                                }else{
                                    ax=20;
                                }
                                if (squat_flag){
                                    ay=20;
                                }else{
                                    ay=0;
                                }
                                tama[i].start(player.x+ax,player.y+ay,player.face_flag,0);
                                break;
                            }
                        }
                    }
                }
                if (skill_L.isIn(down.x,down.y)||skill_R.isIn(down.x,down.y)){
                    if (!attack_flag&&!rush_flag&&player.mp>player.skill_mp_expend){
                        attack_flag=true;
                        player.mp-=player.skill_mp_expend;
                        skill_L.setBottomTo(true);
                        skill_R.setBottomTo(true);
                        for (int i=0;i<tamas;i++){
                            if (!tama[i].getFlag()){
                                int ax,ay;
                                if (player.face_flag==player.face_left){
                                    ax=-20;
                                }else{
                                    ax=20;
                                }
                                if (squat_flag){
                                    ay=20;
                                }else{
                                    ay=0;
                                }
                                tama[i].start(player.x+ax,player.y+ay,player.face_flag,1);
                                break;
                            }
                        }
                    }
                }
                if(down.x>Coordinate.CoordinateX(1280/2)&&down.y<Coordinate.CoordinateY(562)){
                    walk.put(pointerId,player.face_right);
                    check_rush(player.face_right,event.getEventTime());
                }
                if(down.x<Coordinate.CoordinateX(1280/2)&&down.y<Coordinate.CoordinateY(562)){
                    walk.put(pointerId,player.face_left);
                    check_rush(player.face_left,event.getEventTime());
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
    public void check_rush(int face,Long time){
        if (rush.size()==0&&rush_cooler==0){
            rush_data rd=new rush_data();
            rd.face_flag=face;
            rd.time=time;
            rush.put(rush.size(),rd);
        }else if (rush.size()==1){
            if (time-rush.get(0).time<800&&rush.get(0).face_flag==face){
                rush_flag=true;
                rush_face=face;
            }
            rush.remove(0);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {

    }

    public void surfaceDestroyed(SurfaceHolder arg0) {//銷毀時被呼叫
        activity.Score = gv_score;
        player.recycle();
        back.recycle();
        player_info.recycle();
        player_arrow.recycle();
        en_pic.recycle();
        num.recycle();
        botton_a.recycle();
        botton_b.recycle();
        botton_b_lite.recycle();
        for_recycle(tama_pic);
        Constant.Flag=false;
    }
    public void for_recycle(Bitmap[] tmp){
        for(int i=0;i<tmp.length;i++){
            tmp[i].recycle();
        }
    }

}
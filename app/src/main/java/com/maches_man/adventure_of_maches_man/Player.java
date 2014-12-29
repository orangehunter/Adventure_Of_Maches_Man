package com.maches_man.adventure_of_maches_man;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by michael on 2014/12/28.
 */
public class Player {
    Animax animax;
    int x,y;
    int face_flag;
    final int face_left=0;
    final int face_right=1;

    int lv;

    float hp_max;
    float hp;

    float mp_max;
    float mp;

    float skill_mp_expend;
    boolean skill_flag;

    float superSkill_mp_expend;
    boolean superSkill_flag;

    float exp_max;
    float exp;

    boolean life_flag;
    int life;

    int jump_power;
    boolean jump_flag;

    int squat_time;
    boolean squat_flag;

    boolean walk_flag;

    int rush_power;
    int rush_coolTime;
    boolean rush_flag;

    int attack_coolTime;
    boolean attack_flag;

    int weapon_flag;
    final int weapon_Fist   =0;
    final int weapon_Gun    =1;
    final int weapon_Sword  =2;

    Bitmap fist,gun,sword;
    Bitmap[] stop,jump,squat, fist_atk, fist_squat_atk, sword_atk, sword_squat_atk;
    Bitmap[][] walk;

    public Player(MainActivity activity){
        fist=   Graphic.LoadBitmap(activity.getResources(),R.drawable.player_fist ,false);
        gun=    Graphic.LoadBitmap(activity.getResources(),R.drawable.player_gun  ,false);
        sword=  Graphic.LoadBitmap(activity.getResources(),R.drawable.player_sword,false);

        int size=100;


        fist_atk =new Bitmap[9];
        for(int i=0;i<6;i++){
            fist_atk[i]=Graphic.bitSize(Graphic.cutArea(fist,100*i,100,100,100),size,size);
        }
        for(int i=0;i<3;i++){
            fist_atk[6+i]=Graphic.bitSize(Graphic.cutArea(fist,100*i,200,100,100),size,size);
        }

        fist_squat_atk =new Bitmap[3];
        for(int i=0;i<3;i++){
            fist_squat_atk[i]=Graphic.bitSize(Graphic.cutArea(fist,100*i+100,300,100,100),size,size);
        }

        sword_atk =new Bitmap[4];
        for(int i=0;i<4;i++){
            sword_atk[i]=Graphic.bitSize(Graphic.cutArea(sword,120*i,200,120,100),(int)(size*1.2),size);
        }

        sword_squat_atk =new Bitmap[4];
        for(int i=0;i<4;i++){
            sword_squat_atk[i]=Graphic.bitSize(Graphic.cutArea(sword,120*i,300,120,100),(int)(size*1.2),size);
        }

        stop =new Bitmap[3];
        stop[0]= Graphic.bitSize(Graphic.cutArea(fist , 0, 0, 100, 100), size, size);
        stop[1]= Graphic.bitSize(Graphic.cutArea(gun  , 0, 0, 100, 100), size, size);
        stop[2]= Graphic.bitSize(Graphic.cutArea(sword, 0, 0, 100, 100), size, size);

        jump=new Bitmap[3];
        jump[0]= Graphic.bitSize(Graphic.cutArea(fist, 300,   0, 100, 100),size,size);
        jump[1]= Graphic.bitSize(Graphic.cutArea(gun,   0, 100, 100, 100),size,size);
        jump[2]= Graphic.bitSize(Graphic.cutArea(sword,   0, 100, 100, 100),size,size);

        squat=new Bitmap[3];
        squat[0]=Graphic.bitSize(Graphic.cutArea(fist,   0, 300, 100, 100),size,size);
        squat[1]=Graphic.bitSize(Graphic.cutArea(gun, 100, 100, 100, 100),size,size);
        squat[2]=Graphic.bitSize(Graphic.cutArea(sword, 100, 100, 100, 100),size,size);

        walk=new Bitmap[3][3];
        walk[0][0]= Graphic.bitSize(Graphic.cutArea(fist, 0  , 0, 100, 100), size, size);
        walk[0][1]= Graphic.bitSize(Graphic.cutArea(fist, 100, 0, 100, 100), size, size);
        walk[0][2]= Graphic.bitSize(Graphic.cutArea(fist, 200, 0, 100, 100), size, size);

        walk[1][0]= Graphic.bitSize(Graphic.cutArea(gun, 100, 0, 100, 100),size,size);
        walk[1][1]= Graphic.bitSize(Graphic.cutArea(gun, 200, 0, 100, 100),size,size);
        walk[1][2]= Graphic.bitSize(Graphic.cutArea(gun, 300, 0, 100, 100),size,size);

        walk[2][0]= Graphic.bitSize(Graphic.cutArea(sword, 100, 0, 100, 100),size,size);
        walk[2][1]= Graphic.bitSize(Graphic.cutArea(sword, 200, 0, 100, 100),size,size);
        walk[2][2]= Graphic.bitSize(Graphic.cutArea(sword, 300, 0, 100, 100),size,size);

        x=1280/2;
        y=720/2;

        lv=1;

        hp_max=30;
        hp=hp_max;

        mp_max=20;
        mp=0;

        skill_mp_expend=20;
        skill_flag=false;

        superSkill_mp_expend=mp_max;
        superSkill_flag=false;

        exp_max=10;
        exp=0;

        life_flag=false;
        life=3;

        jump_power=15;
        jump_flag=false;

        squat_time=20;
        squat_flag=false;

        rush_power=15;
        rush_coolTime=5;
        rush_flag=false;

        attack_coolTime=5;
        attack_flag=false;

        weapon_flag=0;
    }
    public void setFace_flag(int face_flag){
        this.face_flag=face_flag;
    }
    public void drawPlayer(Canvas canvas,Paint paint){
        if (jump_flag||squat_flag){
            if (jump_flag){
                draw(canvas,jump[weapon_flag],x,y,0,255,paint);
            }
            if (squat_flag){
                draw(canvas,squat[weapon_flag],x,y,0,255,paint);
            }
        }else{

        }
    }
    public void draw(Canvas canvas,Bitmap bit,int x,int y,int rot,int alpha,Paint paint){
        if(face_flag==0) {
            Graphic.drawPic(canvas, Graphic.MirrorFlipHorizontal(bit), x, y, rot, alpha, paint);
        }else {
            Graphic.drawPic(canvas, bit, x, y, rot, alpha, paint);
        }
    }
    public void recycle(){
        fist.recycle();
        gun.recycle();
        sword.recycle();
        for(int i=0;i<stop.length;i++){
            stop[i].recycle();
            jump[i].recycle();
            squat[i].recycle();
        }
        for(int i=0;i<fist_atk.length;i++){
            fist_atk[i].recycle();
        }
        for(int i=0;i<fist_squat_atk.length;i++){
            fist_squat_atk[i].recycle();
        }
        for(int i=0;i<sword_atk.length;i++){
            sword_atk[i].recycle();
        }
        for(int i=0;i<sword_squat_atk.length;i++){
            sword_squat_atk[i].recycle();
        }
        for(int i=0;i<walk.length;i++){
            for(int l=0;l<walk[i].length;l++){
                walk[i][l].recycle();
            }
        }
    }
}

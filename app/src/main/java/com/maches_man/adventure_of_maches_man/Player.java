package com.maches_man.adventure_of_maches_man;

import android.graphics.Bitmap;

/**
 * Created by michael on 2014/12/28.
 */
public class Player {
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
    Bitmap fist_jump,fist_squat,gun_jump,gun_squat,sword_jump,sword_squat;
    Bitmap[] stop_pic, fist_atk_pic, fist_squat_atk_pic, sword_atk_pic, sword_squat_atk_pic;
    smallAnimax stop,fist_atk,fist_squat_atk,sword_atk,sword_squat_atk;
    Bitmap[][] walk;
    public Player(MainActivity activity){
        fist=   Graphic.LoadBitmap(activity.getResources(),R.drawable.player_fist ,false);
        gun=    Graphic.LoadBitmap(activity.getResources(),R.drawable.player_gun  ,false);
        sword=  Graphic.LoadBitmap(activity.getResources(),R.drawable.player_sword,false);

        int size=100;
        fist_jump= Graphic.bitSize(Graphic.cutArea(fist, 300,   0, 100, 100),size,size);
        fist_squat=Graphic.bitSize(Graphic.cutArea(fist,   0, 300, 100, 100),size,size);

        gun_jump=  Graphic.bitSize(Graphic.cutArea(gun,   0, 100, 100, 100),size,size);
        gun_squat= Graphic.bitSize(Graphic.cutArea(gun, 100, 100, 100, 100),size,size);

        sword_jump= Graphic.bitSize(Graphic.cutArea(sword,   0, 100, 100, 100),size,size);
        sword_squat=Graphic.bitSize(Graphic.cutArea(sword, 100, 100, 100, 100),size,size);

        fist_atk_pic =new Bitmap[9];
        for(int i=0;i<6;i++){
            fist_atk_pic[i]=Graphic.bitSize(Graphic.cutArea(fist,100*i,100,100,100),size,size);
        }
        for(int i=0;i<3;i++){
            fist_atk_pic[6+i]=Graphic.bitSize(Graphic.cutArea(fist,100*i,200,100,100),size,size);
        }

        fist_squat_atk_pic =new Bitmap[3];
        for(int i=0;i<3;i++){
            fist_squat_atk_pic[i]=Graphic.bitSize(Graphic.cutArea(fist,100*i+100,300,100,100),size,size);
        }

        sword_atk_pic =new Bitmap[4];
        for(int i=0;i<4;i++){
            sword_atk_pic[i]=Graphic.bitSize(Graphic.cutArea(sword,120*i,200,120,100),(int)(size*1.2),size);
        }

        sword_squat_atk_pic =new Bitmap[4];
        for(int i=0;i<4;i++){
            sword_squat_atk_pic[i]=Graphic.bitSize(Graphic.cutArea(sword,120*i,300,120,100),(int)(size*1.2),size);
        }

        stop_pic =new Bitmap[3];
        stop_pic[0]= Graphic.bitSize(Graphic.cutArea(fist , 0, 0, 100, 100), size, size);
        stop_pic[1]= Graphic.bitSize(Graphic.cutArea(gun  , 0, 0, 100, 100), size, size);
        stop_pic[2]= Graphic.bitSize(Graphic.cutArea(sword, 0, 0, 100, 100), size, size);



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
}

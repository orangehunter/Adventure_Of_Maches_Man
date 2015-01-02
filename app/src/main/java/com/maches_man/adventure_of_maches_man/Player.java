package com.maches_man.adventure_of_maches_man;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by michael on 2014/12/28.
 */
public class Player {
    int move_flag;
    final int player_stop=0;
    final int player_walk=1;
    final int player_jump=2;
    final int player_squat=3;
    final int player_rush=4;
    boolean draw_flag;
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

    float exp_max;
    float exp;

    boolean life_flag;
    int life;

    int jump_power;

    int squat_time;

    int rush_power;
    int rush_coolTime;

    int attack_normal_coolTime;
    int attack_flag;
    final int attack_non=0;
    final int attack_normal=1;
    final int attack_skill=2;
    final int attack_super_skill=3;

    int weapon_flag;
    int fist_flag;
    final int weapon_Fist   =0;
    final int weapon_Gun    =1;
    final int weapon_Sword  =2;

    float skill_mp_expend;

    float superSkill_mp_expend;

    Bitmap fist,gun,sword,other, msword, exsword_p, exsword_a;
    Bitmap rush;
    Bitmap[] stop,jump,squat,fist_squat_atk, sword_atk, sword_squat_atk,msword_atk;
    Bitmap[] exsword_pre,exsword_atk,damaged,die;
    Bitmap[][] walk,fist_atk;

    public Player(MainActivity activity){
        fist=   Graphic.LoadBitmap(activity.getResources(),R.drawable.player_fist       ,false);
        gun=    Graphic.LoadBitmap(activity.getResources(),R.drawable.player_gun        ,false);
        sword=  Graphic.LoadBitmap(activity.getResources(),R.drawable.player_sword      ,false);
        other=  Graphic.LoadBitmap(activity.getResources(),R.drawable.player_other      ,false);
        msword =
                Graphic.LoadBitmap(activity.getResources(),R.drawable.player_msword     ,false);
        exsword_p =
                Graphic.LoadBitmap(activity.getResources(),R.drawable.player_excalibur  ,false);
        exsword_a =
                Graphic.LoadBitmap(activity.getResources(),R.drawable.player_ex_atk     ,false);

        int size=100;

        rush=Graphic.bitSize(Graphic.cutArea(other,0,100,100,100),size,size);

        fist_atk =new Bitmap[3][3];
        for(int i=0;i<3;i++){
            fist_atk[0][i]=Graphic.bitSize(Graphic.cutArea(fist,100*i,100,100,100),size,size);
        }
        for(int i=0;i<3;i++){
            fist_atk[1][i]=Graphic.bitSize(Graphic.cutArea(fist,300+100*i,100,100,100),size,size);
        }
        for(int i=0;i<3;i++){
            fist_atk[2][i]=Graphic.bitSize(Graphic.cutArea(fist,100*i,200,100,100),size,size);
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

        msword_atk=new Bitmap[4];
        for(int i=0;i<4;i++){
            msword_atk[i]=Graphic.bitSize(Graphic.cutArea(msword,120*i,0,120,100),(int)(size*1.2),size);
        }
        msword.recycle();

        exsword_pre=new Bitmap[12];
        for(int i=0;i<12;i++){
            exsword_pre[i]=Graphic.bitSize(Graphic.cutArea(exsword_p,150*i,0,150,100),(int)(size*1.5),size);
        }
        exsword_p.recycle();

        exsword_atk=new Bitmap[5];
        for(int i=0;i<5;i++){
            exsword_atk[i]=Graphic.bitSize(Graphic.cutArea(exsword_a,800*i,0,800,100),(int)(size*8),size);
        }
        exsword_a.recycle();

        damaged=new Bitmap[2];
        for(int i=0;i<2;i++){
            damaged[i]=Graphic.bitSize(Graphic.cutArea(other,100*i,0,100,100),size,size);
        }

        die=new Bitmap[3];
        for(int i=0;i<3;i++){
            die[i]=Graphic.bitSize(Graphic.cutArea(other,200+100*i,0,100,100),size,size);
        }
        other.recycle();

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

        fist.recycle();
        gun.recycle();
        sword.recycle();

        draw_flag=true;
        move_flag=player_stop;

        x=1280/2;
        y=720/2;

        lv=1;

        hp_max=30;
        hp=hp_max;

        mp_max=20;
        mp=0;

        exp_max=10;
        exp=0;

        life_flag=true;
        life=3;

        jump_power=15;

        squat_time=20;

        rush_power=15;
        rush_coolTime=5;

        attack_flag=attack_non;
        fist_flag=0;
        attack_normal_coolTime =5;
        skill_mp_expend=20;
        superSkill_mp_expend=mp_max;

        weapon_flag=0;
    }
    public void drawPlayer(Canvas canvas,Paint paint,double animax_speed){
        if (life_flag) {
            switch (move_flag){
                case player_stop:
                    switch (attack_flag) {
                        case attack_non:
                            draw(canvas, stop[weapon_flag], x, y, 0, 255, paint);
                            break;
                        case attack_normal:
                            switch (weapon_flag){
                                case weapon_Fist:
                                    animax.drawEffect(fist_atk[fist_flag],animax_speed,canvas,paint,x,y,face_flag);
                                    break;
                                case weapon_Gun:
                                    draw(canvas, stop[weapon_flag], x, y, 0, 255, paint);
                                    break;
                                case weapon_Sword:
                                    animax.drawEffect(sword_atk,animax_speed,canvas,paint,x,y,face_flag);
                                    break;
                            }
                            break;
                        case attack_skill:
                            switch (weapon_flag){
                                case weapon_Gun:
                                    draw(canvas, stop[weapon_flag], x, y, 0, 255, paint);
                                    break;
                                case weapon_Sword:
                                    animax.drawEffect(msword_atk,animax_speed,canvas,paint,x,y,face_flag);
                                    break;
                            }
                            break;
                        case attack_super_skill:
                            animax.drawEffect(exsword_atk,animax_speed,canvas,paint,x,y,face_flag);
                            break;
                    }
                    break;
                case player_walk:
                    animax.loop_flag=true;
                    animax.drawEffect(walk[weapon_flag], animax_speed, canvas, paint,x,y, face_flag);
                    animax.loop_flag=false;
                    break;
                case player_jump:
                    draw(canvas, jump[weapon_flag], x, y, 0, 255, paint);
                    break;
                case player_squat:
                    draw(canvas, squat[weapon_flag], x, y, 0, 255, paint);
                    break;
                case player_rush:
                    draw(canvas, rush, x, y, 0, 255, paint);
                    break;
            }
        }else{
            animax.drawEffect(die,animax_speed,canvas,paint,x,y,face_flag);
            //TODO 死亡動畫完畢後
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
        for_recycle(stop);
        for_recycle(jump);
        for_recycle(squat);
        for_recycle(fist_atk[0]);
        for_recycle(fist_atk[1]);
        for_recycle(fist_atk[2]);
        for_recycle(fist_squat_atk);
        for_recycle(sword_atk);
        for_recycle(sword_squat_atk);
        for_recycle(msword_atk);
        for_recycle(exsword_pre);
        for_recycle(exsword_atk);
        for_recycle(damaged);
        for_recycle(die);

        for(int i=0;i<walk.length;i++){
            for(int l=0;l<walk[i].length;l++){
                walk[i][l].recycle();
            }
        }
    }
    public void for_recycle(Bitmap[] tmp){
        for(int i=0;i<tmp.length;i++){
            tmp[i].recycle();
        }
    }
}

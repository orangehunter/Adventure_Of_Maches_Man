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
    int x,y;
    int face_flag;
    final int face_left=0;
    final int face_right=1;

    int lv;

    float hp_max;
    float hp;

    float mp_max;
    float mp;

    boolean damage_flag=false;
    boolean die_flag=false;

    int exp_max;
    int exp;

    boolean life_flag;
    int life;

    int walk_speed;

    int jump_power;

    int squat_time;

    int rush_time;
    int rush_coolTime;

    int attack_normal_coolTime;
    int attack_flag;
    final int attack_non=0;
    final int attack_normal=1;
    final int attack_skill=2;
    final int attack_super_skill=3;
    int damage=4;
    int exdamage=8;

    int weapon_flag;
    int fist_flag;
    final int weapon_Fist   =0;
    final int weapon_Gun    =1;
    final int weapon_Sword  =2;

    float skill_mp_expend;

    float superSkill_mp_expend;
    boolean exsword_flag;

    Bitmap fist,gun,sword,other, msword, exsword_p, exsword_a;
    Bitmap rush;
    Bitmap[] stop,jump,squat, fist_squat_atk_pic, sword_atk_pic, sword_squat_atk_pic, msword_atk_pic,walk_back;
    Bitmap[] exsword_pre_pic, exsword_atk_pic, damaged_pic, die_pic;
    Bitmap[][] walk_pic, fist_atk_pic;

    Animax fist_squat_atk,sword_atk,sword_squat_atk,msword_atk,exsword_pre,exsword_atk,damaged,die;
    Animax[]fist_atk,walk;

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


        fist_atk_pic =new Bitmap[3][3];
        for(int i=0;i<3;i++){
            fist_atk_pic[0][i]=Graphic.bitSize(Graphic.cutArea(fist,100*i,100,100,100),size,size);
        }
        for(int i=0;i<3;i++){
            fist_atk_pic[1][i]=Graphic.bitSize(Graphic.cutArea(fist,300+100*i,100,100,100),size,size);
        }
        for(int i=0;i<3;i++){
            fist_atk_pic[2][i]=Graphic.bitSize(Graphic.cutArea(fist,100*i,200,100,100),size,size);
        }
        fist_atk=new Animax[3];
        for (int i=0;i<3;i++){
            fist_atk[i] = new Animax(fist_atk_pic[i]);
        }


        fist_squat_atk_pic =new Bitmap[3];
        for(int i=0;i<3;i++){
            fist_squat_atk_pic[i]=Graphic.bitSize(Graphic.cutArea(fist,100*i+100,300,100,100),size,size);
        }
        fist_squat_atk=new Animax(fist_squat_atk_pic);


        sword_atk_pic =new Bitmap[4];
        for(int i=0;i<4;i++){
            sword_atk_pic[i]=Graphic.bitSize(Graphic.cutArea(sword,120*i,200,120,100),(int)(size*1.2),size);
        }
        sword_atk=new Animax(sword_atk_pic);

        sword_squat_atk_pic =new Bitmap[4];
        for(int i=0;i<4;i++){
            sword_squat_atk_pic[i]=Graphic.bitSize(Graphic.cutArea(sword,120*i,300,120,100),(int)(size*1.2),size);
        }
        sword_squat_atk=new Animax(sword_squat_atk_pic);


        msword_atk_pic =new Bitmap[4];
        for(int i=0;i<4;i++){
            msword_atk_pic[i]=Graphic.bitSize(Graphic.cutArea(msword,120*i,0,120,100),(int)(size*1.2),size);
        }
        msword_atk=new Animax(msword_atk_pic);
        msword.recycle();


        exsword_pre_pic =new Bitmap[12];
        for(int i=0;i<12;i++){
            exsword_pre_pic[i]=Graphic.bitSize(Graphic.cutArea(exsword_p,150*i,0,150,100),(int)(size*1.5),size);
        }
        exsword_pre=new Animax(exsword_pre_pic);
        exsword_p.recycle();


        exsword_atk_pic =new Bitmap[5];
        for(int i=0;i<5;i++){
            exsword_atk_pic[i]=Graphic.bitSize(Graphic.cutArea(exsword_a,800*i,0,800,100),(int)(size*8),size);
        }
        exsword_atk=new Animax(exsword_atk_pic);
        exsword_a.recycle();


        damaged_pic =new Bitmap[2];
        for(int i=0;i<2;i++){
            damaged_pic[i]=Graphic.bitSize(Graphic.cutArea(other,100*i,0,100,100),size,size);
        }
        damaged=new Animax(damaged_pic);


        die_pic =new Bitmap[3];
        for(int i=0;i<3;i++){
            die_pic[i]=Graphic.bitSize(Graphic.cutArea(other,200+100*i,0,100,100),size,size);
        }
        die=new Animax(die_pic);
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

        walk_pic =new Bitmap[3][3];
        walk_pic[0][0]= Graphic.bitSize(Graphic.cutArea(fist, 0  , 0, 100, 100), size, size);
        walk_pic[0][1]= Graphic.bitSize(Graphic.cutArea(fist, 100, 0, 100, 100), size, size);
        walk_pic[0][2]= Graphic.bitSize(Graphic.cutArea(fist, 200, 0, 100, 100), size, size);
        walk_back=new Bitmap[3];
        walk_back[0]=Graphic.bitSize(Graphic.LoadBitmap(activity.getResources(),R.drawable.walk_back_fist,false),size,size);

        walk_pic[1][0]= Graphic.bitSize(Graphic.cutArea(gun, 100, 0, 100, 100),size,size);
        walk_pic[1][1]= Graphic.bitSize(Graphic.cutArea(gun, 200, 0, 100, 100),size,size);
        walk_pic[1][2]= Graphic.bitSize(Graphic.cutArea(gun, 300, 0, 100, 100),size,size);
        walk_back[1]=Graphic.bitSize(Graphic.LoadBitmap(activity.getResources(),R.drawable.walk_back_gun,false),size,size);

        walk_pic[2][0]= Graphic.bitSize(Graphic.cutArea(sword, 100, 0, 100, 100),size,size);
        walk_pic[2][1]= Graphic.bitSize(Graphic.cutArea(sword, 200, 0, 100, 100),size,size);
        walk_pic[2][2]= Graphic.bitSize(Graphic.cutArea(sword, 300, 0, 100, 100),size,size);
        walk_back[2]=Graphic.bitSize(Graphic.LoadBitmap(activity.getResources(),R.drawable.walk_back_sword,false),size,size);

        walk=new Animax[3];
        for (int i=0;i<3;i++){
            walk[i] = new Animax(walk_pic[i]);
        }

        fist.recycle();
        gun.recycle();
        sword.recycle();

        draw_flag=true;
        move_flag=player_stop;

        x=1280/2;
        y=720/3*2;

        lv=1;

        hp_max=30;
        hp=hp_max;

        mp_max=20;
        mp=4;

        exp_max=10;
        exp=0;

        life_flag=true;
        life=3;

        walk_speed=5;

        jump_power=18;

        squat_time=20;

        rush_time =20;
        rush_coolTime=15;

        attack_flag=attack_non;
        fist_flag=0;
        attack_normal_coolTime =10;
        skill_mp_expend=4;
        superSkill_mp_expend=mp_max;
        exsword_flag=false;

        weapon_flag=0;
    }
    public void lv_up(){
        hp_max+=5;
        exp_max+=2;
        mp_max+=2;
        damage+=1;
        exdamage+=2;
        walk_speed+=1;

        hp=hp_max;
        if (mp>mp_max/2) {
            mp = mp_max;
        }else{
            mp += mp_max/2;
        }
        lv++;
        exp=0;
    }
    public  void setStop(){
        if (move_flag!=player_stop)
            move_flag=player_stop;
    }
    public void setWalk(){
        if (move_flag!=player_walk)
            move_flag=player_walk;
        if (walk[weapon_flag].getFlag()==false)
            walk[weapon_flag].start();
    }
    public void setJump(){
        if (move_flag!=player_jump)
            move_flag=player_jump;
    }
    public void setSquat(){
        if (move_flag!=player_squat)
            move_flag=player_squat;
    }
    public void setRush(){
        if (move_flag!=player_rush)
            move_flag=player_rush;
    }
    public void setFace_flag(int face_flag){
        if (this.face_flag!=face_flag)
            this.face_flag=face_flag;
    }
    public void setDamage(){
        if (!damaged.getFlag())
            damaged.start();
        if (!damage_flag)
            damage_flag=true;
    }
    public boolean setDie(){
        if (life_flag){
            life_flag=false;
        }
        if (!die.getFlag())
            die.start();
        return die_flag;
    }
    public void drawPlayer(Canvas canvas,Paint paint,double animax_speed){
        if (life_flag) {
            if (damage_flag) {
                damaged.drawEffect(animax_speed,canvas,paint,x,y,face_flag);
                if (!damaged.getFlag())
                    damage_flag=false;
            }else{
                switch (move_flag) {
                    case player_stop:
                        switch (attack_flag) {
                            case attack_non:
                                draw(canvas, stop[weapon_flag], x, y, 0, 255, paint);
                                break;
                            case attack_normal:
                                switch (weapon_flag) {
                                    case weapon_Fist:
                                        fist_atk[fist_flag].drawEffect(animax_speed, canvas, paint, x, y, face_flag);
                                        break;
                                    case weapon_Gun:
                                        draw(canvas, stop[weapon_flag], x, y, 0, 255, paint);
                                        break;
                                    case weapon_Sword:
                                        sword_atk.drawEffect(animax_speed, canvas, paint, x, y, face_flag);
                                        break;
                                }
                                break;
                            case attack_skill:
                                switch (weapon_flag) {
                                    case weapon_Gun:
                                        draw(canvas, stop[weapon_flag], x, y, 0, 255, paint);
                                        break;
                                    case weapon_Sword:
                                        msword_atk.drawEffect(animax_speed, canvas, paint, x, y, face_flag);
                                        break;
                                }
                                break;
                            case attack_super_skill:
                                if (exsword_flag) {
                                    exsword_pre.drawEffect(animax_speed, canvas, paint, x, y, face_flag);
                                } else {
                                    exsword_atk.drawEffect(animax_speed, canvas, paint, x, y, face_flag);
                                }
                                break;
                        }
                        break;
                    case player_walk:
                        walk[weapon_flag].loop_flag = true;
                        draw(canvas, walk_back[weapon_flag], x, y, 0, 255, paint);
                        walk[weapon_flag].drawEffect(animax_speed, canvas, paint, x, y, face_flag);
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
            }
        }else{
           die.drawEffect(animax_speed,canvas,paint,x,y,face_flag);
            if (!die.getFlag()){
                die_flag=true;
            }
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
        for_recycle(fist_atk_pic[0]);
        for_recycle(fist_atk_pic[1]);
        for_recycle(fist_atk_pic[2]);
        for_recycle(fist_squat_atk_pic);
        for_recycle(sword_atk_pic);
        for_recycle(sword_squat_atk_pic);
        for_recycle(msword_atk_pic);
        for_recycle(exsword_pre_pic);
        for_recycle(exsword_atk_pic);
        for_recycle(damaged_pic);
        for_recycle(die_pic);
        for_recycle(walk_back);
        for(int i=0;i< walk_pic.length;i++){
            for(int l=0;l< walk_pic[i].length;l++){
                walk_pic[i][l].recycle();
            }
        }
    }
    public void for_recycle(Bitmap[] tmp){
        for(int i=0;i<tmp.length;i++){
            tmp[i].recycle();
        }
    }
}

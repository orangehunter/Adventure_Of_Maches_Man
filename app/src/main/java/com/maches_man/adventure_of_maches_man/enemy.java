package com.maches_man.adventure_of_maches_man;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by michael on 2014/12/28.
 */
public class enemy {
    int move_flag;
    final int move_stop =0;
    final int move_walk =1;
    boolean draw_flag;
    int x,y;
    int face_flag;
    final int face_left=0;
    final int face_right=1;

    int lv;

    float hp_max;
    float hp;

    boolean life_flag;

    int attack_normal_coolTime;
    int attack_flag;
    final int attack_non=0;
    final int attack_normal=1;
    final int attack_skill=2;
    final int attack_super_skill=3;

    int weapon_flag;
    final int weapon_Fist   =0;
    final int weapon_Gun    =1;
    final int weapon_Sword  =2;


    Bitmap fist,gun,sword,other;
    Bitmap[] stop, sword_atk_pic, walk_back,fist_atk_pic;
    Bitmap[]  damaged_pic, die_pic;
    Bitmap[][] walk_pic;

    Animax sword_atk,damaged,die,fist_atk;
    Animax[]walk;

    public enemy(MainActivity activity){
        fist=   Graphic.LoadBitmap(activity.getResources(),R.drawable.player_fist       ,false);
        gun=    Graphic.LoadBitmap(activity.getResources(),R.drawable.player_gun        ,false);
        sword=  Graphic.LoadBitmap(activity.getResources(),R.drawable.player_sword      ,false);
        other=  Graphic.LoadBitmap(activity.getResources(),R.drawable.player_other      ,false);


        int size=100;

        fist_atk_pic =new Bitmap[3];
        for(int i=0;i<3;i++){
            fist_atk_pic[i]=Graphic.bitSize(Graphic.cutArea(fist,100*i,100,100,100),size,size);
        }
        fist_atk = new Animax(fist_atk_pic);


        sword_atk_pic =new Bitmap[4];
        for(int i=0;i<4;i++){
            sword_atk_pic[i]=Graphic.bitSize(Graphic.cutArea(sword,120*i,200,120,100),(int)(size*1.2),size);
        }
        sword_atk=new Animax(sword_atk_pic);


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
        move_flag= move_stop;

        x=1280/2;
        y=720/3*2;

        lv=1;

        hp_max=30;
        hp=hp_max;

        life_flag=true;
        attack_flag=attack_non;
        attack_normal_coolTime =5;

        weapon_flag=0;
    }
    public  void setStop(){
        if (move_flag!= move_stop)
            move_flag= move_stop;
    }
    public void setWalk(){
        if (move_flag!= move_walk)
            move_flag= move_walk;
        if (walk[weapon_flag].getFlag()==false)
            walk[weapon_flag].start();
    }

    public void setFace_flag(int face_flag){
        if (this.face_flag!=face_flag)
            this.face_flag=face_flag;
    }
    public void drawPlayer(Canvas canvas,Paint paint,double animax_speed){
        if (life_flag) {
            switch (move_flag){
                case move_stop:
                    switch (attack_flag) {
                        case attack_non:
                            draw(canvas, stop[weapon_flag], x, y, 0, 255, paint);
                            break;
                        case attack_normal:
                            switch (weapon_flag){
                                case weapon_Fist:
                                    fist_atk.drawEffect(animax_speed,canvas,paint,x,y,face_flag);
                                    break;
                                case weapon_Gun:
                                    draw(canvas, stop[weapon_flag], x, y, 0, 255, paint);
                                    break;
                                case weapon_Sword:
                                    sword_atk.drawEffect(animax_speed,canvas,paint,x,y,face_flag);
                                    break;
                            }
                            break;

                    }
                    break;
                case move_walk:
                    walk[weapon_flag].loop_flag=true;
                    draw(canvas,walk_back[weapon_flag],x,y,0,255,paint);
                    walk[weapon_flag].drawEffect(animax_speed,canvas,paint,x,y,face_flag);
                    break;

            }
        }else{
            die.drawEffect(animax_speed,canvas,paint,x,y,face_flag);
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
        for_recycle(fist_atk_pic);
        for_recycle(sword_atk_pic);
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

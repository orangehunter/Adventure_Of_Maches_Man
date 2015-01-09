package com.maches_man.adventure_of_maches_man;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by michael on 2014/12/28.
 */
public class Enemy {
    enemy_pic pic;
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

    boolean damage_flag=false;
    boolean die_flag=false;

    boolean life_flag;

    int attack_normal_coolTime;
    int attack_flag;
    final int attack_non=0;
    final int attack_normal=1;
    final int attack_skill=2;
    final int attack_super_skill=3;
    int damage=4;

    int weapon_flag;
    final int weapon_Fist   =0;
    final int weapon_Gun    =1;
    final int weapon_Sword  =2;




    public Enemy(MainActivity activity, enemy_pic pic){
        this.pic=pic;

        draw_flag=true;
        move_flag= move_stop;

        x=1280/2;
        y=-100;

        lv=1;

        hp_max=30;
        hp=hp_max;

        life_flag=false;
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
        if (pic.walk[weapon_flag].getFlag()==false)
            pic.walk[weapon_flag].start();
    }
    public void setDamage(){
        if (!pic.damaged.getFlag())
            pic.damaged.start();
        if (!damage_flag)
            damage_flag=true;
    }
    public void setDie(){
        if (!die_flag)
            die_flag=true;
        if (!pic.die.getFlag())
            pic.die.start();
    }

    public void setFace_flag(int face_flag){
        if (this.face_flag!=face_flag)
            this.face_flag=face_flag;
    }
    public int getX(int back_x){
        return this.x+back_x+640;
    }
    public boolean drawEnemy(Canvas canvas,Paint paint,double animax_speed,int back_x){
        int x=this.x+back_x+640;
        if (life_flag) {
            if (die_flag) {
                pic.die.drawEffect(animax_speed,canvas,paint,x,y,face_flag);
                if (!pic.die.getFlag()) {
                    die_flag = false;
                    life_flag = false;
                    return true;
                }
            }else if (damage_flag){
                pic.damaged.drawEffect(animax_speed,canvas,paint,x,y,face_flag);
                if (!pic.damaged.getFlag())
                    damage_flag=false;
            }else {
                switch (move_flag) {
                    case move_stop:
                        switch (attack_flag) {
                            case attack_non:
                                draw(canvas, pic.stop[weapon_flag], x, y, 0, 255, paint);
                                break;
                            case attack_normal:
                                switch (weapon_flag) {
                                    case weapon_Fist:
                                        pic.fist_atk.drawEffect(animax_speed, canvas, paint, x, y, face_flag);
                                        break;
                                    case weapon_Gun:
                                        draw(canvas, pic.stop[weapon_flag], x, y, 0, 255, paint);
                                        break;
                                    case weapon_Sword:
                                        pic.sword_atk.drawEffect(animax_speed, canvas, paint, x, y, face_flag);
                                        break;
                                }
                                break;

                        }
                        break;
                    case move_walk:
                        pic.walk[weapon_flag].loop_flag = true;
                        draw(canvas, pic.walk_back[weapon_flag], x, y, 0, 255, paint);
                        pic.walk[weapon_flag].drawEffect(animax_speed, canvas, paint, x, y, face_flag);

                        break;

                }
            }
        }
        return false;
    }
    public void draw(Canvas canvas,Bitmap bit,int x,int y,int rot,int alpha,Paint paint){
        if(face_flag==0) {
            Graphic.drawPic(canvas, Graphic.MirrorFlipHorizontal(bit), x, y, rot, alpha, paint);
        }else {
            Graphic.drawPic(canvas, bit, x, y, rot, alpha, paint);
        }
    }

}

package com.maches_man.adventure_of_maches_man;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by michael on 2014/12/28.
 */
public class enemy {
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




    public enemy(MainActivity activity,enemy_pic pic){
        this.pic=pic;

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
        if (pic.walk[weapon_flag].getFlag()==false)
            pic.walk[weapon_flag].start();
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
                            draw(canvas, pic.stop[weapon_flag], x, y, 0, 255, paint);
                            break;
                        case attack_normal:
                            switch (weapon_flag){
                                case weapon_Fist:
                                    pic.fist_atk.drawEffect(animax_speed,canvas,paint,x,y,face_flag);
                                    break;
                                case weapon_Gun:
                                    draw(canvas, pic.stop[weapon_flag], x, y, 0, 255, paint);
                                    break;
                                case weapon_Sword:
                                    pic.sword_atk.drawEffect(animax_speed,canvas,paint,x,y,face_flag);
                                    break;
                            }
                            break;

                    }
                    break;
                case move_walk:
                    pic.walk[weapon_flag].loop_flag=true;
                    draw(canvas,pic.walk_back[weapon_flag],x,y,0,255,paint);
                    pic.walk[weapon_flag].drawEffect(animax_speed,canvas,paint,x,y,face_flag);
                    break;

            }
        }else{
            pic.die.drawEffect(animax_speed,canvas,paint,x,y,face_flag);
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

}

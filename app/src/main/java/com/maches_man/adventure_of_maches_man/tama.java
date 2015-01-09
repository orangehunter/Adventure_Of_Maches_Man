package com.maches_man.adventure_of_maches_man;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by michael on 2015/1/8.
 */
public class tama {
    int x,y;
    boolean flag;
    int tama_style;
    final int tama_player=0;
    final int tama_playerex=1;
    final int tama_enemy=2;

    int tama_way;
    final int way_left=0;
    final int way_right=1;

    int speed=15;
    int exspeed=20;

    Bitmap tama[];
    public tama(Bitmap[] tama){
        this.tama=tama;
    }
    public void start(int x,int y,int face,int style){
        this.x=x;
        this.y=y;
        this.tama_way=face;
        this.tama_style=style;
        this.flag=true;
    }
    public void stop(){
        flag=false;
    }
    public boolean getFlag(){
        return flag;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getStyle(){
        return tama_style;
    }
    public void draw_tama(Canvas canvas,Paint paint){
        if (flag) {
            Graphic.drawPic(canvas, tama[tama_style], x, y, 0, 255, paint);
            if (tama_style == tama_playerex) {
                add(exspeed);
            } else {
                add(speed);
            }
            if (x>1300||x<-20){
                flag=false;
            }
        }
    }
    public void add(int speed){
        if (flag) {
            if (tama_way == 0) {
                x -= speed;
            }
            if (tama_way == 1) {
                x += speed;
            }
        }
    }

}

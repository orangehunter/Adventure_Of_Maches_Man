package com.maches_man.adventure_of_maches_man;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Animax {

    Boolean animax_flag=false;
    Boolean pause_flag=false;
    Boolean loop_flag=false;

    double animax_count_flag=0;
    //double count_unit;
    int pic_number=0;
    //int duration;
    //int start_position;
    //int x;
    //int y;

    Bitmap []pic;

    public Animax(Bitmap pic[]){
        //this.pic=new Bitmap [pic.length];
        this.pic=pic;
        this.pic_number=this.pic.length;
    }

    public void start(){//啟動(無設定長度)
        animax_flag=true;
        animax_count_flag=0;
    }
    public void stop(){
        animax_flag=false;
        animax_count_flag=0;
    }
    public void pause(){//暫停
        pause_flag=true;
    }
    public void resume(){//取消暫停
        pause_flag=false;
    }
    public boolean getPause(){//取得暫停狀態
        return pause_flag;
    }
    public int getCount(){
        return (int)animax_count_flag;
    }

    public void drawEffect(double speed,Canvas canvas,Paint paint,int x,int y,int face_flag){//繪圖(無設定長度)
        if(animax_flag){
            if(!pause_flag){
                animax_count_flag+=speed;
            }
            if(((int)animax_count_flag)<pic_number){
                if (face_flag==1) {
                    Graphic.drawPic(canvas, pic[((int) animax_count_flag)%pic_number], x, y, 0, 255, paint);
                }
                if (face_flag==0){
                    Graphic.drawPic(canvas, Graphic.MirrorFlipHorizontal(pic[((int) animax_count_flag)%pic_number]), x, y, 0, 255, paint);
                }
            }else{
                if (loop_flag){
                    animax_count_flag = 0;
                }else {
                    animax_flag = false;
                    animax_count_flag = 0;
                }
            }
        }
    }

    public Boolean getFlag(){
        return animax_flag;
    }


}

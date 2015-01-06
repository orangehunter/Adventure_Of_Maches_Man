package com.maches_man.adventure_of_maches_man;

import android.graphics.Bitmap;

/**
 * Created by michael on 2015/1/6.
 */
public class enemy_pic {
    Bitmap fist,gun,sword,other;
    Bitmap[] stop, sword_atk_pic, walk_back,fist_atk_pic;
    Bitmap[]  damaged_pic, die_pic;
    Bitmap[][] walk_pic;

    Animax sword_atk,damaged,die,fist_atk;
    Animax[]walk;
    public enemy_pic(MainActivity activity){
        fist=   Graphic.LoadBitmap(activity.getResources(),R.drawable.teki_riot       ,false);
        gun=    Graphic.LoadBitmap(activity.getResources(),R.drawable.teki_gun        ,false);
        sword=  Graphic.LoadBitmap(activity.getResources(),R.drawable.teki_sword      ,false);
        other=  Graphic.LoadBitmap(activity.getResources(),R.drawable.teki_other      ,false);


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

        walk_pic =new Bitmap[3][];
        walk_pic[0]=new Bitmap[3];
        walk_pic[0][0]= Graphic.bitSize(Graphic.cutArea(fist, 0  , 0, 100, 100), size, size);
        walk_pic[0][1]= Graphic.bitSize(Graphic.cutArea(fist, 100, 0, 100, 100), size, size);
        walk_pic[0][2]= Graphic.bitSize(Graphic.cutArea(fist, 200, 0, 100, 100), size, size);
        walk_back=new Bitmap[3];
        walk_back[0]=Graphic.bitSize(Graphic.LoadBitmap(activity.getResources(),R.drawable.teki_riot_back,false),size,size);


        walk_pic[1]=new Bitmap[3];
        walk_pic[1][0]= Graphic.bitSize(Graphic.cutArea(gun, 0, 0, 100, 100),size,size);
        walk_pic[1][1]= Graphic.bitSize(Graphic.cutArea(gun, 100, 0, 100, 100),size,size);
        walk_pic[1][2]= Graphic.bitSize(Graphic.cutArea(gun, 200, 0, 100, 100),size,size);
        walk_back[1]=Graphic.bitSize(Graphic.LoadBitmap(activity.getResources(),R.drawable.teki_gun_back,false),size,size);


        walk_pic[2]=new Bitmap[2];
        walk_pic[2][0]= Graphic.bitSize(Graphic.cutArea(sword, 0, 0, 100, 100),size,size);
        walk_pic[2][1]= Graphic.bitSize(Graphic.cutArea(sword, 100, 0, 100, 100),size,size);
        walk_back[2]=Graphic.bitSize(Graphic.LoadBitmap(activity.getResources(),R.drawable.teki_sword_back,false),size,size);

        walk=new Animax[3];
        for (int i=0;i<3;i++){
            walk[i] = new Animax(walk_pic[i]);
        }

        fist.recycle();
        gun.recycle();
        sword.recycle();
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

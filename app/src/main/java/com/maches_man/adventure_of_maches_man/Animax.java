package com.maches_man.adventure_of_maches_man;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Animax {
	
	Boolean animax_flag=false;
	Boolean pause_flag=false;
    Boolean loop_flag=false;
	
	double animax_count_flag=0;	
	double count_unit;
	int duration;
	int start_position;
	int x;
	int y;
	

    public void setLoop(boolean loop){loop_flag=loop;}

	public void setPosition(int x,int y){//�]�w��m
		this.x=x;
		this.y=y;
	}

	public void start(){//�Ұ�(�L�]�w����)
		animax_flag=true;
		 animax_count_flag=0;	
	}

	public void pause(){//�Ȱ�
		pause_flag=true;
	}

	public void resume(){//�����Ȱ�
	pause_flag=false;		
	}

    public void stop(){
        animax_flag = false;
        animax_count_flag = 0;
    }

	public boolean getPause(){//���o�Ȱ����A
		return pause_flag;
	}

	public int getCount(){
		return (int)animax_count_flag;
	}

	public void drawEffect(Bitmap pic[],double speed,Canvas canvas,Paint paint,int flip){//ø��(�L�]�w����)
		if(animax_flag){
			if(!pause_flag){
			animax_count_flag+=speed;
			}
			if(((int)animax_count_flag)<pic.length){
                if (flip==0) {
                    Graphic.drawPic(canvas, Graphic.MirrorFlipHorizontal(pic[((int) animax_count_flag)]), x, y, 0, 255, paint);
                }else{
                    Graphic.drawPic(canvas, pic[((int) animax_count_flag)], x, y, 0, 255, paint);
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

package com.maches_man.adventure_of_maches_man;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity{
	int nowActivity=0;
	MainView mainview;
    TeachView teachview;
	gameView gameview;
    ScoreView scoreview;

	Intent intent;
	Intent deintent;

    int video_select;
    int Score = 0;


	public void changeView(int what)//切換Serface訊息發送
	{
		Message msg = myHandler.obtainMessage(what); 
		myHandler.sendMessage(msg);
		nowActivity=what;
	} 
	
	Handler myHandler = new Handler(){//接收各個SurfaceView傳送的訊息
		public void handleMessage(Message msg) {
			switch(msg.what)
			{
			case 0:
				goToMainView();
				break;
			case 1:
				goToGameview();
				break;
            case 2:
                goToTeachview();
                break;
            case 3:
                goToScoreview();
                break;
			}
		}
	};
	
	private void goToMainView() {
		if(mainview==null)
		{
			mainview=new MainView(this);
		}
		setContentView(mainview);
		mainview.requestFocus();//取得焦點
		mainview.setFocusableInTouchMode(true);//設為可觸控
	}
	private void goToGameview() {
		if(gameview ==null)
		{
			gameview =new gameView(this);
		}
		setContentView(gameview);
		gameview.requestFocus();//取得焦點
		gameview.setFocusableInTouchMode(true);//設為可觸控
	}
    private void goToTeachview() {
        if(teachview ==null)
        {
            teachview =new TeachView(this);
        }
        setContentView(teachview);
        teachview.requestFocus();//取得焦點
        teachview.setFocusableInTouchMode(true);//設為可觸控
    }
    private void goToScoreview() {
        if(scoreview ==null)
        {
            scoreview =new ScoreView(this);
        }
        setContentView(scoreview);
        scoreview.requestFocus();//取得焦點
        scoreview.setFocusableInTouchMode(true);//設為可觸控
    }

	
	public void callToast(int what)//Toast訊息傳送
	{
		Message msg = toastHandler.obtainMessage(what); 
		toastHandler.sendMessage(msg);
	} 
	Handler toastHandler = new Handler(){//處理各個SurfaceView傳送的Toast訊息
		public void handleMessage(Message msg) {
			switch(msg.what)
			{
			case 0:
				createToast("");
				break;
			case 1:
				createToast("");
				break;
		
			}
		}
	};
	public void createToast(String msg){//顯示Toast
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//游戲過程中只容許調整多媒體音量，而不容許調整通話音量
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉標題
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉標頭
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//強制橫屏
		//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//強制直屏

		//取得解析度
		DisplayMetrics dm=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		//給常數類別中的螢幕高和寬給予值
		//衡屏
		if(dm.widthPixels>dm.heightPixels)
		{
			Constant.SCREEN_WIDTH=dm.widthPixels;
			Constant.SCREEN_HIGHT=dm.heightPixels;
		}else
		{
			Constant.SCREEN_HIGHT=dm.widthPixels;
			Constant.SCREEN_WIDTH=dm.heightPixels;
		}
		if(Constant.SCREEN_HIGHT>Constant.SCREEN_WIDTH/16*9)//將螢幕固定為16:9
			Constant.SCREEN_HIGHT=Constant.SCREEN_WIDTH/16*9;
		else
			Constant.SCREEN_WIDTH=Constant.SCREEN_HIGHT/9*16;
		
		//直屏
		/*
		 if(dm.widthPixels<dm.heightPixels)
		{
			Constant.SCREEN_WIDTH=dm.widthPixels;
			Constant.SCREEN_HIGHT=dm.heightPixels;
		}else
		{
			Constant.SCREEN_HIGHT=dm.widthPixels;
			Constant.SCREEN_WIDTH=dm.heightPixels;
		}
		if(Constant.SCREEN_WIDTH>Constant.SCREEN_HIGHT/16*9)//將螢幕固定為16:9
			Constant.SCREEN_WIDTH=Constant.SCREEN_HIGHT/16*9;
		else
			Constant.SCREEN_HIGHT=Constant.SCREEN_WIDTH/9*16;
		*/
		

		Constant.GAME_WIDTH_UNIT= ((float)Constant.SCREEN_WIDTH/Constant.DEFULT_WITH);
		Constant.SCREEN_HEIGHT_UNIT= ((float)Constant.SCREEN_HIGHT/Constant.DEFULT_HIGHT);
		
		changeView(0);//進入"0界面"
	}
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)//按鍵偵測
	{
		if(keyCode==4)//返回建
		{
			switch(nowActivity)//偵測目前介面
			{
			case 1:
				Constant.Flag=false;
				this.changeView(0);//回到0界面
				break;
			case 0:
				System.exit(0);//離開游戲
				break;

			}
			return true;
		}
		/*if(keyCode==e.KEYCODE_HOME){//HOME鍵
			 System.exit(0);
			return true;
		}*/
		return false;

	}
	

	@Override 
	public void onResume(){
		Constant.setFlag(true);
		super.onResume();
	}
	@Override 
	public void onPause(){
		Constant.setFlag(false);
		super.onPause();		
	}
}

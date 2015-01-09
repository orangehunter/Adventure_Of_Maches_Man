package com.maches_man.adventure_of_maches_man;

public class Constant {
static int SCREEN_WIDTH;
static int SCREEN_HIGHT;

//直屏
static int DEFULT_WITH=1280;
static int DEFULT_HIGHT=720;
//橫屏
/*
 static int DEFULT_WITH=720;
static int DEFULT_HIGHT=1280;
 */
static float GAME_WIDTH_UNIT;
static float SCREEN_HEIGHT_UNIT;

static boolean Flag;

public static void setFlag(boolean set){	
	Constant.Flag=set;
}
}
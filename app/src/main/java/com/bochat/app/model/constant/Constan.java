package com.bochat.app.model.constant;

import android.os.Environment;

public class Constan {

    /*网络相关常量*/
    public final static int CONNECT_TIMEOUT=60;
    public final static int READ_TIME_OUT=60;
    public final static int WRITE_TIME_OUT=60;
    public final static String TAG="ldl_debug";
    public final static int NET_SUCCESS=0;

    /*数据库操作*/
    public final static String DbName="bochat_db_2";

    /*微信QQ*/
    public final static String QQ_APPID="1108740347";
    public final static String WECHAT_APPID="wx0389afb0bb6b3ae5";

    /*日常使用*/
    public final static String GET_SWEET_RECEIVER="GET_SWEET_RECEIVER";
    public final static int DEVICE_PHOTO_REQUEST=2003;
    public final static int VIEWPAGER_JUMP_KEY=2004;
    public final static String DOWN_ACTION_STAR="DOWN_ACTION_STAR";
    public final static String DOWN_ACTION_PROGREE="DOWN_ACTION_PROGREE";
    public final static String DOWN_ACTION_DOWN_STOP="DOWN_ACTION_DOWN_STOP";
    public final static String DOWN_ACTION_DOWN_FINISH="DOWN_ACTION_DOWN_FINISH";
    public final static String SWEET_SYSTEM_MESSAGE="SWEET_SYSTEM_MESSAGE";
    public final static int CHOOSE_BID=5001;
    public final static int SPEED_CONVER_STATU=6001;
    public final static int USE_CAMERA=7001;
    public final static int CHOOSE_PHOTO=7002;
    public static final int REQUEST_CODE_CLIP_PHOTO = 8001;

    public final static String DIRPATH= Environment.getExternalStorageDirectory()+"/BoChat";
    public final static String SOON_FILE_DIR="/apk";
    public final static String APK_NAME="/BoChat.apk";

    public final static int GET_GROUP_REDPACKET=1002;
    public final static int GET_GROUP_FLASH_EXCHANGE=1001;

    /*好友申请*/
    public final static int FRIEND_APPLY_ACCOUNT=1001;
    public final static int FRIEND_APPLY_CODE=1002;
    public final static int FRIEND_APPLY_GROUP=1003;

    public final static String REDPACKET_TITLE="送利是，猪佩奇";



}

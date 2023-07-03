package com.coolweather.android.util;

import android.content.Context;
import android.content.SharedPreferences;


public class SharePreferenceUtils {

    private static final String SHARE_NAME="note";

    /**
     * 写入手机号码
     * @param mContext
     * @param phone
     */
    public static void writePhone(Context mContext, String phone){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHARE_NAME,mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("phone",phone);
        editor.commit();
    }


    /**
     * 读取手机号码
     * @param mContext
     * @return
     */
    public static String getPhone(Context mContext){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHARE_NAME,mContext.MODE_PRIVATE);
        return sharedPreferences.getString("phone",null);
    }

    /**
     * 写入密码
     * @param mContext
     * @param password
     */
    public static void writePassword(Context mContext, String password){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHARE_NAME,mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("password",password);
        editor.commit();
    }


    /**
     * 读取密码
     * @param mContext
     * @return
     */
    public static String getPassword(Context mContext){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHARE_NAME,mContext.MODE_PRIVATE);
        return sharedPreferences.getString("password",null);
    }

    /**
     * 写入是否记住登录状态
     * @param mContext
     * @param b
     */
    public static void writeRememder(Context mContext, boolean b){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHARE_NAME,mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("remember",b);
        editor.commit();
    }

    /**
     * 获取是否记住登录状态
     * @param mContext
     * @return
     */
    public static boolean getRemember(Context mContext){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHARE_NAME,mContext.MODE_PRIVATE);
        return sharedPreferences.getBoolean("remember",false);
    }

    /**
     * 写入cid
     * @param mContext
     * @param cid
     */
    public static void writeCid(Context mContext, String cid){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHARE_NAME,mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("cid",cid);
        editor.commit();
    }

    /**
     * 获取cid
     * @param mContext
     * @return
     */
    public static String getCid(Context mContext){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHARE_NAME,mContext.MODE_PRIVATE);
        return sharedPreferences.getString("cid",null);
    }
}

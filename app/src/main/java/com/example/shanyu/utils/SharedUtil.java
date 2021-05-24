package com.example.shanyu.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.shanyu.MyApplication;


/**
 * 作者： HeroCat
 * 时间：2018/12/10/010
 * 描述：
 */
public class SharedUtil {

    private static Context context;
    private static SharedUtil sharedUtil;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private static final String fileName = "qy_file";

    public static SharedUtil getIntence() {
        if (sharedUtil == null) {
            SharedUtil.context = MyApplication.context;
            preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            editor = preferences.edit();
            sharedUtil = new SharedUtil();
        }
        return sharedUtil;
    }

    private SharedUtil() {
    }


    public boolean setMobile(String token) {
        editor.putString("mobile", token);
        return editor.commit();
    }


    public String getMobile() {
        return preferences.getString("mobile", "");
    }
    
    /**
     * 获取倒计时时间
     *
     * @return
     */
    public int getCodeTime(boolean isUpdata) {
        //获取现在时间
        int time = (int) (System.currentTimeMillis() / 1000);
        int T = time - getTimeWithPhone();
        if (T > 60) {//距上次请求验证码以及超过60秒
            if (isUpdata)
                setTimeWithPhone(time);
            return 60;
        } else {
            return 60 - T;
        }
    }

    private boolean setTimeWithPhone(int time) {
        editor.putInt("regist_code", time);
        return editor.commit();
    }

    private int getTimeWithPhone() {
        return preferences.getInt("regist_code", 0);
    }


    public boolean setAccount(String token) {
        editor.putString("account", token);
        return editor.commit();
    }

    public String getAccount() {
        return preferences.getString("account", "");
    }

    public boolean setUid(String uid) {
        editor.putString("uid", uid);
        return editor.commit();
    }

    public String getUid() {
        return preferences.getString("uid", "");
    }


    public boolean setMessage(int flag) {
        editor.putInt("ismessage", flag);
        return editor.commit();
    }

    public int isMessage() {
        return preferences.getInt("ismessage", 0);
    }

    //=============================定位信息=============================================

    public boolean setCity(String city) {
        editor.putString("city", city);
        return editor.commit();
    }

    public String getCity() {
        return preferences.getString("city", "");
    }

    public boolean setAddress(String address) {
        editor.putString("address", address);
        return editor.commit();
    }

    public String getAddress() {
        return preferences.getString("address", "");
    }

    public boolean setLongitude(String longitude) {
        editor.putString("longitude", longitude);
        return editor.commit();
    }

    public String getLongitude() {
        return preferences.getString("longitude", "120.142294");
    }

    public boolean setLatitude(String latitude) {
        editor.putString("latitude", latitude);
        return editor.commit();
    }

    public String getLatitude() {
        return preferences.getString("latitude", "30.291441");
    }


    //=================================================================================


    public boolean setNickName(String nickName) {
        editor.putString("nickName", nickName);
        return editor.commit();
    }

    public String getNickName() {
        return preferences.getString("nickName", "");
    }


    public boolean setAvatar(String avatar) {
        editor.putString("avatar", avatar);
        return editor.commit();
    }

    public String getAvatar() {
        return preferences.getString("avatar", "");
    }


    /**
     * 保存震动开关
     *
     * @param shake
     */
    public void saveShake(boolean shake) {
        editor.putBoolean("shake", shake);
        editor.commit();
    }

    /**
     * 保存声音开关
     *
     * @param voice
     */
    public void saveVoice(boolean voice) {
        editor.putBoolean("voice", voice);
        editor.commit();
    }

    /**
     * 设置第一次进入标记
     *
     * @param flag
     */
    public void setFirstInto(boolean flag) {
        editor.putBoolean("firstInto:", flag);
        editor.commit();
    }

    /**
     * 获取第一次进入标记
     */
    public boolean isFirstInto() {
        return preferences.getBoolean("firstInto:", true);
    }


    /**
     * 获取震动是否开关
     *
     * @return
     */
    public boolean getShake() {
        return preferences.getBoolean("shake", false);
    }

    /**
     * 获取声音是否开关
     *
     * @return
     */
    public boolean getVoice() {
        return preferences.getBoolean("voice", false);
    }


}

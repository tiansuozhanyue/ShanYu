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


    public boolean setToken(String token) {
        editor.putString("token", token);
        return editor.commit();
    }


    public String getToken() {
        return preferences.getString("token", "");
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

    public boolean setKeyWordHeight(int height) {
        editor.putInt("KeyWordHeight", height);
        return editor.commit();
    }


    public int getKeyWordHeight() {
        return preferences.getInt("KeyWordHeight", 0);
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


    public boolean setUserId(String userId) {
        editor.putString("userId", userId);
        return editor.commit();
    }

    public String getUserId() {
        return preferences.getString("userId", "");
    }

    public boolean setPlayTeaherId(String userId) {
        editor.putString("playTeaherId", userId);
        return editor.commit();
    }

    public String getPlayTeaherId() {
        return preferences.getString("playTeaherId", "");
    }

    public boolean setNickName(String nickName) {
        editor.putString("nickName", nickName);
        return editor.commit();
    }

    public String getNickName() {
        return preferences.getString("nickName", "");
    }

    public boolean setHeadImg(String headImg) {
        editor.putString("headImg" + getUserId(), headImg);
        return editor.commit();
    }

    public int getSex() {
        return preferences.getInt("sex" + getUserId(), 0);
    }

    public boolean setSex(int sex) {
        editor.putInt("sex" + getUserId(), sex);
        return editor.commit();
    }

    public String getHeadImg() {
        return preferences.getString("headImg" + getUserId(), "");
    }

    public boolean setVerified(int level) {
        editor.putInt("verified", level);
        return editor.commit();
    }

    public int getVerified() {
        return preferences.getInt("verified", 0);
    }

    public boolean isRead() {
        return preferences.getBoolean("order" + getUserId(), true);
    }

    public void setRead(boolean flag) {
        editor.putBoolean("order" + getUserId(), flag);
        editor.commit();
    }

    public boolean isRead_sys() {
        return preferences.getBoolean("msg_sys" + getUserId(), true);
    }

    public void setRead_sys(boolean flag) {
        editor.putBoolean("msg_sys" + getUserId(), flag);
        editor.commit();
    }

    public boolean isRead_order() {
        return preferences.getBoolean("msg_order" + getUserId(), true);
    }

    public void setRead_order(boolean flag) {
        editor.putBoolean("msg_order" + getUserId(), flag);
        editor.commit();
    }


    /**
     * 1表示开启新提醒；0表示关闭提醒
     *
     * @return
     */
    public int getMessageRemain() {
        return preferences.getInt("isOpen", 0);
    }

    /**
     * 设置消息提醒是否开启
     *
     * @param remainMessage
     */
    public void saveMessageRemain(int remainMessage) {
        editor.putInt("isOpen", remainMessage);
        editor.commit();
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

package com.example.shanyu.login;

import android.content.Intent;

import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.main.MainActivity;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.utils.SharedUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public abstract class BaseLoginActivity extends BaseActivity implements EMCallBack {

    protected void goLogin(String phone, String uid) {
        //保存数据
        SharedUtil.getIntence().setAccount(phone);
        SharedUtil.getIntence().setUid(uid);
        startActivity(new Intent(BaseLoginActivity.this, MainActivity.class));

        new EMThread().start();
    }


    class EMThread extends Thread {

        @Override
        public void run() {
            super.run();

            try {
                //登录注册
                EMClient.getInstance().createAccount(SharedUtil.getIntence().getUid(), SharedUtil.getIntence().getUid());//同步方法

                EMClient.getInstance().login(SharedUtil.getIntence().getUid(), SharedUtil.getIntence().getUid(), BaseLoginActivity.this);
            } catch (HyphenateException e) {
                if (e.getErrorCode() == 203) {//用户已存在
                    //登录环信
                    EMClient.getInstance().login(SharedUtil.getIntence().getUid(), SharedUtil.getIntence().getUid(), BaseLoginActivity.this);
                } else {
                    LogUtil.net_i("---->环信注册失败：" + e.getErrorCode());
                }
            }

        }
    }

    @Override
    public void onSuccess() {
        LogUtil.net_i("---->环信登录成功 : "+SharedUtil.getIntence().getUid());
    }

    @Override
    public void onError(int i, String s) {
        LogUtil.net_i("---->环信登录失败：" + s);
    }

    @Override
    public void onProgress(int i, String s) {

    }

}

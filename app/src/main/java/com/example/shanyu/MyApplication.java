package com.example.shanyu;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.example.shanyu.http.HttpApi;
import com.example.shanyu.main.chat.EaseHelper;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MyApplication extends Application {

    public static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        // 将该app id 注册到微信
        IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        msgApi.registerApp(HttpApi.WX_APPID);

        //初始化环信
        EaseHelper.getInstance().init(context);

    }

}

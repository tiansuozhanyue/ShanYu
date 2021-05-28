package com.example.shanyu.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shanyu.base.EventBean;
import com.example.shanyu.http.HttpApi;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * 微信登录回调
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, HttpApi.WX_APPID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    /**
     * 处理结果回调
     *
     * @param resp
     */
    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {//登录

            if (resp.errCode == 0) {
                SendAuth.Resp newResp = (SendAuth.Resp) resp;
                EventBus.getDefault().post(new EventBean(EventBean.LOGIN_SUCESSS, newResp.code));
            }

        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {//分享

            if (resp.errCode == 0) {
                EventBus.getDefault().post(new EventBean(EventBean.SHARE_SUCESSS));
            }

        }

        finish();//这里需要关闭该页面

    }

}
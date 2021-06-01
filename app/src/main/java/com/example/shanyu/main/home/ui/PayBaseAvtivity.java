package com.example.shanyu.main.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.alipay.sdk.app.PayTask;
import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.base.EventBean;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.bean.WxPayBean;
import com.example.shanyu.main.mine.ui.MineOrderActivity;
import com.example.shanyu.utils.ToastUtil;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public abstract class PayBaseAvtivity extends BaseActivity {

    final int SDK_PAY_FLAG = 100;
    String orderId, allSum;
    MyHandler myHandler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 下单
     */
    protected void setOrder(Map<String, String> payMap) {
        showLoading();
        HttpUtil.doPost(HttpApi.ORDER_ADD, payMap, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();

                switch (payMap.get("type")) {

                    case "0":
                        WxPayBean wxPayBean = new Gson().fromJson(resultData, WxPayBean.class);
                        orderId = wxPayBean.getOrderId() + "";
                        allSum = wxPayBean.getMoney() + "";
                        startWechatPay(wxPayBean);
                        break;

                    case "1":
                        try {
                            JSONObject object = new JSONObject(resultData);
                            orderId = object.getInt("order_id") + "";
                            allSum = object.getDouble("total_fee") + "";
                            startAliPay(object.getString("order"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                }

            }
        });

    }

    /**
     * 调用支付宝本地支付
     *
     * @param orderInfo
     */
    private void startAliPay(String orderInfo) {
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(this);
            Map<String, String> result = alipay.payV2(orderInfo, true);

            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            myHandler.sendMessage(msg);

        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }


    private class MyHandler extends Handler {
        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);

            switch (msg.what) {
                case SDK_PAY_FLAG:
                    Map<String, String> result = (Map<String, String>) msg.obj;
                    if ("9000".equals(result.get("resultStatus"))) {
                        goPaySucess();
                    } else {
                        goPayFaile();
                    }
                    break;
            }

        }
    }

    private void goPaySucess() {
        Intent intent_s = new Intent(this, PaySucessActivity.class);
        intent_s.putExtra("orderId", orderId);
        intent_s.putExtra("money", allSum);
        startActivity(intent_s);
    }

    private void goPayFaile() {
        startActivity(new Intent(this, MineOrderActivity.class).putExtra("index", 1));
    }

    /**
     * 调起本地微信支付
     *
     * @param wxPayBean
     */
    private void startWechatPay(WxPayBean wxPayBean) {

        //这里的appid，替换成自己的即可
        IWXAPI api = WXAPIFactory.createWXAPI(this, HttpApi.WX_APPID);
        api.registerApp(HttpApi.WX_APPID);

        //这里的bean，是服务器返回的json生成的bean
        PayReq payRequest = new PayReq();
        payRequest.appId = wxPayBean.getAppid();
        payRequest.partnerId = wxPayBean.getPartnerid();
        payRequest.prepayId = wxPayBean.getPrepayid();
        payRequest.packageValue = "Sign=WXPay";//固定值
        payRequest.nonceStr = wxPayBean.getNoncestr();
        payRequest.timeStamp = wxPayBean.getTimestamp();
        payRequest.sign = wxPayBean.getSign();

        orderId = wxPayBean.getOrderId().toString();

        //发起请求，调起微信前去支付
        api.sendReq(payRequest);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void studentEventBus(EventBean eventBean) {
        switch (eventBean.flag) {

            case EventBean.PAY_SUCESSS:
                goPaySucess();
                break;

            case EventBean.PAY_FAILE:
            case EventBean.PAY_CANCLE:
                goPayFaile();
                break;

        }

    }


}

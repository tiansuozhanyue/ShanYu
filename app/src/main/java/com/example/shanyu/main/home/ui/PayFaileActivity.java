package com.example.shanyu.main.home.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.main.home.bean.WxPayBean;
import com.example.shanyu.widget.CirButton;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayFaileActivity extends BaseActivity {

    @BindView(R.id.money)
    public TextView money;
    @BindView(R.id.code)
    public TextView code;
    @BindView(R.id.address)
    public TextView address;
    @BindView(R.id.final_sum11)
    public TextView final_sum11;
    @BindView(R.id.final_sum12)
    public TextView final_sum12;
    @BindView(R.id.goPay)
    public CirButton goPay;
    @BindView(R.id.payGroup)
    public RadioGroup payGroup;
    WxPayBean wxPayBean;
    String lacation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_faile, "支付失败");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        wxPayBean = (WxPayBean) getIntent().getSerializableExtra("wxPayBean");
        lacation = getIntent().getStringExtra("lacation");
        money.setText("￥" + wxPayBean.getMoney());
        code.setText(wxPayBean.getPartnerid());
        address.setText(lacation);

        String[] sum = String.valueOf(wxPayBean.getMoney()).split("\\.");
        final_sum11.setText(sum[0]);
        final_sum12.setText("." + sum[1]);

        payGroup.check(R.id.wxPay);
        goPay.setSelected(true);


    }

    @OnClick({R.id.goPay})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.goPay:
                startWechatPay();
                break;
        }
    }

    /**
     * 调起本地微信支付
     */
    private void startWechatPay() {

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

        //发起请求，调起微信前去支付
        api.sendReq(payRequest);

    }

}
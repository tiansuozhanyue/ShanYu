package com.example.shanyu.main.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.bean.WxPayBean;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.widget.CirButton;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayFaileActivity extends PayBaseAvtivity implements RadioGroup.OnCheckedChangeListener {

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
    String orderId, money_free, lacation;
    static Map<String, String> payMap;

    public static void start(BaseActivity activity, Map<String, String> map, String orderId, String money, String address) {
        PayFaileActivity.payMap = map;
        Intent intent = new Intent(activity, PayFaileActivity.class);
        intent.putExtra("lacation", address);
        intent.putExtra("orderId", orderId);
        intent.putExtra("money", money);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_faile, "支付失败");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {

        orderId = getIntent().getStringExtra("orderId");
        money_free = getIntent().getStringExtra("money");
        lacation = getIntent().getStringExtra("lacation");

        if (payMap.get("type") == "0") {
            payGroup.check(R.id.wxPay);
        } else {
            payGroup.check(R.id.aliPay);
        }

        goPay.setSelected(true);

        money.setText("￥" + money_free);
        code.setText(orderId);
        address.setText(lacation);

        String[] sum = money_free.split("\\.");
        final_sum11.setText(sum[0]);
        final_sum12.setText("." + sum[1]);

        payGroup.setOnCheckedChangeListener(this);

    }

    @OnClick({R.id.goPay})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.goPay:
                setOrder(payMap, lacation);
                break;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.aliPay:
                payMap.put("type", "1");
                break;
            case R.id.wxPay:
                payMap.put("type", "0");
                break;
        }
    }

}
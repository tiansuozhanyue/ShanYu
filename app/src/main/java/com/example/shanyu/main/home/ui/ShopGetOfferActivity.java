package com.example.shanyu.main.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.bean.ShopOfferBean;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.CirButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.to.aboomy.banner.IndicatorView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopGetOfferActivity extends BaseActivity {

    ShopOfferBean bean;

    @BindView(R.id.mCirButton)
    public CirButton mCirButton;
    @BindView(R.id.money)
    public TextView money;
    @BindView(R.id.rule)
    public TextView rule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_get_offer, "领取优惠券");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        bean = (ShopOfferBean) getIntent().getSerializableExtra("ShopOfferBean");
        money.setText(bean.getMoney() + "");
        rule.setText(bean.getExplain());
        mCirButton.setSelected(true);
    }

    @OnClick({R.id.mCirButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mCirButton:
                getOffers();
                break;
        }
    }

    /**
     * 获取优惠券列表
     */
    private void getOffers() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("relation_id", bean.getId() + "");
        HttpUtil.doPost(HttpApi.OFFERS_ADD, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                ToastUtil.shortToastMid(errorMsg);
            }

            @Override
            public void onSuccess(String resultData) {
                ToastUtil.shortToastMid("领取成功");
            }
        });

    }


}
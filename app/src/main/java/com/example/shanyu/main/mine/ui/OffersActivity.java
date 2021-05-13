package com.example.shanyu.main.mine.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.mine.adapter.FootAdapter;
import com.example.shanyu.main.mine.adapter.OfferAdapter;
import com.example.shanyu.main.mine.bean.FootMode;
import com.example.shanyu.main.mine.bean.OffersMode;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersActivity extends BaseActivity implements OfferAdapter.OfferOnClick, MyRefreshLayout.RefreshListener {

    @BindView(R.id.mListView)
    public ListView mListView;

    @BindView(R.id.myRefreshLayout)
    public MyRefreshLayout myRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers, "优惠券");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        myRefreshLayout.setRefreshListener(this);
        showLoading();
        getOfferss();
    }

    private void getOfferss() {

        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("ty", "1");
        HttpUtil.doPost(HttpApi.OFFERS, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                ToastUtil.shortToast(errorMsg);
                myRefreshLayout.closeLoadingView();
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                myRefreshLayout.closeLoadingView();
                dismissLoading();
                List<OffersMode> actionModes = new Gson().fromJson(resultData, new TypeToken<List<OffersMode>>() {
                }.getType());
                mListView.setAdapter(new OfferAdapter(OffersActivity.this, actionModes, OffersActivity.this));
            }
        });

    }

    @Override
    public void onActionClick(int p) {

    }

    @Override
    public void onRefresh(MyRefreshLayout refreshLayout) {
        getOfferss();
    }

}
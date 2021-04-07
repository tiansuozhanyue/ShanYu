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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersActivity extends BaseActivity implements OfferAdapter.OfferOnClick {

    @BindView(R.id.mListView)
    public ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers, "优惠券");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {

        List<OffersMode> actionModes = new ArrayList<>();
        actionModes.add(new OffersMode());
        actionModes.add(new OffersMode());
        actionModes.add(new OffersMode());
        actionModes.add(new OffersMode());
        mListView.setAdapter(new OfferAdapter(OffersActivity.this, actionModes, OffersActivity.this));

    }

    private void getOfferss() {

        HttpUtil.doGet(HttpApi.OFFERS, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                LogUtil.i("===>" + errorMsg);
            }

            @Override
            public void onSuccess(String resultData) {
                List<OffersMode> actionModes = new Gson().fromJson(resultData, new TypeToken<List<OffersMode>>() {
                }.getType());

                mListView.setAdapter(new OfferAdapter(OffersActivity.this, actionModes, OffersActivity.this));
            }
        });

    }

    @Override
    public void onActionClick(int p) {

    }
}
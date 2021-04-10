package com.example.shanyu.main.mine.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.action.ActionAdapter;
import com.example.shanyu.main.action.ActionFragment;
import com.example.shanyu.main.action.ActionMode;
import com.example.shanyu.main.mine.adapter.FootAdapter;
import com.example.shanyu.main.mine.bean.FootMode;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.utils.SharedUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootActivity extends BaseActivity implements FootAdapter.FootOnClick {

    @BindView(R.id.mGridView)
    public GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot, "我的足迹");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        getFoots();
    }

    private void getFoots() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        showLoading();
        HttpUtil.doGet(HttpApi.FOOTS, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();
                List<FootMode> actionModes = new Gson().fromJson(resultData, new TypeToken<List<FootMode>>() {
                }.getType());

                mGridView.setAdapter(new FootAdapter(FootActivity.this, actionModes, FootActivity.this));
            }
        });

    }

    @Override
    public void onActionClick(int p) {

    }

}
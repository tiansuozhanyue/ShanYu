package com.example.shanyu.main.mine.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.db.HistoryDBHelper;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.action.ActionAdapter;
import com.example.shanyu.main.action.ActionFragment;
import com.example.shanyu.main.action.ActionMode;
import com.example.shanyu.main.home.ui.BookInfoActivity;
import com.example.shanyu.main.mine.adapter.FootAdapter;
import com.example.shanyu.main.mine.bean.FootMode;
import com.example.shanyu.main.mine.bean.HistoryBean;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootActivity extends BaseActivity implements FootAdapter.FootOnClick, MyRefreshLayout.RefreshListener {

    @BindView(R.id.mGridView)
    public GridView mGridView;

    @BindView(R.id.mMyRefreshLayout)
    public MyRefreshLayout mMyRefreshLayout;

    ArrayList<HistoryBean> beans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot, "我的足迹");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        mMyRefreshLayout.setRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFoots();
    }

    private void getFoots() {

        //从本地数据库获取数据
        HistoryDBHelper historyDBHelper = HistoryDBHelper.getInstance(this);
        historyDBHelper.openReadLink();
        beans = historyDBHelper.query(SharedUtil.getIntence().getUid());
        historyDBHelper.closeLink();

        //按时间排序
        Collections.sort(beans, (o1, o2) -> (int) (o2.getTime() - o1.getTime()));

        mMyRefreshLayout.closeLoadingView();

        mGridView.setAdapter(new FootAdapter(FootActivity.this, beans, FootActivity.this));

    }

    @Override
    public void onActionClick(int p) {
        Intent intent = new Intent(this, BookInfoActivity.class);
        intent.putExtra("bookModeId", beans.get(p).getBookid());
        startActivity(intent);
    }

    @Override
    public void onRefresh(MyRefreshLayout refreshLayout) {
        getFoots();
    }

}
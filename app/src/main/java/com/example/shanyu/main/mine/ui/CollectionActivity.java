package com.example.shanyu.main.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.ui.BookInfoActivity;
import com.example.shanyu.main.mine.adapter.CollectionAdapter;
import com.example.shanyu.main.mine.bean.CollectionBean;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.widget.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionActivity extends BaseActivity implements MyRefreshLayout.RefreshListener, AdapterView.OnItemClickListener {

    @BindView(R.id.myRefreshLayout)
    public MyRefreshLayout myRefreshLayout;
    @BindView(R.id.mListView)
    public ListView mListView;

    List<CollectionBean> collectionBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection, "我的收藏");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        myRefreshLayout.setRefreshListener(this);
        showLoading();
        getCollection();
    }

    /**
     * 获取收藏书籍列表
     */
    private void getCollection() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        HttpUtil.doGet(HttpApi.COLLECTIONS, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                myRefreshLayout.closeLoadingView();
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                myRefreshLayout.closeLoadingView();
                dismissLoading();
                collectionBeans = new Gson().fromJson(resultData, new TypeToken<List<CollectionBean>>() {
                }.getType());
                if (collectionBeans != null && collectionBeans.size() > 0) {
                    mListView.setAdapter(new CollectionAdapter(CollectionActivity.this, collectionBeans));
                    mListView.setOnItemClickListener(CollectionActivity.this);
                }
            }
        });

    }

    @Override
    public void onRefresh(MyRefreshLayout refreshLayout) {
        getCollection();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, BookInfoActivity.class);
        intent.putExtra("bookModeId", collectionBeans.get(position).getId().toString());
        startActivity(intent);
    }

}
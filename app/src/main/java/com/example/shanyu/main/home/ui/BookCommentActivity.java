package com.example.shanyu.main.home.ui;


import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.adapter.CommentAdapter;
import com.example.shanyu.main.home.bean.CommentBean;
import com.example.shanyu.widget.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookCommentActivity extends BaseActivity implements MyRefreshLayout.RefreshListener {

    String id;
    @BindView(R.id.myRefreshLayout)
    public MyRefreshLayout myRefreshLayout;
    @BindView(R.id.mListView)
    public ListView mListView;
    @BindView(R.id.comment_num)
    public TextView comment_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_comment, "商品评价");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        id = getIntent().getStringExtra("id");
        myRefreshLayout.setRefreshListener(this);
        showLoading();
        getComments();
    }

    /**
     * 获取评论列表
     */
    private void getComments() {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpUtil.doGet(HttpApi.EVALUATE, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                myRefreshLayout.closeLoadingView();
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {

                myRefreshLayout.closeLoadingView();
                dismissLoading();

                List<CommentBean> comments = new Gson().fromJson(resultData, new TypeToken<List<CommentBean>>() {
                }.getType());

                comment_num.setText("用户评价(" + comments.size() + ")");

                mListView.setAdapter(new CommentAdapter(BookCommentActivity.this, comments, false));

            }
        });

    }

    @Override
    public void onRefresh(MyRefreshLayout refreshLayout) {
        getComments();
    }

}
package com.example.shanyu.main.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.adapter.SearchBooksAdapter;
import com.example.shanyu.main.home.bean.BookMode;
import com.example.shanyu.widget.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopCategoryActivity extends BaseActivity implements SearchBooksAdapter.BookOnClick, MyRefreshLayout.RefreshListener {

    private String categoryId, shopId, type;
    private ListView mListView;
    private MyRefreshLayout myRefreshLayout;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_category, getIntent().getStringExtra("shopName") +
                "(" + getIntent().getStringExtra("categoryName") + ")");
        initView();
    }

    @Override
    public void initView() {
        categoryId = getIntent().getStringExtra("categoryId");
        shopId = getIntent().getStringExtra("shopId");
        type = getIntent().getStringExtra("type");
        myRefreshLayout = findViewById(R.id.myRefreshLayout);
        mListView = findViewById(R.id.mListView);
        empty = findViewById(R.id.empty);
        showLoading();
        searchBooks();
        myRefreshLayout.setRefreshListener(this);
    }

    private void searchBooks() {
        Map<String, String> map = new HashMap<>();
        map.put("category_id", categoryId);
        map.put("shop_id", shopId);
        map.put("type", type);
        HttpUtil.doGet(HttpApi.CATEGORYLIST, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                myRefreshLayout.closeLoadingView();
                dismissLoading();
                empty.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(String resultData) {
                myRefreshLayout.closeLoadingView();
                dismissLoading();
                empty.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
                List<BookMode> bookModes = new Gson().fromJson(resultData, new TypeToken<List<BookMode>>() {
                }.getType());

                mListView.setAdapter(new SearchBooksAdapter(ShopCategoryActivity.this, bookModes, ShopCategoryActivity.this));

            }
        });

    }


    @Override
    public void onBookClick(BookMode mode) {
        Intent intent = new Intent(this, BookInfoActivity.class);
        intent.putExtra("bookModeId", mode.getId().toString());
        startActivity(intent);
    }

    @Override
    public void onRefresh(MyRefreshLayout refreshLayout) {
        searchBooks();
    }

}
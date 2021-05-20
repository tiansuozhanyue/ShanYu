package com.example.shanyu.main.home.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.adapter.BooksAdapter;
import com.example.shanyu.main.home.adapter.SearchBooksAdapter;
import com.example.shanyu.main.home.bean.BookMode;
import com.example.shanyu.utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopSearchActivity extends BaseActivity implements TextView.OnEditorActionListener, SearchBooksAdapter.BookOnClick {

    String searchInfo = "";
    String shop_id;
    @BindView(R.id.edit_input)
    public EditText edit_input;
    @BindView(R.id.mListView)
    public ListView mListView;
    @BindView(R.id.ratingbar)
    public RatingBar ratingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmersiveStatusBar(true);
        setContentView(R.layout.activity_shop_search);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        shop_id = getIntent().getStringExtra("shop_id");
        edit_input.setOnEditorActionListener(this);
        getBooks(1);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
            searchInfo = edit_input.getText().toString();
            if (!StringUtil.isEmpty(searchInfo))
                searchBooks();
            return true;
        }
        return false;
    }


    @OnClick({R.id.goback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goback:
                finish();
                break;
        }
    }

    /**
     * 搜索商品
     */
    private void searchBooks() {

        Map<String, String> map = new HashMap<>();
        map.put("title", searchInfo);
        map.put("shop_id", shop_id);
        showLoading();
        HttpUtil.doGet(HttpApi.SEARCH, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
                mListView.setAdapter(null);
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();
                List<BookMode> bookModes = new Gson().fromJson(resultData, new TypeToken<List<BookMode>>() {
                }.getType());
                mListView.setAdapter(new SearchBooksAdapter(ShopSearchActivity.this, bookModes, ShopSearchActivity.this));
            }
        });

    }

    /**
     * 商铺的商铺列表
     */
    private void getBooks(int ty) {

        Map<String, String> map = new HashMap<>();
        map.put("ty", ty + "");
        map.put("shop_id", shop_id);
        showLoading();
        HttpUtil.doGet(HttpApi.SHOPBOOKLIST, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
                mListView.setAdapter(null);
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();

                List<BookMode> bookModes = new Gson().fromJson(resultData, new TypeToken<List<BookMode>>() {
                }.getType());

                if (bookModes != null && bookModes.size() > 0) {
                    double n = Double.valueOf(bookModes.get(0).getDecimal());
                    ratingbar.setRating((int) Math.round(n));
                    mListView.setAdapter(new SearchBooksAdapter(ShopSearchActivity.this, bookModes, ShopSearchActivity.this));
                } else {
                    ratingbar.setRating(0);
                }

            }
        });

    }


    @Override
    public void onBookClick(BookMode mode) {
        Intent intent = new Intent(this, BookInfoActivity.class);
        intent.putExtra("bookModeId", mode.getId().toString());
        startActivity(intent);
    }

}
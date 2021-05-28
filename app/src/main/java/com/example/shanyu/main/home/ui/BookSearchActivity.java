package com.example.shanyu.main.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
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

public class BookSearchActivity extends BaseActivity implements SearchBooksAdapter.BookOnClick, TextView.OnEditorActionListener {

    String searchInfo;

    @BindView(R.id.mListView)
    public ListView mListView;
    @BindView(R.id.edit_input)
    public EditText edit_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmersiveStatusBar(true);
        setContentView(R.layout.activity_book_search);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        searchInfo = getIntent().getStringExtra("searchInfo");
        edit_input.setText(searchInfo);
        edit_input.setOnEditorActionListener(this);
        searchBooks();
    }

    @OnClick({R.id.cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancle:
                edit_input.setText("");
                hideKeyboard(edit_input);
                break;
        }
    }


    /**
     * 搜索商品
     */
    private void searchBooks() {

        Map<String, String> map = new HashMap<>();
        map.put("title", searchInfo);

        HttpUtil.doGet(HttpApi.SEARCH, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                mListView.setAdapter(null);
            }

            @Override
            public void onSuccess(String resultData) {

                List<BookMode> bookModes = new Gson().fromJson(resultData, new TypeToken<List<BookMode>>() {
                }.getType());

                mListView.setAdapter(new SearchBooksAdapter(BookSearchActivity.this, bookModes, BookSearchActivity.this));

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
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
            searchInfo = edit_input.getText().toString();
            if (!StringUtil.isEmpty(searchInfo))
                searchBooks();
            return true;
        }
        return false;
    }

}
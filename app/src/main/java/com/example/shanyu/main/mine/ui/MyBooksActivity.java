package com.example.shanyu.main.mine.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.mine.adapter.MyBooksAdapter;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.CirButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyBooksActivity extends BaseActivity {

    @BindView(R.id.mListView)
    public ListView mListView;
    @BindView(R.id.allCheckBox)
    public CheckBox allCheckBox;
    @BindView(R.id.mCirButton)
    public CirButton mCirButton;

    boolean isAllSelected;
    MyBooksAdapter myBooksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books, "我的书包", "管理", v -> {
//            if (mAddressAdapter != null) {
//                isEditStyle = !isEditStyle;
//                rightView.setText(isEditStyle ? "完成" : "管理");
//                mAddressAdapter.exchangeStyle(isEditStyle);
//                add_address.setVisibility(isEditStyle ? View.GONE : View.VISIBLE);
//            }
        });
        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.allCheckBox, R.id.allCheckBox_text})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.allCheckBox:
            case R.id.allCheckBox_text:
                isAllSelected = !isAllSelected;
                allCheckBox.setChecked(isAllSelected);
                myBooksAdapter.setAllSelected(isAllSelected);
                break;
        }

    }

    @Override
    public void initView() {
        mCirButton.setSelected(true);
        getBookss();
    }

    /**
     * 获取购物车列表
     */
    private void getBookss() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        showLoading();
        HttpUtil.doGet(HttpApi.CARTLIST, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                ToastUtil.shortToast(errorMsg);
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();

                try {

                    List<MyBooksMode> actionModes = new Gson().fromJson(resultData, new TypeToken<List<MyBooksMode>>() {
                    }.getType());

                    myBooksAdapter = new MyBooksAdapter(MyBooksActivity.this, actionModes);
                    mListView.setAdapter(myBooksAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

}
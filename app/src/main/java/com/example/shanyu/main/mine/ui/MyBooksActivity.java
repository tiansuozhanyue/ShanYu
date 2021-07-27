package com.example.shanyu.main.mine.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.ui.BookInfoActivity;
import com.example.shanyu.main.home.ui.BookOrderActivity;
import com.example.shanyu.main.mine.adapter.MyBooksAdapter;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.utils.AppUtil;
import com.example.shanyu.utils.ArithUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.CirButton;
import com.example.shanyu.widget.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyBooksActivity extends BaseActivity implements MyRefreshLayout.RefreshListener, MyBooksAdapter.DataListener {

    @BindView(R.id.mListView)
    public ListView mListView;
    @BindView(R.id.allCheckBox)
    public CheckBox allCheckBox;
    @BindView(R.id.mCirButton)
    public CirButton mCirButton;
    @BindView(R.id.pay_layout)
    public LinearLayout pay_layout;
    @BindView(R.id.delet_bth)
    public TextView delet_bth;
    @BindView(R.id.myRefreshLayout)
    public MyRefreshLayout myRefreshLayout;
    @BindView(R.id.all_money)
    public TextView all_money;
    @BindView(R.id.all_money_small)
    public TextView all_money_small;
    @BindView(R.id.empty)
    public TextView empty;

    boolean isEdit;
    MyBooksAdapter myBooksAdapter;
    List<MyBooksMode> actionModes;
    Set<Integer> allSelectIds = new HashSet<>();//所有id集合
    Set<Integer> selectIds = new HashSet<>();//选中id集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books, "我的书包", "管理", v -> {
            isEdit = !isEdit;
            rightView.setText(isEdit ? "完成" : "管理");
            pay_layout.setVisibility(isEdit ? View.GONE : View.VISIBLE);
            delet_bth.setVisibility(isEdit ? View.VISIBLE : View.GONE);

        });
        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.allCheckBox, R.id.allCheckBox_text,
            R.id.delet_bth, R.id.mCirButton})
    public void onClickView(View view) {

        switch (view.getId()) {

            case R.id.allCheckBox:
            case R.id.allCheckBox_text:
                selectAllorNot();
                break;

            case R.id.delet_bth:
                deletBookss();
                break;

            case R.id.mCirButton:
                if (mCirButton.isSelected()) {
                    List<MyBooksMode> listDTOS = new ArrayList<>();
                    for (MyBooksMode mode : actionModes) {
                        List<MyBooksMode.ListDTO> list = new ArrayList<>();
                        for (MyBooksMode.ListDTO listDTO : mode.getList()) {
                            if (selectIds.contains(listDTO.getId()))
                                list.add(listDTO);
                        }
                        if (list.size() > 0) {
                            MyBooksMode mode1 = new MyBooksMode(mode.getShopId(), mode.getName(), list);
                            listDTOS.add(mode1);
                        }
                    }
                    BookOrderActivity.start(MyBooksActivity.this, listDTOS);
                }
                break;
        }

    }

    @Override
    public void initView() {
        myRefreshLayout.setRefreshListener(this);
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
                dismissLoading();
                myRefreshLayout.closeLoadingView();
                empty.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();
                myRefreshLayout.closeLoadingView();
                try {

                    empty.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);

                    actionModes = new Gson().fromJson(resultData, new TypeToken<List<MyBooksMode>>() {
                    }.getType());

                    for (MyBooksMode myBooksMode : actionModes) {
                        for (MyBooksMode.ListDTO listDTO : myBooksMode.getList()) {
                            if (listDTO.getIsselected() == 1)
                                selectIds.add(listDTO.getId());
                            allSelectIds.add(listDTO.getId());
                        }
                    }

                    showSumInfo();

                    myBooksAdapter = new MyBooksAdapter(MyBooksActivity.this, actionModes, selectIds, MyBooksActivity.this);
                    mListView.setAdapter(myBooksAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * 上报选中
     */
    private void selectBookss(String id, String flag) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("isselected", flag);
        HttpUtil.doGet(HttpApi.CARTSELECT, map, null);
    }

    private void selectAllorNot() {
        if (allCheckBox.isChecked()) {
            selectIds.clear();
        } else {
            selectIds.addAll(allSelectIds);
        }
        showSumInfo();
        myBooksAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (actionModes == null)
            return;

        List<String> ids_delet = new ArrayList<>();
        List<String> ids_add = new ArrayList<>();

        for (MyBooksMode mode : actionModes) {
            for (MyBooksMode.ListDTO listDTO : mode.getList()) {
                if (listDTO.getIsselected() == 0 && selectIds.contains(listDTO.getId())) {
                    ids_add.add(listDTO.getId() + "");
                } else if (listDTO.getIsselected() == 1 & !selectIds.contains(listDTO.getId())) {
                    ids_delet.add(listDTO.getId() + "");
                }
            }
        }

        for (String id : ids_delet) {
            selectBookss(id, "0");
        }

        for (String id : ids_add) {
            selectBookss(id, "1");
        }

    }

    /**
     * 删除购物车列表
     */
    private void deletBookss() {

        if (selectIds.size() == 0)
            return;

        StringBuffer buffer = new StringBuffer();

        for (Integer id : selectIds) {
            buffer.append(id + ",");
        }

        Map<String, String> map = new HashMap<>();
        map.put("id", buffer.toString());
        showLoading();
        HttpUtil.doGet(HttpApi.CARTDELET, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
                ToastUtil.shortToast(errorMsg);
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();
                getBookss();

            }
        });

    }

    @Override
    public void onRefresh(MyRefreshLayout refreshLayout) {
        getBookss();
    }

    @Override
    public void selectExchange() {
        showSumInfo();
        myBooksAdapter.notifyDataSetChanged();
    }

    private void showSumInfo() {
        allCheckBox.setChecked(allSelectIds.size() == selectIds.size());
        mCirButton.setSelected(selectIds.size() > 0);
        String allMoney = "0.00";
        for (MyBooksMode mode : actionModes) {
            for (MyBooksMode.ListDTO listDTO : mode.getList()) {
                if (selectIds.contains(listDTO.getId())) {
                    allMoney = ArithUtil.add(allMoney, ArithUtil.mul(listDTO.getPreevent(), listDTO.getCount().toString()));
                }
            }
        }
        String[] money = allMoney.split("\\.");
        all_money.setText(money[0]);
        all_money_small.setText("." + money[1]);
    }

    @Override
    public void bookOnclick(String id) {
        Intent intent = new Intent(this, BookInfoActivity.class);
        intent.putExtra("bookModeId", id);
        startActivity(intent);
    }

}
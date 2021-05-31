package com.example.shanyu.main.mine.ui;


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
import java.util.List;
import java.util.Map;

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
    boolean isAllSelected;
    MyBooksAdapter myBooksAdapter;
    List<MyBooksMode> listDTOS;
    List<Integer> allSelectIds = new ArrayList<>();//所有id集合
    List<Integer> selectIds = new ArrayList<>();//原始选中id集合
    List<Integer> editSelectIds = new ArrayList<>();//编辑后的选中id集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books, "我的书包", "管理", v -> {
            rightView.setText(isEdit ? "完成" : "管理");
            pay_layout.setVisibility(isEdit ? View.GONE : View.VISIBLE);
            delet_bth.setVisibility(isEdit ? View.VISIBLE : View.GONE);
            isEdit = !isEdit;
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
                isAllSelected = !isAllSelected;
                allCheckBox.setChecked(isAllSelected);
                myBooksAdapter.setAllSelected(isAllSelected);
                break;

            case R.id.delet_bth:
                deletBookss();
                break;

            case R.id.mCirButton:
                if (mCirButton.isSelected())
                    BookOrderActivity.start(MyBooksActivity.this, listDTOS);
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

                    List<MyBooksMode> actionModes = new Gson().fromJson(resultData, new TypeToken<List<MyBooksMode>>() {
                    }.getType());

                    for (MyBooksMode myBooksMode : actionModes) {
                        for (MyBooksMode.ListDTO listDTO : myBooksMode.getList()) {
                            if (listDTO.getIsselected() == 1)
                                selectIds.add(listDTO.getId());
                            allSelectIds.add(listDTO.getId());
                        }
                    }

                    myBooksAdapter = new MyBooksAdapter(MyBooksActivity.this, actionModes, MyBooksActivity.this);
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

    @Override
    protected void onStop() {
        super.onStop();

        StringBuffer ids_delet = new StringBuffer();
        StringBuffer ids_add = new StringBuffer();
        for (int id : allSelectIds) {

            switch (getFlag(id)) {
                case 1: //取消选中
                    ids_delet.append(id + ",");
                    break;
                case 2://选中
                    ids_add.append(id + ",");
                    break;
                case 0: //不变
                case 3:////不变
                    break;
            }

        }

        if (!StringUtil.isEmpty(ids_delet.toString()))
            selectBookss(ids_delet.substring(0, ids_delet.length() - 1), "0");

        if (!StringUtil.isEmpty(ids_add.toString()))
            selectBookss(ids_add.substring(0, ids_add.length() - 1), "1");

    }

    private int getFlag(int id) {
        int flag = 0;

        for (int i : selectIds) {
            if (i == id)
                flag += 1;

        }

        for (int i : editSelectIds) {
            if (i == id)
                flag += 2;

        }

        return flag;
    }

    /**
     * 删除购物车列表
     */
    private void deletBookss() {

        if (listDTOS == null)
            return;

        List<Integer> ids = new ArrayList<>();
        for (MyBooksMode mode : listDTOS) {
            for (MyBooksMode.ListDTO listDTO : mode.getList()) {
                ids.add(listDTO.getId());
            }
        }

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            buffer.append(i == ids.size() - 1 ? ids.get(i) : ids.get(i) + ",");
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

                listDTOS = null;
                getBookss();

            }
        });

    }

    @Override
    public void onRefresh(MyRefreshLayout refreshLayout) {
        getBookss();
    }

    @Override
    public void selectExchange(List<MyBooksMode> listDTOS) {

        this.listDTOS = listDTOS;
        editSelectIds.clear();

        mCirButton.setSelected(listDTOS != null && listDTOS.size() > 0);

        if (!isEdit) {
            String allMoney = "0.00";
            for (MyBooksMode mode : listDTOS) {
                for (MyBooksMode.ListDTO listDTO : mode.getList()) {
                    allMoney = ArithUtil.add(allMoney, ArithUtil.mul(listDTO.getPreevent(), listDTO.getCount().toString()));
                    editSelectIds.add(listDTO.getId());
                }
            }
            String[] money = allMoney.split("\\.");
            all_money.setText(money[0]);
            all_money_small.setText("." + money[1]);
        }
    }

}
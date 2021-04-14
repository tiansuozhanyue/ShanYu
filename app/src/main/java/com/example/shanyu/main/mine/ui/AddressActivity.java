package com.example.shanyu.main.mine.ui;


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
import com.example.shanyu.main.mine.adapter.AddressAdapter;
import com.example.shanyu.main.mine.bean.AddressMode;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends BaseActivity implements MyRefreshLayout.RefreshListener, AddressAdapter.AddressOnClick {

    AddressAdapter mAddressAdapter;
    private boolean isEditStyle;

    @BindView(R.id.myRefreshLayout)
    public MyRefreshLayout myRefreshLayout;

    @BindView(R.id.mListView)
    public ListView mListView;

    @BindView(R.id.add_address)
    public TextView add_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address, "收货地址", "管理", v -> {
            if (mAddressAdapter != null) {
                isEditStyle = !isEditStyle;
                rightView.setText(isEditStyle ? "完成" : "管理");
                mAddressAdapter.exchangeStyle(isEditStyle);
                add_address.setVisibility(isEditStyle ? View.GONE : View.VISIBLE);
            }
        });
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        showLoading();
        getAddress();
        myRefreshLayout.setRefreshListener(this);
    }

    @OnClick({R.id.add_address,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_address:
                startActivityForResult(new Intent(this, SetAddressActivity.class), 10);
                break;
        }
    }

    /**
     * 获取地址列表
     */
    private void getAddress() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        showLoading();
        HttpUtil.doGet(HttpApi.ADDRESS, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
                myRefreshLayout.closeLoadingView();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();
                myRefreshLayout.closeLoadingView();
                List<AddressMode> addressModes = new Gson().fromJson(resultData, new TypeToken<List<AddressMode>>() {
                }.getType());

                mAddressAdapter = new AddressAdapter(AddressActivity.this, addressModes, AddressActivity.this);
                mListView.setAdapter(mAddressAdapter);

            }
        });

    }

    /**
     * 删除地址
     */
    private void dellAddress(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        showLoading();
        HttpUtil.doGet(HttpApi.DELL, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();
                ToastUtil.shortToast("删除成功");
                isEditStyle = false;
                rightView.setText("管理");
                add_address.setVisibility(View.VISIBLE);
                getAddress();
            }
        });

    }

    /**
     * 设置/取消默认地址
     */
    private void seletedAddress(boolean flag, String id) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("id", id);
        map.put("isselected", flag ? "1" : "0");
        showLoading();
        HttpUtil.doGet(HttpApi.SELECTED, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();
                ToastUtil.shortToast("设置成功");
                isEditStyle = false;
                rightView.setText("管理");
                add_address.setVisibility(View.VISIBLE);
                getAddress();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            getAddress();
        }
    }

    @Override
    public void onRefresh(MyRefreshLayout refreshLayout) {
        getAddress();
    }

    @Override
    public void onAddressSet(boolean flag, String id) {
        seletedAddress(flag, id);
    }

    @Override
    public void onAddressEdit(AddressMode mode) {
        Intent intent = new Intent(AddressActivity.this, SetAddressActivity.class);
        intent.putExtra("mode", mode);
        startActivityForResult(intent, 11);
    }

    @Override
    public void onAddressSelet(AddressMode mode) {
        if (getIntent().getBooleanExtra("SeletAddress", false)) {
            Intent intent = new Intent();
            intent.putExtra("AddressMode", mode);
            setResult(101, intent);
            finish();
        }
    }

    @Override
    public void onAddressDell(String id) {
        dellAddress(id);
    }


}
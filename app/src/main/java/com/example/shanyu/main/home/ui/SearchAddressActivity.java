package com.example.shanyu.main.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.main.mine.ui.SetAddressActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAddressActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.address)
    public TextView address;
    @BindView(R.id.search)
    public EditText search;
    @BindView(R.id.area)
    public TextView area;
    @BindView(R.id.getLocation)
    public TextView getLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address, "选择收货地址", "新增地址", this);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {
        startActivityForResult(new Intent(this, SetAddressActivity.class), 10);
    }

}
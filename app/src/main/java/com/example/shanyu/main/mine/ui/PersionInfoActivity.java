package com.example.shanyu.main.mine.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;

import butterknife.ButterKnife;

public class PersionInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_info, "个人信息");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {

    }
}
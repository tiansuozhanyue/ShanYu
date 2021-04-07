package com.example.shanyu.main.mine.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;

import butterknife.ButterKnife;

public class AdviceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice, "意见反馈");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {

    }
}
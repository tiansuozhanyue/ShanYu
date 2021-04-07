package com.example.shanyu.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_login, false);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.btn_login,
            R.id.regist_goto,
            R.id.bth_setPws})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.regist_goto:
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.bth_setPws:
                startActivity(new Intent(this, PwsEditActivity.class));
                break;
        }
    }

}
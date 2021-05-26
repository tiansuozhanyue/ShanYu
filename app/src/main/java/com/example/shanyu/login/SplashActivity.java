package com.example.shanyu.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.main.MainActivity;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.StringUtil;

public class SplashActivity extends BaseLoginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setImmersiveStatusBar(true);
        setContentView(R.layout.activity_splash);
        initView();
    }

    @Override
    public void initView() {
        new Handler().postDelayed(() -> {
            if (StringUtil.isEmpty(SharedUtil.getIntence().getUid())) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            } else {
                goLogin(SharedUtil.getIntence().getAccount(), SharedUtil.getIntence().getUid());
                finish();
            }
        }, 500);

    }
}
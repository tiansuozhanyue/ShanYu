package com.example.shanyu.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_regist, false);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bth_regist,
            R.id.login_goto})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bth_regist:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.login_goto:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

}
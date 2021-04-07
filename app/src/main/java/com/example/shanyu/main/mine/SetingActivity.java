package com.example.shanyu.main.mine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.login.LoginActivity;
import com.example.shanyu.login.PwsEditActivity;
import com.example.shanyu.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting, "设置");
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.reset_pws})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reset_pws:
                Intent intent = new Intent(this, PwsEditActivity.class);
                intent.putExtra("isRestPws", true);
                startActivity(intent);
                break;
        }
    }



}
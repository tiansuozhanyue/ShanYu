package com.example.shanyu.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;

public class PwsEditActivity extends BaseActivity {

    boolean isRestPws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isRestPws = getIntent().getBooleanExtra("isRestPws", false);

        setContentView(R.layout.activity_pws_edit, getResources().getString(isRestPws ? R.string.activity_pws_edit2 : R.string.activity_pws_edit1));
    }

    @Override
    public void initView() {

    }

}
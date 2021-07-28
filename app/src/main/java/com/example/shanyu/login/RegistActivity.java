package com.example.shanyu.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.MainActivity;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.CirButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity {

    @BindView(R.id.regist_phone)
    public EditText regist_phone;
    @BindView(R.id.regist_pws)
    public EditText regist_pws;
    @BindView(R.id.regist_code)
    public EditText regist_code;
    @BindView(R.id.regist_school)
    public EditText regist_school;
    @BindView(R.id.regist_getCode)
    public TextView regist_getCode;
    @BindView(R.id.bth_regist)
    public CirButton bth_regist;

    String phone, pws, code, school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_regist, false);
        ButterKnife.bind(this);
        initView();
    }

    int timeCode;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {

            switch (msg.what) {
                case 0:
                    if (timeCode > -1) {
                        regist_getCode.setText("重新发送(" + timeCode + "s)");
                        timeCode--;
                        sendEmptyMessageDelayed(0, 1000);
                    } else {
                        regist_getCode.setText("获取验证码");
                        regist_getCode.setSelected(true);
                    }
                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    public void initView() {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                phone = regist_phone.getText().toString();
                pws = regist_pws.getText().toString();
                code = regist_code.getText().toString();
                school = regist_school.getText().toString();

                if (StringUtil.isPhoneNumber(phone)) {
                    int t = SharedUtil.getIntence().getCodeTime(false);
                    if (t < 60) {
                        timeCode = t;
                        regist_getCode.setSelected(false);
                        mHandler.sendEmptyMessage(0);
                    } else {
                        regist_getCode.setSelected(true);
                    }

                    if (StringUtil.isEmpty(pws) || !StringUtil.isVerifyCode(code) || StringUtil.isEmpty(school)) {
                        bth_regist.setSelected(false);
                    } else {
                        bth_regist.setSelected(true);
                    }

                } else {
                    bth_regist.setSelected(false);
                    regist_getCode.setSelected(false);
                }

            }
        };

        regist_phone.addTextChangedListener(textWatcher);
        regist_pws.addTextChangedListener(textWatcher);
        regist_code.addTextChangedListener(textWatcher);
        regist_school.addTextChangedListener(textWatcher);

    }

    @OnClick({R.id.bth_regist, R.id.regist_getCode,
            R.id.login_goto, R.id.see_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.bth_regist:
                if (bth_regist.isSelected())
                    regist();
                break;

            case R.id.login_goto:
                finish();
                break;

            case R.id.regist_getCode:
                if (regist_getCode.isSelected())
                    getCode();
                break;
            case R.id.see_info:
                startActivity(new Intent(this, WebViewActivity.class));
                break;

        }
    }

    /**
     * 注册
     */
    private void regist() {

        if (!StringUtil.isPhoneNumber(phone)) {
            ToastUtil.shortToast(getResources().getString(R.string.hint_phone_set));
            return;
        }

        if (StringUtil.isEmpty(pws)) {
            ToastUtil.shortToast(getResources().getString(R.string.hint_pws_set));
            return;
        }

        if (StringUtil.isEmpty(code)) {
            ToastUtil.shortToast(getResources().getString(R.string.hint_code_set));
            return;
        }

        if (StringUtil.isEmpty(school)) {
            ToastUtil.shortToast(getResources().getString(R.string.hint_school_set));
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("school", school);
        map.put("password", pws);
        map.put("mobile", phone);
        map.put("pcaptcha", code);
        showLoading();
        HttpUtil.doPost(HttpApi.REGIST, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                ToastUtil.shortToast(errorMsg);
                dismissLoading();
            }

            @Override
            public void onSuccess(String t) {
                dismissLoading();
                finish();
            }
        });


    }


    /**
     * 获取验证码
     */
    private void getCode() {

        if (StringUtil.isEmpty(phone) || phone.length() != 11) {
            ToastUtil.shortToast(getResources().getString(R.string.hint_phone_set));
            return;
        }


        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);

        HttpUtil.doPost(HttpApi.SEND, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onSuccess(String t) {
                timeCode = SharedUtil.getIntence().getCodeTime(true);
                regist_getCode.setSelected(false);
                mHandler.sendEmptyMessage(0);
            }
        });
    }

}
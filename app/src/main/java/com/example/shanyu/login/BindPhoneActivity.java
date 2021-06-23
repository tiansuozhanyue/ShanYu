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
import android.widget.CheckBox;
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

public class BindPhoneActivity extends BaseLoginActivity implements TextWatcher {

    @BindView(R.id.phone_input)
    public EditText phone_input;
    @BindView(R.id.code_input)
    public EditText code_input;
    @BindView(R.id.getCode)
    public TextView getCode;
    @BindView(R.id.mCirButton)
    public CirButton mCirButton;
    String mobile, pcaptcha;
    int timeCode;
    MHandler mHandler = new MHandler();

    class MHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    if (timeCode > -1) {
                        getCode.setText("重新发送(" + timeCode + "s)");
                        timeCode--;
                        sendEmptyMessageDelayed(0, 1000);
                    } else {
                        getCode.setText("获取验证码");
                        getCode.setSelected(false);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone, "绑定手机号");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        phone_input.addTextChangedListener(this);
        code_input.addTextChangedListener(this);
    }

    @OnClick({R.id.getCode, R.id.mCirButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.getCode:
                if (!getCode.isSelected())
                    getCode();
                break;

            case R.id.mCirButton:
                if (mCirButton.isSelected())
                    bind();
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mobile = phone_input.getText().toString();
        pcaptcha = code_input.getText().toString();
        mCirButton.setSelected(StringUtil.isPhoneNumber(mobile) && StringUtil.isVerifyCode(pcaptcha));
    }

    /**
     * 登录
     */
    private void bind() {
        String unionid = getIntent().getStringExtra("unionid");
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("pcaptcha", pcaptcha);
        map.put("openid", getIntent().getStringExtra("openid"));
        map.put("nickname", getIntent().getStringExtra("nickname"));
        map.put("avatar", getIntent().getStringExtra("avatar"));
        map.put("unionid", unionid);

        showLoading();
        HttpUtil.doPost(HttpApi.VERIFICATION, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
                ToastUtil.shortToast(errorMsg);
            }

            @Override
            public void onSuccess(String t) {
                SharedUtil.getIntence().setUnionid(unionid);
                dismissLoading();
                finish();
                goLogin(mobile, t);

            }
        });
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        showLoading();
        HttpUtil.doPost(HttpApi.SEND, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String t) {
                dismissLoading();
                timeCode = SharedUtil.getIntence().getCodeTime(true);
                getCode.setSelected(true);
                mHandler.sendEmptyMessage(0);
            }
        });
    }

}
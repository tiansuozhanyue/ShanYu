package com.example.shanyu.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.CirButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PwsEditActivity extends BaseActivity {

    @BindView(R.id.reset_phone)
    public EditText reset_phone;
    @BindView(R.id.reset_code)
    public EditText reset_code;
    @BindView(R.id.reset_pws)
    public EditText reset_pws;
    @BindView(R.id.reset_pwsAgin)
    public EditText reset_pwsAgin;
    @BindView(R.id.reset_getCode)
    public TextView reset_getCode;
    @BindView(R.id.reset_commit)
    public CirButton reset_commit;

    private String phone, code, pws, pwsAgin;

    boolean isRestPws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isRestPws = getIntent().getBooleanExtra("isRestPws", false);
        setContentView(R.layout.activity_pws_edit, getResources().getString(isRestPws ? R.string.activity_pws_edit2 : R.string.activity_pws_edit1));
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
                        reset_getCode.setText("重新发送(" + timeCode + "s)");
                        timeCode--;
                        sendEmptyMessageDelayed(0, 1000);
                    } else {
                        reset_getCode.setText("获取验证码");
                        reset_getCode.setSelected(true);
                    }
                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    public void initView() {

        TextWatcher mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                phone = reset_phone.getText().toString();
                code = reset_code.getText().toString();
                pws = reset_pws.getText().toString();
                pwsAgin = reset_pwsAgin.getText().toString();

                if (StringUtil.isPhoneNumber(phone)) {
                    int t = SharedUtil.getIntence().getCodeTime(false);
                    if (t < 60) {
                        timeCode = t;
                        reset_getCode.setSelected(false);
                        mHandler.sendEmptyMessage(0);
                    } else {
                        reset_getCode.setSelected(true);
                    }

                    if (StringUtil.isVerifyCode(code) && !StringUtil.isEmpty(pws) && !StringUtil.isEmpty(pwsAgin) && pws.equals(pwsAgin)) {
                        reset_commit.setSelected(true);
                    } else {
                        reset_commit.setSelected(false);
                    }

                } else {
                    reset_getCode.setSelected(false);
                    reset_commit.setSelected(false);
                }


            }
        };

        reset_phone.addTextChangedListener(mTextWatcher);
        reset_code.addTextChangedListener(mTextWatcher);
        reset_pws.addTextChangedListener(mTextWatcher);
        reset_pwsAgin.addTextChangedListener(mTextWatcher);

    }

    @OnClick({R.id.reset_getCode,
            R.id.reset_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.reset_getCode:
                if (reset_getCode.isSelected())
                    getCode();
                break;

            case R.id.reset_commit:
                if (reset_commit.isSelected())
                    resetPws();
                break;

        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {

        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);
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
                reset_getCode.setSelected(false);
                mHandler.sendEmptyMessage(0);
            }
        });
    }


    /**
     * 修改密码
     */
    private void resetPws() {
        Map<String, String> map = new HashMap<>();
        map.put("password1", pws);
        map.put("password2", pwsAgin);
        map.put("mobile", phone);
        map.put("pcaptcha", code);
        showLoading();
        HttpUtil.doPost(HttpApi.RESET, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
                ToastUtil.shortToast(errorMsg);
            }

            @Override
            public void onSuccess(String t) {
                dismissLoading();
                finish();
            }
        });


    }

}
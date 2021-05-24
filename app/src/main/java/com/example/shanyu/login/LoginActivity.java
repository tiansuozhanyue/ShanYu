package com.example.shanyu.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.MainActivity;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.CirButton;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements EMCallBack {

    @BindView(R.id.edit_phone)
    public EditText edit_phone;
    @BindView(R.id.edit_pws)
    public EditText edit_pws;
    @BindView(R.id.btn_login)
    public CirButton btn_login;
    @BindView(R.id.check)
    public CheckBox check;

    String phone, pws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_login, false);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {

        if (!StringUtil.isEmpty(SharedUtil.getIntence().getAccount()))
            edit_phone.setText(SharedUtil.getIntence().getAccount());

        TextWatcher mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                phone = edit_phone.getText().toString();
                pws = edit_pws.getText().toString();

                if (StringUtil.isPhoneNumber(phone) && !StringUtil.isEmpty(pws)) {
                    btn_login.setSelected(true);
                } else {
                    btn_login.setSelected(false);
                }

            }
        };

        edit_phone.addTextChangedListener(mTextWatcher);
        edit_pws.addTextChangedListener(mTextWatcher);

    }


    @OnClick({R.id.btn_login, R.id.regist_goto,
            R.id.bth_setPws, R.id.login_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (btn_login.isSelected()) {
                    hideKeyboard(check);
                    if (check.isChecked()) {
                        Login();
                    } else {
                        ToastUtil.shortToastMid("请先同意用户协议");
                    }
                }

                break;

            case R.id.regist_goto:
                startActivity(new Intent(this, RegistActivity.class));
                break;

            case R.id.bth_setPws:
                startActivity(new Intent(this, PwsEditActivity.class));
                break;

            case R.id.login_wx:
                wake();
                break;
        }
    }


    /**
     * 登录
     */
    private void Login() {

        if (StringUtil.isEmpty(phone) || phone.length() != 11) {
            ToastUtil.shortToast(getResources().getString(R.string.hint_phone_set));
            return;
        }

        if (StringUtil.isEmpty(pws)) {
            ToastUtil.shortToast(getResources().getString(R.string.hint_pws));
            return;
        }


        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("password", pws);

        showLoading();
        HttpUtil.doPost(HttpApi.LOGIN, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
                ToastUtil.shortToast(errorMsg);
            }

            @Override
            public void onSuccess(String t) {
                dismissLoading();

                //保存数据
                SharedUtil.getIntence().setAccount(phone);
                SharedUtil.getIntence().setUid(t);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                new EMThread().start();

                finish();
            }
        });
    }


    //发送请求唤起收起授权页
    public void wake() {
        if (isWeixinAvilible(this)) {
            ToastUtil.longToastMid("您还未安装微信客户端！");
        } else {
            // send oauth request
            IWXAPI wx_api = WXAPIFactory.createWXAPI(this, HttpApi.WxPayAppId);
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            wx_api.sendReq(req);
        }
    }

    /**
     * 微信登录
     */
    private void Login_wx() {


        String nickName = SharedUtil.getIntence().getNickName();
        String avatar = SharedUtil.getIntence().getAvatar();
        if (StringUtil.isEmpty(nickName) || StringUtil.isEmpty(avatar)) {
            startActivity(new Intent(this, BindPhoneActivity.class));
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("openid", HttpApi.WxPayAppId);
            map.put("nickname", nickName);
            map.put("avatar", avatar);
            showLoading();
            HttpUtil.doPost(HttpApi.LOGIN_WX, map, new HttpResultInterface() {
                @Override
                public void onFailure(String errorMsg) {
                    dismissLoading();
                    ToastUtil.shortToast(errorMsg);
                }

                @Override
                public void onSuccess(String t) {
                    dismissLoading();

                    //保存数据
                    SharedUtil.getIntence().setAccount(phone);
                    SharedUtil.getIntence().setUid(t);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    new EMThread().start();

                    finish();

                }
            });
        }
    }

    class EMThread extends Thread {
        @Override
        public void run() {
            super.run();

            try {
                //登录注册
                EMClient.getInstance().createAccount(SharedUtil.getIntence().getUid(), SharedUtil.getIntence().getUid());//同步方法

                EMClient.getInstance().login(SharedUtil.getIntence().getUid(), SharedUtil.getIntence().getUid(), LoginActivity.this);
            } catch (HyphenateException e) {
                if (e.getErrorCode() == 203) {//用户已存在
                    //登录环信
                    EMClient.getInstance().login(SharedUtil.getIntence().getUid(), SharedUtil.getIntence().getUid(), LoginActivity.this);
                } else {
                    LogUtil.i("---->环信注册失败：" + e.getErrorCode());
                }
            }

        }
    }

    @Override
    public void onSuccess() {
        LogUtil.i("---->环信登录成功！");
    }

    @Override
    public void onError(int i, String s) {
        LogUtil.i("---->环信登录失败：" + s);
    }

    @Override
    public void onProgress(int i, String s) {

    }
}
package com.example.shanyu.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.EventBean;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.MainActivity;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.CirButton;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends BaseLoginActivity {

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
        EventBus.getDefault().register(this);
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
            R.id.bth_setPws, R.id.login_wx,
            R.id.see_info})
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

            case R.id.see_info:
                startActivity(new Intent(this, WebViewActivity.class));
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
                goLogin(phone, t);
                finish();
            }
        });
    }


    /**
     * 发送请求唤起收起授权页
     */
    public void wake() {
        if (!isWeixinAvilible(this)) {
            ToastUtil.longToastMid("您还未安装微信客户端！");
        } else {
            // send oauth request
            IWXAPI wx_api = WXAPIFactory.createWXAPI(this, HttpApi.WX_APPID);
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            wx_api.sendReq(req);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void studentEventBus(EventBean eventBean) {
        switch (eventBean.flag) {
            case EventBean.LOGIN_SUCESSS:
                Login_wx(eventBean.info);
                break;
        }

    }

    /**
     * 微信登录
     */
    private void Login_wx(String code) {

        final String nickName = SharedUtil.getIntence().getNickName();
        final String avatar = SharedUtil.getIntence().getAvatar();
        if (StringUtil.isEmpty(nickName) || StringUtil.isEmpty(avatar)) {
            getWXToken(code);
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("openid", code);
            map.put("nickname", nickName);
            map.put("avatar", avatar);
            showLoading();
            HttpUtil.doPost(HttpApi.LOGIN_WX, map, new HttpResultInterface() {
                @Override
                public void onFailure(String errorMsg) {
                    dismissLoading();
                    Intent intent = new Intent(LoginActivity.this, BindPhoneActivity.class);
                    intent.putExtra("openid", code);
                    intent.putExtra("nickname", nickName);
                    intent.putExtra("avatar", avatar);
                    startActivity(intent);
                }

                @Override
                public void onSuccess(String t) {
                    dismissLoading();
                    goLogin(phone, t);
                    finish();
                }
            });
        }
    }

    /**
     * 获取微信token
     */
    private void getWXToken(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", HttpApi.WX_APPID);
        map.put("secret", HttpApi.WX_SECRET);
        map.put("code", code);
        map.put("grant_type", "authorization_code");

        HttpUtil.Get(HttpApi.getWxInfo1, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String access_token = jsonObject.getString("access_token");
                    String openid = jsonObject.getString("openid");
                    getWXInfo(access_token, openid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取微信基本信息
     *
     * @param code
     */
    private void getWXInfo(String access_token, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("access_token", access_token);
        map.put("openid", code);

        HttpUtil.Get(HttpApi.getWxInfo2, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Intent intent = new Intent(LoginActivity.this, BindPhoneActivity.class);
                    intent.putExtra("openid", code);
                    intent.putExtra("nickname", jsonObject.getString("nickname"));
                    intent.putExtra("avatar", jsonObject.getString("headimgurl"));
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
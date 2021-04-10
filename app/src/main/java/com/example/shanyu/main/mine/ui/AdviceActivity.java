package com.example.shanyu.main.mine.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.mine.bean.UserMode;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.CirButton;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdviceActivity extends BaseActivity {

    String action;
    String info;

    @BindView(R.id.edit)
    public EditText edit;
    @BindView(R.id.commit)
    public CirButton commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        action = getIntent().getStringExtra("action");

        switch (action) {
            case "advice":
                setContentView(R.layout.activity_advice, "意见反馈");
                break;
            case "nickname":
                setContentView(R.layout.activity_advice, "设置昵称");
                break;
            case "autograph":
                setContentView(R.layout.activity_advice, "设置个性签名");
                break;
        }

        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {

        switch (action) {
            case "advice":
                edit.setHint("请填写你的意见");
                break;
            case "nickname":
                edit.setHint("请设置你的昵称");
                break;
            case "autograph":
                edit.setHint("请设置你的个性签名");
                break;
        }

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                info = s.toString();
                commit.setSelected(!StringUtil.isEmpty(info));
            }
        });

    }

    @OnClick({R.id.commit})
    public void onClickView(View view) {

        if (!commit.isSelected())
            return;

        switch (action) {
            case "advice":
                proposal();
                break;
            case "nickname":
                setNickname();
                break;
            case "autograph":
                seTautograph();
                break;
        }
    }

    /**
     * 意见反馈
     */
    private void proposal() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("content", info);
        showLoading();
        HttpUtil.doPost(HttpApi.PROPOSAL, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String t) {
                dismissLoading();
                ToastUtil.shortToast("提交成功");
                finish();
            }
        });
    }

    /**
     * 设置昵称
     */
    private void setNickname() {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", info);
        showLoading();
        HttpUtil.doPost(HttpApi.SET + "?uid=" + SharedUtil.getIntence().getUid(), map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String t) {
                dismissLoading();
                ToastUtil.shortToast("昵称设置成功");
                setResult(111, new Intent().putExtra("nickname", info));
                finish();
            }
        });
    }

    /**
     * 设置个性签名
     */
    private void seTautograph() {
        Map<String, String> map = new HashMap<>();
        map.put("autograph", info);
        showLoading();
        HttpUtil.doPost(HttpApi.SET + "?uid=" + SharedUtil.getIntence().getUid(), map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String t) {
                dismissLoading();
                ToastUtil.shortToast("个性签名设置成功");
                setResult(112, new Intent().putExtra("autograph", info));
                finish();
            }
        });
    }

}
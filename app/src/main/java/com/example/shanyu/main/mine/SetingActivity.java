package com.example.shanyu.main.mine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.login.LoginActivity;
import com.example.shanyu.login.PwsEditActivity;
import com.example.shanyu.main.MainActivity;
import com.example.shanyu.main.mine.adapter.OfferAdapter;
import com.example.shanyu.main.mine.bean.OffersMode;
import com.example.shanyu.main.mine.ui.AboutActivity;
import com.example.shanyu.main.mine.ui.OffersActivity;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.CirButton;
import com.example.shanyu.widget.MyCommonDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetingActivity extends BaseActivity {

    @BindView(R.id.btn_signout)
    public CirButton btn_signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting, "设置");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        btn_signout.setSelected(true);
    }

    @OnClick({R.id.reset_pws, R.id.btn_signout, R.id.mine_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.reset_pws:
                Intent intent = new Intent(this, PwsEditActivity.class);
                intent.putExtra("isRestPws", true);
                startActivity(intent);
                break;

            case R.id.btn_signout:
                showSignOutView();
                break;

            case R.id.mine_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;

        }
    }

    private void showSignOutView() {
        MyCommonDialog myCommonDialog = new MyCommonDialog(this);
        myCommonDialog.setDialogContent("确认退出？");
        myCommonDialog.setOnSetPositiveButton("确定", new MyCommonDialog.onSetPositiveButton() {
            @Override
            public void OnPositiveButton(MyCommonDialog dialog) {
                dialog.dismiss();
                signOut();
            }
        });
        myCommonDialog.setOnSetNegativeButton("取消", new MyCommonDialog.onSetNegativeButton() {
            @Override
            public void OnNegativeButton(MyCommonDialog dialog) {
                dialog.dismiss();
            }
        });
        myCommonDialog.show();
    }

    private void signOut() {
        showLoading();
        HttpUtil.doGet(HttpApi.SIGNOUT, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                ToastUtil.shortToast(errorMsg);
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();
                SharedUtil.getIntence().setUid("");
                setResult(102);
                new EMThread().start();
                finish();
            }
        });

    }

    class EMThread extends Thread {
        @Override
        public void run() {
            super.run();
            EMClient.getInstance().logout(true);
        }
    }


}
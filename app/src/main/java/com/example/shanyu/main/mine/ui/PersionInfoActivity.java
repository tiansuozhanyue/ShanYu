package com.example.shanyu.main.mine.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.base.GetPhotoCallBack;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.login.PwsEditActivity;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.utils.MPermissionUtils;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.RoundImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersionInfoActivity extends BaseActivity implements GetPhotoCallBack {

    @BindView(R.id.avatar)
    public RoundImageView avatar;
    @BindView(R.id.nickname)
    public TextView nickname;
    @BindView(R.id.autograph)
    public TextView autograph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_info, "个人信息");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        ImageLoaderUtil.loadImage(getIntent().getStringExtra("avatar"), avatar);
        nickname.setText(getIntent().getStringExtra("nickname"));
        autograph.setText(getIntent().getStringExtra("autograph"));
    }

    @OnClick({R.id.avatar, R.id.nickname, R.id.autograph})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                selectedAvatar();
                break;
            case R.id.nickname:
                Intent intent1 = new Intent(this, AdviceActivity.class);
                intent1.putExtra("action", "nickname");
                startActivityForResult(intent1, 11);
                break;
            case R.id.autograph:
                Intent intent2 = new Intent(this, AdviceActivity.class);
                intent2.putExtra("action", "autograph");
                startActivityForResult(intent2, 12);
                break;
        }
    }

    /**
     * 选择头像
     */
    private void selectedAvatar() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        MPermissionUtils.requestPermissionsResult(this, 1, permissions, new MPermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                selectPhoto("请选择图片", new int[]{100, 100, 1, 1}, PersionInfoActivity.this);
            }

            @Override
            public void onPermissionDenied() {
                ToastUtil.shortToast("获取权限失败");
            }
        });
    }

    /**
     * 设置头像url
     */
    private void setAvatar(String url) {
        Map<String, String> map = new HashMap<>();
        map.put("avatar", url);
        showLoading();
        HttpUtil.doPost(HttpApi.SET + "?uid=" + SharedUtil.getIntence().getUid(), map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String t) {
                dismissLoading();
                setResult(100);
                ImageLoaderUtil.loadImage(url, avatar);
            }
        });
    }

    /**
     * 上传头像照片
     */
    private void uploadAvatar(File file) {
        showLoading();
        HttpUtil.upload(file, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String t) {
                dismissLoading();
//                setAvatar("");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 111) {
            setResult(100);
            nickname.setText(data.getStringExtra("nickname"));
        } else if (requestCode == 12 && resultCode == 112) {
            setResult(100);
            autograph.setText(data.getStringExtra("autograph"));
        }
    }

    @Override
    public void selectPhotoCallback(Uri photoOutputUri, File file) {
        uploadAvatar(file);
    }
}
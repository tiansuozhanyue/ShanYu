package com.example.shanyu.main.mine.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.base.GetPhotoCallBack;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.ui.ShopJoinActivity1;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.utils.MPermissionUtils;
import com.example.shanyu.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SetCommentsActivity extends BaseActivity implements View.OnClickListener, GetPhotoCallBack {

    private RatingBar ratingbar1, ratingbar2, ratingbar3;
    private EditText edit;
    private GridView mGridView;
    private List<String> pictures;
    private PictureAdapter pictureAdapter;
    int REQUESTCODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_comments, "评论", "发布", this);
        initView();
    }

    @Override
    public void initView() {
        pictures = new ArrayList<>();
        pictures.add("getPicture");
        ratingbar1 = findViewById(R.id.ratingbar1);
        ratingbar2 = findViewById(R.id.ratingbar2);
        ratingbar3 = findViewById(R.id.ratingbar3);
        edit = findViewById(R.id.edit);
        mGridView = findViewById(R.id.mGridView);
        pictureAdapter = new PictureAdapter();
        mGridView.setAdapter(pictureAdapter);
    }

    @Override
    public void onClick(View v) {

        // TODO 发布

    }

    /**
     * 请选择图片
     */
    private void selectedPicture() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        MPermissionUtils.requestPermissionsResult(this, REQUESTCODE, permissions, new MPermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                selectPhoto("请选择图片", new int[]{100, 100, 1, 1}, SetCommentsActivity.this);
            }

            @Override
            public void onPermissionDenied() {
                ToastUtil.shortToast("获取权限失败");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUESTCODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectPhoto("请选择图片", new int[]{100, 100, 1, 1}, SetCommentsActivity.this);
            } else {
                MPermissionUtils.showTipsDialog(this);
            }
        }
    }

    @Override
    public void selectPhotoCallback(Uri photoOutputUri, File file) {
        uploadPicture(file);
    }

    /**
     * 上传图片
     */
    private void uploadPicture(File file) {
        showLoading();

        HttpUtil.upload(file, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String t) {
                dismissLoading();

                try {
                    JSONObject object = new JSONObject(t);
                    pictures.add(pictures.size() - 1, object.getString("img"));
                    pictureAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    class PictureAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return pictures.size();
        }

        @Override
        public Object getItem(int position) {
            return pictures.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(SetCommentsActivity.this).inflate(R.layout.adapter_picture_item, parent, false);
            ImageView picture = view.findViewById(R.id.picture);
            ImageView delete = view.findViewById(R.id.delete);

            String path = pictures.get(position);

            if ("getPicture".equals(path)) {
                delete.setVisibility(View.GONE);
                picture.setImageResource(R.mipmap.detail_photo);
                picture.setOnClickListener(v -> selectedPicture());
            } else {
                delete.setVisibility(View.VISIBLE);
                ImageLoaderUtil.loadImage(path, picture);
                delete.setOnClickListener(v -> {
                    pictures.remove(position);
                    pictureAdapter.notifyDataSetChanged();
                });
            }

            return view;
        }
    }

}
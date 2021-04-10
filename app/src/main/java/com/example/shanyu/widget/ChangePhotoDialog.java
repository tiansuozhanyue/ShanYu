package com.example.shanyu.widget;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shanyu.R;


/**
 * <pre>
 *     author: memoThree
 *     time  : 2018/1/12
 *     desc  :  选择照片的dialog
 * </pre>
 */

public class ChangePhotoDialog extends Dialog {

    private  TextView tvDialogTitle;
    private  TextView tvDialogPhoto;
    private  TextView tvDialogCamera;
    private  TextView tvDialogCancel;
    private  String  title;
    private  String  message1;
    private  String  message2;
    private  String  cancelMessage;
    private  boolean isShowCancel;



    public ChangePhotoDialog(@NonNull Context context) {
        super(context, R.style.ChangePhotoDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_head_image);
        findViews();
        initData();
        initEvent();

    }


    private void findViews() {
        tvDialogTitle =  findViewById(R.id.tv_dialog_title);
        tvDialogPhoto = findViewById( R.id.tv_dialog_photo );
        tvDialogCamera = findViewById( R.id.tv_dialog_camera );
        tvDialogCancel = findViewById( R.id.tv_dialog_cancel );


        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.alpha=1;
        // 获取屏幕宽高
        DisplayMetrics d = getContext().getResources().getDisplayMetrics();
        // 设置宽高
        lp.width = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(lp);

    }
    private void initData() {
        if(tvDialogTitle !=null){
            tvDialogTitle.setText(title);
        }
        if(tvDialogPhoto !=null){
            tvDialogPhoto.setText(message1);
        }
        if(tvDialogCamera !=null){
            tvDialogCamera.setText(message2);
        }
        if(tvDialogCancel !=null){
            if(isShowCancel){
                tvDialogCancel.setVisibility(View.VISIBLE);
                tvDialogCancel.setText(cancelMessage);
            }else{
                tvDialogCancel.setVisibility(View.GONE);
            }
        }

    }


    private void initEvent() {
        tvDialogPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvDialogPhotoClickListener!=null){
                    tvDialogPhotoClickListener.onPhotoClick();
                }
            }
        });

        tvDialogCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvDialogCameraClickListener !=null){
                    tvDialogCameraClickListener.onCameraClick();
                }
            }
        });

        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDialogCancelClickListener.onCancelClick();
            }
        });

    }


    public interface  tvDialogPhotoClickListener{
        void  onPhotoClick();
    }

    public  interface   tvDialogCameraClickListener{
        void  onCameraClick();
    }

    public  interface   tvDialogCancelClickListener{
        void  onCancelClick();
    }

    private  tvDialogPhotoClickListener  tvDialogPhotoClickListener;
    private  tvDialogCameraClickListener  tvDialogCameraClickListener;
    private  tvDialogCancelClickListener  tvDialogCancelClickListener;



    public void setTvDialogPhotoClickListener(String  photo, ChangePhotoDialog.tvDialogPhotoClickListener tvDialogPhotoClickListener) {
       if(message1!=null){
           message1 = photo;
       }
        this.tvDialogPhotoClickListener = tvDialogPhotoClickListener;
    }

    public void setTvDialogCameraClickListener(String  camera, ChangePhotoDialog.tvDialogCameraClickListener tvDialogCameraClickListener) {
       if(message2!=null){
           message2 = camera;
       }
        this.tvDialogCameraClickListener = tvDialogCameraClickListener;
    }



    public void setTvDialogCancelClickListener(ChangePhotoDialog.tvDialogCancelClickListener tvDialogCancelClickListener) {
        this.tvDialogCancelClickListener = tvDialogCancelClickListener;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public void setCancelMessage(String cancelMessage) {
        this.cancelMessage = cancelMessage;
    }

    public void setShowCancel(boolean showCancel) {
        isShowCancel = showCancel;
    }

    public boolean isShowCancel() {
        return isShowCancel;
    }
}

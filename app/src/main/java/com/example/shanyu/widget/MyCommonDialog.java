package com.example.shanyu.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shanyu.R;
import com.example.shanyu.utils.StringUtil;


/**
 * <pre>
 *     author: memoThree
 *     time  : 2018/5/26
 *     desc  :  自定义 dialog
 * </pre>
 */
public class MyCommonDialog extends Dialog {


    private TextView tvCommonDialogContent;
    private TextView tvCommonDialogCancel;
    private TextView tvCommonDialogConfirm;

    private String DialogContent; //外界设置标题
    private String DialogCancel; //外界设置取消
    private String DialogConfirm; //外界设置确定

    private onSetPositiveButton onSetPositiveButton;
    private onSetNegativeButton onSetNegativeButton;

    private boolean isShowCancel = true;  //是否展示取消按钮

    public MyCommonDialog(@NonNull Context context) {
        super(context, R.style.my_style_common_dialog);
    }

    public MyCommonDialog(@NonNull Context context, String content) {
        super(context, R.style.my_style_common_dialog);
        DialogContent = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_my);
        setCanceledOnTouchOutside(false);   //点击外部不让消失
        findViews();
        hideCancel(isShowCancel);
        initData();
        initEvent();
    }


    private void findViews() {
        tvCommonDialogContent = findViewById(R.id.tv_common_dialog_content);
        tvCommonDialogCancel = findViewById(R.id.tv_common_dialog_cancel);
        tvCommonDialogConfirm = findViewById(R.id.tv_common_dialog_confirm);


        //        String name = PreferencesUtil.getInstance().getBarName();
        //        if (!StringUtil.isEmpty(name))
        //            nameTitle.setText(name);

    }

    private void initData() {
        if (DialogContent != null) {
            tvCommonDialogContent.setText(DialogContent);
        }
        if (DialogCancel != null) {
            tvCommonDialogCancel.setText(DialogCancel);
        }
        if (DialogConfirm != null) {
            tvCommonDialogConfirm.setText(DialogConfirm);
        }

        if (isShowCancel) {
            tvCommonDialogCancel.setVisibility(View.VISIBLE);
        } else {
            tvCommonDialogCancel.setVisibility(View.GONE);
        }
    }

    public void hideCancel(boolean isHide) {
        isShowCancel = isHide;
    }


    //外界设置Message
    public void setDialogContent(String dialogContent) {
        if (StringUtil.isEmpty(dialogContent))
            return;
        DialogContent = dialogContent;
    }

    private void initEvent() {
        tvCommonDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSetPositiveButton != null) {
                    onSetPositiveButton.OnPositiveButton(MyCommonDialog.this);
                }
            }
        });

        tvCommonDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSetNegativeButton != null) {
                    onSetNegativeButton.OnNegativeButton(MyCommonDialog.this);
                }
            }
        });
    }


    public interface onSetPositiveButton {
        void OnPositiveButton(MyCommonDialog dialog);
    }

    public interface onSetNegativeButton {
        void OnNegativeButton(MyCommonDialog dialog);
    }

    public void setOnSetPositiveButton(String str, MyCommonDialog.onSetPositiveButton onSetPositiveButton) {
        if (str != null) {
            DialogConfirm = str;
        }
        this.onSetPositiveButton = onSetPositiveButton;
    }

    public void setOnSetNegativeButton(String str,MyCommonDialog.onSetNegativeButton onSetNegativeButton) {
        if (str != null) {
            DialogCancel = str;
        }
        this.onSetNegativeButton = onSetNegativeButton;
    }
}

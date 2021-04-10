package com.example.shanyu.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.shanyu.R;
import com.wang.avi.AVLoadingIndicatorView;


public class NetLoadingDialog {

    private static Dialog mLoadingDialog;

    /**
     * 显示加载对话框
     */
    public static void showProgressDialog(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        AVLoadingIndicatorView avLoadingIndicatorView = view.findViewById(R.id.AVLoadingIndicatorView);

        if (mLoadingDialog == null) {
            mLoadingDialog = new Dialog(context, R.style.loading_dialog_style);
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            avLoadingIndicatorView.showContextMenu();
            mLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        mLoadingDialog.dismiss();
                        return true;
                    }
                    return false;
                }
            });
        }

        if (!mLoadingDialog.isShowing())
            mLoadingDialog.show();

    }

    /**
     * 隐藏加载对话框
     */
    public static void hideProgressDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

}

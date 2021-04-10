package com.example.shanyu.base;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.utils.OsUtil;
import com.example.shanyu.widget.NetLoadingDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class BaseActivity extends BaseRootActivity {

    public TextView rightView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }


    protected void dismissLoading() {
        NetLoadingDialog.hideProgressDialog();
    }

    protected void showLoading() {
        NetLoadingDialog.showProgressDialog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }

    /**
     * 带标题栏布局1
     *
     * @param layoutResID
     * @param titile
     * @param titleRight
     * @param onClickListener
     */
    public void setContentView(@LayoutRes int layoutResID, String titile, String titleRight, View.OnClickListener onClickListener) {

        View root = LayoutInflater.from(this).inflate(R.layout.activity_base, null, false);
        View view = LayoutInflater.from(this).inflate(layoutResID, null, false);

        root.findViewById(R.id.base_back).setOnClickListener(v -> finish());

        ((TextView) root.findViewById(R.id.base_title)).setText(titile);

        if (titleRight != null) {
            rightView = root.findViewById(R.id.base_right_title);
            rightView.setText(titleRight);
            rightView.setOnClickListener(onClickListener);
        }

        FrameLayout frameLayout = root.findViewById(R.id.root_View);
        frameLayout.addView(view);

        //设置状态栏字体颜色
        setImmersiveStatusBar(true);
        super.setContentView(root);

    }

    /**
     * 带标题栏布局2
     *
     * @param layoutResID
     * @param titile
     */
    public void setContentView(@LayoutRes int layoutResID, String titile) {
        setContentView(layoutResID, titile, null, null);
    }

    public void setContentView(int view, boolean isblack) {
        //设置状态栏字体颜色
        setImmersiveStatusBar(isblack);
        super.setContentView(view);
    }

    //设置顶部状态栏的颜色
    protected void setImmersiveStatusBar(boolean isBlack) {
        try {
            if (isBlack) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || OsUtil.isMIUI() || OsUtil.isFlyme()) {
                    setStatusBarFontIconDark(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setTranslucentStatus() {
        //5.0 以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置Android状态栏的字体颜色
     *
     * @param dark 状态栏字体是否为深色
     */
    private void setStatusBarFontIconDark(boolean dark) {
        // 小米MIUI
        try {
            Window window = getWindow();
            Class clazz = getWindow().getClass();
            @SuppressLint("PrivateApi") Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {       //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (ClassNotFoundException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 魅族FlymeUI
        try {
            Window window = getWindow();
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);

        } catch (NoSuchFieldException e) {
            //            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // android6.0+系统
        // 这个设置和在xml的style文件中用这个<item name="android:windowLightStatusBar">true</item>属性是一样的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

}
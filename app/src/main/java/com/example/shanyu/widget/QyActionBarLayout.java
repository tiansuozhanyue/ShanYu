package com.example.shanyu.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.shanyu.utils.AppUtil;


/**
 * 作者： HeroCat
 * 时间：2019/7/17/017
 * 描述：
 */
@SuppressLint("AppCompatCustomView")
public class QyActionBarLayout extends View {
    private int height;
    private Context mContext;

    public QyActionBarLayout(Context context) {
        this(context, null);
    }

    public QyActionBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QyActionBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        height = AppUtil.getStatusBarHeight(context);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(AppUtil.getScreenWH(mContext)[0], height);
    }
}

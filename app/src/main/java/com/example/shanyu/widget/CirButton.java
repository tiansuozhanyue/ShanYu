package com.example.shanyu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.shanyu.R;


public class CirButton extends TextView {
    public CirButton(Context context) {
        super(context);
    }

    public CirButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        setBackgroundResource(R.drawable.bg_cir_bth);
        setTextColor(context.getResources().getColor(R.color.white));
        setGravity(Gravity.CENTER);
    }

}

package com.example.shanyu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shanyu.R;

public class ShopSumButton extends LinearLayout {

    int sum = 1;
    TextView mTextView;
    SumExchangeListener sumExchangeListener;

    public void setSumExchangeListener(SumExchangeListener sumExchangeListener) {
        this.sumExchangeListener = sumExchangeListener;
    }

    public ShopSumButton(Context context) {
        super(context);
    }

    public ShopSumButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShopSumButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context mContext) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.button_view, null, false);
        ImageView button1 = view.findViewById(R.id.button1);
        ImageView button2 = view.findViewById(R.id.button2);
        mTextView = view.findViewById(R.id.mTextView);

        button1.setOnClickListener(v -> {
            if (sum > 1) {
                sum--;
                mTextView.setText(sum + "");
                if (sumExchangeListener != null)
                    sumExchangeListener.sumExchange(sum);
            }
        });

        button2.setOnClickListener(v -> {
            sum++;
            mTextView.setText(sum + "");
            if (sumExchangeListener != null)
                sumExchangeListener.sumExchange(sum);
        });

        addView(view);

    }

    public int getSum() {
        return sum;
    }

    public void setSum(int n) {
        sum = n;
        mTextView.setText(sum + "");
    }

    public interface SumExchangeListener {
        void sumExchange(int sum);
    }

}

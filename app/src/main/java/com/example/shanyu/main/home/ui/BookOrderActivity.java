package com.example.shanyu.main.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.main.mine.ui.AddressActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookOrderActivity extends BaseActivity {

    @BindView(R.id.book_get1)
    public TextView book_get1;
    @BindView(R.id.book_get2)
    public TextView book_get2;
    @BindView(R.id.book_get1_index)
    public TextView book_get1_index;
    @BindView(R.id.book_get2_index)
    public TextView book_get2_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_order, "提交订单");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        book_get1.setSelected(true);
        book_get1_index.setSelected(true);
    }

    @OnClick({R.id.book_get1, R.id.book_get2})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.book_get1:
                book_get1.setSelected(true);
                book_get1_index.setSelected(true);
                book_get2.setSelected(false);
                book_get2_index.setSelected(false);
                break;

            case R.id.book_get2:
                book_get1.setSelected(false);
                book_get1_index.setSelected(false);
                book_get2.setSelected(true);
                book_get2_index.setSelected(true);
                break;

        }
    }

}
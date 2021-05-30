package com.example.shanyu.main.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.main.MainActivity;
import com.example.shanyu.main.mine.ui.OrderInfoActivity;
import com.example.shanyu.widget.CirButton;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaySucessActivity extends BaseActivity {

    @BindView(R.id.price1)
    public TextView price1;
    @BindView(R.id.price2)
    public TextView price2;
    @BindView(R.id.go_home)
    public CirButton go_home;
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_sucess, "支付成功");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        go_home.setSelected(true);
        orderId = getIntent().getStringExtra("orderId");
        String money = getIntent().getStringExtra("money");
        String[] moneys = money.split("\\.");
        price1.setText(moneys[0]);
        price2.setText("." + moneys[1]);
    }

    @OnClick({R.id.check_order, R.id.go_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.check_order:
                Intent intent = new Intent(this, OrderInfoActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
                break;

            case R.id.go_home:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }

}
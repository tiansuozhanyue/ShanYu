package com.example.shanyu.main.mine.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.bean.BannerMode;
import com.example.shanyu.main.mine.bean.OrderInfoBean;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.TimeUtil;
import com.example.shanyu.utils.ToastUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderInfoActivity extends BaseActivity {

    @BindView(R.id.name)
    public TextView name;
    @BindView(R.id.phone)
    public TextView phone;
    @BindView(R.id.address)
    public TextView address;
    @BindView(R.id.shopName)
    public TextView shopName;
    @BindView(R.id.cover)
    public ImageView cover;
    @BindView(R.id.statue)
    public TextView statue;
    @BindView(R.id.title)
    public TextView title;
    @BindView(R.id.price2)
    public TextView price2;
    @BindView(R.id.price1)
    public TextView price1;
    @BindView(R.id.count)
    public TextView count;
    @BindView(R.id.order_code)
    public TextView order_code;
    @BindView(R.id.order_time)
    public TextView order_time;
    @BindView(R.id.order_money)
    public TextView order_money;
    @BindView(R.id.order_offers)
    public TextView order_offers;
    @BindView(R.id.price3)
    public TextView price3;
    @BindView(R.id.price4)
    public TextView price4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info, "订单详情");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        getOrderDetail(getIntent().getStringExtra("orderId"));
    }

    /**
     * 获取订单详情
     *
     * @param order_id
     */
    private void getOrderDetail(String order_id) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("order_id", order_id);
        HttpUtil.doPost(HttpApi.SETORDERDETAIL, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                ToastUtil.shortToast(errorMsg);
            }

            @Override
            public void onSuccess(String resultData) {

                OrderInfoBean infoBean = new Gson().fromJson(resultData, OrderInfoBean.class);
                name.setText(infoBean.getNickname());
                phone.setText(infoBean.getMobile());
                address.setText(infoBean.getAddress());
                shopName.setText(infoBean.getName());
                title.setText(infoBean.getTitle());
                count.setText("X" + infoBean.getCount());
                order_code.setText(infoBean.getOrder());
                order_time.setText(TimeUtil.stampToDate2(infoBean.getCreated_at() + "000"));
                order_money.setText("￥" + infoBean.getSum());
                switch (infoBean.getStatus()) {
                    case 0:
                        statue.setText("待评价");
                        break;

                    case 1:
                        statue.setText("待发货");
                        break;

                    case 2:
                        statue.setText("已退款");
                        break;

                    case 3:
                        statue.setText("待收货");
                        break;

                    case 4:
                        statue.setText("已完成");
                        break;

                    case 5:
                        statue.setText("待付款");
                        break;

                    case 6:
                        statue.setText("已取消");
                        break;

                    case 7:
                        statue.setText("待自提");
                        break;
                }

                String[] p1 = infoBean.getPreevent().split("\\.");
                price1.setText(p1[0]);
                price2.setText("." + p1[1]);

                String[] p2 = infoBean.getPrincipal().split("\\.");
                price3.setText(p2[0]);
                price4.setText("." + p2[1]);

                ImageLoaderUtil.loadImage(HttpApi.HOST + infoBean.getCovers(), cover);
            }
        });
    }

}
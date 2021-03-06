package com.example.shanyu.main.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.ui.BookInfoActivity;
import com.example.shanyu.main.mine.bean.OrderInfoBean;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.utils.TimeUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.MyListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
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
    @BindView(R.id.statue)
    public TextView statue;
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
    @BindView(R.id.pay_type)
    public TextView pay_type;
    @BindView(R.id.price4)
    public TextView price4;
    @BindView(R.id.logistics)
    public TextView logistics;
    @BindView(R.id.mMyListView)
    public MyListView mMyListView;
    @BindView(R.id.layout0)
    public LinearLayout layout0;
    @BindView(R.id.logisticsNum)
    public TextView logisticsNum;
    @BindView(R.id.layout1)
    public LinearLayout layout1;
    @BindView(R.id.order_freight)
    public TextView order_freight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info, "????????????");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        getOrderDetail(getIntent().getStringExtra("orderId"));
    }

    /**
     * ??????????????????
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
                mMyListView.setAdapter(new MyAdapter(infoBean.getGoods_list()));
                order_code.setText(infoBean.getOrder());
                order_time.setText(TimeUtil.stampToDate2(infoBean.getCreated_at() + "000"));
                order_money.setText("???" + infoBean.getPrincipal());

                if (StringUtil.isEmpty(infoBean.getLoginame())) {
                    layout0.setVisibility(View.GONE);
                } else {
                    layout0.setVisibility(View.VISIBLE);
                    logistics.setText(infoBean.getLoginame());
                    logisticsNum.setText(infoBean.getNumbers());
                }

                if (StringUtil.isEmpty(infoBean.getFreight())) {
                    layout1.setVisibility(View.GONE);
                } else {
                    layout1.setVisibility(View.VISIBLE);
                    order_freight.setText("???" + infoBean.getFreight());
                }

                if ("0.00".equals(infoBean.getMoney())) {
                    order_offers.setText("??????????????????");
                } else {
                    order_offers.setText("-???" + infoBean.getMoney());

                }

                switch (infoBean.getStatus()) {
                    case 0:
                        statue.setText("?????????");
                        break;

                    case 1:
                        statue.setText("?????????");
                        break;

                    case 2:
                        statue.setText("?????????");
                        break;

                    case 3:
                        statue.setText("?????????");
                        break;

                    case 4:
                        statue.setText("?????????");
                        break;

                    case 5:
                        statue.setText("?????????");
                        break;

                    case 6:
                        statue.setText("?????????");
                        break;

                    case 7:
                        statue.setText("?????????");
                        break;
                }

                pay_type.setText(infoBean.getType() == 1 ? "?????????" : "??????");

                String[] p2 = infoBean.getSum().split("\\.");
                price3.setText(p2[0]);
                price4.setText("." + p2[1]);
            }
        });
    }


    class MyAdapter extends BaseAdapter {

        private List<OrderInfoBean.GoodsListBean> goods_list;

        public MyAdapter(List<OrderInfoBean.GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        @Override
        public int getCount() {
            return goods_list.size();
        }

        @Override
        public Object getItem(int position) {
            return goods_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(OrderInfoActivity.this).inflate(R.layout.adapter_info_item, parent, false);
            OrderInfoBean.GoodsListBean goodsBean = goods_list.get(position);
            ImageView cover = view.findViewById(R.id.cover);
            TextView title = view.findViewById(R.id.title);
            TextView count = view.findViewById(R.id.count);
            TextView price1 = view.findViewById(R.id.price1);
            TextView price2 = view.findViewById(R.id.price2);

            ImageLoaderUtil.loadImage(HttpApi.HOST + goodsBean.getCovers(), cover);
            title.setText(goodsBean.getTitle());
            count.setText("x" + goodsBean.getCount());
            String[] p1 = goodsBean.getPreevent().split("\\.");
            price1.setText(p1[0]);
            price2.setText("." + p1[1]);

            view.setOnClickListener(v -> {
                Intent intent = new Intent(OrderInfoActivity.this, BookInfoActivity.class);
                intent.putExtra("bookModeId", goodsBean.getGoods_id() + "");
                startActivity(intent);
            });

            return view;
        }
    }

}
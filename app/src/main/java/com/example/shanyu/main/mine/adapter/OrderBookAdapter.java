package com.example.shanyu.main.mine.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.main.mine.bean.AddressMode;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.main.mine.bean.OrderBookBean;
import com.example.shanyu.utils.AppUtil;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.widget.MyListView;

import java.util.List;

public class OrderBookAdapter extends BaseAdapter {

    private Context mContext;
    private List<OrderBookBean> booksModes;
    private OrderBookAdapterOnclick onClick;

    public OrderBookAdapter(Context mContext, List<OrderBookBean> booksModes, OrderBookAdapterOnclick onClick) {
        this.mContext = mContext;
        this.booksModes = booksModes;
        this.onClick = onClick;
    }

    @Override
    public int getCount() {
        return booksModes.size();
    }

    @Override
    public Object getItem(int position) {
        return booksModes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderBookBean booksMode = booksModes.get(position);
        View view = LayoutInflater.from(mContext).inflate(R.layout.adpater_bookorder_item, parent, false);
        ((TextView) view.findViewById(R.id.shopName)).setText(booksMode.getName());
        MyListView myListView = view.findViewById(R.id.mMyListView);
        List<OrderBookBean.GoodsListBean> goods = booksMode.getGoods_list();
        myListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return goods.size();
            }

            @Override
            public Object getItem(int position) {
                return goods.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.adpater_bookorder_item_2, parent, false);
                OrderBookBean.GoodsListBean goodsListBean = goods.get(position);
                ImageLoaderUtil.loadImage(HttpApi.HOST + goodsListBean.getCovers(), view.findViewById(R.id.cover));
                ((TextView) view.findViewById(R.id.title)).setText(goodsListBean.getTitle());
                ((TextView) view.findViewById(R.id.price)).setText("￥" + goodsListBean.getPreevent());
                ((TextView) view.findViewById(R.id.count)).setText(goodsListBean.getCount() + "件");
                view.setOnClickListener(v -> onClick.onIemClick(position));
                return view;
            }
        });

        String[] p = booksMode.getPrincipal().split("\\.");
        LinearLayout layout = view.findViewById(R.id.layout);
        ((TextView) view.findViewById(R.id.price2)).setText(p[0]);
        ((TextView) view.findViewById(R.id.price1)).setText("." + p[1]);
        TextView statue = view.findViewById(R.id.statue);
        view.setOnClickListener(v -> onClick.onIemClick(position));
        switch (booksMode.getStatus()) {
            case 0:
                statue.setText("待评价");
                View view1 = getGrayView("评价");
                view1.setOnClickListener(v -> onClick.onAppraise(position));
                layout.addView(view1);
                break;

            case 1:
                statue.setText("待发货");
                View view2 = getGrayView("取消订单");
                view2.setOnClickListener(v -> onClick.onCanaleOrder(position));
                layout.addView(view2);
                break;

            case 2:
                statue.setText("已退款");
                break;

            case 3:
                statue.setText("待收货");
                View view3 = getGrayView("查看物流");
                View view4 = getGrayView("取消订单");
                View view5 = getGrayView("确认收货");
                view3.setOnClickListener(v -> onClick.onShowLogistics(position));
                view4.setOnClickListener(v -> onClick.onCanaleOrder(position));
                view5.setOnClickListener(v -> onClick.onGetGoods(position));
                layout.addView(view3);
                layout.addView(view4);
                layout.addView(view5);
                break;

            case 4:
                statue.setText("已完成");
                View view6 = getGrayView("追加评论");
                View view7 = getGrayView("删除订单");
                View view8 = getGrayView("再来一单");
                view6.setOnClickListener(v -> onClick.onSetCommit(position));
                view7.setOnClickListener(v -> onClick.onDeletOrder(position));
                view8.setOnClickListener(v -> onClick.onGetAgin(position));
                layout.addView(view6);
                layout.addView(view7);
                layout.addView(view8);
                break;

            case 5:
                statue.setText("待付款");
                View view9 = getGrayView("取消订单");
                View view10 = getRedView("去支付");
                view9.setOnClickListener(v -> onClick.onCanaleOrder(position));
                view10.setOnClickListener(v -> onClick.onGoPay(position));
                layout.addView(view9);
                layout.addView(view10);
                break;

            case 6:
                statue.setText("已取消");
                View view11 = getGrayView("删除订单");
                View view12 = getGrayView("再来一单");
                view11.setOnClickListener(v -> onClick.onDeletOrder(position));
                view12.setOnClickListener(v -> onClick.onGetAgin(position));
                layout.addView(view11);
                layout.addView(view12);
                break;

            case 7:
                statue.setText("待自提");
                View view13 = getGrayView("取消订单");
                View view14 = getGrayView("确认收货");
                view13.setOnClickListener(v -> onClick.onCanaleOrder(position));
                view14.setOnClickListener(v -> onClick.onGetGoods(position));
                layout.addView(view13);
                layout.addView(view14);
                break;

        }


        return view;
    }

    private View getGrayView(String info) {
        TextView textView = new TextView(mContext);
        textView.setBackgroundResource(R.drawable.shape_comment);
        textView.setPadding(AppUtil.dp2px(mContext, 20), AppUtil.dp2px(mContext, 5),
                AppUtil.dp2px(mContext, 20), AppUtil.dp2px(mContext, 5));
        textView.setTextColor(mContext.getResources().getColor(R.color.color_black_E6));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(AppUtil.dp2px(mContext, 10), 0, 0, 0);
        textView.setLayoutParams(lp);
        textView.setTextSize(14);
        textView.setText(info);
        return textView;
    }

    private View getRedView(String info) {
        TextView textView = new TextView(mContext);
        textView.setBackgroundResource(R.drawable.shape_pay);
        textView.setPadding(AppUtil.dp2px(mContext, 20), AppUtil.dp2px(mContext, 5),
                AppUtil.dp2px(mContext, 20), AppUtil.dp2px(mContext, 5));
        textView.setTextColor(mContext.getResources().getColor(R.color.white));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(AppUtil.dp2px(mContext, 10), 0, 0, 0);
        textView.setLayoutParams(lp);
        textView.setTextSize(14);
        textView.setText(info);
        return textView;
    }

    public interface OrderBookAdapterOnclick {

        void onAppraise(int positon);

        void onCanaleOrder(int positon);

        void onShowLogistics(int positon);

        void onGetGoods(int positon);

        void onDeletOrder(int positon);

        void onSetCommit(int positon);

        void onGetAgin(int positon);

        void onGoPay(int positon);

        void onIemClick(int positon);

    }

}

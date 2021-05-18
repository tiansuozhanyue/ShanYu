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

import java.util.List;

public class OrderBookAdapter extends BaseAdapter {

    private Context mContext;
    private List<OrderBookBean> booksModes;
    private AddressOnClick onClick;

    public OrderBookAdapter(Context mContext, List<OrderBookBean> booksModes) {
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
        ImageLoaderUtil.loadImage(HttpApi.HOST + booksMode.getCovers(), view.findViewById(R.id.cover));
        ((TextView) view.findViewById(R.id.shopName)).setText(booksMode.getName());
        ((TextView) view.findViewById(R.id.title)).setText(booksMode.getTitle());
        ((TextView) view.findViewById(R.id.price)).setText("￥" + booksMode.getPreevent());
        ((TextView) view.findViewById(R.id.count)).setText(booksMode.getCount() + "件");
        String[] p = booksMode.getPrincipal().split("\\.");
        LinearLayout layout = view.findViewById(R.id.layout);
        ((TextView) view.findViewById(R.id.price2)).setText(p[0]);
        ((TextView) view.findViewById(R.id.price1)).setText(p[1]);
        TextView statue = view.findViewById(R.id.statue);
        switch (booksMode.getStatus()) {
            case 0:
                statue.setText("待评价");
                layout.addView(getGrayView("评价"));
                break;
            case 1:
                statue.setText("待发货");
                layout.addView(getGrayView("取消订单"));
                break;
            case 2:
                statue.setText("已退款");
                break;
            case 3:
                statue.setText("待收货");
                layout.addView(getGrayView("查看物流"));
                layout.addView(getGrayView("取消订单"));
                layout.addView(getGrayView("确认收货"));
                break;
            case 4:
                statue.setText("已完成");
                layout.addView(getGrayView("追加评论"));
                layout.addView(getGrayView("删除订单"));
                layout.addView(getGrayView("再来一单"));
                break;
            case 5:
                statue.setText("待付款");
                layout.addView(getGrayView("取消订单"));
                layout.addView(getRedView("去支付"));

                break;
            case 6:
                statue.setText("已取消");
                layout.addView(getGrayView("删除订单"));
                layout.addView(getRedView("再来一单"));
                break;
            case 7:
                statue.setText("待自提");
                layout.addView(getGrayView("取消订单"));
                layout.addView(getGrayView("确认收货"));
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

    public interface AddressOnClick {

        void onAddressSet(boolean flag, String id);

        void onAddressEdit(AddressMode mode);

        void onAddressSelet(AddressMode mode);

        void onAddressDell(String id);
    }

}

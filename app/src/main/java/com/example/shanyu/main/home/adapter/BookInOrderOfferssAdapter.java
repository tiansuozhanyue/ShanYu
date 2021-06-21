package com.example.shanyu.main.home.adapter;

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
import com.example.shanyu.main.mine.bean.AddressMode;
import com.example.shanyu.main.mine.bean.OffersMode;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.utils.TimeUtil;

import java.util.List;

public class BookInOrderOfferssAdapter extends BaseAdapter {

    private Context mContext;
    private List<OffersMode> addressModes;
    private OffersOnClick onClick;
    private int couponId;

    public BookInOrderOfferssAdapter(Context mContext, List<OffersMode> actionModes, int couponId, OffersOnClick onClick) {
        this.mContext = mContext;
        this.addressModes = actionModes;
        this.onClick = onClick;
        this.couponId = couponId;
    }

    @Override
    public int getCount() {
        return addressModes.size();
    }

    @Override
    public Object getItem(int position) {
        return addressModes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OffersMode mode = addressModes.get(position);
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_offers_item, parent, false);
        ImageView selectView = view.findViewById(R.id.select);
        TextView sum = view.findViewById(R.id.sum);
        TextView time = view.findViewById(R.id.time);
        TextView info1 = view.findViewById(R.id.info1);
        TextView rule = view.findViewById(R.id.rule);

        selectView.setVisibility(mode.getId() == couponId ? View.VISIBLE : View.GONE);
        sum.setText(mode.getMoney() + "");

        time.setText(TimeUtil.stampToDate3(mode.getBusiness()) + " - " + TimeUtil.stampToDate3(mode.getRest()));
        info1.setText(mode.getExplain());
        rule.setText(mode.getRule());


        if (onClick != null) {
            view.setOnClickListener(v -> onClick.onOffersSelet(mode));
        }

        return view;
    }

    public interface OffersOnClick {

        void onOffersSelet(OffersMode mode);

    }

}

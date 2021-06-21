package com.example.shanyu.main.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.main.mine.bean.FootMode;
import com.example.shanyu.main.mine.bean.OffersMode;
import com.example.shanyu.utils.TimeUtil;

import java.util.List;

public class OfferAdapter extends BaseAdapter {

    private Context mContext;
    private List<OffersMode> actionModes;
    private OfferOnClick onClick;

    public OfferAdapter(Context mContext, List<OffersMode> actionModes, OfferOnClick onClick) {
        this.mContext = mContext;
        this.actionModes = actionModes;
        this.onClick = onClick;
    }

    @Override
    public int getCount() {
        return actionModes.size();
    }

    @Override
    public Object getItem(int position) {
        return actionModes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_offer_item, parent, false);

        OffersMode mode = actionModes.get(position);
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_offers_item, parent, false);
        ImageView selectView = view.findViewById(R.id.select);
        TextView sum = view.findViewById(R.id.sum);
        TextView time = view.findViewById(R.id.time);
        TextView info1 = view.findViewById(R.id.info1);
        TextView rule = view.findViewById(R.id.rule);

        selectView.setVisibility( View.GONE);
        sum.setText(mode.getMoney() + "");

        time.setText(TimeUtil.stampToDate3(mode.getBusiness()) + " - " + TimeUtil.stampToDate3(mode.getRest()));
        info1.setText(mode.getExplain());
        rule.setText(mode.getRule());

        return view;

//        ImageView mImageView = view.findViewById(R.id.mImageView);
//        ImageLoaderUtil.loadImage(HttpApi.HOST + actionModes.get(position).getPath(), mImageView);
//        if (onClick != null) {
//            mImageView.setOnClickListener(v -> {
//                onClick.onActionClick(position);
//            });
//        }
    }

    public interface OfferOnClick {
        void onActionClick(int p);
    }

}

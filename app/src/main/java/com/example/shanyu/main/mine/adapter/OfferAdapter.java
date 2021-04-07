package com.example.shanyu.main.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.shanyu.R;
import com.example.shanyu.main.mine.bean.FootMode;
import com.example.shanyu.main.mine.bean.OffersMode;

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_offer_item, parent, false);
//        ImageView mImageView = view.findViewById(R.id.mImageView);
//        ImageLoaderUtil.loadImage(HttpApi.HOST + actionModes.get(position).getPath(), mImageView);
//        if (onClick != null) {
//            mImageView.setOnClickListener(v -> {
//                onClick.onActionClick(position);
//            });
//        }

        return view;
    }

    public interface OfferOnClick {
        void onActionClick(int p);
    }

}

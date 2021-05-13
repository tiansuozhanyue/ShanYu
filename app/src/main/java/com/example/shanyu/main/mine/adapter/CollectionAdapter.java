package com.example.shanyu.main.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.main.mine.bean.CollectionBean;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.widget.MyListView;

import java.util.List;

public class CollectionAdapter extends BaseAdapter {

    private Context mContext;
    private List<CollectionBean> names;

    public CollectionAdapter(Context mContext, List<CollectionBean> names) {
        this.mContext = mContext;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_collection_item, parent, false);
        CollectionBean mode = names.get(position);
        ImageLoaderUtil.loadImage(HttpApi.HOST + mode.getCovers(), view.findViewById(R.id.bookCover));
        ((TextView) view.findViewById(R.id.bookName)).setText(mode.getTitle());
        ((TextView) view.findViewById(R.id.num)).setText(mode.getCollectionNum() + "人收藏");
        String[] prices = mode.getPrice().split("\\.");
        ((TextView) view.findViewById(R.id.price1)).setText(prices[0]);
        ((TextView) view.findViewById(R.id.price2)).setText("."+prices[1]);

        return view;
    }


}

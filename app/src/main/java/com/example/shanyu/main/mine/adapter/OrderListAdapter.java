package com.example.shanyu.main.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.shanyu.R;
import com.example.shanyu.main.mine.bean.ShopBook;

import java.util.List;

public class OrderListAdapter extends BaseAdapter {

    private Context mContext;
    private List<ShopBook> shopBooks;

    public OrderListAdapter(Context mContext, List<ShopBook> shopBooks) {
        this.mContext = mContext;
        this.shopBooks = shopBooks;
    }

    @Override
    public int getCount() {
        return shopBooks.size();
    }

    @Override
    public Object getItem(int position) {
        return shopBooks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_books_item, parent, false);
        return view;
    }
}

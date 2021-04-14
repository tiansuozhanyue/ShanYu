package com.example.shanyu.main.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.main.home.bean.BookMode;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.main.mine.bean.ShopBook;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.widget.MyListView;

import java.util.List;

public class OrderBooksAdapter extends BaseAdapter {

    private Context mContext;
    private List<ShopBook> names;
    private int index = 0;

    public OrderBooksAdapter(Context mContext, List<ShopBook> names) {
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

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_book_item, parent, false);
        ShopBook mode = names.get(position);
        List<MyBooksMode> booksModes = mode.getModes();

        TextView shopName = view.findViewById(R.id.shopName);
        shopName.setText(mode.getName());

        MyListView myListView = view.findViewById(R.id.myListView);

        myListView.setAdapter(new BaseAdapter() {
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
                View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_books_item, parent, false);
                MyBooksMode book = booksModes.get(position);

                TextView bookName = view.findViewById(R.id.bookName);
                TextView bookPrice = view.findViewById(R.id.bookPrice);
                TextView bookNum = view.findViewById(R.id.bookNum);

                ImageLoaderUtil.loadImage(HttpApi.HOST + book.getPath(), view.findViewById(R.id.bookCover));
                bookName.setText(book.getTitle());
                bookPrice.setText("￥" + book.getPreevent());
                bookNum.setText("x" + book.getCount());

                return view;
            }
        });

        return view;
    }


}

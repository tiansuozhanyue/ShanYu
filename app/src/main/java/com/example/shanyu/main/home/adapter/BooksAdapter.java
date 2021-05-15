package com.example.shanyu.main.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.main.home.bean.BookMode;
import com.example.shanyu.utils.ImageLoaderUtil;

import java.util.List;

public class BooksAdapter extends BaseAdapter {

    private Context mContext;
    private List<BookMode> names;
    private int index = 0;
    private BookOnClick onClick;

    public BooksAdapter(Context mContext, List<BookMode> names, BookOnClick onClick) {
        this.mContext = mContext;
        this.names = names;
        this.onClick = onClick;
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

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_book_item, parent, false);
        BookMode mode = names.get(position);

        ImageLoaderUtil.loadImage(HttpApi.HOST + mode.getCovers(), view.findViewById(R.id.book_img));

        ((TextView) view.findViewById(R.id.book_name)).setText(mode.getTitle());
        ((TextView) view.findViewById(R.id.book_shop)).setText(mode.getName());

        String[] sum = mode.getPreevent().split("\\.");

        ((TextView) view.findViewById(R.id.book_sum1)).setText(sum[0]);
        ((TextView) view.findViewById(R.id.book_sum2)).setText("." + sum[1]);

        TextView textView = view.findViewById(R.id.book_sum3);
        textView.setText(mode.getPrice());
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        ((TextView) view.findViewById(R.id.book_persions)).setText(mode.getSold() + "人已付款");


        if (onClick != null) {
            view.setOnClickListener(v -> {
                onClick.onBookClick(mode);
            });
        }

        return view;
    }

    public interface BookOnClick {
        void onBookClick(BookMode mode);
    }

}

package com.example.shanyu.main.mine.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.main.mine.bean.ShopBook;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.widget.MyListView;
import com.example.shanyu.widget.ShopSumButton;

import java.util.ArrayList;
import java.util.List;

public class MyBooksAdapter extends BaseAdapter {

    private Context mContext;
    private List<MyBooksMode> shopBooks;

    public MyBooksAdapter(Context mContext, List<MyBooksMode> shopBooks) {
        this.mContext = mContext;
        this.shopBooks = shopBooks;
    }

    public void setAllSelected(boolean allSelected) {
        for (MyBooksMode booksMode : shopBooks) {
            for (MyBooksMode.ListDTO listDTO : booksMode.getList()) {
                listDTO.setIsselected(allSelected ? 1 : 0);
            }
        }
        notifyDataSetChanged();
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
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_books_item, parent, false);

        MyBooksMode mode = shopBooks.get(position);
        List<MyBooksMode.ListDTO> booksModes = mode.getList();

        CheckBox shopCheckBox = view.findViewById(R.id.shopCheckBox);

        int sum = 0;
        for (MyBooksMode.ListDTO listDTO : booksModes) {
            sum += listDTO.getIsselected();
        }
        shopCheckBox.setChecked(sum == booksModes.size());

        shopCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (MyBooksMode.ListDTO listDTO : booksModes) {
                listDTO.setIsselected(shopCheckBox.isChecked() ? 1 : 0);
            }
            notifyDataSetChanged();
        });


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

                View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_cart_books_item, parent, false);
                MyBooksMode.ListDTO book = booksModes.get(position);

                CheckBox checkBox = view.findViewById(R.id.checkBox);
                TextView bookName = view.findViewById(R.id.bookName);
                TextView bookPrice = view.findViewById(R.id.bookPrice);
                TextView price = view.findViewById(R.id.price);
                ShopSumButton bookNum = view.findViewById(R.id.bookNum);

                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    book.setIsselected(checkBox.isChecked() ? 1 : 0);
                    notifyDataSetChanged();
                    MyBooksAdapter.this.notifyDataSetChanged();
                });

                checkBox.setChecked(book.getIsselected() == 1);

                ImageLoaderUtil.loadImage(HttpApi.HOST + book.getPath(), view.findViewById(R.id.bookCover));
                bookName.setText(book.getTitle());
                bookPrice.setText("￥" + book.getPreevent());
                price.setText("￥" + book.getPrice());
                price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                bookNum.setSum(book.getCount());

                return view;

            }
        });

        return view;
    }
}

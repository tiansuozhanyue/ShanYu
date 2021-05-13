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
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.main.mine.bean.ShopBook;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.widget.MyListView;
import com.example.shanyu.widget.ShopSumButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBooksAdapter extends BaseAdapter {

    private Context mContext;
    private List<MyBooksMode> shopBooks;
    private DataListener dataListener;

    public MyBooksAdapter(Context mContext, List<MyBooksMode> shopBooks, DataListener dataListener) {
        this.mContext = mContext;
        this.shopBooks = shopBooks;
        this.dataListener = dataListener;
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
//        updateMoney();
    }

    private void updateMoney() {
        List<MyBooksMode> myBooksModes = new ArrayList<>();

        if (dataListener != null) {
            StringBuffer ids1 = new StringBuffer();
            StringBuffer ids2 = new StringBuffer();
            for (MyBooksMode myBooksMode : shopBooks) {
                List<MyBooksMode.ListDTO> listDTOS = new ArrayList<>();
                for (MyBooksMode.ListDTO listDTO : myBooksMode.getList()) {
                    if (listDTO.getIsselected() == 1) {
                        listDTOS.add(listDTO);
                        ids1.append(listDTO.getId() + ",");

                    } else {

                        ids2.append(listDTO.getId() + ",");
                    }
                }
                if (listDTOS.size() > 0) {
                    myBooksModes.add(new MyBooksMode(myBooksMode.getShopId(), myBooksMode.getName(), listDTOS));
                }
            }
            dataListener.selectExchange(myBooksModes);
        }
    }


    /**
     * 上报数量
     */
    private void sumBookss(String goods_id, String buy_num) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("goods_id", goods_id);
        map.put("buy_num", buy_num);
        HttpUtil.doGet(HttpApi.CARTSUM, map, null);
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
                    MyBooksAdapter.this.notifyDataSetChanged();
                    updateMoney();
                });

                checkBox.setChecked(book.getIsselected() == 1);

                ImageLoaderUtil.loadImage(HttpApi.HOST + book.getCovers(), view.findViewById(R.id.bookCover));
                bookName.setText(book.getTitle());
                bookPrice.setText("￥" + book.getPreevent());
                price.setText("￥" + book.getPrice());
                price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                bookNum.setSum(book.getCount());
                bookNum.setSumExchangeListener(sum1 -> {
                    book.setCount(sum1);
                    sumBookss(book.getGoodsId().toString(), sum1 + "");
                    updateMoney();
                });

                return view;

            }
        });

        return view;

    }

    public interface DataListener {
        void selectExchange(List<MyBooksMode> listDTOS);
    }

}

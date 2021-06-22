package com.example.shanyu.main.mine.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
import java.util.Set;

public class MyBooksAdapter extends BaseAdapter {

    private Context mContext;
    private List<MyBooksMode> shopBooks;
    private DataListener dataListener;
    private Set<Integer> selectIds;

    public MyBooksAdapter(Context mContext, List<MyBooksMode> shopBooks, Set<Integer> selectIds, DataListener dataListener) {
        this.mContext = mContext;
        this.shopBooks = shopBooks;
        this.dataListener = dataListener;
        this.selectIds = selectIds;
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

//    private void updateMoney() {
//        List<MyBooksMode> myBooksModes = new ArrayList<>();
//
//        if (dataListener != null) {
//            StringBuffer ids1 = new StringBuffer();
//            StringBuffer ids2 = new StringBuffer();
//            for (MyBooksMode myBooksMode : shopBooks) {
//                List<MyBooksMode.ListDTO> listDTOS = new ArrayList<>();
//                for (MyBooksMode.ListDTO listDTO : myBooksMode.getList()) {
//                    if (listDTO.getIsselected() == 1) {
//                        listDTOS.add(listDTO);
//                        ids1.append(listDTO.getId() + ",");
//
//                    } else {
//
//                        ids2.append(listDTO.getId() + ",");
//                    }
//                }
//                if (listDTOS.size() > 0) {
//                    myBooksModes.add(new MyBooksMode(myBooksMode.getShopId(), myBooksMode.getName(), listDTOS));
//                }
//            }
//            dataListener.selectExchange(myBooksModes);
//        }
//    }


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
        TextView shopName = view.findViewById(R.id.shopName);
        CheckBox shopCheckBox = view.findViewById(R.id.shopCheckBox);

        shopName.setText(mode.getName());

        boolean flag = true;
        for (MyBooksMode.ListDTO booksMode : booksModes) {
            flag = flag & selectIds.contains(booksMode.getId());
        }
        shopCheckBox.setChecked(flag);

        shopCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (MyBooksMode.ListDTO booksMode : booksModes) {
                if (isChecked) {
                    selectIds.add(booksMode.getId());
                } else {
                    selectIds.remove(booksMode.getId());
                }
            }
            dataListener.selectExchange();
        });

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
                ImageView bookCover = view.findViewById(R.id.bookCover);

                checkBox.setChecked(selectIds.contains(book.getId()));

                ImageLoaderUtil.loadImage(HttpApi.HOST + book.getCovers(), bookCover);
                bookName.setText(book.getTitle());
                bookPrice.setText("￥" + book.getPreevent());
                price.setText("￥" + book.getPrice());
                price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                bookNum.setSum(book.getCount());

                bookCover.setOnClickListener(v -> dataListener.bookOnclick(book.getGoodsId() + ""));
                bookName.setOnClickListener(v -> dataListener.bookOnclick(book.getGoodsId() + ""));
                bookPrice.setOnClickListener(v -> dataListener.bookOnclick(book.getGoodsId() + ""));
                price.setOnClickListener(v -> dataListener.bookOnclick(book.getGoodsId() + ""));

                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectIds.add(book.getId());
                    } else {
                        selectIds.remove(book.getId());
                    }
                    dataListener.selectExchange();
                });

                bookNum.setSumExchangeListener(sum1 -> {
                    book.setCount(sum1);
                    sumBookss(book.getGoodsId().toString(), sum1 + "");
                    dataListener.selectExchange();
                });

                return view;

            }
        });

        return view;

    }

    public interface DataListener {
        void selectExchange();

        void bookOnclick(String id);
    }

}

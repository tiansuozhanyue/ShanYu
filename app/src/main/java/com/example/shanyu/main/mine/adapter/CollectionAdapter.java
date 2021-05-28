package com.example.shanyu.main.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.main.mine.bean.CollectionBean;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.widget.MyListView;
import com.example.shanyu.widget.SwipeListLayout;

import java.util.List;
import java.util.Set;

public class CollectionAdapter extends BaseAdapter {

    private Context mContext;
    private List<CollectionBean> names;
    private Set<SwipeListLayout> sets;
    private OnClick mOnClick;

    public CollectionAdapter(Context mContext, List<CollectionBean> names, Set<SwipeListLayout> sets, OnClick mOnClick) {
        this.mContext = mContext;
        this.names = names;
        this.sets = sets;
        this.mOnClick = mOnClick;
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

        View view = LayoutInflater.from(mContext).inflate(R.layout.slip_list_item, parent, false);
        CollectionBean mode = names.get(position);
        final SwipeListLayout sll_main = (SwipeListLayout) view.findViewById(R.id.sll_main);
        ImageLoaderUtil.loadImage(HttpApi.HOST + mode.getCovers(), view.findViewById(R.id.bookCover));
        ((TextView) view.findViewById(R.id.bookName)).setText(mode.getTitle());
        ((TextView) view.findViewById(R.id.num)).setText(mode.getCollectionNum() + "人收藏");
        String[] prices = mode.getPrice().split("\\.");
        ((TextView) view.findViewById(R.id.price1)).setText(prices[0]);
        ((TextView) view.findViewById(R.id.price2)).setText("." + prices[1]);
        TextView tv_delete = (TextView) view.findViewById(R.id.tv_delete);
        RelativeLayout itemView = (RelativeLayout) view.findViewById(R.id.item);
        sll_main.setOnSwipeStatusListener(new SwipeListLayout.OnSwipeStatusListener() {
            @Override
            public void onStatusChanged(SwipeListLayout.Status status) {
                if (status == SwipeListLayout.Status.Open) {
                    //若有其他的item的状态为Open，则Close，然后移除
                    if (sets.size() > 0) {
                        for (SwipeListLayout s : sets) {
                            s.setStatus(SwipeListLayout.Status.Close, true);
                            sets.remove(s);
                        }
                    }
                    sets.add(sll_main);
                } else {
                    if (sets.contains(sll_main))
                        sets.remove(sll_main);
                }
            }

            @Override
            public void onStartCloseAnimation() {

            }

            @Override
            public void onStartOpenAnimation() {

            }
        });

        tv_delete.setOnClickListener(view1 -> {
            sll_main.setStatus(SwipeListLayout.Status.Close, true);
            mOnClick.deletItemOnclick(position);
        });

        itemView.setOnClickListener(v -> mOnClick.itemOnclick(position));

        return view;

    }

    public interface OnClick {
        void itemOnclick(int p);

        void deletItemOnclick(int p);
    }

}

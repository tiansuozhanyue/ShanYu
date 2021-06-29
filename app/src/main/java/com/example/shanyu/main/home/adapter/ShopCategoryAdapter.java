package com.example.shanyu.main.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.main.home.bean.BannerMode;
import com.example.shanyu.main.home.bean.CategoryBean;
import com.example.shanyu.widget.MyListView;

import java.util.List;

public class ShopCategoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<CategoryBean> beans;
    private int index = 0;
    private CategoryOnClick onClick;

    public ShopCategoryAdapter(Context mContext, List<CategoryBean> beans, CategoryOnClick onClick) {
        this.mContext = mContext;
        this.beans = beans;
        this.onClick = onClick;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_shop_category_item, parent, false);
        TextView mTextView = view.findViewById(R.id.mTextView);
        MyListView mListView = view.findViewById(R.id.mListView);
        CategoryBean bean = beans.get(position);
        List<CategoryBean.ListBean> listBean = bean.getList();

        mTextView.setText(bean.getName());

        view.setOnClickListener(v -> {
            if (listBean == null) {
                onClick.onCategoryClick(bean.getId() + "", bean.getName());
            } else {
                if (mListView.getVisibility() == View.GONE) {
                    mListView.setVisibility(View.VISIBLE);
                    mListView.setAdapter(new ShopCategoryAdapter2(mContext, listBean, onClick));
                } else {
                    mListView.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    public interface CategoryOnClick {
        void onCategoryClick(String id, String categoryName);
    }

}

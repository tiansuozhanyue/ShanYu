package com.example.shanyu.main.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.main.home.bean.CategoryBean;

import java.util.List;

public class ShopCategoryAdapter2 extends BaseAdapter {

    private Context mContext;
    private List<CategoryBean.ListBean> beans;
    private ShopCategoryAdapter.CategoryOnClick onClick;

    public ShopCategoryAdapter2(Context mContext, List<CategoryBean.ListBean> beans, ShopCategoryAdapter.CategoryOnClick onClick) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_shop_category_item2, parent, false);
        TextView mTextView = view.findViewById(R.id.mTextView);
        CategoryBean.ListBean bean = beans.get(position);
        mTextView.setText(bean.getName());
        view.setOnClickListener(v -> onClick.onCategoryClick(bean.getId() + "", bean.getName()));
        return view;
    }


}

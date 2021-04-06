package com.example.shanyu.main.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shanyu.R;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> names;
    private int index = 0;
    private CategoryOnClick onClick;

    public CategoryAdapter(Context mContext, List<String> names, CategoryOnClick onClick) {
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

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_category_item, parent, false);
        TextView mTextView = view.findViewById(R.id.mTextView);
        mTextView.setSelected(index == position);
        mTextView.setText(names.get(position));

        if (onClick != null) {
            mTextView.setOnClickListener(v -> {
                index = position;
                notifyDataSetChanged();
                onClick.onCategoryClick(position);
            });
        }

        return view;
    }

    public interface CategoryOnClick {
        void onCategoryClick(int p);
    }

}

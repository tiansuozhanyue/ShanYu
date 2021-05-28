package com.example.shanyu.main.action;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.utils.ImageLoaderUtil;

import java.util.List;

public class ActionAdapter extends BaseAdapter {

    private Context mContext;
    private List<ActionMode> actionModes;
    private ActionOnClick onClick;

    public ActionAdapter(Context mContext, List<ActionMode> actionModes, ActionOnClick onClick) {
        this.mContext = mContext;
        this.actionModes = actionModes;
        this.onClick = onClick;
    }

    @Override
    public int getCount() {
        return actionModes.size();
    }

    @Override
    public Object getItem(int position) {
        return actionModes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_action_item, parent, false);
        ImageView mImageView = view.findViewById(R.id.mImageView);
        ImageLoaderUtil.loadImage(HttpApi.HOST + actionModes.get(position).getCovers(), mImageView);
        if (onClick != null) {
            mImageView.setOnClickListener(v -> {
                onClick.onActionClick(position);
            });
        }

        return view;
    }

    public interface ActionOnClick {
        void onActionClick(int p);
    }

}

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
import com.example.shanyu.main.home.bean.CommentBean;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.utils.TimeUtil;
import com.example.shanyu.widget.RoundImageView;

import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private Context mContext;
    private List<CommentBean> commentBeans;
    private boolean isLimit;

    public CommentAdapter(Context mContext, List<CommentBean> commentBeans, boolean isLimit) {
        this.mContext = mContext;
        this.commentBeans = commentBeans;
        this.isLimit = isLimit;
    }

    @Override
    public int getCount() {
        return isLimit ? commentBeans.size() > 2 ? 2 : commentBeans.size() : commentBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return commentBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_comment_item, parent, false);
        CommentBean bean = commentBeans.get(position);
        RoundImageView image = view.findViewById(R.id.image);
        TextView name = view.findViewById(R.id.name);
        TextView time = view.findViewById(R.id.time);
        TextView comment = view.findViewById(R.id.comment);

        ImageLoaderUtil.loadImage(bean.getAvatar(), image);
        name.setText("…" + bean.getOrder_nickname().charAt(bean.getOrder_nickname().length() - 1));
        time.setText(TimeUtil.stampToDate(bean.getCreated_at()+"000"));
        comment.setText(bean.getEvaluate());

        return view;
    }


}

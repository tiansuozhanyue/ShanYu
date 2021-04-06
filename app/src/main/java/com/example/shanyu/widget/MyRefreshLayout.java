package com.example.shanyu.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.example.shanyu.R;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 新幽游重组架构后的上拉刷新下拉加载列表控件
 */
public class MyRefreshLayout extends SmartRefreshLayout {

    public MyRefreshLayout(Context context) {
        super(context);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 控件属性初始化设置：默认有下拉刷新，是否上拉加载由传入的参数控制。
     *
     * @param isLoadMoew
     * @param context
     */
    public MyRefreshLayout initView(Context context, boolean isLoadMoew) {
        setEnableLoadMore(isLoadMoew);
        setEnableRefresh(true);
        setHeaderView(context);
        return this;
    }

    /**
     * 控件属性初始化设置：默认有下拉刷新、无上拉加载。
     *
     * @param context
     */
    public MyRefreshLayout initView(Context context) {
        return initView(context, false);
    }


    /**
     * 设置头部下拉动画
     */
    private void setHeaderView(Context context) {
        MaterialHeader header = new MaterialHeader(context);
        header.setColorSchemeColors(getResources().getColor(R.color.color_red));
        setRefreshHeader(header);
    }


    /**
     * 设置下拉刷新、上拉加载接口
     */
    public MyRefreshLayout setRefreshLoadMoreListener(final RefreshLoadMoreListener refreshLoadMoreListener) {
        setEnableRefresh(true);
        if (refreshLoadMoreListener != null)
            super.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshLayout) {
                    refreshLoadMoreListener.onLoadMore((MyRefreshLayout) refreshLayout);
                }

                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    refreshLoadMoreListener.onRefresh((MyRefreshLayout) refreshLayout);
                }
            });
        return this;
    }

    /**
     * 设置单纯下拉刷新接口
     */
    public MyRefreshLayout setRefreshListener(final RefreshListener onRefreshListener) {
        if (onRefreshListener != null)
            super.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    onRefreshListener.onRefresh((MyRefreshLayout) refreshLayout);
                }
            });
        return this;
    }

    /**
     * 关闭加载视图
     */
    public void closeLoadingView() {
        if (isRefreshing())
            finishRefresh();
        if (isLoading())
            finishLoadMore();
    }

    public interface RefreshLoadMoreListener {
        void onRefresh(MyRefreshLayout refreshLayout);

        void onLoadMore(MyRefreshLayout refreshLayout);
    }

    public interface RefreshListener {
        void onRefresh(MyRefreshLayout refreshLayout);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        getParent().requestDisallowInterceptTouchEvent(true);
//        return super.onInterceptTouchEvent(ev);
//    }

}

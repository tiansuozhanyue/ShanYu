package com.example.shanyu.main.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.ui.BookInfoActivity;
import com.example.shanyu.main.mine.adapter.CollectionAdapter;
import com.example.shanyu.main.mine.bean.CollectionBean;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.widget.MyCommonDialog;
import com.example.shanyu.widget.MyRefreshLayout;
import com.example.shanyu.widget.SwipeListLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionActivity extends BaseActivity implements MyRefreshLayout.RefreshListener, AbsListView.OnScrollListener, CollectionAdapter.OnClick {

    @BindView(R.id.myRefreshLayout)
    public MyRefreshLayout myRefreshLayout;
    @BindView(R.id.mListView)
    public ListView mListView;
    @BindView(R.id.empty)
    public TextView empty;

    List<CollectionBean> collectionBeans;
    private Set<SwipeListLayout> sets = new HashSet();
    private CollectionAdapter mCollectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection, "我的收藏");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        myRefreshLayout.setRefreshListener(this);
        mListView.setOnScrollListener(CollectionActivity.this);
        showLoading();
        getCollection();
    }

    /**
     * 获取收藏书籍列表
     */
    private void getCollection() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        HttpUtil.doGet(HttpApi.COLLECTIONS, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                myRefreshLayout.closeLoadingView();
                dismissLoading();
                empty.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(String resultData) {
                myRefreshLayout.closeLoadingView();
                dismissLoading();
                collectionBeans = new Gson().fromJson(resultData, new TypeToken<List<CollectionBean>>() {
                }.getType());
                if (collectionBeans != null && collectionBeans.size() > 0) {
                    empty.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                    mCollectionAdapter = new CollectionAdapter(CollectionActivity.this, collectionBeans, sets, CollectionActivity.this);
                    mListView.setAdapter(mCollectionAdapter);
                } else {
                    empty.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void onRefresh(MyRefreshLayout refreshLayout) {
        getCollection();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            //当listview开始滑动时，若有item的状态为Open，则Close，然后移除
            case SCROLL_STATE_TOUCH_SCROLL:
                if (sets.size() > 0) {
                    for (SwipeListLayout s : sets) {
                        s.setStatus(SwipeListLayout.Status.Close, true);
                        sets.remove(s);
                    }
                }
                break;

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void itemOnclick(int p) {

        if (sets.size() > 0) {
            for (SwipeListLayout s : sets) {
                s.setStatus(SwipeListLayout.Status.Close, true);
                sets.remove(s);
            }
        }

        Intent intent = new Intent(this, BookInfoActivity.class);
        intent.putExtra("bookModeId", collectionBeans.get(p).getId().toString());
        startActivity(intent);
    }

    @Override
    public void deletItemOnclick(int p) {

        MyCommonDialog myCommonDialog = new MyCommonDialog(this);
        myCommonDialog.setDialogContent("确认取消该收藏？");
        myCommonDialog.setOnSetPositiveButton("确定", dialog -> {
            dialog.dismiss();
            cancleCollection(p);
        });
        myCommonDialog.setOnSetNegativeButton("取消", dialog -> dialog.dismiss());
        myCommonDialog.show();

    }

    /**
     * 取消收藏书籍
     */
    private void cancleCollection(int p) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("goods_id", collectionBeans.get(p).getId().toString());
        HttpUtil.doGet(HttpApi.CANCLE_COLLECTION, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                collectionBeans.remove(p);
                mCollectionAdapter.notifyDataSetChanged();
                if (collectionBeans.size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                }
                setResult(110);
            }
        });

    }

}
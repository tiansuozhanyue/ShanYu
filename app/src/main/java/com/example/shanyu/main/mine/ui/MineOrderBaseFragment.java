package com.example.shanyu.main.mine.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shanyu.R;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.mine.adapter.OrderBookAdapter;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.main.mine.bean.OrderBookBean;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class MineOrderBaseFragment extends Fragment implements MyRefreshLayout.RefreshListener, HttpResultInterface {

    protected ListView mListView;
    protected MyRefreshLayout myRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine_order0, container, false);
        intView(view);
        return view;
    }

    protected abstract String getStatue();


    private void intView(View view) {
        mListView = view.findViewById(R.id.mListView);
        myRefreshLayout = view.findViewById(R.id.myRefreshLayout);
        myRefreshLayout.setRefreshListener(this);
        getOrders();
    }

    private void getOrders() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        String status = getStatue();
        if (!StringUtil.isEmpty(status))
            map.put("status", status);
        HttpUtil.doPost(HttpApi.ORDER_LIST, map, this);
    }


    @Override
    public void onRefresh(MyRefreshLayout refreshLayout) {
        getOrders();
    }

    @Override
    public void onFailure(String errorMsg) {
        myRefreshLayout.closeLoadingView();
    }

    @Override
    public void onSuccess(String t) {
        myRefreshLayout.closeLoadingView();
        List<OrderBookBean> actionModes = new Gson().fromJson(t, new TypeToken<List<OrderBookBean>>() {
        }.getType());
        mListView.setAdapter(new OrderBookAdapter(getContext(), actionModes));
    }

}
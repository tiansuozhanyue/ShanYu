package com.example.shanyu.main.mine.ui;

import android.content.Intent;
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
import com.example.shanyu.main.mine.adapter.FootAdapter;
import com.example.shanyu.main.mine.adapter.OrderBookAdapter;
import com.example.shanyu.main.mine.bean.FootMode;
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


public abstract class MineOrderBaseFragment extends Fragment implements MyRefreshLayout.RefreshListener, HttpResultInterface, OrderBookAdapter.OrderBookAdapterOnclick {

    protected ListView mListView;
    protected MyRefreshLayout myRefreshLayout;
    private List<OrderBookBean> actionModes;
    private OrderBookAdapter orderBookAdapter;

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
        actionModes = new Gson().fromJson(t, new TypeToken<List<OrderBookBean>>() {
        }.getType());
        orderBookAdapter = new OrderBookAdapter(getContext(), actionModes, this);
        mListView.setAdapter(orderBookAdapter);
    }

    /**
     * 评价
     *
     * @param positon
     */
    @Override
    public void onAppraise(int positon) {
        OrderBookBean bookBean=actionModes.get(positon);
        Intent intent = new Intent(getContext(), SetCommentsActivity.class);
        intent.putExtra("goods_id",bookBean.getGoodsId());
        intent.putExtra("goods_uid",bookBean.getGoodsUid());
        intent.putExtra("order_id",bookBean.getOrder());
        startActivity(intent);
    }

    /**
     * 取消订单
     *
     * @param positon
     */
    @Override
    public void onCanaleOrder(int positon) {
        setOrderStatue(positon, "6");
    }

    /**
     * 查询物流
     *
     * @param positon
     */
    @Override
    public void onShowLogistics(int positon) {

    }

    /**
     * 收货
     *
     * @param positon
     */
    @Override
    public void onGetGoods(int positon) {
        setOrderStatue(positon, "0");
    }

    /**
     * 删除订单
     *
     * @param positon
     */
    @Override
    public void onDeletOrder(int positon) {
        setOrderStatue(positon, "8");
    }

    /**
     * 追加评论
     *
     * @param positon
     */
    @Override
    public void onSetCommit(int positon) {

    }

    /**
     * 再来一单
     *
     * @param positon
     */
    @Override
    public void onGetAgin(int positon) {

    }

    /**
     * 去支付
     *
     * @param positon
     */
    @Override
    public void onGoPay(int positon) {

    }

    @Override
    public void onIemClick(int positon) {
        Intent intent = new Intent(getContext(), OrderInfoActivity.class);
        intent.putExtra("orderId", actionModes.get(positon).getId() + "");
        startActivity(intent);
    }

    /**
     * 设置订单状态
     *
     * @param postion
     * @param status  状态0|收货6|取消8|删除
     */
    private void setOrderStatue(int postion, String status) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("order_id", actionModes.get(postion).getId() + "");
        map.put("status", status);
        HttpUtil.doPost(HttpApi.SETORDERSTATUE, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                ToastUtil.shortToast(errorMsg);
            }

            @Override
            public void onSuccess(String resultData) {
                getOrders();
            }
        });
    }


}
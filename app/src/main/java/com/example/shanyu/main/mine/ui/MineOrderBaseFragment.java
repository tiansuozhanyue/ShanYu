package com.example.shanyu.main.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alipay.sdk.app.PayTask;
import com.example.shanyu.R;
import com.example.shanyu.base.EventBean;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.MainActivity;
import com.example.shanyu.main.home.bean.WxPayBean;
import com.example.shanyu.main.home.ui.PayBaseAvtivity;
import com.example.shanyu.main.home.ui.PaySucessActivity;
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
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class MineOrderBaseFragment extends Fragment implements MyRefreshLayout.RefreshListener, HttpResultInterface, OrderBookAdapter.OrderBookAdapterOnclick {

    protected ListView mListView;
    private TextView empty;
    protected MyRefreshLayout myRefreshLayout;
    private List<OrderBookBean> actionModes;
    private OrderBookAdapter orderBookAdapter;
    final int SDK_PAY_FLAG = 100;
    String orderId, allSum;
    MyHandler myHandler = new MyHandler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine_order0, container, false);
        intView(view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected abstract String getStatue();

    private void intView(View view) {
        mListView = view.findViewById(R.id.mListView);
        myRefreshLayout = view.findViewById(R.id.myRefreshLayout);
        empty = view.findViewById(R.id.empty);
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
        mListView.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(String t) {
        myRefreshLayout.closeLoadingView();
        actionModes = new Gson().fromJson(t, new TypeToken<List<OrderBookBean>>() {
        }.getType());

        if (actionModes != null && actionModes.size() > 0) {
            mListView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            orderBookAdapter = new OrderBookAdapter(getContext(), actionModes, this);
            mListView.setAdapter(orderBookAdapter);
        } else {
            mListView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 评价
     *
     * @param positon
     */
    @Override
    public void onAppraise(int positon) {
//        OrderBookBean bookBean = actionModes.get(positon);
//        Intent intent = new Intent(getContext(), SetCommentsActivity.class);
//        intent.putExtra("goods_id", bookBean.getGoodsId());
//        intent.putExtra("goods_uid", bookBean.getGoodsUid());
//        intent.putExtra("order_id", bookBean.getOrder());
//        startActivity(intent);
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
        OrderBookBean order = actionModes.get(positon);
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("count", "1");
        map.put("order_id", order.getOrder());
        map.put("type", order.getType() + "");
        map.put("ty", "1");
        HttpUtil.doPost(order.getType() == 0 ? HttpApi.PAY_W : HttpApi.PAY_Z, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                ToastUtil.shortToast(errorMsg);
            }

            @Override
            public void onSuccess(String resultData) {
                switch (order.getType()) {

                    case 0:
                        WxPayBean wxPayBean = new Gson().fromJson(resultData, WxPayBean.class);
                        orderId = wxPayBean.getOrderId() + "";
                        allSum = wxPayBean.getMoney() + "";
                        startWechatPay(wxPayBean);
                        break;

                    case 1:
                        try {
                            JSONObject object = new JSONObject(resultData);
                            orderId = object.getInt("order_id") + "";
                            allSum = object.getDouble("total_fee") + "";
                            startAliPay(object.getString("order"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });

    }

    /**
     * 调用支付宝本地支付
     *
     * @param orderInfo
     */
    private void startAliPay(String orderInfo) {
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(getActivity());
            Map<String, String> result = alipay.payV2(orderInfo, true);

            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            myHandler.sendMessage(msg);

        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    private class MyHandler extends Handler {
        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);

            switch (msg.what) {
                case SDK_PAY_FLAG:
                    Map<String, String> result = (Map<String, String>) msg.obj;
                    if ("9000".equals(result.get("resultStatus"))) {
                        goPaySucess();
                    } else {
                        goPayFaile();
                    }
                    break;
            }

        }
    }

    private void goPaySucess() {
        Intent intent_s = new Intent(getContext(), PaySucessActivity.class);
        intent_s.putExtra("orderId", orderId);
        intent_s.putExtra("money", allSum);
        startActivity(intent_s);
    }

    private void goPayFaile() {
        startActivity(new Intent(getActivity(), MineOrderActivity.class).putExtra("index", 1));
    }

    /**
     * 调起本地微信支付
     *
     * @param wxPayBean
     */
    private void startWechatPay(WxPayBean wxPayBean) {

        //这里的appid，替换成自己的即可
        IWXAPI api = WXAPIFactory.createWXAPI(getContext(), HttpApi.WX_APPID);
        api.registerApp(HttpApi.WX_APPID);

        //这里的bean，是服务器返回的json生成的bean
        PayReq payRequest = new PayReq();
        payRequest.appId = wxPayBean.getAppid();
        payRequest.partnerId = wxPayBean.getPartnerid();
        payRequest.prepayId = wxPayBean.getPrepayid();
        payRequest.packageValue = "Sign=WXPay";//固定值
        payRequest.nonceStr = wxPayBean.getNoncestr();
        payRequest.timeStamp = wxPayBean.getTimestamp();
        payRequest.sign = wxPayBean.getSign();

        orderId = wxPayBean.getOrderId().toString();

        //发起请求，调起微信前去支付
        api.sendReq(payRequest);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void studentEventBus(EventBean eventBean) {
        switch (eventBean.flag) {

            case EventBean.PAY_SUCESSS:
                goPaySucess();
                break;

            case EventBean.PAY_FAILE:
            case EventBean.PAY_CANCLE:
                goPayFaile();
                break;

        }

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
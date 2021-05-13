package com.example.shanyu.main.home.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.base.EventBean;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.adapter.BookInOrderOfferssAdapter;
import com.example.shanyu.main.home.adapter.BookInfoAddressAdapter;
import com.example.shanyu.main.home.adapter.OrderBooksAdapter;
import com.example.shanyu.main.home.bean.BookMode;
import com.example.shanyu.main.home.bean.WxPayBean;
import com.example.shanyu.main.mine.adapter.OfferAdapter;
import com.example.shanyu.main.mine.bean.AddressMode;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.main.mine.bean.OffersMode;
import com.example.shanyu.main.mine.bean.ShopBook;
import com.example.shanyu.main.mine.ui.AddressActivity;
import com.example.shanyu.main.mine.ui.OffersActivity;
import com.example.shanyu.utils.AppUtil;
import com.example.shanyu.utils.ArithUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.widget.CirButton;
import com.example.shanyu.widget.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookOrderActivity extends BaseActivity implements BookInOrderOfferssAdapter.OffersOnClick {

    @BindView(R.id.book_get1)
    public TextView book_get1;
    @BindView(R.id.book_get2)
    public TextView book_get2;
    @BindView(R.id.book_get1_index)
    public TextView book_get1_index;
    @BindView(R.id.book_get2_index)
    public TextView book_get2_index;
    @BindView(R.id.payGroup)
    public RadioGroup payGroup;
    @BindView(R.id.goPay)
    public CirButton goPay;
    @BindView(R.id.address)
    public TextView address;
    @BindView(R.id.name)
    public TextView name;
    @BindView(R.id.phone)
    public TextView phone;
    @BindView(R.id.all_sum)
    public TextView all_sum;
    @BindView(R.id.offers_sum)
    public TextView offers_sum;
    @BindView(R.id.final_sum1)
    public TextView final_sum1;
    @BindView(R.id.final_sum2)
    public TextView final_sum2;
    @BindView(R.id.final_sum11)
    public TextView final_sum11;
    @BindView(R.id.final_sum12)
    public TextView final_sum12;
    @BindView(R.id.myListView)
    public MyListView myListView;

    private static AddressMode addressMode;
    private static List<MyBooksMode> shopBooks;
    private String allSum = "0.00";        //总金额
    private String offersSum = "0.00";     //优惠金额
    private String finalSum = "0.00";      //合计金额
    private String orderId;
    private Dialog OffersDialog;
    private List<OffersMode> offersModes;
    private int couponId;
    static boolean isCart;

    public static void start(BaseActivity activity, AddressMode addressMode, List<MyBooksMode> shopBooks) {
        isCart = false;
        BookOrderActivity.addressMode = addressMode;
        BookOrderActivity.shopBooks = shopBooks;
        Intent intent = new Intent(activity, BookOrderActivity.class);
        activity.startActivity(intent);
    }

    public static void start(BaseActivity activity, List<MyBooksMode> shopBooks) {
        isCart = true;
        BookOrderActivity.shopBooks = shopBooks;
        Intent intent = new Intent(activity, BookOrderActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_order, "提交订单");
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    @Override
    public void initView() {

        updataSumView();
        updataAddressView();
        showGoods();

        payGroup.check(R.id.aliPay);
        book_get1.setSelected(true);
        book_get1_index.setSelected(true);
        goPay.setSelected(true);

    }

    @OnClick({R.id.book_get1, R.id.book_get2,
            R.id.address, R.id.address_more,
            R.id.goPay, R.id.offers_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.book_get1:
                book_get1.setSelected(true);
                book_get1_index.setSelected(true);
                book_get2.setSelected(false);
                book_get2_index.setSelected(false);
                break;

            case R.id.book_get2:
                book_get1.setSelected(false);
                book_get1_index.setSelected(false);
                book_get2.setSelected(true);
                book_get2_index.setSelected(true);
                break;

            case R.id.address:
            case R.id.address_more:
                Intent intent = new Intent(BookOrderActivity.this, AddressActivity.class);
                intent.putExtra("SeletAddress", true);
                startActivityForResult(intent, 110);
                break;


            case R.id.goPay:
                addOrder();
                break;

            case R.id.offers_layout:
                addressSelctDialog();
                break;

        }
    }

    /**
     * 获取默认地址
     */
    private void getAddress() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        HttpUtil.doGet(HttpApi.ADDRESS, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onSuccess(String resultData) {
                List<AddressMode> addressModes = new Gson().fromJson(resultData, new TypeToken<List<AddressMode>>() {
                }.getType());
                for (AddressMode mode : addressModes) {
                    if (mode.getIsselected() == 1) {
                        addressMode = mode;
                        address.setText(addressMode.getAreaname() + addressMode.getAddress());
                        name.setText(addressMode.getName());
                        phone.setText(addressMode.getPhone());
                    }
                }

            }
        });

    }

    /**
     * 优惠券选择弹窗
     */
    private void addressSelctDialog() {
        OffersDialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_offers_select, null, false);
        view.findViewById(R.id.close).setOnClickListener(v -> OffersDialog.dismiss());
        ListView mListView = view.findViewById(R.id.mListView);
        BookInOrderOfferssAdapter mAddressAdapter = new BookInOrderOfferssAdapter(this, offersModes, couponId, BookOrderActivity.this);
        mListView.setAdapter(mAddressAdapter);
        OffersDialog.setContentView(view);
        WindowManager.LayoutParams lp = OffersDialog.getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.height = AppUtil.getScreenWH(this)[1] * 65 / 100;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        OffersDialog.getWindow().setAttributes(lp);
        OffersDialog.getWindow().setBackgroundDrawable(null);
        OffersDialog.show();
    }

    /**
     * 获取优惠券列表
     */
    private void getOffers(String shopIds) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("ty", "1");
        map.put("mum", allSum);
        map.put("shop_id", shopIds);
        HttpUtil.doPost(HttpApi.OFFERS, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onSuccess(String resultData) {

                offersModes = new Gson().fromJson(resultData, new TypeToken<List<OffersMode>>() {
                }.getType());

            }
        });

    }

    /**
     * 计算总价
     */
    private String getAllSum() {
        allSum = "0.00";
        if (shopBooks == null) return "0.00";

        for (MyBooksMode shopBook : shopBooks) {
            for (MyBooksMode.ListDTO booksMode : shopBook.getList()) {
                String sum = ArithUtil.mul(booksMode.getPreevent(), booksMode.getCount().toString());
                allSum = ArithUtil.add(sum, allSum);
            }
        }

        return allSum;
    }

    /**
     * 更新金额信息
     */
    private void updataSumView() {

        allSum = getAllSum();
        finalSum = ArithUtil.sub(allSum, offersSum);

        all_sum.setText("￥" + allSum);
        offers_sum.setText("-￥" + offersSum);

        String[] sum = finalSum.split("\\.");

        final_sum1.setText(sum[0]);
        final_sum11.setText(sum[0]);

        final_sum2.setText("." + sum[1]);
        final_sum12.setText("." + sum[1]);

    }

    /**
     * 更新地址信息
     */
    private void updataAddressView() {
        if (addressMode == null) {
            getAddress();
        } else {
            address.setText(addressMode.getAreaname() + addressMode.getAddress());
            name.setText(addressMode.getName());
            phone.setText(addressMode.getPhone());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void studentEventBus(EventBean eventBean) {
        switch (eventBean.flag) {

            case EventBean.PAY_SUCESSS:
                Intent intent_s = new Intent(BookOrderActivity.this, PaySucessActivity.class);
                intent_s.putExtra("orderId", orderId);
                intent_s.putExtra("money", allSum);
                startActivity(intent_s);
                break;

            case EventBean.PAY_FAILE:
                break;

            case EventBean.PAY_CANCLE:
                break;

        }

    }

    /**
     * 显示商品列表
     */
    private void showGoods() {
        myListView.setAdapter(new OrderBooksAdapter(BookOrderActivity.this, shopBooks));

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < shopBooks.size(); i++) {
            if (i == shopBooks.size() - 1) {
                buffer.append(shopBooks.get(i).getShopId());
            } else {
                buffer.append(shopBooks.get(i).getShopId() + ",");
            }
        }

        getOffers(buffer.toString());
    }

    /**
     * 下单
     */
    private void addOrder() {

        Map<String, String> map = new HashMap<>();
        if (isCart) {
            StringBuffer buffer = new StringBuffer();
            for (MyBooksMode myBooksMode : shopBooks) {
                for (MyBooksMode.ListDTO listDTO : myBooksMode.getList()) {
                    buffer.append(listDTO.getId() + ",");
                }
            }
            MyBooksMode.ListDTO dto = shopBooks.get(0).getList().get(0);
            map.put("is_cart", "1");
            map.put("cart_id", buffer.substring(0, buffer.length() - 1));

        } else {
            MyBooksMode.ListDTO dto = shopBooks.get(0).getList().get(0);
            map.put("is_cart", "0");
            map.put("goods_id", dto.getGoodsId() + "");
            map.put("count", dto.getCount() + "");
        }
        map.put("user_id", SharedUtil.getIntence().getUid());
        map.put("coupon_id", couponId + "");
        map.put("ty", "0");
        map.put("addressid", addressMode.getId() + "");


        showLoading();
        HttpUtil.doPost(HttpApi.ORDER_ADD, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();

                WxPayBean wxPayBean = new Gson().fromJson(resultData, WxPayBean.class);
                startWechatPay(wxPayBean);

            }
        });

    }

    /**
     * 调起本地微信支付
     *
     * @param wxPayBean
     */
    private void startWechatPay(WxPayBean wxPayBean) {

        //这里的appid，替换成自己的即可
        IWXAPI api = WXAPIFactory.createWXAPI(this, HttpApi.WxPayAppId);
        api.registerApp(HttpApi.WxPayAppId);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110 && resultCode == 101) {
            addressMode = (AddressMode) data.getSerializableExtra("AddressMode");
            updataAddressView();
        }
    }

    @Override
    public void onOffersSelet(OffersMode mode) {
        OffersDialog.dismiss();
        couponId = mode.getId();
        offersSum = mode.getMoney() + "";
        updataSumView();
    }

}
package com.example.shanyu.main.home.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alipay.sdk.app.PayTask;
import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.base.EventBean;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.adapter.BookInOrderOfferssAdapter;
import com.example.shanyu.main.home.adapter.OrderBooksAdapter;
import com.example.shanyu.main.home.bean.WxPayBean;
import com.example.shanyu.main.mine.bean.AddressMode;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.main.mine.bean.OffersMode;
import com.example.shanyu.main.mine.ui.AddressActivity;
import com.example.shanyu.utils.AppUtil;
import com.example.shanyu.utils.ArithUtil;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.widget.CirButton;
import com.example.shanyu.widget.MyListView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookOrderActivity extends PayBaseAvtivity implements BookInOrderOfferssAdapter.OffersOnClick {

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
    Map<String, String> payMap = new HashMap<>();
    String type = "1";//支付方式
    static String ty, uid;
    boolean hasTy;

    public static void start(BaseActivity activity, AddressMode addressMode, List<MyBooksMode> shopBooks, String t, String id) {
        isCart = false;
        BookOrderActivity.addressMode = addressMode;
        BookOrderActivity.shopBooks = shopBooks;
        Intent intent = new Intent(activity, BookOrderActivity.class);
        intent.putExtra("ty", t);
        intent.putExtra("uid", id);
        activity.startActivity(intent);
    }

    public static void start(BaseActivity activity, List<MyBooksMode> shopBooks) {
        isCart = true;
        BookOrderActivity.shopBooks = shopBooks;
        Intent intent = new Intent(activity, BookOrderActivity.class);
        intent.putExtra("ty", "0");
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_order, "提交订单");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {

        ty = getIntent().getStringExtra("ty");
        uid = getIntent().getStringExtra("uid");

        updataSumView();
        updataAddressView();
        showGoods();

        payGroup.check(R.id.aliPay);
        goPay.setSelected(true);

        if (!StringUtil.isEmpty(uid)) {
            switch (ty) {
                case "0":
                    book_get1.setSelected(true);
                    book_get1_index.setSelected(true);
                    break;
                case "1":
                    book_get2.setSelected(true);
                    book_get2_index.setSelected(true);
                    break;
            }
        }

        payGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.aliPay:
                    type = "1";
                    break;
                case R.id.wxPay:
                    type = "0";
                    break;
            }
        });

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
                ty = "0";
                getAddress1();
                break;

            case R.id.book_get2:
                if (hasTy) {
                    book_get1.setSelected(false);
                    book_get1_index.setSelected(false);
                    book_get2.setSelected(true);
                    book_get2_index.setSelected(true);
                    ty = "1";
                    getAddress2(true);
                }
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
    private void getAddress1() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("isselected", "0");
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
     * 获取默认地址（商铺）
     */
    private void getAddress2(boolean flag) {

        if (StringUtil.isEmpty(uid)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("isselected", "1");
        HttpUtil.doGet(HttpApi.ADDRESS, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                book_get2.setTextColor(getResources().getColor(R.color.color_black_4D));
                hasTy = false;
            }

            @Override
            public void onSuccess(String resultData) {
                hasTy = true;
                List<AddressMode> addressModes = new Gson().fromJson(resultData, new TypeToken<List<AddressMode>>() {
                }.getType());
                if (flag) {
                    addressMode = addressModes.get(0);
                    address.setText(addressMode.getAreaname() + addressMode.getAddress());
                    name.setText(addressMode.getName());
                    phone.setText(addressMode.getPhone());
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
        TextView title1 = view.findViewById(R.id.title1);
        TextView title2 = view.findViewById(R.id.title2);
        TextView titleLine1 = view.findViewById(R.id.titleLine1);
        TextView titleLine2 = view.findViewById(R.id.titleLine2);
        TextView tipInfo1 = view.findViewById(R.id.tipInfo1);
        TextView tipInfo2 = view.findViewById(R.id.tipInfo2);

        if ("0.00".equals(offersSum)) {
            tipInfo1.setText("已选中推荐优惠，使用优惠券0张，共抵扣");
            tipInfo2.setText("￥0.00");
        } else {
            tipInfo1.setText("已选中推荐优惠，使用优惠券1张，共抵扣");
            tipInfo2.setText("￥" + offersSum);
        }

        title1.setText("可用优惠券(" + offersModes.size() + ")");

        title1.setSelected(true);
        titleLine1.setSelected(true);

        title1.setOnClickListener(v -> {
            title1.setSelected(true);
            title2.setSelected(false);
            titleLine1.setSelected(true);
            titleLine2.setSelected(false);
        });

        title2.setOnClickListener(v -> {
            title1.setSelected(false);
            title2.setSelected(true);
            titleLine1.setSelected(false);
            titleLine2.setSelected(true);
        });

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
    private void getOffers() {

        try {

            JSONArray array = new JSONArray();
            for (MyBooksMode mode : shopBooks) {
                JSONObject object = new JSONObject();
                object.put("shop_id", mode.getShopId());
                JSONArray array2 = new JSONArray();
                for (MyBooksMode.ListDTO dto : mode.getList()) {
                    JSONObject object2 = new JSONObject();
                    object2.put("goods_id", dto.getGoodsId());
                    object2.put("count", dto.getCount());
                    array2.put(object2);
                }
                object.put("list", array2);
                array.put(object);
            }

            Map<String, String> map = new HashMap<>();
            map.put("uid", SharedUtil.getIntence().getUid());
            map.put("list", array.toString());
            HttpUtil.doPost(HttpApi.OFFERS_ORDER, map, new HttpResultInterface() {
                @Override
                public void onFailure(String errorMsg) {
                    offersModes = new ArrayList<>();
                }

                @Override
                public void onSuccess(String resultData) {

                    offersModes = new Gson().fromJson(resultData, new TypeToken<List<OffersMode>>() {
                    }.getType());

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

        if (offersSum == "0.00") {
            offers_sum.setTextColor(getResources().getColor(R.color.color_black_4D));
            offers_sum.setText("请选择优惠券");
        } else {
            offers_sum.setTextColor(getResources().getColor(R.color.color_black_E6));
            offers_sum.setText("-￥" + offersSum);
        }


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
            getAddress1();
        } else {
            address.setText(addressMode.getAreaname() + addressMode.getAddress());
            name.setText(addressMode.getName());
            phone.setText(addressMode.getPhone());
        }

        if (StringUtil.isEmpty(uid)) {
            book_get2.setTextColor(getResources().getColor(R.color.color_black_4D));
            hasTy = false;
        } else {
            getAddress2(false);
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

        getOffers();
    }

    /**
     * 下单
     */
    private void addOrder() {

        if (isCart) {
            StringBuffer buffer = new StringBuffer();
            for (MyBooksMode myBooksMode : shopBooks) {
                for (MyBooksMode.ListDTO listDTO : myBooksMode.getList()) {
                    buffer.append(listDTO.getId() + ",");
                }
            }
            payMap.put("is_cart", "1");
            payMap.put("cart_id", buffer.substring(0, buffer.length() - 1));

        } else {
            MyBooksMode.ListDTO dto = shopBooks.get(0).getList().get(0);
            payMap.put("is_cart", "0");
            payMap.put("goods_id", dto.getGoodsId() + "");
            payMap.put("count", dto.getCount() + "");
        }
        payMap.put("user_id", SharedUtil.getIntence().getUid());
        payMap.put("coupon_id", couponId + "");
        payMap.put("ty", ty);
        payMap.put("type", type);
        payMap.put("addressid", addressMode.getId() + "");

        setOrder(payMap);

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
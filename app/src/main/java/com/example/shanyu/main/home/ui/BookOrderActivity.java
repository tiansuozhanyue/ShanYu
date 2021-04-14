package com.example.shanyu.main.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.main.home.adapter.OrderBooksAdapter;
import com.example.shanyu.main.home.bean.BookMode;
import com.example.shanyu.main.mine.bean.AddressMode;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.main.mine.bean.ShopBook;
import com.example.shanyu.main.mine.ui.AddressActivity;
import com.example.shanyu.utils.ArithUtil;
import com.example.shanyu.widget.CirButton;
import com.example.shanyu.widget.MyListView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookOrderActivity extends BaseActivity {

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
    private static List<ShopBook> shopBooks;
    String allSum = "0.00";        //总金额
    String offersSum = "0.00";     //优惠金额
    String finalSum = "0.00";      //合计金额

    public static void start(BaseActivity activity, AddressMode addressMode, List<ShopBook> shopBooks) {
        BookOrderActivity.addressMode = addressMode;
        BookOrderActivity.shopBooks = shopBooks;
        Intent intent = new Intent(activity, BookOrderActivity.class);
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

        updataSumView();
        updataAddressView();
        showGoods();

        payGroup.check(R.id.aliPay);
        book_get1.setSelected(true);
        book_get1_index.setSelected(true);
        goPay.setSelected(true);

    }

    @OnClick({R.id.book_get1, R.id.book_get2,
            R.id.address, R.id.address_more})
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

        }
    }

    /**
     * 计算总价
     */
    private String getAllSum() {
        if (shopBooks == null) return "0.00";

        for (ShopBook shopBook : shopBooks) {
            for (MyBooksMode booksMode : shopBook.getModes()) {
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

        String[] sum = allSum.split("\\.");

        final_sum1.setText(sum[0]);
        final_sum11.setText(sum[0]);

        final_sum2.setText("." + sum[1]);
        final_sum12.setText("." + sum[1]);

    }

    /**
     * 更新地址信息
     */
    private void updataAddressView() {
        if (addressMode == null)
            return;
        address.setText(addressMode.getAreaname() + addressMode.getAddress());
        name.setText(addressMode.getName());
        phone.setText(addressMode.getPhone());
    }

    /**
     * 显示商品列表
     */
    private void showGoods() {
        myListView.setAdapter(new OrderBooksAdapter(BookOrderActivity.this, shopBooks));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110 && resultCode == 101) {
            addressMode = (AddressMode) data.getSerializableExtra("AddressMode");
            updataAddressView();
        }
    }
}
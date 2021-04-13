package com.example.shanyu.main.home.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.androidkun.xtablayout.XTabLayout;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.mine.adapter.AddressAdapter;
import com.example.shanyu.main.mine.bean.AddressMode;
import com.example.shanyu.main.mine.ui.AddressActivity;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.CustomViewPager;

import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.main.MainPageAdapter;
import com.example.shanyu.main.home.bean.BookMode;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookInfoActivity extends BaseActivity {

    @BindView(R.id.cover)
    public ImageView cover;
    @BindView(R.id.name)
    public TextView name;
    @BindView(R.id.price)
    public TextView price;
    @BindView(R.id.price1)
    public TextView price1;
    @BindView(R.id.def_address)
    public TextView def_address;
    @BindView(R.id.book_detail)
    public TextView book_detail;
    @BindView(R.id.book_commot)
    public TextView book_commot;
    @BindView(R.id.book_detail_index)
    public TextView book_detail_index;
    @BindView(R.id.book_commot_index)
    public TextView book_commot_index;

    BookDetailFragment bookDetailFragment;
    BookCommentFragment bookCommentFragment;
    BookMode mode;
    AddressMode addressMode;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmersiveStatusBar(true);
        setContentView(R.layout.activity_book_info);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {

        mode = (BookMode) getIntent().getSerializableExtra("mode");
        ImageLoaderUtil.loadImage(HttpApi.HOST + mode.getPath(), cover);
        name.setText(mode.getGoods());
        price.setText(mode.getPreevent());
        price1.setText("￥" + mode.getPrice());
        price1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        bookDetailFragment = new BookDetailFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("details", mode.getDetails());
        bookDetailFragment.setArguments(bundle1);

        bookCommentFragment = new BookCommentFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("id", mode.getId().toString());
        bookCommentFragment.setArguments(bundle2);

        book_detail.setSelected(true);
        book_detail_index.setSelected(true);
        switchFragment(bookDetailFragment).commit();

        getAddress();
    }

    @OnClick({R.id.goback, R.id.share, R.id.shop,
            R.id.chat, R.id.buy, R.id.add,
            R.id.address_layout, R.id.book_detail, R.id.book_commot})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.goback:
                finish();
                break;

            case R.id.share:
                showShareDialog();
                break;

            case R.id.shop:
                break;

            case R.id.chat:
                break;

            case R.id.buy:
                bugBookDialog();
                break;

            case R.id.add:
                addCart();
                break;

            case R.id.address_layout:
                startActivity(new Intent(BookInfoActivity.this, AddressActivity.class));
                break;

            case R.id.book_detail:
                book_detail.setSelected(true);
                book_detail_index.setSelected(true);
                book_commot.setSelected(false);
                book_commot_index.setSelected(false);
                switchFragment(bookDetailFragment).commit();
                break;

            case R.id.book_commot:
                book_detail.setSelected(false);
                book_detail_index.setSelected(false);
                book_commot.setSelected(true);
                book_commot_index.setSelected(true);
                switchFragment(bookCommentFragment).commit();
                break;

        }
    }

    private FragmentTransaction switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.fragment_layout, targetFragment, targetFragment.getClass().getName());
        } else {
            transaction.hide(currentFragment).show(targetFragment);
        }
        currentFragment = targetFragment;
        return transaction;
    }

    /**
     * 分享弹窗
     */
    private void showShareDialog() {
        Dialog shareDialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_share, null, false);
        view.findViewById(R.id.share_weixin).setOnClickListener(v -> ToastUtil.shortToast("微信分享"));
        view.findViewById(R.id.share_weibo).setOnClickListener(v -> ToastUtil.shortToast("微博分享"));
        view.findViewById(R.id.share_pyq).setOnClickListener(v -> ToastUtil.shortToast("朋友圈分享"));
        view.findViewById(R.id.share_qq).setOnClickListener(v -> ToastUtil.shortToast("QQ分享"));
        view.findViewById(R.id.share_cancle).setOnClickListener(v -> shareDialog.dismiss());
        shareDialog.setContentView(view);
        WindowManager.LayoutParams lp = shareDialog.getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        shareDialog.getWindow().setAttributes(lp);
        shareDialog.getWindow().setBackgroundDrawable(null);
        shareDialog.show();
    }

    /**
     * 购买弹窗
     */
    private void bugBookDialog() {
        Dialog shareDialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_buy, null, false);

        ImageLoaderUtil.loadImage(HttpApi.HOST + mode.getPath(), view.findViewById(R.id.imag));

        ((TextView) view.findViewById(R.id.name)).setText(mode.getGoods());
        String[] sum = mode.getPreevent().split("\\.");
        ((TextView) view.findViewById(R.id.price1)).setText(sum[0]);
        ((TextView) view.findViewById(R.id.price2)).setText("." + sum[1]);
        if (addressMode != null) {
            ((TextView) view.findViewById(R.id.address)).setSelected(true);
            ((TextView) view.findViewById(R.id.address)).setText(addressMode.getAreaname() + addressMode.getAddress());
        }
        TextView price3 = view.findViewById(R.id.price3);
        price3.setText(mode.getPrice());
        price3.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        view.findViewById(R.id.commit).setSelected(true);
        view.findViewById(R.id.commit).setOnClickListener(v -> {
            shareDialog.dismiss();
            startActivity(new Intent(BookInfoActivity.this, BookOrderActivity.class));
        });
        view.findViewById(R.id.address_layout).setOnClickListener(v ->
                startActivity(new Intent(BookInfoActivity.this, AddressActivity.class)));
        shareDialog.setContentView(view);
        WindowManager.LayoutParams lp = shareDialog.getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        shareDialog.getWindow().setAttributes(lp);
        shareDialog.getWindow().setBackgroundDrawable(null);
        shareDialog.show();
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
                    if (mode.getIsselected() == 1)
                        addressMode = mode;
                }
                if (addressMode != null) {
                    def_address.setSelected(true);
                    def_address.setText(addressMode.getAreaname() + addressMode.getAddress());
                }

            }
        });

    }

    /**
     * 加入购物车
     */
    private void addCart() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("goods_id", mode.getId() + "");
        showLoading();
        HttpUtil.doGet(HttpApi.ADDCART, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();
                ToastUtil.shortToast("添加成功");
            }
        });

    }

}
package com.example.shanyu.main.home.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.adapter.SearchBooksAdapter;
import com.example.shanyu.main.home.bean.BookMode;
import com.example.shanyu.main.home.bean.ShopOfferBean;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopSearchActivity extends BaseActivity implements TextView.OnEditorActionListener, SearchBooksAdapter.BookOnClick {

    String searchInfo = "";
    String shop_id;
    @BindView(R.id.edit_input)
    public EditText edit_input;
    @BindView(R.id.mListView)
    public ListView mListView;
    @BindView(R.id.home)
    public TextView home;
    @BindView(R.id.news)
    public TextView news;
    @BindView(R.id.action)
    public TextView action;
    @BindView(R.id.home_line)
    public TextView home_line;
    @BindView(R.id.news_line)
    public TextView news_line;
    @BindView(R.id.action_line)
    public TextView action_line;
    @BindView(R.id.ratingbar)
    public RatingBar ratingbar;
    @BindView(R.id.mBanner)
    public Banner mBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmersiveStatusBar(true);
        setContentView(R.layout.activity_shop_search);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        shop_id = getIntent().getStringExtra("shop_id");
        edit_input.setOnEditorActionListener(this);
        home.setSelected(true);
        home_line.setSelected(true);
        getBooks(0);
        getOffers(shop_id);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
            searchInfo = edit_input.getText().toString();
            if (!StringUtil.isEmpty(searchInfo))
                searchBooks();
            return true;
        }
        return false;
    }


    @OnClick({R.id.goback, R.id.home, R.id.news, R.id.action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goback:
                finish();
                break;

            case R.id.home:
                home.setSelected(true);
                home_line.setSelected(true);
                news.setSelected(false);
                news_line.setSelected(false);
                action.setSelected(false);
                action_line.setSelected(false);
                getBooks(0);
                break;

            case R.id.news:
                home.setSelected(false);
                home_line.setSelected(false);
                news.setSelected(true);
                news_line.setSelected(true);
                action.setSelected(false);
                action_line.setSelected(false);
                getBooks(1);
                break;

            case R.id.action:
                home.setSelected(false);
                home_line.setSelected(false);
                news.setSelected(false);
                news_line.setSelected(false);
                action.setSelected(true);
                action_line.setSelected(true);
                getBooks(2);
                break;

        }
    }

    /**
     * 搜索商品
     */
    private void searchBooks() {

        Map<String, String> map = new HashMap<>();
        map.put("title", searchInfo);
        map.put("shop_id", shop_id);
        showLoading();
        HttpUtil.doGet(HttpApi.SEARCH, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
                mListView.setAdapter(null);
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();

                List<BookMode> bookModes = new Gson().fromJson(resultData, new TypeToken<List<BookMode>>() {
                }.getType());

                mListView.setAdapter(new SearchBooksAdapter(ShopSearchActivity.this, bookModes, ShopSearchActivity.this));

            }
        });

    }

    /**
     * 商铺的商品列表
     */
    private void getBooks(int ty) {

        Map<String, String> map = new HashMap<>();
        if (ty > 0)
            map.put("ty", ty + "");
        map.put("shop_id", shop_id);
        showLoading();
        HttpUtil.doGet(HttpApi.SHOPBOOKLIST, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
                mListView.setAdapter(null);
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();

                List<BookMode> bookModes = new Gson().fromJson(resultData, new TypeToken<List<BookMode>>() {
                }.getType());

                if (bookModes != null && bookModes.size() > 0) {
                    double n = Double.valueOf(bookModes.get(0).getDecimal());
                    ratingbar.setRating((int) Math.round(n));
                    mListView.setAdapter(new SearchBooksAdapter(ShopSearchActivity.this, bookModes, ShopSearchActivity.this));
                } else {
                    ratingbar.setRating(0);
                }

            }
        });

    }

    /**
     * 获取优惠券列表
     */
    private void getOffers(String shopIds) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", shopIds);
        HttpUtil.doGet(HttpApi.OFFERS_SHOP, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onSuccess(String resultData) {

                List<ShopOfferBean> offersModes = new Gson().fromJson(resultData, new TypeToken<List<ShopOfferBean>>() {
                }.getType());

                if (offersModes != null && offersModes.size() > 0) {
                    mBanner.setVisibility(View.VISIBLE);
                    IndicatorView qyIndicator = new IndicatorView(ShopSearchActivity.this)
                            .setIndicatorColor(getResources().getColor(R.color.white))
                            .setIndicatorSelectorColor(getResources().getColor(R.color.color_black_4D));
                    mBanner.setIndicator(qyIndicator)
                            .setAutoTurningTime(3000)
                            .setHolderCreator((context, index, o) -> {
                                View view = LayoutInflater.from(ShopSearchActivity.this).inflate(R.layout.layout_shop_banner, null, false);
                                TextView banner = new TextView(context);
                                ShopOfferBean offerBean = (ShopOfferBean) o;
                                banner.setText(offerBean.getExplain());
                                return view;
                            })
                            .setPages(offersModes);
                }

            }
        });

    }


    @Override
    public void onBookClick(BookMode mode) {
        Intent intent = new Intent(this, BookInfoActivity.class);
        intent.putExtra("bookModeId", mode.getId().toString());
        startActivity(intent);
    }

}
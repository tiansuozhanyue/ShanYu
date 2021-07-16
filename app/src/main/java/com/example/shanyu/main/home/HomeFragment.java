package com.example.shanyu.main.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shanyu.R;
import com.example.shanyu.base.EventBean;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.adapter.BooksAdapter;
import com.example.shanyu.main.home.adapter.CategoryAdapter;
import com.example.shanyu.main.home.bean.BannerMode;
import com.example.shanyu.main.home.bean.BookMode;
import com.example.shanyu.main.home.ui.BookInfoActivity;
import com.example.shanyu.main.home.ui.BookSearchActivity;
import com.example.shanyu.main.home.ui.BookOrderActivity;
import com.example.shanyu.main.home.ui.PaySucessActivity;
import com.example.shanyu.main.home.ui.SearchAddressActivity;
import com.example.shanyu.main.home.ui.ShopJoinActivity1;
import com.example.shanyu.main.home.ui.ShopJoinActivity2;
import com.example.shanyu.main.home.ui.ShopJoinActivity3;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.CirButton;
import com.example.shanyu.widget.MyGridView;
import com.example.shanyu.widget.MyListView;
import com.example.shanyu.widget.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements BooksAdapter.BookOnClick,
        CategoryAdapter.CategoryOnClick,
        MyRefreshLayout.RefreshListener,
        TextWatcher,
        TextView.OnEditorActionListener {

    Unbinder bind;
    int statue = -1;
    String searchInfo;

    @BindView(R.id.mBanner)
    public Banner mBanner;
    @BindView(R.id.myGridView)
    public MyGridView myGridView;
    @BindView(R.id.mListView)
    public MyListView mListView;
    @BindView(R.id.myRefreshLayout)
    public MyRefreshLayout myRefreshLayout;
    @BindView(R.id.search)
    public CirButton search;
    @BindView(R.id.edit_input)
    public EditText edit_input;
    @BindView(R.id.address)
    public TextView address;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bind = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        edit_input.addTextChangedListener(this);
        edit_input.setOnEditorActionListener(this);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        search.setSelected(true);

        address.setText(SharedUtil.getIntence().getAddress());

        getBanner();
        getShopStatue();
        myRefreshLayout.setRefreshListener(this);

    }

    @OnClick({R.id.shop_join, R.id.shop_study, R.id.search, R.id.address})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.shop_join:
                switch (statue) {
                    case -1:
                        startActivity(new Intent(getContext(), ShopJoinActivity1.class));
                        break;
                    case 0:
                        startActivity(new Intent(getContext(), ShopJoinActivity2.class));
                        break;
                    case 1:
                        startActivity(new Intent(getContext(), ShopJoinActivity3.class));
                        break;
                }
                break;

            case R.id.search:
                if (!StringUtil.isEmpty(searchInfo)) {
                    hideKeyboard(edit_input);
                    Intent intent = new Intent(getContext(), BookSearchActivity.class);
                    intent.putExtra("searchInfo", searchInfo);
                    startActivity(intent);
                    edit_input.setText("");
                }
                break;

            case R.id.address:
                startActivity(new Intent(getContext(), SearchAddressActivity.class));
                break;

            case R.id.shop_study:
                EventBus.getDefault().post(new EventBean(EventBean.GO_ACTION));
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void studentEventBus(EventBean eventBean) {
        switch (eventBean.flag) {
            case EventBean.ADDRESS:
                address.setText(eventBean.info);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }


    /**
     * 获取banner
     */
    private void getBanner() {

        HttpUtil.doGet(HttpApi.BANNER, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                myRefreshLayout.closeLoadingView();
                LogUtil.i("===>" + errorMsg);
            }

            @Override
            public void onSuccess(String resultData) {
                myRefreshLayout.closeLoadingView();
                BannerMode mode = new Gson().fromJson(resultData, BannerMode.class);

                //刷新banner
                IndicatorView qyIndicator = new IndicatorView(getContext())
                        .setIndicatorColor(getResources().getColor(R.color.white))
                        .setIndicatorSelectorColor(getResources().getColor(R.color.color_black_4D));
                mBanner.setIndicator(qyIndicator)
                        .setAutoTurningTime(3000)
                        .setHolderCreator((context, index, o) -> {
                            ImageView banner = new ImageView(context);
                            banner.setScaleType(ImageView.ScaleType.FIT_XY);
                            BannerMode.BannerDTO bannerDTO = (BannerMode.BannerDTO) o;
                            ImageLoaderUtil.loadImage((HttpApi.HOST + bannerDTO.getPicture()), banner, 13.0f);
                            banner.setOnClickListener(v -> {
                                Intent intent = new Intent(getContext(), BookInfoActivity.class);
                                intent.putExtra("bookModeId", bannerDTO.getId().toString());
                                startActivity(intent);
                            });
                            return banner;
                        })
                        .setPages(mode.getBanner());

                //设置分类
                myGridView.setAdapter(new CategoryAdapter(getContext(), mode.getCategory(), HomeFragment.this));

                //请求分类下的商品
                getBooks(mode.getCategory().get(0).getId().toString());


            }
        });

    }

    /**
     * 获取分类下的商品
     */
    private void getBooks(String id) {

        Map<String, String> map = new HashMap<>();
        map.put("category_id", id);
        map.put("longitude", SharedUtil.getIntence().getLongitude());
        map.put("latitude", SharedUtil.getIntence().getLatitude());

        HttpUtil.doGet(HttpApi.BOOKS, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                mListView.setAdapter(null);
            }

            @Override
            public void onSuccess(String resultData) {

                List<BookMode> bookModes = new Gson().fromJson(resultData, new TypeToken<List<BookMode>>() {
                }.getType());

                mListView.setAdapter(new BooksAdapter(getContext(), bookModes, HomeFragment.this));

            }
        });

    }


    /**
     * 获取商铺审核状态
     */
    private void getShopStatue() {

        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());

        HttpUtil.doPost(HttpApi.SHOP_STATUE, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                statue = -1;
            }

            @Override
            public void onSuccess(String resultData) {

                try {
                    JSONArray array = new JSONArray(resultData);
                    JSONObject object = (JSONObject) array.get(0);
                    statue = object.getInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onBookClick(BookMode mode) {
        Intent intent = new Intent(getContext(), BookInfoActivity.class);
        intent.putExtra("bookModeId", mode.getId().toString());
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String id) {
        getBooks(id);
    }

    @Override
    public void onRefresh(MyRefreshLayout refreshLayout) {
        getBanner();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        searchInfo = s.toString();
        search.setSelected(!StringUtil.isEmpty(searchInfo));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            hideKeyboard(edit_input);
            Intent intent = new Intent(getContext(), BookSearchActivity.class);
            intent.putExtra("searchInfo", searchInfo);
            startActivity(intent);
            edit_input.setText("");
            return true;
        }
        return false;
    }

    public void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
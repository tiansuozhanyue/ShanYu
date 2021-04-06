package com.example.shanyu.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shanyu.R;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.home.adapter.CategoryAdapter;
import com.example.shanyu.main.home.bean.BannerMode;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.widget.MyGridView;
import com.google.gson.Gson;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    Unbinder bind;
    CategoryAdapter mCategoryAdapter;

    @BindView(R.id.mBanner)
    public Banner mBanner;
    @BindView(R.id.myGridView)
    public MyGridView myGridView;

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
        getBanner();
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
                LogUtil.i("===>" + errorMsg);
            }

            @Override
            public void onSuccess(String resultData) {

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
                            banner.setOnClickListener(v -> Toast.makeText(context, index + "", Toast.LENGTH_LONG).show());
                            return banner;
                        })
                        .setPages(mode.getBanner());

                //刷新书籍分类
                List<String> names = new ArrayList<>();
                names.add("语文");
                names.add("数学");
                names.add("音乐");
                names.add("文学");
                names.add("英语");
                names.add("政治");
                mCategoryAdapter = new CategoryAdapter(getContext(), names, p -> {

                });
                myGridView.setAdapter(mCategoryAdapter);

            }
        });

    }

}
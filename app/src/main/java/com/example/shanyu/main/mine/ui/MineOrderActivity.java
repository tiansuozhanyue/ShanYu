package com.example.shanyu.main.mine.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.main.MainPageAdapter;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineOrderActivity extends BaseActivity {

    private int index;

    @BindView(R.id.tabLayout)
    public TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    public ViewPager mViewPager;

    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_order, "我的订单");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {

        index = getIntent().getIntExtra("index", 0);

        String[] tvTabs = {"全部", "待付款", "待发货", "待自提", "待收货", "待评价"};

        fragmentList.add(new MineOrderFragment0());
        fragmentList.add(new MineOrderFragment1());
        fragmentList.add(new MineOrderFragment2());
        fragmentList.add(new MineOrderFragment3());
        fragmentList.add(new MineOrderFragment4());
        fragmentList.add(new MineOrderFragment5());

        mViewPager.setAdapter(new MainPageAdapter(getSupportFragmentManager(), fragmentList));
        mViewPager.setCurrentItem(index);

        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < fragmentList.size(); i++) {

            View view = LayoutInflater.from(this).inflate(R.layout.mine_tab_item, null, false);
            TextView tvTab = view.findViewById(R.id.tvTab);

            tvTab.setText(tvTabs[i]);

            if (i == index) {
                tvTab.setSelected(true);
            }

            mTabLayout.getTabAt(i).setCustomView(view);

        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                TextView tv = customView.findViewById(R.id.tvTab);
                tv.setSelected(true);
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                TextView tv = customView.findViewById(R.id.tvTab);
                tv.setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
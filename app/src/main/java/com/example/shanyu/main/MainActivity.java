package com.example.shanyu.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.main.action.ActionFragment;
import com.example.shanyu.main.chat.ChatFragment;
import com.example.shanyu.main.home.HomeFragment;
import com.example.shanyu.main.mine.MineFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> fragmentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTranslucentStatus();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main, true);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        initBottomNav();

    }

    //底部导航
    private void initBottomNav() {

        int[] ivTabs = {
                R.drawable.tab_bg_home,
                R.drawable.tab_bg_activity,
                R.drawable.tab_bg_chat,
                R.drawable.tab_bg_me};

        String[] tvTabs = {
                getResources().getString(R.string.main_home),
                getResources().getString(R.string.main_action),
                getResources().getString(R.string.main_chat),
                getResources().getString(R.string.main_mine)
        };

        fragmentList.add(new HomeFragment());
        fragmentList.add(new ActionFragment());
        fragmentList.add(new ChatFragment());
        fragmentList.add(new MineFragment());

        viewPager.setAdapter(new MainPageAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setCurrentItem(0);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setSelectedTabIndicatorHeight(0);

        for (int i = 0; i < fragmentList.size(); i++) {

            View view = LayoutInflater.from(this).inflate(R.layout.main_tab_item, null, false);
            ImageView ivTab = view.findViewById(R.id.ivTab);
            TextView tvTab = view.findViewById(R.id.tvTab);

            ivTab.setBackgroundResource(ivTabs[i]);
            tvTab.setText(tvTabs[i]);

            if (i == 0) {
                ivTab.setSelected(true);
                tvTab.setSelected(true);
            }

            tabLayout.getTabAt(i).setCustomView(view);

        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                ImageView iv = customView.findViewById(R.id.ivTab);
                TextView tv = customView.findViewById(R.id.tvTab);
                iv.setSelected(true);
                tv.setSelected(true);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                ImageView iv = customView.findViewById(R.id.ivTab);
                TextView tv = customView.findViewById(R.id.tvTab);
                iv.setSelected(false);
                tv.setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

}
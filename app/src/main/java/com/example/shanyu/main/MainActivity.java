package com.example.shanyu.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.login.LoginActivity;
import com.example.shanyu.main.action.ActionFragment;
import com.example.shanyu.main.chat.ChatFragment;
import com.example.shanyu.main.home.HomeFragment;
import com.example.shanyu.main.mine.MineFragment;
import com.example.shanyu.utils.ToastUtil;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> fragmentList = new ArrayList<>();
    long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTranslucentStatus();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main, true);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        initBottomNav();

    }

    @Override
    public void initView() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 102) {//退出登录
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            fragmentList.get(viewPager.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                ToastUtil.shortToast("再按一次退出程序");
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
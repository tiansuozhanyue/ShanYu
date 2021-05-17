package com.example.shanyu.main;

import android.Manifest;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.base.EventBean;
import com.example.shanyu.login.LoginActivity;
import com.example.shanyu.main.action.ActionFragment;
import com.example.shanyu.main.chat.ChatFragment;
import com.example.shanyu.main.home.HomeFragment;
import com.example.shanyu.main.mine.MineFragment;
import com.example.shanyu.main.mine.ui.PersionInfoActivity;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.utils.MPermissionUtils;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.ToastUtil;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AMapLocationListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> fragmentList = new ArrayList<>();
    long mExitTime;
    int REQUESTCODE = 1;

    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
//    private OnLocationChangedListener mListener = null;//定位监听器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTranslucentStatus();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main, true);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        initView();

    }

    @Override
    public void initView() {
        initBottomNav();
        getLation();
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

    /**
     * 选择头像
     */
    private void getLation() {

        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        MPermissionUtils.requestPermissionsResult(this, REQUESTCODE, permissions, new MPermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                initLoc();
            }

            @Override
            public void onPermissionDenied() {
                ToastUtil.shortToast("获取权限失败");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUESTCODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initLoc();
            } else {
                MPermissionUtils.showTipsDialog(this);
            }
        }
    }

    /**
     * 定位
     */
    private void initLoc() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(20 * 1000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
//                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
//                amapLocation.getAccuracy();//获取精度信息
//                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                amapLocation.getCountry();//国家信息
//                amapLocation.getProvince();//省信息
//                amapLocation.getCityCode();//城市编码
//                amapLocation.getAdCode();//地区编码

                String district = amapLocation.getDistrict();//城区信息
                String street = amapLocation.getStreet();//街道信息
                String streetNum = amapLocation.getStreetNum();//街道门牌号信息
                String city = amapLocation.getCity();//城市信息
                double latitude = amapLocation.getLatitude();//获取纬度
                double longitude = amapLocation.getLongitude();//获取经度

                SharedUtil.getIntence().setCity(city);
                SharedUtil.getIntence().setLatitude(latitude+"");
                SharedUtil.getIntence().setLongitude(longitude+"");

                EventBus.getDefault().post(new EventBean(EventBean.ADDRESS, district + street + streetNum));


            } else {
                LogUtil.i("====>定位失败：");
            }
        }
    }

}
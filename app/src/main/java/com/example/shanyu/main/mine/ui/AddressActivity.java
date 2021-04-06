package com.example.shanyu.main.mine.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.action.ActionMode;
import com.example.shanyu.main.home.adapter.CategoryAdapter;
import com.example.shanyu.main.home.bean.BannerMode;
import com.example.shanyu.main.mine.adapter.AddressAdapter;
import com.example.shanyu.main.mine.bean.AddressMode;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.widget.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.to.aboomy.banner.IndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressActivity extends BaseActivity {

    AddressAdapter mAddressAdapter;

    @BindView(R.id.myRefreshLayout)
    public MyRefreshLayout myRefreshLayout;

    @BindView(R.id.mListView)
    public ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address, "收货地址", "管理", v -> {
            if (mAddressAdapter != null)
                mAddressAdapter.exchangeStyle();
        });
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        getAddress();
    }

    private void getAddress() {

        List<AddressMode> addressModes = new ArrayList<>();
        AddressMode mode1 = new AddressMode();
        mode1.setAddress("杭州市西湖区教工路198号浙商大创业园二楼浙商大创业园二楼");
        mode1.setIsselected(1);
        mode1.setName("李四");
        mode1.setPhone("18868026919");

        AddressMode mode2 = new AddressMode();
        mode2.setAddress("杭州市西湖区教工路198号浙商大创业园二楼浙商大创业园二楼");
        mode2.setIsselected(0);
        mode2.setName("张三");
        mode2.setPhone("13407389414");

        addressModes.add(mode1);
        addressModes.add(mode2);

        mAddressAdapter = new AddressAdapter(AddressActivity.this, addressModes);
        mListView.setAdapter(mAddressAdapter);

//        Map<String, String> map = new HashMap<>();
//        map.put("uid", "1");
//        HttpUtil.doPost(HttpApi.ADDRESS, map, new HttpResultInterface() {
//            @Override
//            public void onFailure(String errorMsg) {
//                LogUtil.i("===>" + errorMsg);
//            }
//
//            @Override
//            public void onSuccess(String resultData) {
//
//                List<AddressMode> addressModes = new Gson().fromJson(resultData, new TypeToken<List<AddressMode>>() {
//                }.getType());
//
//                mAddressAdapter = new AddressAdapter(AddressActivity.this, addressModes);
//                mListView.setAdapter(mAddressAdapter);
//
//            }
//        });

    }

}
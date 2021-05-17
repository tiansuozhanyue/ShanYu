package com.example.shanyu.main.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.mine.ui.SelectAddressActivity;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.slider.SortModel;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopJoinActivity1 extends BaseActivity {

    SortModel model0, model1, model2;
    String name, phone, code, area;
    @BindView(R.id.area0)
    public TextView area0;
    @BindView(R.id.area1)
    public TextView area1;
    @BindView(R.id.area2)
    public TextView area2;
    @BindView(R.id.edit_name)
    public TextView edit_name;
    @BindView(R.id.edit_phone)
    public TextView edit_phone;
    @BindView(R.id.edit_code)
    public TextView edit_code;
    @BindView(R.id.edit_are)
    public TextView edit_are;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_join, "店铺入驻");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                name = edit_name.getText().toString();
                phone = edit_phone.getText().toString();
                code = edit_code.getText().toString();
                area = edit_are.getText().toString();

            }
        };

        edit_name.addTextChangedListener(watcher);
        edit_phone.addTextChangedListener(watcher);
        edit_code.addTextChangedListener(watcher);
        edit_are.addTextChangedListener(watcher);

    }

    @OnClick({R.id.area0, R.id.area1, R.id.area2})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.area0:
                startActivityForResult(new Intent(ShopJoinActivity1.this, SelectAddressActivity.class).putExtra("key", 0), 10);
                break;
            case R.id.area1:
                if (model0 == null) {
                    ToastUtil.shortToast("请先选择省");
                } else {
                    startActivityForResult(new Intent(ShopJoinActivity1.this, SelectAddressActivity.class).putExtra("key", model0.getKey()), 11);
                }

                break;
            case R.id.area2:
                if (model1 == null) {
                    ToastUtil.shortToast("请先选择市");
                } else {
                    startActivityForResult(new Intent(ShopJoinActivity1.this, SelectAddressActivity.class).putExtra("key", model1.getKey()), 12);
                }
                break;
        }
    }


    /**
     * 获取商铺审核申请
     */
    private void shopAdd() {

        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("name", name);
        map.put("phone", phone);
        map.put("certificates", "");
        map.put("picture", "");
        map.put("latitude", "");
        map.put("longitude", "");
        map.put("province", "");
        map.put("city", "");
        map.put("area", "");
        map.put("address", "");

        HttpUtil.doPost(HttpApi.SHOP_ADD, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onSuccess(String resultData) {


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 100) {
            model0 = (SortModel) data.getSerializableExtra("model");
            area0.setText(model0.getName());
            area0.setSelected(true);
            model1 = null;
            area1.setText("请选择市");
            area1.setSelected(false);
            model2 = null;
            area2.setText("请选择区");
            area2.setSelected(false);

        } else if (requestCode == 11 && resultCode == 100) {
            model1 = (SortModel) data.getSerializableExtra("model");
            area1.setText(model1.getName());
            area1.setSelected(true);
            model2 = null;
            area2.setText("请选择区");
            area2.setSelected(false);

        } else if (requestCode == 12 && resultCode == 100) {
            model2 = (SortModel) data.getSerializableExtra("model");
            area2.setText(model2.getName());
            area2.setSelected(true);

        }

    }

}
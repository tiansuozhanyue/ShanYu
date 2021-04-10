package com.example.shanyu.main.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.mine.adapter.OfferAdapter;
import com.example.shanyu.main.mine.bean.OffersMode;
import com.example.shanyu.main.mine.bean.PositionMode;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.CirButton;
import com.example.shanyu.widget.slider.SortModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetAddressActivity extends BaseActivity {

    SortModel model0, model1, model2;

    String nameText, phoneText, address, province, city, area;

    @BindView(R.id.name)
    public EditText name;
    @BindView(R.id.phone)
    public EditText phone;
    @BindView(R.id.area0)
    public TextView area0;
    @BindView(R.id.area1)
    public TextView area1;
    @BindView(R.id.area2)
    public TextView area2;
    @BindView(R.id.area3)
    public EditText area3;
    @BindView(R.id.commit)
    public CirButton commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_address, "新增收货地址");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {

        TextWatcher mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nameText = name.getText().toString();
                phoneText = phone.getText().toString();
                address = area3.getText().toString();
                setCommitStatue();
            }
        };

        name.addTextChangedListener(mTextWatcher);
        phone.addTextChangedListener(mTextWatcher);
        area3.addTextChangedListener(mTextWatcher);

    }

    @OnClick({R.id.area0, R.id.area1, R.id.area2, R.id.commit})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.area0:
                startActivityForResult(new Intent(SetAddressActivity.this, SelectAddressActivity.class).putExtra("key", 0), 10);
                break;
            case R.id.area1:
                if (model0 == null) {
                    ToastUtil.shortToast("请先选择省");
                } else {
                    startActivityForResult(new Intent(SetAddressActivity.this, SelectAddressActivity.class).putExtra("key", model0.getKey()), 11);
                }

                break;
            case R.id.area2:
                if (model1 == null) {
                    ToastUtil.shortToast("请先选择市");
                } else {
                    startActivityForResult(new Intent(SetAddressActivity.this, SelectAddressActivity.class).putExtra("key", model1.getKey()), 12);
                }
                break;
            case R.id.commit:
                if (commit.isSelected())
                    setAddress();
                break;
        }
    }

    private void setAddress() {
        Map<String, String> map = new HashMap<>();
        map.put("name", nameText);
        map.put("phone", phoneText);
        map.put("address", address);
        map.put("province", model0.getKey().toString());
        map.put("city", model1.getKey().toString());
        map.put("area", model2.getKey().toString());
        map.put("uid", SharedUtil.getIntence().getUid());
        showLoading();
        HttpUtil.doPost(HttpApi.ADD, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                ToastUtil.shortToast(errorMsg);
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();
                ToastUtil.shortToast("添加成功");
                setResult(200);
                finish();
            }
        });

    }

    private void setCommitStatue() {
        if (!StringUtil.isEmpty(nameText) &&
                !StringUtil.isEmpty(address) &&
                StringUtil.isPhoneNumber(phoneText) &&
                model0 != null && model1 != null && model2 != null) {

            commit.setSelected(true);

        } else {
            commit.setSelected(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

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
            setCommitStatue();
        } else if (requestCode == 11 && resultCode == 100) {
            model1 = (SortModel) data.getSerializableExtra("model");
            area1.setText(model1.getName());
            area1.setSelected(true);
            model2 = null;
            area2.setText("请选择区");
            area2.setSelected(false);
            setCommitStatue();
        } else if (requestCode == 12 && resultCode == 100) {
            model2 = (SortModel) data.getSerializableExtra("model");
            area2.setText(model2.getName());
            area2.setSelected(true);
            setCommitStatue();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
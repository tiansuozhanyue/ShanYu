package com.example.shanyu.main.mine.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.mine.adapter.SortAdapter;
import com.example.shanyu.main.mine.bean.PositionMode;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.slider.ClearEditText;
import com.example.shanyu.widget.slider.PinyinComparator;
import com.example.shanyu.widget.slider.PinyinUtils;
import com.example.shanyu.widget.slider.SideBar;
import com.example.shanyu.widget.slider.SortModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectAddressActivity extends BaseActivity implements SideBar.OnTouchingLetterChangedListener, TextWatcher, SortAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private SideBar sideBar;
    private SortAdapter adapter;
    private EditText mClearEditText;
    private LinearLayoutManager manager;
    private List<SortModel> SourceDateList;
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmersiveStatusBar(true);
        setContentView(R.layout.activity_select_address);
        initView();
    }

    private void getCity(String bank_province) {
        Map<String, String> map = new HashMap<>();
        map.put("bank_province", bank_province);
        showLoading();
        HttpUtil.doGet(HttpApi.POSITION, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                ToastUtil.shortToast(errorMsg);
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();
                List<PositionMode> positionModes = new Gson().fromJson(resultData, new TypeToken<List<PositionMode>>() {
                }.getType());

                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                SourceDateList = filledData(positionModes);

                // ??????a-z?????????????????????
                Collections.sort(SourceDateList, pinyinComparator);

                //RecyclerView??????manager
                manager = new LinearLayoutManager(SelectAddressActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(manager);
                adapter = new SortAdapter(SelectAddressActivity.this, SourceDateList);
                adapter.setOnItemClickListener(SelectAddressActivity.this);
                mRecyclerView.setAdapter(adapter);

            }
        });
    }

    @Override
    public void initView() {

        pinyinComparator = new PinyinComparator();
        mClearEditText = findViewById(R.id.filter_edit);
        sideBar = (SideBar) findViewById(R.id.sideBar);

        findViewById(R.id.goback).setOnClickListener(v -> finish());

        //????????????SideBar????????????
        sideBar.setOnTouchingLetterChangedListener(this);
        //????????????????????????????????????????????????
        mClearEditText.addTextChangedListener(this);

        getCity(getIntent().getIntExtra("key", 0) + "");

    }

    /**
     * ???RecyclerView????????????
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<PositionMode> date) {
        List<SortModel> mSortList = new ArrayList<>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i).getValue());
            sortModel.setKey(date.get(i).getKey());
            //?????????????????????
            String pinyin = PinyinUtils.getPingYin(date.get(i).getValue());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // ??????????????????????????????????????????????????????
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * ????????????????????????????????????????????????RecyclerView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 ||
                        PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                        //??????????????????
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString())
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
                ) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // ??????a-z????????????
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateList(filterDateList);
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        //??????????????????????????????
        int position = adapter.getPositionForSection(s.charAt(0));
        if (position != -1) {
            manager.scrollToPositionWithOffset(position, 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        filterData(s.toString());
    }

    @Override
    public void onItemClick(SortModel model) {
        Intent intent = new Intent();
        intent.putExtra("model", model);
        setResult(100, intent);
        finish();
    }
}
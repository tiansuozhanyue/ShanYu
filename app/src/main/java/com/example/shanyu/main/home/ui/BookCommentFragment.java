package com.example.shanyu.main.home.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shanyu.R;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

public class BookCommentFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_comment_fragment, container, false);
        initView();
        return view;
    }

    private void initView() {
        String id = getArguments().getString("id");
        addCart(id);
    }

    /**
     * 获取评论列表
     */
    private void addCart(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpUtil.doGet(HttpApi.EVALUATE, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onSuccess(String resultData) {
            }
        });

    }

}
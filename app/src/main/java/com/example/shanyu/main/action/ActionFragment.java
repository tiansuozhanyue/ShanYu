package com.example.shanyu.main.action;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shanyu.MyApplication;
import com.example.shanyu.R;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.utils.LogUtil;


public class ActionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_action, container, false);
        initView();
        return view;
    }


    private void initView() {
        getBanner();
    }


    /**
     * 获取banner
     */
    private void getBanner() {

        HttpUtil.doGet(HttpApi.BANNER, new HttpResultInterface<String>() {
            @Override
            public void onFailure(String errorMsg) {
                LogUtil.i("===>" + errorMsg);
            }

            @Override
            public void onSuccess(String resultData) {
                LogUtil.i("===>" + resultData);
            }
        });

    }

}
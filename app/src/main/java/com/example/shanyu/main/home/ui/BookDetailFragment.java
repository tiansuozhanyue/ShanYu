package com.example.shanyu.main.home.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.shanyu.R;

public class BookDetailFragment extends Fragment {

    private WebView mWebView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_detail_fragment, container, false);
        mWebView = view.findViewById(R.id.mWebView);
        initView();
        return view;
    }


    private void initView() {

        String info = getArguments().getString("details");

        //设置字符编码，避免乱码
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.loadDataWithBaseURL(null, info, "text/html", "utf-8", null);
    }


}
package com.example.shanyu.main.action;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.shanyu.R;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ActionFragment extends Fragment implements ActionAdapter.ActionOnClick {

    Unbinder bind;

    @BindView(R.id.mListView)
    public ListView mListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_action, container, false);
        bind = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    private void initView() {
        getActions();
    }


    /**
     * 获取banner
     */
    private void getActions() {

        HttpUtil.doGet(HttpApi.ACTION, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                LogUtil.i("===>" + errorMsg);
            }

            @Override
            public void onSuccess(String resultData) {
                List<ActionMode> actionModes = new Gson().fromJson(resultData, new TypeToken<List<ActionMode>>() {
                }.getType());
                mListView.setAdapter(new ActionAdapter(getContext(), actionModes, ActionFragment.this));
            }
        });

    }

    @Override
    public void onActionClick(int p) {

    }
}
package com.example.shanyu.main.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shanyu.R;
import com.example.shanyu.main.mine.ui.AddressActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MineFragment extends Fragment {

    Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.mine_set, R.id.set_address})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.mine_set:
                startActivity(new Intent(getContext(), SetingActivity.class));
                break;
            case R.id.set_address:
                startActivity(new Intent(getContext(), AddressActivity.class));
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除绑定
        bind.unbind();
    }


}
package com.example.shanyu.main.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shanyu.R;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.login.LoginActivity;
import com.example.shanyu.main.MainActivity;
import com.example.shanyu.main.mine.bean.UserMode;
import com.example.shanyu.main.mine.ui.AddressActivity;
import com.example.shanyu.main.mine.ui.AdviceActivity;
import com.example.shanyu.main.mine.ui.CollectionActivity;
import com.example.shanyu.main.mine.ui.FootActivity;
import com.example.shanyu.main.mine.ui.MineOrderActivity;
import com.example.shanyu.main.mine.ui.MyBooksActivity;
import com.example.shanyu.main.mine.ui.OffersActivity;
import com.example.shanyu.main.mine.ui.PersionInfoActivity;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.RoundImageView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MineFragment extends Fragment {

    Unbinder bind;
    UserMode mUserMode;

    @BindView(R.id.user_name)
    public TextView user_name;
    @BindView(R.id.user_sign)
    public TextView user_sign;
    @BindView(R.id.user_img)
    public RoundImageView user_img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        bind = ButterKnife.bind(this, view);
        getUserInfo();
        return view;
    }

    @OnClick({R.id.mine_set, R.id.set_address, R.id.mine_foot,
            R.id.user_img, R.id.user_name, R.id.user_sign,
            R.id.mine_order0, R.id.mine_order1, R.id.mine_order2,
            R.id.mine_order3, R.id.mine_order4, R.id.mine_order5,
            R.id.mine_advice, R.id.mine_offers, R.id.mine_mybooks,
            R.id.mine_collection
    })
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.mine_set:
                startActivityForResult(new Intent(getContext(), SetingActivity.class), 101);
                break;
            case R.id.mine_mybooks:
                startActivity(new Intent(getContext(), MyBooksActivity.class));
                break;
            case R.id.set_address:
                startActivity(new Intent(getContext(), AddressActivity.class));
                break;
            case R.id.mine_foot:
                startActivity(new Intent(getContext(), FootActivity.class));
                break;
            case R.id.mine_offers:
                startActivity(new Intent(getContext(), OffersActivity.class));
                break;
            case R.id.mine_advice:
                startActivity(new Intent(getContext(), AdviceActivity.class).putExtra("action", "advice"));
                break;
            case R.id.user_img:
            case R.id.user_name:
            case R.id.user_sign:
                Intent intent = new Intent(getContext(), PersionInfoActivity.class);
                intent.putExtra("avatar", mUserMode.getAvatar());
                intent.putExtra("nickname", mUserMode.getNickname());
                intent.putExtra("autograph", mUserMode.getAutograph());
                startActivityForResult(intent, 10);
                break;
            case R.id.mine_order0:
                startActivity(new Intent(getContext(), MineOrderActivity.class).putExtra("index", 0));
                break;
            case R.id.mine_order1:
                startActivity(new Intent(getContext(), MineOrderActivity.class).putExtra("index", 1));
                break;
            case R.id.mine_order2:
                startActivity(new Intent(getContext(), MineOrderActivity.class).putExtra("index", 2));
                break;
            case R.id.mine_order3:
                startActivity(new Intent(getContext(), MineOrderActivity.class).putExtra("index", 3));
                break;
            case R.id.mine_order4:
                startActivity(new Intent(getContext(), MineOrderActivity.class).putExtra("index", 4));
                break;
            case R.id.mine_order5:
                startActivity(new Intent(getContext(), MineOrderActivity.class).putExtra("index", 5));
                break;
            case R.id.mine_collection:
                startActivity(new Intent(getContext(), CollectionActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除绑定
        bind.unbind();
    }

    /**
     * 获取个人信息
     */
    private void getUserInfo() {

        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());

        HttpUtil.doGet(HttpApi.SET, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onSuccess(String t) {

                mUserMode = new Gson().fromJson(t, UserMode.class);

                if (!StringUtil.isEmpty(mUserMode.getAvatar()))
                    ImageLoaderUtil.loadImage(mUserMode.getAvatar(), user_img);

                if (!StringUtil.isEmpty(mUserMode.getNickname()))
                    user_name.setText(mUserMode.getNickname());

                if (!StringUtil.isEmpty(mUserMode.getAutograph()))
                    user_sign.setText(mUserMode.getAutograph());

                SharedUtil.getIntence().setMessage(mUserMode.getIsmessage());

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            getUserInfo();
        }

    }
}
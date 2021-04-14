package com.example.shanyu.main.mine.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.mine.adapter.FootAdapter;
import com.example.shanyu.main.mine.adapter.MyBooksAdapter;
import com.example.shanyu.main.mine.bean.FootMode;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.main.mine.bean.ShopBook;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.ToastUtil;
import com.example.shanyu.widget.CirButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBooksActivity extends BaseActivity {

    @BindView(R.id.mListView)
    public ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books, "我的书包", "管理", v -> {
//            if (mAddressAdapter != null) {
//                isEditStyle = !isEditStyle;
//                rightView.setText(isEditStyle ? "完成" : "管理");
//                mAddressAdapter.exchangeStyle(isEditStyle);
//                add_address.setVisibility(isEditStyle ? View.GONE : View.VISIBLE);
//            }
        });
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        getBookss();
    }

    private void getBookss() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        showLoading();
        HttpUtil.doGet(HttpApi.CARTLIST, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                ToastUtil.shortToast(errorMsg);
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();

                try {
                    List<MyBooksMode> actionModes = new Gson().fromJson(resultData, new TypeToken<List<MyBooksMode>>() {
                    }.getType());

                    List<ShopBook> shopBooks = new ArrayList<>();

                    for (int i = 0; i < actionModes.size(); i++) {
                        MyBooksMode myBooksMode = actionModes.get(i);
                        if (shopBooks.size() == 0) {
                            ShopBook shopBook = new ShopBook(myBooksMode.getName(), myBooksMode.getShopId(), myBooksMode);
                            shopBooks.add(shopBook);
                        } else {
                            ShopBook shopBook = shopBooks.get(shopBooks.size() - 1);
                            if (shopBook.getShopId() == myBooksMode.getShopId()) {
                                shopBook.add(myBooksMode);
                            } else {
                                ShopBook shopBook1 = new ShopBook(myBooksMode.getName(), myBooksMode.getShopId(), myBooksMode);
                                shopBooks.add(shopBook1);
                            }
                        }
                    }

                    mListView.setAdapter(new MyBooksAdapter(MyBooksActivity.this, shopBooks));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

}
package com.example.shanyu.main.mine.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.mine.bean.LogiBean;
import com.example.shanyu.main.mine.bean.OrderBookBean;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogisticsActivity extends BaseActivity implements HttpResultInterface {

    private OrderBookBean orderBookBean;
    @BindView(R.id.statue)
    public TextView statue;
    @BindView(R.id.name)
    public TextView name;
    @BindView(R.id.code)
    public TextView code;
    @BindView(R.id.cover)
    public ImageView cover;
    @BindView(R.id.mListView)
    public ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics, "查看物流");
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {

        orderBookBean = (OrderBookBean) getIntent().getSerializableExtra("OrderBookBean");

        String numbers = orderBookBean.getNumbers();
        String logistics = orderBookBean.getLogistics();

        ImageLoaderUtil.loadImage(orderBookBean.getCovers(), cover);
        name.setText(orderBookBean.getLoginame());
        code.setText(numbers);

        Map<String, String> map = new TreeMap<>();
        map.put("type", logistics);
        map.put("postid", numbers);
        String url = "http://www.kuaidi100.com/query";

        HttpUtil.doGet(url, map, this);

    }

    @Override
    public void onFailure(String errorMsg) {

    }

    @Override
    public void onSuccess(String t) {

        statue.setText("运输中");
        List<LogiBean> logiBeans = new Gson().fromJson(t, new TypeToken<List<LogiBean>>() {
        }.getType());

        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return logiBeans.size();
            }

            @Override
            public Object getItem(int position) {
                return logiBeans.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.adapter_logi_item, parent, false);
                LogiBean logiBean = logiBeans.get(position);
                TextView name = view.findViewById(R.id.name);
                TextView time = view.findViewById(R.id.time);

                if(position==0)
                    name.setTextColor(getResources().getColor(R.color.color_black_E6));

                name.setText(logiBean.getContext());
                time.setText(logiBean.getFtime());

                return view;
            }
        });

    }

}
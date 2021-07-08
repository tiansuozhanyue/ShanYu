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
import com.example.shanyu.utils.SignUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

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


        try {

            String customer = "2A101BC1803144E410DB27595A079165";
            String key = "WklQRXPm8389";

            JSONObject object = new JSONObject();
            object.put("com", logistics);
            object.put("num", numbers);
            object.put("from", "");
            object.put("phone", "");
            object.put("to", "");
            object.put("to", "");
            object.put("resultv2", "0");
            object.put("show", "0");
            object.put("order", "desc");

            Map<String, String> map = new TreeMap<>();
            map.put("customer", customer);
            map.put("param", object.toString());
            map.put("sign", SignUtil.querySign(object.toString(), key, customer));

            String url = "http://poll.kuaidi100.com/poll/query.do";
            HttpUtil.doGet(url, map, this);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onFailure(String errorMsg) {

    }

    @Override
    public void onSuccess(String t) {

        try {

            JSONObject object = new JSONObject(t);
            //0在途，1揽收，2疑难，3签收，4退签，5派件，6退回，7转单，10待清关，11清关中，12已清关，13清关异常，14收件人拒签等13个状态
            switch (object.getString("state")) {
                case "0":
                    statue.setText("运输中");
                    break;
                case "1":
                    statue.setText("已揽件");
                    break;
                case "2":
                    break;
                case "3":
                    statue.setText("已签收");
                    break;
                case "4":
                    statue.setText("已退签");
                    break;
                case "5":
                    statue.setText("派件zhong");
                    break;
                case "6":
                    statue.setText("已退回");
                    break;
                case "7":
                    statue.setText("已转单");
                    break;
                case "10":
                    statue.setText("待清关");
                    break;
                case "11":
                    statue.setText("清关中");
                    break;
                case "13":
                    statue.setText("清关异常");
                    break;
                case "14":
                    statue.setText("已拒收");
                    break;
            }

            List<LogiBean> logiBeans = new Gson().fromJson(object.getString("data"), new TypeToken<List<LogiBean>>() {
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

                    if (position == 0)
                        name.setTextColor(getResources().getColor(R.color.color_black_E6));

                    name.setText(logiBean.getContext());
                    time.setText(logiBean.getFtime());

                    return view;
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
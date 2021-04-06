package com.example.shanyu.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import com.example.shanyu.MyApplication;
import com.example.shanyu.utils.LogUtil;
import com.example.shanyu.utils.StringUtil;
import com.example.shanyu.utils.ToastUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * author: heroCat
 * time  : 2018/5/25/025.
 * desc  :
 */
public class HttpUtil {
    private static Handler mhander = new Handler();

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        return NetConfig.createOkHttp(builder);
    }


    public static void doPost(String url, Map<String, String> map, HttpResultInterface<T> resultInterface) {

        if (!isNetworkAvailable()) {
            ToastUtil.shortToast("网络异常，请稍后重试！");
            return;
        }

        if (map == null)
            return;

        //构建表单参数
        FormBody.Builder requestBuild = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            requestBuild.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = requestBuild.build();

        final Request request = new Request.Builder()
                .url(url.trim())
                .header("Content-Type", "application/json;charset=utf-8")
                .post(requestBody)
                .build();
        Call call = getOkHttpClient().newCall(request);
        call.enqueue(getCallback(resultInterface));

    }

    public static void doGet(String url, HttpResultInterface<String> resultInterface) {

        if (!isNetworkAvailable()) {
            ToastUtil.shortToast("网络异常，请稍后重试！");
            return;
        }

        final Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/json;charset=utf-8")
                .get()
                .build();
        Call call = getOkHttpClient().newCall(request);
        call.enqueue(getCallback(resultInterface));

    }

    private static Callback getCallback(final HttpResultInterface<T> resultInterface) {

        Callback myCallback = new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mhander.post(new Runnable() {
                    @Override
                    public void run() {
                        if (resultInterface != null)
                            resultInterface.onFailure(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                try {
                    JSONObject object = new JSONObject(data);
                    int status = object.getInt("status");
                    String result = object.getString("result");
                    if (status == 1) {
                        T t = new Gson().fromJson(result, T.class);
                        resultInterface.onSuccess(t);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        return myCallback;
    }

    /**
     * 检查网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null)
            return false;

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        return !(networkinfo == null || !networkinfo.isAvailable());

    }

}

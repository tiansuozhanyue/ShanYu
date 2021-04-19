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
import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
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

    private static String session;
    private static Handler mhander = new Handler();

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        return NetConfig.createOkHttp(builder);
    }


    public static void doPost(String url, Map<String, String> map, HttpResultInterface resultInterface) {

        JSONObject json = new JSONObject(map);
        LogUtil.net_i("====>doPost : " + url + " >>> " + json);

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

        Request.Builder builder = new Request.Builder()
                .url(url.trim())
                .header("Content-Type", "application/json;charset=utf-8")
                .post(requestBody);

        if (!StringUtil.isEmpty(session))
            builder.addHeader("cookie", session);

        Call call = getOkHttpClient().newCall(builder.build());
        call.enqueue(getCallback(resultInterface));

    }

    public static void doGet(String url, HttpResultInterface resultInterface) {
        doGet(url, null, resultInterface);
    }

    public static void doGet(String url, Map<String, String> map, HttpResultInterface resultInterface) {


        if (!isNetworkAvailable()) {
            ToastUtil.shortToast("网络异常，请稍后重试！");
            return;
        }

        String newUrl = "";
        if (map == null) {
            newUrl = url;
        } else {
            newUrl = url + "?";
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                newUrl = newUrl + key + "=" + value + "&";
            }
        }

        LogUtil.net_i("====>doGet : " + newUrl);

        Request.Builder builder = new Request.Builder()
                .url(newUrl.trim())
                .header("Content-Type", "application/json;charset=utf-8")
                .get();

        if (!StringUtil.isEmpty(session))
            builder.addHeader("cookie", session);

        Call call = getOkHttpClient().newCall(builder.build());
        call.enqueue(getCallback(resultInterface));

    }

    public static void upload(File file, HttpResultInterface resultInterface) {

        if (!isNetworkAvailable()) {
            ToastUtil.shortToast("网络异常，请稍后重试！");
            if (resultInterface != null)
                resultInterface.onFailure("网络异常......");
            return;
        }

        if (file == null)
            return;

        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
        final Request request = new Request.Builder()
                .url(HttpApi.UPLOAD)
                .header("Content-Type", "application/json")
                .post(body)
                .build();
        Call call = getOkHttpClient().newCall(request);
        call.enqueue(getCallback(resultInterface));

    }

    private static Callback getCallback(final HttpResultInterface resultInterface) {

        Callback myCallback = new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mhander.post(() -> {
                    if (resultInterface != null) {
                        resultInterface.onFailure(e.getMessage());
                        LogUtil.net_i("====>onFailure : " + e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = unicodeDecode(response.body().string());

                Headers headers = response.headers();
                List<String> cookies = headers.values("Set-Cookie");
                if (cookies.size() > 0) {
                    String s = cookies.get(0);
                    session = s.substring(0, s.indexOf(";"));
                }

                LogUtil.net_i("====>onResponse : " + result);
                try {
                    JSONObject object = new JSONObject(result);
                    int status = object.getInt("status");
                    if (resultInterface != null) {
                        mhander.post(() -> {
                            try {

                                if (status == 1) {
                                    String data = object.getString("data");
                                    resultInterface.onSuccess(data);
                                } else {
                                    String msg = object.getString("msg");
                                    resultInterface.onFailure(msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        return myCallback;
    }

    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
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

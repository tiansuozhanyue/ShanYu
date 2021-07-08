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

        StringBuffer loginfo = new StringBuffer();

        loginfo.append("  \r\n");
        loginfo.append("====>doPost : " + url + " >>> " + map.toString() + "\r\n");

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
        call.enqueue(getCallback(resultInterface, loginfo));

    }

    public static void doPost(String url, JSONObject json, HttpResultInterface resultInterface) {

        StringBuffer loginfo = new StringBuffer();

        loginfo.append("  \r\n");
        loginfo.append("====>doPost : " + url + " >>> " + json.toString() + "\r\n");

        if (!isNetworkAvailable()) {
            ToastUtil.shortToast("网络异常，请稍后重试！");
            return;
        }

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , String.valueOf(json));

        Request.Builder builder = new Request.Builder()
                .url(url.trim())
                .header("Content-Type", "application/json;charset=utf-8")
                .post(requestBody);

        if (!StringUtil.isEmpty(session))
            builder.addHeader("cookie", session);

        Call call = getOkHttpClient().newCall(builder.build());
        call.enqueue(getCallback(resultInterface, loginfo));

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
            StringBuffer buffer = new StringBuffer();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                buffer.append(entry.getKey() + "=" + entry.getValue() + "&");
            }

            newUrl = url + "?" + buffer.substring(0, buffer.length() - 1);

        }

        StringBuffer loginfo = new StringBuffer();
        loginfo.append("  \r\n");
        loginfo.append("====>doGet: " + newUrl + "\r\n");

        Request.Builder builder = new Request.Builder()
                .url(newUrl.trim())
                .header("Content-Type", "application/json;charset=utf-8")
                .get();

        if (!StringUtil.isEmpty(session))
            builder.addHeader("cookie", session);

        Call call = getOkHttpClient().newCall(builder.build());
        call.enqueue(getCallback(resultInterface, loginfo));

    }


    public static void Get(String url, Map<String, String> map, Callback responseCallback) {


        if (!isNetworkAvailable()) {
            ToastUtil.shortToast("网络异常，请稍后重试！");
            return;
        }

        String newUrl = "";
        if (map == null) {
            newUrl = url;
        } else {
            StringBuffer buffer = new StringBuffer();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                buffer.append(entry.getKey() + "=" + entry.getValue() + "&");
            }

            newUrl = url + "?" + buffer.substring(0, buffer.length() - 1);

        }

        StringBuffer loginfo = new StringBuffer();
        loginfo.append("  \r\n");
        loginfo.append("====>doGet: " + newUrl + "\r\n");

        Request.Builder builder = new Request.Builder()
                .url(newUrl.trim())
                .header("Content-Type", "application/json;charset=utf-8")
                .get();

        if (!StringUtil.isEmpty(session))
            builder.addHeader("cookie", session);

        Call call = getOkHttpClient().newCall(builder.build());
        call.enqueue(responseCallback);

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

        StringBuffer loginfo = new StringBuffer();
        loginfo.append("\r\n");
        loginfo.append("====>upload: " + HttpApi.UPLOAD + "\r\n");

        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("filecontent", file.getName(), body)
                .build();
        Request request = new Request.Builder()
                .url(HttpApi.UPLOAD)
                .post(requestBody)
                .build();

        Call call = getOkHttpClient().newCall(request);
        call.enqueue(getCallback(resultInterface, loginfo));

    }


    private static Callback getCallback(final HttpResultInterface resultInterface, StringBuffer loginfo) {

        Callback myCallback = new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                loginfo.append("====>onResponse :" + e.getMessage() + "\r\r");

                LogUtil.net_i(loginfo.toString());

                mhander.post(() -> {
                    if (resultInterface != null) {
                        resultInterface.onFailure(e.getMessage());
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

                loginfo.append("====>onResponse :" + result + "\r\n");
                loginfo.append("\r\n");
                LogUtil.net_i(loginfo.toString());

                try {
                    JSONObject object = new JSONObject(result);
                    int status = object.getInt("status");
                    if (resultInterface != null) {
                        mhander.post(() -> {
                            try {

                                if (status == 1) {
                                    String data = object.getString("data");
                                    resultInterface.onSuccess(data);
                                } else if (status == 200) {
                                    resultInterface.onSuccess(result);
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

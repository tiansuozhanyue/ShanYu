package com.example.shanyu.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.shanyu.http.HttpApi;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeChatShareUtil {

    //从官网申请的合法appId
    public static final String APP_ID = HttpApi.WX_APPID;

    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

    //IWXAPI是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private Context context;
    public static WeChatShareUtil weChatShareUtil;

    public static WeChatShareUtil getInstance(Context context) {
        if (weChatShareUtil == null) {
            weChatShareUtil = new WeChatShareUtil();
        }
        if (weChatShareUtil.api != null) {
            weChatShareUtil.api.unregisterApp();
        }
        weChatShareUtil.context = context;
        weChatShareUtil.regToWx();
        return weChatShareUtil;
    }

    //注册应用id到微信
    private void regToWx() {
        //通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
        //将应用的appId注册到微信
        api.registerApp(APP_ID);
    }

    /**
     * 分享文字到朋友圈或者好友
     *
     * @param text  文本内容
     * @param scene 分享方式：好友还是朋友圈
     */
    public boolean shareText(String text, int scene) {
        //初始化一个WXTextObject对象，填写分享的文本对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;
        return share(textObj, text, scene);
    }

    /**
     * 分享图片到朋友圈或者好友
     *
     * @param bmp   图片的Bitmap对象
     * @param scene 分享方式：好友还是朋友圈
     */
    public boolean sharePic(Bitmap bmp, int scene) {
        //初始化一个WXImageObject对象
        WXImageObject imageObj = new WXImageObject(bmp);
        //设置缩略图
        Bitmap thumb = Bitmap.createScaledBitmap(bmp, 60, 60, true);
        bmp.recycle();
        return share(imageObj, thumb, scene);
    }

    /**
     * 分享网页到朋友圈或者好友，视频和音乐的分享和网页大同小异，只是创建的对象不同。
     * 详情参考官方文档：
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317340&token=&lang=zh_CN
     *
     * @param url         网页的url
     * @param title       显示分享网页的标题
     * @param description 对网页的描述
     * @param scene       分享方式：好友还是朋友圈
     */
    public boolean shareUrl(String url, String title, String path, String description, int scene) {
        //初试话一个WXWebpageObject对象，填写url
        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = url;
        return share(webPage, title, getbitmap(path), description, scene);
    }

    private boolean share(WXMediaMessage.IMediaObject mediaObject, Bitmap thumb, int scene) {
        return share(mediaObject, null, thumb, null, scene);
    }

    private boolean share(WXMediaMessage.IMediaObject mediaObject, String description, int scene) {
        return share(mediaObject, null, null, description, scene);
    }

    private boolean share(WXMediaMessage.IMediaObject mediaObject, String title, Bitmap thumb, String description, int scene) {
        //初始化一个WXMediaMessage对象，填写标题、描述
        WXMediaMessage msg = new WXMediaMessage(mediaObject);
        if (title != null) {
            msg.title = title;
        }
        if (description != null) {
            msg.description = description;
        }
        if (thumb != null) {
            msg.thumbData = bmpToByteArray(thumb, true);
        }
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = scene;
        return api.sendReq(req);
    }

    /**
     * 根据一个网络连接(String)获取bitmap图像
     *
     * @param imageUri
     * @return
     */
    public static Bitmap getbitmap(String imageUri) {
        // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }

    //判断是否支持转发到朋友圈
    //微信4.2以上支持，如果需要检查微信版本支持API的情况， 可调用IWXAPI的getWXAppSupportAPI方法,0x21020001及以上支持发送朋友圈
    public boolean isSupportWX() {
        int wxSdkVersion = api.getWXAppSupportAPI();
        return wxSdkVersion >= TIMELINE_SUPPORTED_VERSION;
    }

    private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * wx分享
     *
     * @param type
     */
    public static void setShare(Activity activity, int type, String url, String title, String description, String image) {
        IWXAPI mApi = WXAPIFactory.createWXAPI(activity, APP_ID);
        mApi.registerApp(APP_ID);
        // 通过appId得到IWXAPI这个对象
        // 检查手机或者模拟器是否安装了微信
        if (!mApi.isWXAppInstalled()) {
            ToastUtil.shortToast("您还没有安装微信");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 初始化一个WXWebpageObject对象
                    WXWebpageObject webpageObject = new WXWebpageObject();
                    // 填写网页的url
                    webpageObject.webpageUrl = url;

                    // 用WXWebpageObject对象初始化一个WXMediaMessage对象
                    WXMediaMessage msg = new WXMediaMessage(webpageObject);
                    // 填写网页标题、描述、位图
                    msg.title = title;
                    msg.description = description;


                    Bitmap bmp = null;
                    //image网络图片
                    bmp = BitmapFactory.decodeStream(new URL(image).openStream());

                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
                    bmp.recycle();
                    msg.thumbData = Bitmap2Bytes(thumbBmp);
                    // 构造一个Req
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    // transaction用于唯一标识一个请求（可自定义）
                    req.transaction = "webpage";
                    // 上文的WXMediaMessage对象
                    req.message = msg;
                    //根据type设置分享情景
                    switch (type) {
                        case 0:
                            //分享到微信好友
                            req.scene = SendMessageToWX.Req.WXSceneSession;
                            break;
                        case 1:
                            //分享到微信朋友圈
                            req.scene = SendMessageToWX.Req.WXSceneTimeline;
                            break;
                    }
                    mApi.sendReq(req);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 图片转 byte[] 数组
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}

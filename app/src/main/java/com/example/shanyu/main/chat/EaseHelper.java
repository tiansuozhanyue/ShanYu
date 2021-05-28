package com.example.shanyu.main.chat;

import android.content.Context;
import android.util.Log;

import com.example.shanyu.utils.SharedUtil;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMUserInfo;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.delegate.EaseExpressionAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseFileAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseImageAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseLocationAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseTextAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseVideoAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseVoiceAdapterDelegate;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.manager.EaseMessageTypeSetManager;
import com.hyphenate.easeui.provider.EaseUserProfileProvider;

import java.util.Map;
import java.util.Set;

/**
 * 作为hyphenate-sdk的入口控制类，获取sdk下的基础类均通过此类
 */
public class EaseHelper {
    private static final String TAG = EaseHelper.class.getSimpleName();

    private static EaseHelper mInstance;

    Map<String, EMUserInfo> eMUserInfos;

    private EaseHelper() {
    }

    public static EaseHelper getInstance() {
        if (mInstance == null) {
            synchronized (EaseHelper.class) {
                if (mInstance == null) {
                    mInstance = new EaseHelper();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {

        //初始化IM SDK
        initSDK(context);

        //初始化ease ui相关
        initEaseUI();

        //注册对话类型
        registerConversationType();

        getUserInfoAndSave();

    }

    /**
     * 初始化SDK
     *
     * @param context
     * @return
     */
    private void initSDK(Context context) {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        //初始化
        EMClient.getInstance().init(context, options);

        //EaseIM初始化
        if (EaseIM.getInstance().init(context, options)) {
            //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
            EMClient.getInstance().setDebugMode(true);
            //EaseIM初始化成功之后再去调用注册消息监听的代码 ...
        }
    }


    /**
     * 注册对话类型
     */
    private void registerConversationType() {
        EaseMessageTypeSetManager.getInstance()
                .addMessageType(EaseExpressionAdapterDelegate.class)       //自定义表情
                .addMessageType(EaseFileAdapterDelegate.class)             //文件
                .addMessageType(EaseImageAdapterDelegate.class)            //图片
                .addMessageType(EaseLocationAdapterDelegate.class)         //定位
                .addMessageType(EaseVideoAdapterDelegate.class)            //视频
                .addMessageType(EaseVoiceAdapterDelegate.class)            //声音
                .setDefaultMessageType(EaseTextAdapterDelegate.class);       //文本
    }


    private void initEaseUI() {
        //统一设置头像样式
        EaseAvatarOptions avatarOptions = new EaseAvatarOptions();
        avatarOptions.setAvatarShape(2);//1代表圆形，2代表方形
        EaseIM.getInstance().setAvatarOptions(avatarOptions);
    }


    private void getUserInfoAndSave() {

        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        Set<String> keys = conversations.keySet();
        String[] userId = new String[keys.size()];
        int i = 0;
        for (String key : keys) {
            userId[i] = key;
            i++;
        }

        //获取用户信息
        EMClient.getInstance().userInfoManager().fetchUserInfoByUserId(userId,
                new EMValueCallBack<Map<String, EMUserInfo>>() {
                    @Override
                    public void onSuccess(Map<String, EMUserInfo> value) {
                        eMUserInfos = value;
                    }

                    @Override
                    public void onError(int error, String errorMsg) {

                    }
                });

        //动态提供用户信息
        EaseIM.getInstance().setUserProvider(username -> {
            EaseUser user = new EaseUser(username);
            if (SharedUtil.getIntence().getUid().equals(username)) {
                user.setNickname(SharedUtil.getIntence().getNickName());
                user.setAvatar(SharedUtil.getIntence().getAvatar());
            } else {
                user.setNickname(eMUserInfos.get(username).getNickName());
                user.setAvatar(eMUserInfos.get(username).getAvatarUrl());
            }
            return user;
        });

    }

    public EMUserInfo getUserInfo(String username) {
        if (eMUserInfos != null)
            return eMUserInfos.get(username);
        return null;
    }


}

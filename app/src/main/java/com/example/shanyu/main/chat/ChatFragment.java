package com.example.shanyu.main.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.shanyu.R;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.widget.QyActionBarLayout;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMUserInfo;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.manager.EaseSystemMsgManager;
import com.hyphenate.easeui.modules.conversation.EaseConversationListFragment;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationSetStyle;
import com.hyphenate.easeui.provider.EaseUserProfileProvider;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseImageView;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class ChatFragment extends EaseConversationListFragment {

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_chat, null);
        llRoot.addView(view, 0);

        //设置头像尺寸
        conversationListLayout.setAvatarSize(EaseCommonUtils.dip2px(mContext, 45));

        conversationListLayout.setItemBackGround(ContextCompat.getDrawable(mContext,R.drawable.bg_login_white));

        //设置圆角半径
        conversationListLayout.setAvatarRadius((int) EaseCommonUtils.dip2px(mContext, 5));
        //设置标题字体的颜色
        conversationListLayout.setTitleTextColor(ContextCompat.getColor(mContext, R.color.color_black_E6));
        //设置是否隐藏未读消息数，默认为不隐藏
        conversationListLayout.hideUnreadDot(false);
        //设置未读消息数展示位置，默认为左侧
        conversationListLayout.showUnreadDotPosition(EaseConversationSetStyle.UnreadDotPosition.LEFT);
        conversationListLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_root_bg));

    }


    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        Object item = conversationListLayout.getItem(position).getInfo();
        if (item instanceof EMConversation) {
            if (EaseSystemMsgManager.getInstance().isSystemConversation((EMConversation) item)) {
//                SystemMsgsActivity.actionStart(mContext);
            } else {
                EMConversation emConversation = (EMConversation) item;
                ChatActivity.actionStart(mContext,
                        emConversation.conversationId(),
                        EaseHelper.getInstance().getUserInfo(emConversation.conversationId()).getNickName(),
                        EaseCommonUtils.getChatType(emConversation));
            }
        }
    }

    @Override
    public int getLayoutId() {
        return super.getLayoutId();
    }

}
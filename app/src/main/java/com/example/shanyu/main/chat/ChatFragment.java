package com.example.shanyu.main.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.shanyu.R;
import com.example.shanyu.widget.QyActionBarLayout;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMUserInfo;
import com.hyphenate.easeui.manager.EaseSystemMsgManager;
import com.hyphenate.easeui.modules.conversation.EaseConversationListFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import java.util.Map;


public class ChatFragment extends EaseConversationListFragment {

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_chat, null);
        llRoot.addView(view, 0);
        conversationListLayout.getListAdapter().setEmptyLayoutId(R.layout.ease_layout_default_no_data);

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
                ChatActivity.actionStart(mContext, emConversation.conversationId(), EaseCommonUtils.getChatType(emConversation));
            }
        }
    }

    @Override
    public int getLayoutId() {
        return super.getLayoutId();
    }
}
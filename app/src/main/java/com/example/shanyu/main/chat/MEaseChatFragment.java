package com.example.shanyu.main.chat;

import androidx.core.content.ContextCompat;

import com.example.shanyu.R;
import com.hyphenate.easeui.modules.chat.EaseChatFragment;
import com.hyphenate.easeui.modules.chat.EaseChatInputMenu;

public class MEaseChatFragment extends EaseChatFragment {

    @Override
    public void initView() {
        super.initView();

        //设置聊天列表背景
        chatLayout.getChatMessageListLayout().setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_root_bg));

        EaseChatInputMenu chatInputMenu = chatLayout.getChatInputMenu();
        //设置输入栏背景
        chatInputMenu.getPrimaryMenu().setMenuBackground(ContextCompat.getDrawable(mContext, R.color.white));
    }

}

package com.example.shanyu.main.chat;

import android.view.Gravity;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.shanyu.R;
import com.hyphenate.easeui.modules.chat.EaseChatFragment;
import com.hyphenate.easeui.modules.chat.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class MEaseChatFragment extends EaseChatFragment {

    @Override
    public void initView() {
        super.initView();

        //设置聊天列表背景
        chatLayout.getChatMessageListLayout().setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_root_bg));

        //设置输入栏背景
        EaseChatInputMenu chatInputMenu = chatLayout.getChatInputMenu();
        chatInputMenu.getPrimaryMenu().setMenuBackground(ContextCompat.getDrawable(mContext, R.color.white));

        EditText editText = chatInputMenu.getPrimaryMenu().getEditText();
        editText.setTextSize(14);
        editText.setGravity(Gravity.CENTER_VERTICAL);

    }

}

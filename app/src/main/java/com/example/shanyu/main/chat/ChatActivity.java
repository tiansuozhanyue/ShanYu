
package com.example.shanyu.main.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.constants.EaseConstant;

public class ChatActivity extends BaseActivity {
    private MEaseChatFragment chatFragment;

    public static void actionStart(Context context, String conversationId, String nickName, int chatType) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, conversationId);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType);
        intent.putExtra("nickName", nickName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat, getIntent().getStringExtra("nickName"));
        initView();
    }

    @Override
    public void initView() {
        //use EaseChatFratFragment
        chatFragment = new MEaseChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }
}
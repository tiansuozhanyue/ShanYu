
package com.example.shanyu.main.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class ChatActivity extends BaseActivity {
    private MEaseChatFragment chatFragment;
    private TextView titleBarMessage;

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
        setContentView(R.layout.activity_chat);
        initView();
    }

    public void initView() {

        titleBarMessage = findViewById(R.id.title_bar_message);

        findViewById(R.id.base_back).setOnClickListener(v -> finish());

        titleBarMessage.setText(getIntent().getStringExtra("nickName"));

        //use EaseChatFratFragment
        chatFragment = new MEaseChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, chatFragment, "chat").commit();
    }
}
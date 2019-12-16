package com.example.chaofanteaching.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.chaofanteaching.R;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;


/**
 * author:Meris
 * date:2018/6/11
 */

public class ChatListActivity extends AppCompatActivity {
    private static EMMessageListener emMessageListener;
    private EaseConversationListFragment conversationFragment;

    public static void show(Context context) {
        Intent intent = new Intent(context, ChatListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_activity);
        initView();
    }


    public void initView() {
        conversationFragment = new EaseConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.chat_list,conversationFragment).commit();
        conversationFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(ChatListActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID,conversation.conversationId()));
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
    }

}

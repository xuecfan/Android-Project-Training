package com.example.chaofanteaching.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.ChatActivity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

public class Message extends Fragment {
    private static EMMessageListener emMessageListener;
    private EaseConversationListFragment conversationFragment;
;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.chat_list_activity,container,false);
        initView();
        return view;
    }

    public void initView() {
        conversationFragment = new EaseConversationListFragment();
        getFragmentManager().beginTransaction().add(R.id.chat_list,conversationFragment).commit();
        conversationFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(getContext(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID,conversation.conversationId()));
            }
        });

    }
}

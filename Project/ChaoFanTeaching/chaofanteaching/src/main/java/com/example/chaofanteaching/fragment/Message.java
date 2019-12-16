package com.example.chaofanteaching.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chaofanteaching.Chat.ECChatActivity;
import com.example.chaofanteaching.Chat.ECLoginActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import com.example.chaofanteaching.R;

public class Message extends Fragment {
    // 发起聊天 username 输入框
    private EditText mChatIdEdit;
    // 发起聊天
    private Button mStartChatBtn;
    // 退出登录
    private Button mSignOutBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!EMClient.getInstance().isLoggedInBefore()) {
            Intent intent = new Intent(getActivity(), ECLoginActivity.class);
            startActivity(intent);
        }
        final View view=inflater.inflate(R.layout.activity_main,container,false);
        mChatIdEdit = view.findViewById(R.id.ec_edit_chat_id);
        mStartChatBtn = view.findViewById(R.id.ec_btn_start_chat);
        mSignOutBtn = view.findViewById(R.id.ec_btn_sign_out);
        initView();
        return view;
    }
    private void initView() {

        mStartChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取我们发起聊天的者的username
                String chatId = mChatIdEdit.getText().toString().trim();
                if (!TextUtils.isEmpty(chatId)) {
                    // 获取当前登录用户的 username
                    String currUsername = EMClient.getInstance().getCurrentUser();
                    if (chatId.equals(currUsername)) {
                        Toast.makeText(getContext(), "不能和自己聊天", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 跳转到聊天界面，开始聊天
                    Intent intent = new Intent(getContext(), ECChatActivity.class);
                    intent.putExtra("ec_chat_id", chatId);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Username 不能为空", Toast.LENGTH_LONG).show();
                }
            }
        });
        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }
    /**
     * 退出登录
     */
    private void signOut() {
        // 调用sdk的退出登录方法，第一个参数表示是否解绑推送的token，没有使用推送或者被踢都要传false
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i("lzan13", "logout success");
                // 调用退出成功，结束app
            }

            @Override
            public void onError(int i, String s) {
                Log.i("lzan13", "logout error " + i + " - " + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}

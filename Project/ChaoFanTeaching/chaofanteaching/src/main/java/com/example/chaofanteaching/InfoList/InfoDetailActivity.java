package com.example.chaofanteaching.InfoList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.ChatActivity;
import com.example.chaofanteaching.utils.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class InfoDetailActivity extends AppCompatActivity {
    private String user;
    private Handler handler;
    private TextView nametext;
    private TextView sextext;
    private TextView universitytext;
    private TextView collegetext;
    private TextView majortext;
    private TextView gradetext;
    private TextView subjecttext;
    private TextView timetext;
    private TextView pricetext;
    private TextView introducetext;
    private Button sendbtn;
    private SharedPreferences pre;
    protected EaseTitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_detail);
//        pre= getSharedPreferences("login", Context.MODE_PRIVATE);
//        user = pre.getString("userName", "");

        sendbtn=findViewById(R.id.send);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatIn();
            }
        });
        Intent request=getIntent();
        String name=request.getStringExtra("name");
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle(name);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        nametext=findViewById(R.id.name);
        sextext=findViewById(R.id.sex);
        universitytext=findViewById(R.id.university);
        collegetext=findViewById(R.id.college);
        majortext=findViewById(R.id.major);
        gradetext=findViewById(R.id.grade);
        subjecttext=findViewById(R.id.subject);
        timetext=findViewById(R.id.freetime);
        pricetext=findViewById(R.id.price);
        introducetext=findViewById(R.id.introduce);
        nametext.setText(name);
        dbKey(name);
    }
    //发起聊天
    public void chatIn() {
        String name = user;
        String myName = EMClient.getInstance().getCurrentUser();
        if (!TextUtils.isEmpty(name)) {
            if (name.equals(myName)) {
                ToastUtils.showLong("不能和自己聊天");
                return;
            }
            Intent chat = new Intent(this, ChatActivity.class);
            chat.putExtra(EaseConstant.EXTRA_USER_ID, name);  //对方账号
            chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat); //单聊模式
            startActivity(chat);

        } else {
            ToastUtils.showLong("用户名不可为空");
        }
    }
    private void dbKey(final String key) {
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        String str = msg.obj.toString();
                        String[] s = str.split(",");
                        sextext.setText(s[1]);
                        universitytext.setText(s[2]);
                        collegetext.setText(s[3]);
                        majortext.setText(s[4]);
                        gradetext.setText(s[5]);
                        subjecttext.setText(s[6]);
                        timetext.setText(s[7]);
                        pricetext.setText(s[8]);
                        introducetext.setText(s[9]);
                        user=s[10];
                        break;
                }
            }
        };
        new Thread() {
            HttpURLConnection connection = null;

            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("InfoDetailServlet?id=0&key="+key);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = str;
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
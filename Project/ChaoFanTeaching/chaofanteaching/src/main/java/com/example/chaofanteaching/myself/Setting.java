package com.example.chaofanteaching.myself;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chaofanteaching.ActivityCollector;
import com.example.chaofanteaching.All;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.about.About;
import com.example.chaofanteaching.sign.LoginActivity;
import com.example.chaofanteaching.utils.LogUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class Setting extends AppCompatActivity {
    private ConstraintLayout about;
    private Button btn1;
    private Button btn2;
    private ConstraintLayout fanhui;
    private ConstraintLayout privacy;
    private ConstraintLayout cuurency;
    private ConstraintLayout help;
    private ConstraintLayout plug;
    private Handler handler;
    protected EaseTitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle("设置");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final SharedPreferences pre = getSharedPreferences("login", Context.MODE_PRIVATE);
        final SharedPreferences pre1 = getSharedPreferences("data", Context.MODE_PRIVATE);

        ActivityCollector.addActivity(this);

        about=findViewById(R.id.about);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        fanhui=findViewById(R.id.fanhui);
        privacy=findViewById(R.id.privacy);
        cuurency=findViewById(R.id.currency);
        help=findViewById(R.id.help);
        plug=findViewById(R.id.plug);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });
        cuurency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });
        plug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Setting.this, About.class);
                startActivity(i);
            }
        });
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = pre.getString("userName","");
                System.out.println(userName);
                changeLandingStatus(userName);
                logOut();
                pre.edit().clear().commit();
                pre1.edit().clear().commit();
                Intent i=new Intent(Setting.this, LoginActivity.class);
                startActivity(i);
                ActivityCollector.finishAll();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = pre.getString("userName","");
                System.out.println(userName);
                changeLandingStatus(userName);
                logOut();
                pre.edit().clear().commit();
                pre1.edit().clear().commit();
                Intent i=new Intent(Setting.this,All.class);
                startActivity(i);
                ActivityCollector.finishAll();

            }
        });
    }

    //退出聊天服务器
    public void logOut() {
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                LogUtils.d("logout success");
                finish();
            }

            @Override
            public void onError(int i, String s) {
                LogUtils.d("logout error" + i + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    //退出后更改用户登录状态为未登录
    private void changeLandingStatus(final String a){
        handler = new Handler(){
            public void handleMessage(android.os.Message message){
                switch (message.what){
                    case 1:
                        String string = message.obj.toString();
                        System.out.println("从服务器传来的servlet页面数字："+string);
                        if (string.equals("1")){
                            Toast.makeText(getApplication(),"成功退出账号",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
        new Thread(){
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    Log.e("a",a);
                    connection = HttpConnectionUtils.getConnection("LogoutServlet?userName="+a);
                    int code = connection.getResponseCode();
                    if(code!=200){
                        Log.e("error","网络连接失败");
                    }else{
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = str;
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

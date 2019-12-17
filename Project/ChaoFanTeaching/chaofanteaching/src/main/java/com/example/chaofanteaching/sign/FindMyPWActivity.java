package com.example.chaofanteaching.sign;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaofanteaching.ActivityCollector;
import com.example.chaofanteaching.All;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.google.android.gms.tasks.Task;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FindMyPWActivity extends AppCompatActivity {
    private int sendCodeStatus = 0;
    private int min = 1000;
    private int max = 9999;
    private Handler handler;
    private EditText myEmail;
    private EditText myCode;
    private TextView toTestCode;
    private String sNum = "";
    private String userEmail;
    private String userCode;
    private String string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_my_pw);
        //获取id
        Button backToLogin = findViewById(R.id.backToLogin);
        myEmail = findViewById(R.id.myId);
        myCode = findViewById(R.id.myCode);

        TextView sendCode = findViewById(R.id.sendCode);
        toTestCode = findViewById(R.id.toTestCode);


        //点击返回登录页
        backToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //点击发送验证码
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail = myEmail.getText().toString();
                userCode = myCode.getText().toString();
                if (userEmail.isEmpty()){
                    Toast.makeText(getApplication(),"请输入邮箱",Toast.LENGTH_SHORT).show();
                }else if (userEmail.contains("@qq.com")){
                    Random random = new Random();
                    int num = random.nextInt(max)%(max-min+1) + min;
                    sNum = num+"";
                    Toast.makeText(getApplication(),num+"",Toast.LENGTH_SHORT).show();

                    Log.e("2",userEmail);
                    testUserCode(sNum,userEmail);
                }else{
                    Toast.makeText(getApplication(),"请输入正确邮箱格式",Toast.LENGTH_SHORT).show();
                }
//                sendCodeStatus = 1;
//                Timer timer = new Timer();
//                TimerTask timerTask = new TimerTask() {
//                    @Override
//                    public void run() {
//                        sendCodeStatus = 0;
//                    }
//                };
//                timer.schedule(timerTask,60000);
            }
        });

        //点击下一步
        toTestCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                userEmail = myEmail.getText().toString();
                userCode = myCode.getText().toString();
                if (userEmail.isEmpty()){
                    Toast.makeText(getApplication(),"请输入邮箱",Toast.LENGTH_SHORT).show();
                }else if (userCode.isEmpty()){
                    Toast.makeText(getApplication(),"请获取验证码",Toast.LENGTH_SHORT).show();
                }else if (sNum.equals(userCode)){
                    //进入修改密码界面
                    Intent intent = new Intent();
                    intent.setClass(FindMyPWActivity.this, RePWActivity.class);
                    intent.putExtra("userEmail",userEmail);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplication(),"验证码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //连接数据库，向数据库发送用户邮箱和验证码
    private void testUserCode(final String a, final String b){
        handler = new Handler(){
            public void handleMessage(android.os.Message message){
                switch (message.what){
                    case 1:
                        string = message.obj.toString();
                        System.out.println("从服务器传来的servlet页面数字："+string);
                        break;
                }
            }
        };
        new Thread(){
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    Log.e("1",a+b);
                    connection = HttpConnectionUtils.getConnection("FindPW?op=sendcode&num="+a+"&email="+b);
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

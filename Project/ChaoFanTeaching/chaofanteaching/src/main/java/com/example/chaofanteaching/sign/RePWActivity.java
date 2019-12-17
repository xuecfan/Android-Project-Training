package com.example.chaofanteaching.sign;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chaofanteaching.ActivityCollector;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Objects;

public class RePWActivity extends AppCompatActivity {
    private Handler handler;
    private String userEmail;
    private EditText myNewPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repw);
        //获取id
        Button backToFindMyPW = findViewById(R.id.backToFindMyPW);
        myNewPW = findViewById(R.id.myNewPW);
        final EditText myNewPWAgain = findViewById(R.id.myNewPWAgain);
        Button rePW = findViewById(R.id.rePW);


        //获取从找回密码页传来的用户邮箱
        Intent intent =getIntent();
        userEmail = intent.getStringExtra("userEmail");
        Log.e("用户的邮箱",userEmail);
        //返回找回密码页
        backToFindMyPW.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //修改密码完成，返回登陆页
        rePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myNewPW.getText().toString().isEmpty() || myNewPWAgain.getText().toString().isEmpty()){
                    Toast.makeText(getApplication(),"请输入更改后的密码",Toast.LENGTH_SHORT).show();
                }else{
                    changePW(userEmail,myNewPW);

                }
            }
        });
    }

    //向数据库发送用户邮箱和新密码
    private void changePW(final String a, final EditText b){
        final String password = b.getText().toString();
        handler = new Handler(){
            public void handleMessage(android.os.Message message){
                switch (message.what){
                    case 1:
                        String string = message.obj.toString();
                        String para = String.valueOf(1);
                        if (string.equals(para)){
                            Toast.makeText(getApplication(),"密码修改成功，请登录",Toast.LENGTH_LONG).show();
//                            finish();

                            ActivityCollector.finishAll();
                            Intent intent = new Intent();
                            intent.setClass(RePWActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else if (string.equals("0")){
                            Toast.makeText(getApplication(),"修改密码失败，请重新尝试",Toast.LENGTH_SHORT).show();

                        }
                }
            }
        };
        new Thread(){
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("FindPW?op=repw&email="+a+"&pasd="+password);
                    int code = connection.getResponseCode();
                    if(code!=200){
                        Log.e("error","网络连接失败");
                    }else {
                        InputStream inputStream = connection.getInputStream();
                        String string = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = string;
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

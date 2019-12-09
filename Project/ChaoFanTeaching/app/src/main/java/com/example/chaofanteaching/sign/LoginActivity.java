package com.example.chaofanteaching.sign;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chaofanteaching.All;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class LoginActivity extends AppCompatActivity {
    private EditText myId;
    private EditText myPW;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();//隐藏标题栏
        setStatusBar();//设置状态栏

        setContentView(R.layout.activity_login);

        //获取id
        myId = findViewById(R.id.myId);
        myPW = findViewById(R.id.myPW);
        ImageView loginBtn = findViewById(R.id.myLoginBtn);
        Button toLogonBtn=findViewById(R.id.toLogonBtn);
        Button findMyPW = findViewById(R.id.findMyPW);
        Button serviceAgreement = findViewById(R.id.serviceAgreement);

        //登陆按钮
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String myid = myId.getText().toString();
                String mypw = myPW.getText().toString();
                String IdAndPW = myid +","+ mypw;
                System.out.println(myid+"   "+mypw+"   "+IdAndPW);
                if ((myid.isEmpty()) || (mypw.isEmpty())){
                    Toast.makeText(getApplication(),"邮箱和密码不能为空",Toast.LENGTH_LONG).show();
                } else{
                    testUser(myid,mypw);

                }

            }
        });

        //忘记密码
        findMyPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,FindMyPWActivity.class);
                startActivity(intent);
            }
        });
        //用户注册
        toLogonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(LoginActivity.this,LogonActivity.class);
                startActivity(intent);
            }
        });

    }

    //设置状态栏为白色，图标和字体为暗色
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));//设置状态栏颜色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
    }

    private void testUser(final String a, final String b){
        handler = new Handler(){
            public void handleMessage(android.os.Message message){
                switch (message.what){
                    case 1:
                        String string = message.obj.toString();
                        String para = String.valueOf(1);
                        System.out.println("从服务器传来的servlet页面数字："+string);
                        if (string.equals(para)){
                            SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("loginOrNot", string);
                            editor.apply();

                            Toast.makeText(getApplication(),"登陆成功",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            intent.setClass( LoginActivity.this, All.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(getApplication(),"用户名或密码错误",Toast.LENGTH_LONG).show();
                            myPW.setText("");
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
                    connection = HttpConnectionUtils.getConnection("LoginServlet?myid="+a+"&mypw="+b);
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

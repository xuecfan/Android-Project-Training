package com.example.chaofanteaching.sign;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class LogonActivity extends AppCompatActivity {
    private int status;//表示身份是家长还是老师，0为家长，1为老师
    private EditText myId;
    private EditText myPW;
    private EditText myPWAgain;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();//隐藏标题栏
        setStatusBar();//设置状态栏
        setContentView(R.layout.activity_logon);



        //获取id
        ImageView backToLogin = findViewById(R.id.backToLogin);
        final TextView imParent = findViewById(R.id.imParent);
        final TextView imTeacher = findViewById(R.id.imTeacher);
        myId = findViewById(R.id.myId);
        myPW = findViewById(R.id.myPW);
        myPWAgain = findViewById(R.id.myPWAgain);
        ImageView logonBtn = findViewById(R.id.logonBtn);

        //返回登陆页面
        backToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
//                Intent intent = new Intent();
//                intent.setClass(LogonActivity.this,LoginActivity.class);
//                startActivity(intent);
            }
        });

        //切换身份
        imParent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //更改背景
                imParent.setBackground(getResources().getDrawable(R.drawable.leftround2));
                imTeacher.setBackground(getResources().getDrawable(R.drawable.rightround));
                //更改字的颜色
                imParent.setTextColor(Color.parseColor("#ffffff"));
                imTeacher.setTextColor(Color.parseColor("#888888"));

//                imParent.setBackgroundColor(Color.parseColor("#D8900A"));
//                imTeacher.setBackgroundColor(Color.parseColor("#F2F3F6"));
                status = 0;
            }
        });
        imTeacher.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //更改背景
                imParent.setBackground(getResources().getDrawable(R.drawable.leftround));
                imTeacher.setBackground(getResources().getDrawable(R.drawable.rightround2));
                //更改字的颜色
                imParent.setTextColor(Color.parseColor("#888888"));
                imTeacher.setTextColor(Color.parseColor("#ffffff"));

//                imTeacher.setBackgroundColor(Color.parseColor("#D8900A"));
//                imParent.setBackgroundColor(Color.parseColor("#F2F3F6"));
                status = 1;
            }
        });

        //注册按钮
        logonBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                String loginOrNot = sharedPreferences.getString("loginOrNot", null);//(key,若无数据需要赋的值)
                Log.e("xcf",loginOrNot);

                String user=myId.getText().toString();
                String pasd=myPW.getText().toString();
                String pasdAgain = myPWAgain.getText().toString();
                if ((user.isEmpty()) || (pasd.isEmpty()) || (pasdAgain.isEmpty())){
                    Toast.makeText(getApplication(),"用户名和密码不能为空",Toast.LENGTH_LONG).show();
                }else if (!pasd.equals(pasdAgain)){
                    Toast.makeText(getApplication(),"两次密码不一致",Toast.LENGTH_LONG).show();
                }else{
                    addUser(user,pasd,status);

                }

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

    //在数据库里添加用户，判断是否注册成功
    private void addUser(final String a, final String b, final int c){
        handler = new Handler(){
            public void handleMessage(android.os.Message message){
                switch (message.what){
                    case 1:
                        String string = message.obj.toString();
                        String para = String.valueOf(1);
                        if (string.equals(para)){
                            Toast.makeText(getApplication(),"注册成功，请登录",Toast.LENGTH_LONG).show();
                            finish();

//                            Intent intent = new Intent();
//                            intent.setClass(LogonActivity.this,LoginActivity.class);
//                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplication(),"注册失败",Toast.LENGTH_LONG).show();

                        }
                }
            }
        };
        new Thread(){
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("AccountServlet?name="+a+"&pasd="+b+"&role="+c);
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

package com.example.chaofanteaching.sign;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.chaofanteaching.All;
import com.example.chaofanteaching.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();//隐藏标题栏
        setStatusBar();//设置状态栏

        setContentView(R.layout.activity_login);

        //获取id
        EditText myId = findViewById(R.id.myId);
        EditText myPW = findViewById(R.id.myPW);
        ImageView loginBtn = findViewById(R.id.myLoginBtn);
        Button toLogonBtn=findViewById(R.id.toLogonBtn);
        Button findMyPW = findViewById(R.id.findMyPW);
        Button serviceAgreement = findViewById(R.id.serviceAgreement);

        //登陆按钮
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, All.class);
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

}

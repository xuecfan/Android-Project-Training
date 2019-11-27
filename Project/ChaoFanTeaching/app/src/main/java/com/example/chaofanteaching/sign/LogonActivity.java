package com.example.chaofanteaching.sign;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaofanteaching.R;

public class LogonActivity extends AppCompatActivity {
    private int status;//表示身份是家长还是老师，0为家长，1为老师

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏标题栏
        setStatusBar();//设置状态栏
        setContentView(R.layout.activity_logon);

        //获取id
        ImageView backToLogin = findViewById(R.id.backToLogin);
        final TextView imParent = findViewById(R.id.imParent);
        final TextView imTeacher = findViewById(R.id.imTeacher);
        EditText myId = findViewById(R.id.myId);
        EditText myPW = findViewById(R.id.myPW);
        EditText myPWAgain = findViewById(R.id.myPWAgain);
        ImageView logonBtn = findViewById(R.id.logonBtn);

        //返回登陆页面
        backToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LogonActivity.this,LoginActivity.class);
                startActivity(intent);
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
                Intent intent = new Intent();
                intent.setClass(LogonActivity.this,LoginActivity.class);
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

package com.example.chaofanteaching.sign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chaofanteaching.R;

public class FindMyPWActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_my_pw);
        //获取id
        Button backToLogin = findViewById(R.id.backToLogin);
        EditText myId = findViewById(R.id.myId);
        EditText myCode = findViewById(R.id.myCode);
        Button sendCode = findViewById(R.id.sendCode);
        Button toTestCode = findViewById(R.id.toTestCode);
        //返回登录页
        backToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //进入验证验证码界面
        toTestCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FindMyPWActivity.this, RePWActivity.class);
                startActivity(intent);
            }
        });
    }
}

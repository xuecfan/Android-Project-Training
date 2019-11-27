package com.example.chaofanteaching.sign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chaofanteaching.R;

public class RePWActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repw);
        //获取id
        Button backToFindMyPW = findViewById(R.id.backToFindMyPW);
        EditText myNewPW = findViewById(R.id.myNewPW);
        Button rePW = findViewById(R.id.rePW);

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
                Intent intent = new Intent();
                intent.setClass(RePWActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

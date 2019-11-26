package com.example.chaofanteaching.sign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chaofanteaching.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取id
        EditText myId = findViewById(R.id.myId);
        EditText myPW = findViewById(R.id.myPW);
        Button button=findViewById(R.id.toLogonBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(LoginActivity.this,LogonActivity.class);
                startActivity(intent);
            }
        });

    }
}

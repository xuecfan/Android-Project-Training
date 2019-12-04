package com.example.chaofanteaching.myself;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.chaofanteaching.All;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.about.About;
import com.example.chaofanteaching.sign.LoginActivity;

public class Setting extends AppCompatActivity {
    private LinearLayout about;
    private Button btn1;
    private Button btn2;
    private LinearLayout fanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        about=findViewById(R.id.about);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        fanhui=findViewById(R.id.fanhui);
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
                Intent i=new Intent(Setting.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}

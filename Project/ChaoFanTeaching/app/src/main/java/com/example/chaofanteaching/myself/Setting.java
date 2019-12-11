package com.example.chaofanteaching.myself;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chaofanteaching.All;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.about.About;
import com.example.chaofanteaching.sign.LoginActivity;

public class Setting extends AppCompatActivity {
    private LinearLayout about;
    private Button btn1;
    private Button btn2;
    private LinearLayout fanhui;
    private LinearLayout privacy;
    private LinearLayout cuurency;
    private LinearLayout help;
    private LinearLayout plug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final SharedPreferences pre = getSharedPreferences("login", Context.MODE_PRIVATE);
        final SharedPreferences pre1 = getSharedPreferences("data", Context.MODE_PRIVATE);


        about=findViewById(R.id.about);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        fanhui=findViewById(R.id.fanhui);
        privacy=findViewById(R.id.privacy);
        cuurency=findViewById(R.id.currency);
        help=findViewById(R.id.help);
        plug=findViewById(R.id.plug);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });
        cuurency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });
        plug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });

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
                pre.edit().clear().commit();
                pre1.edit().clear().commit();
                Intent i=new Intent(Setting.this, LoginActivity.class);
                startActivity(i);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    pre.edit().clear().commit();
                    pre1.edit().clear().commit();
                    Setting.super.onBackPressed();
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);

            }
        });
    }
}

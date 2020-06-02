package com.example.chaofanteaching.myself;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chaofanteaching.R;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class PhoneDetail extends AppCompatActivity {

    protected EaseTitleBar titleBar;
    private Button btn1;
    private Button btn2;
    private EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_detail);
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle("电话");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        phone=findViewById(R.id.phone);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i=new Intent(PhoneDetail.this,MyData.class);
                SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("phoneContent",phone.getText().toString());
                editor.apply();
                //startActivity(i);
                finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

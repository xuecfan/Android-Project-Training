package com.example.chaofanteaching.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chaofanteaching.R;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class SubSuccess extends AppCompatActivity {
    protected EaseTitleBar titleBar;
    private Button back;
    private Button look_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_success);
        initView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        look_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SubSuccess.this,OrderInfo.class);
                startActivity(i);
                finish();
            }
        });
    }
    public void initView(){
        titleBar=findViewById(R.id.title_bar);
        back=findViewById(R.id.back);
        look_order=findViewById(R.id.look_order);
        setTitie();
    }
    public void setTitie(){
        titleBar.setTitle("提交成功");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

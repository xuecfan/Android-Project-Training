package com.example.chaofanteaching.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.chaofanteaching.R;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class About extends AppCompatActivity {
    protected EaseTitleBar titleBar;
    private ImageButton a1;
    private ImageButton a2;
    private ImageButton a3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle("关于");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        a1=findViewById(R.id.a1);
        a2=findViewById(R.id.a2);
        a3=findViewById(R.id.a3);
        a1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent a1=new Intent(About.this,A1.class);
                startActivity(a1);
            }
        });
        a2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent a1=new Intent(About.this,A2.class);
                startActivity(a1);
            }
        });
        a3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent a1=new Intent(About.this,A3.class);
                startActivity(a1);
            }
        });
    }
}
package com.example.chaofanteaching.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.chaofanteaching.R;
import com.example.chaofanteaching.fragment.My;

public class About extends AppCompatActivity {
    private ImageButton about_fanhui;
    private ImageButton a1;
    private ImageButton a2;
    private ImageButton a3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        about_fanhui=findViewById(R.id.about_fanhui);
        a1=findViewById(R.id.a1);
        a2=findViewById(R.id.a2);
        a3=findViewById(R.id.a3);
        about_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
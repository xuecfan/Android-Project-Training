package com.example.chaofanteaching.InfoList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.chaofanteaching.R;

public class InfoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_detail);
        Intent request=getIntent();
        String name=request.getStringExtra("name");
        TextView nametext=findViewById(R.id.name);
        nametext.setText(name);
    }
}
package com.example.chaofanteaching.myself;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chaofanteaching.R;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class NameDetail extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    private EditText name;
    protected EaseTitleBar titleBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_detail);
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle("昵称");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        name=findViewById(R.id.name);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"姓名不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("nameContent",name.getText().toString());
                    editor.apply();
                    finish();
                }
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

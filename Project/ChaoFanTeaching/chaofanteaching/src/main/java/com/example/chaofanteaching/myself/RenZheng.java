package com.example.chaofanteaching.myself;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chaofanteaching.R;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class RenZheng extends AppCompatActivity {
    protected EaseTitleBar titleBar;
    private SharedPreferences pre;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng);
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle("认证信息");
        text=findViewById(R.id.text);
        pre=getSharedPreferences("login",MODE_PRIVATE);
        if(pre.getString("role","").equals("10")){
            text.setText("恭喜您，身份认证已通过");
        }
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

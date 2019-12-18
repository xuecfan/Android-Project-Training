package com.example.chaofanteaching.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.chaofanteaching.R;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class About extends AppCompatActivity {
    protected EaseTitleBar titleBar;

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
    }
}
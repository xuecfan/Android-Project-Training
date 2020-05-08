package com.example.chaofanteaching.comments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.chaofanteaching.R;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class CommentingActivity extends AppCompatActivity {
    private EaseTitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commenting);

        //初始化view
        initView();
        //标题栏
        setTitleBar();
    }

    /**
     * 通过id获取view
     */
    public void initView(){
        titleBar=findViewById(R.id.commenting_titleBar);
    }

    /**
     * 标题栏
     */
    public void setTitleBar(){
        titleBar.setTitle("发表评价");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}

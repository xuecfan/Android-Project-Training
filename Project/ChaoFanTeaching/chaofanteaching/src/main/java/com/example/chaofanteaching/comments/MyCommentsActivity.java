package com.example.chaofanteaching.comments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.chaofanteaching.InfoList.InfoAdapter;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.comments.fragments.LeftFragment;
import com.example.chaofanteaching.comments.fragments.RightFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

/**
 * 我的评价页面，含两个fragment：待评价和已评价
 */
public class MyCommentsActivity extends AppCompatActivity {
    private EaseTitleBar titleBar;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private List<Fragment> fragments;

    private String[] arr = new String[]{"000","myl"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comments);

        //初始化view
        initView();
        //标题栏
        setTitleBar();
        //初始化viewPager
        initViewPager();
    }

    /**
     * 初始化viewPager
     */
    private void initViewPager() {
        //创建List集合
        fragments = new ArrayList<>();
        //添加到fragments集合里
        fragments.add(new LeftFragment());
        fragments.add(new RightFragment());
        //创建适配器
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        //把fragment添加到adapter
        adapter.setFragments(fragments);
        //把adapter添加到viewPager
        viewPager.setAdapter(adapter);

        //tabLayouut有几个创建几个
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        //使tabLayout和viewPager  关联同步一下
        tabLayout.setupWithViewPager(viewPager);
        //设置创建的名字  万物从0开始
        tabLayout.getTabAt(0).setText("待评价");
        tabLayout.getTabAt(1).setText("已评价");
    }


    /**
     * 通过id获取view
     */
    public void initView(){
        titleBar = findViewById(R.id.myComments_titleBar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
    }

    /**
     * 标题栏
     */
    public void setTitleBar(){
        titleBar.setTitle("我的评价");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        titleBar.setRightImageResource(R.drawable.ease_mm_title_remove);
        titleBar.setRightLayoutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), CommentingActivity.class);
                intent.putExtra("user",arr);
                startActivity(intent);
            }
        });
    }
}

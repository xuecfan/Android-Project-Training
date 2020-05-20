package com.example.chaofanteaching.order;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.chaofanteaching.R;
import com.example.chaofanteaching.comments.MyAdapter;
import com.example.chaofanteaching.fragment.TeacherList;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.ArrayList;
import java.util.List;

public class OrderList extends AppCompatActivity {

    protected EaseTitleBar titleBar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initView();
    }

    private void initViewPager() {
        //创建List集合
        fragments = new ArrayList<>();
        //添加到fragments集合里
        fragments.add(new SentOrder());
        fragments.add(new ReceivedOrder());
        //创建适配器
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        //把fragment添加到adapter
        adapter.setFragments(fragments);
        //把adapter添加到viewPager
        viewPager.setAdapter(adapter);
        //tabLayouut有几个创建几个
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        //使tabLayout和viewPager  关联同步一下
        tabLayout.setupWithViewPager(viewPager);
        //设置创建的名字  万物从0开始
        tabLayout.getTabAt(0).setText("我发出的");
        tabLayout.getTabAt(1).setText("我收到的");
    }

    public void initView(){
        viewPager=findViewById(R.id.viewPager);
        tabLayout=findViewById(R.id.tabLayout);
        titleBar=findViewById(R.id.title_bar);
        setTitie();
        initViewPager();
        //divider();
    }
    public void setTitie(){
        titleBar.setTitle("我的试讲");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void divider(){
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tablayout));
    }
}
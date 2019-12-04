package com.example.chaofanteaching;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentTabHost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.chaofanteaching.fragment.List;
import com.example.chaofanteaching.fragment.Message;
import com.example.chaofanteaching.fragment.My;

import java.util.HashMap;
import java.util.Map;

public class All extends AppCompatActivity {

    private Map<String, ImageView> imageViewMap = new HashMap<>();
    private Map<String, TextView> textViewMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all);

        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);

        fragmentTabHost.setup(this,
                getSupportFragmentManager(),
                android.R.id.tabcontent);

        TabHost.TabSpec tabSpec1 = fragmentTabHost.newTabSpec("tag1")
                .setIndicator(getTabSpecView("tag1",R.drawable.list,"列表"));

        fragmentTabHost.addTab(tabSpec1,
                List.class,
                null);

        TabHost.TabSpec tabSpec2 = fragmentTabHost.newTabSpec("tag2")
                .setIndicator(getTabSpecView("tag2",R.drawable.message,"消息"));

        fragmentTabHost.addTab(tabSpec2,
                Message.class,
                null);

        TabHost.TabSpec tabSpec3 = fragmentTabHost.newTabSpec("tag3")
                .setIndicator(getTabSpecView("tag3",R.drawable.my,"我的"));

        fragmentTabHost.addTab(tabSpec3,
                My.class,
                null);

//        if(getIntent().getStringExtra("value").equals("3")){
//            fragmentTabHost.setCurrentTab(2);
//            imageViewMap.get("tag3").setImageResource(R.drawable.my1);
//            textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.colorPrimary));
//        }else{
            fragmentTabHost.setCurrentTab(0);
            imageViewMap.get("tag1").setImageResource(R.drawable.list1);
            textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.colorPrimary));
//        }

        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                textViewMap.get(tabId).setTextColor(getResources().getColor(R.color.colorPrimary));
                switch (tabId){
                    case "tag1":
                        imageViewMap.get("tag1").setImageResource(R.drawable.list1);
                        imageViewMap.get("tag2").setImageResource(R.drawable.message);
                        textViewMap.get("tag2").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("tag3").setImageResource(R.drawable.my);
                        textViewMap.get("tag3").setTextColor(getResources().getColor(android.R.color.black));

                        break;
                    case "tag2":
                        imageViewMap.get("tag1").setImageResource(R.drawable.list);
                        imageViewMap.get("tag2").setImageResource(R.drawable.message1);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("tag3").setImageResource(R.drawable.my);
                        textViewMap.get("tag3").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                    case "tag3":
                        imageViewMap.get("tag3").setImageResource(R.drawable.my1);
                        imageViewMap.get("tag1").setImageResource(R.drawable.list);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("tag2").setImageResource(R.drawable.message);
                        textViewMap.get("tag2").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                }
            }
        });
    }

    public View getTabSpecView(String tag, int imageResId, String title){
        //加载布局文件
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.tabspec,null);

        //获取控件对象
        ImageView imageView = view.findViewById(R.id.iv_icon);
        imageView.setImageResource(imageResId);

        TextView textView = view.findViewById(R.id.tv_title);
        textView.setText(title);

        imageViewMap.put(tag,imageView);
        textViewMap.put(tag,textView);

        return view;
    }

}

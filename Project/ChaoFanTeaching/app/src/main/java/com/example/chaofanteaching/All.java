package com.example.chaofanteaching;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentTabHost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaofanteaching.fragment.List;
import com.example.chaofanteaching.fragment.List1;
import com.example.chaofanteaching.fragment.Message;
import com.example.chaofanteaching.fragment.My;
import com.example.chaofanteaching.myself.MyData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

public class All extends AppCompatActivity {

    private static int isExit = 0;
    private Map<String, ImageView> imageViewMap = new HashMap<>();
    private Map<String, TextView> textViewMap = new HashMap<>();
    private EventBus eventBus;
    private FragmentTabHost fragmentTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        ActivityCollector.addActivity(this);
        SharedPreferences pre=getSharedPreferences("login", Context.MODE_PRIVATE);
        String user = pre.getString("userName", "");
        String role=pre.getString("role","");
        fragmentTabHost = findViewById(android.R.id.tabhost);

        fragmentTabHost.setup(this,
                getSupportFragmentManager(),
                android.R.id.tabcontent);

        Intent i=getIntent();
        int a=i.getIntExtra("status",0);
        TabHost.TabSpec tabSpec1 = fragmentTabHost.newTabSpec("tag1")
                .setIndicator(getTabSpecView("tag1",R.drawable.list,"列表"));
        if(!role.equals("")){
        if(role.equals("10")){
            fragmentTabHost.addTab(tabSpec1,
                    List.class,
                    null);
        }else if(role.equals("11")){
            fragmentTabHost.addTab(tabSpec1,
                    List1.class,
                    null);
        }
        }else {
            if(a==0){
                fragmentTabHost.addTab(tabSpec1,
                        List.class,
                        null);
            }else if(a==1){
                fragmentTabHost.addTab(tabSpec1,
                        List1.class,
                        null);
            }
        }



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

        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("tag1").setImageResource(R.drawable.list1);
        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.colorPrimary));
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

//    @Subscribe(sticky = true)
//    public void onEventBeanEvent(String message){
//        if(message.equals("parent")){
//            TabHost.TabSpec tabSpec1 = fragmentTabHost.newTabSpec("tag1")
//                    .setIndicator(getTabSpecView("tag1",R.drawable.list,"列表"));
//
//            fragmentTabHost.addTab(tabSpec1,
//                    List.class,
//                    null);
//        }else if(message.equals("teacher")){
//            TabHost.TabSpec tabSpec1 = fragmentTabHost.newTabSpec("tag1")
//                    .setIndicator(getTabSpecView("tag1",R.drawable.list,"列表"));
//
//            fragmentTabHost.addTab(tabSpec1,
//                    List1.class,
//                    null);
//        }
//    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
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


    //实现按两次后退才退出
    Handler handler=new Handler(){
        @Override
        public void handleMessage(android.os.Message msg){
            super.handleMessage(msg);
            isExit--;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            isExit++;
            exit();
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void exit(){
        if (isExit < 2){
            Toast.makeText(getApplicationContext(),R.string.Exit,Toast.LENGTH_SHORT).show();

            //利用handler延迟发送更改状态信息
            handler.sendEmptyMessageDelayed(0,2000);
        }else{
            //在程序退出之前重置isExit,使下次打开时isExit还为0
            handler.sendEmptyMessage(0);

            super.onBackPressed();
        }
    }
}

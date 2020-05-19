package com.example.chaofanteaching;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.chaofanteaching.InfoList.AddInfoActivity;
import com.example.chaofanteaching.InfoList.AddStuInfoActivity;
import com.example.chaofanteaching.fragment.Blank;
import com.example.chaofanteaching.fragment.TeacherList;
import com.example.chaofanteaching.fragment.ParentList;
import com.example.chaofanteaching.fragment.Message;
import com.example.chaofanteaching.fragment.My;
import com.example.chaofanteaching.fragment.White;
import com.example.chaofanteaching.sign.ChooseIdentityActivity;
import com.example.chaofanteaching.sign.LoginActivity;
import java.util.HashMap;
import java.util.Map;

import ru.alexbykov.nopermission.PermissionHelper;

public class All extends AppCompatActivity {

    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private static int isExit = 0;
    private Map<String, ImageView> imageViewMap = new HashMap<>();
    private Map<String, TextView> textViewMap = new HashMap<>();
    private FragmentTabHost fragmentTabHost;
    private SharedPreferences pre;
    private PermissionHelper permissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        applyPermission();
        locationOption();
        pre=getSharedPreferences("login",MODE_PRIVATE);
        ImageView main_image_center =  findViewById(R.id.main_image_center);
        main_image_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pre.getString("userName","").equals("")){
                    Toast.makeText(getApplicationContext(),"请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(All.this, LoginActivity.class);
                    startActivity(i);
                }else{
                    if(pre.getString("role","").equals("10")){
                        Intent i=new Intent(All.this, AddInfoActivity.class);
                        startActivity(i);
                    }else if(pre.getString("role","").equals("11")){
                        Intent i=new Intent(All.this, AddStuInfoActivity.class);
                        startActivity(i);
                    }
                }
            }
        });
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
                    TeacherList.class,
                    null);
        }else if(role.equals("11")){
            fragmentTabHost.addTab(tabSpec1,
                    ParentList.class,
                    null);
        }
        }else {
            if(a==0){
                fragmentTabHost.addTab(tabSpec1,
                        TeacherList.class,
                        null);
            }else if(a==1){
                fragmentTabHost.addTab(tabSpec1,
                        ParentList.class,
                        null);
            }
        }



        TabHost.TabSpec tabSpec2 = fragmentTabHost.newTabSpec("tag2")
                .setIndicator(getTabSpecView("tag2",R.drawable.message,"消息"));

        fragmentTabHost.addTab(tabSpec2,
                Message.class,
                null);
        TabHost.TabSpec tabSpec3 = fragmentTabHost.newTabSpec("tag3")
                .setIndicator(getTabSpecView("tag3",R.drawable.white,""));

        fragmentTabHost.addTab(tabSpec3,
                Blank.class,
                null);
        TabHost.TabSpec tabSpec4 = fragmentTabHost.newTabSpec("tag4")
                .setIndicator(getTabSpecView("tag4",R.drawable.subject,"科目"));

        fragmentTabHost.addTab(tabSpec4,
                White.class,
                null);
        TabHost.TabSpec tabSpec5 = fragmentTabHost.newTabSpec("tag5")
                .setIndicator(getTabSpecView("tag5",R.drawable.my,"我的"));

        fragmentTabHost.addTab(tabSpec5,
                My.class,
                null);

        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("tag1").setImageResource(R.drawable.list1);
        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.buju));
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                textViewMap.get(tabId).setTextColor(getResources().getColor(R.color.colorPrimary));
                switch (tabId){
                    case "tag1":
                        imageViewMap.get("tag1").setImageResource(R.drawable.list1);
                        textViewMap.get("tag1").setTextColor(Color.parseColor("#D8900A"));
                        imageViewMap.get("tag2").setImageResource(R.drawable.message);
                        textViewMap.get("tag2").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("tag4").setImageResource(R.drawable.subject);
                        textViewMap.get("tag4").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("tag5").setImageResource(R.drawable.my);
                        textViewMap.get("tag5").setTextColor(getResources().getColor(android.R.color.black));

                        break;
                    case "tag2":
                        imageViewMap.get("tag1").setImageResource(R.drawable.list);
                        imageViewMap.get("tag2").setImageResource(R.drawable.message1);
                        textViewMap.get("tag2").setTextColor(Color.parseColor("#D8900A"));
                        textViewMap.get("tag1").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("tag4").setImageResource(R.drawable.subject);
                        textViewMap.get("tag4").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("tag5").setImageResource(R.drawable.my);
                        textViewMap.get("tag5").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                    case "tag4":
                        textViewMap.get("tag4").setTextColor(Color.parseColor("#D8900A"));
                        imageViewMap.get("tag4").setImageResource(R.drawable.subject1);
                        imageViewMap.get("tag1").setImageResource(R.drawable.list);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("tag2").setImageResource(R.drawable.message);
                        textViewMap.get("tag2").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("tag5").setImageResource(R.drawable.my);
                        textViewMap.get("tag5").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                    case "tag5":
                        textViewMap.get("tag5").setTextColor(Color.parseColor("#D8900A"));
                        imageViewMap.get("tag5").setImageResource(R.drawable.my1);
                        imageViewMap.get("tag1").setImageResource(R.drawable.list);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("tag2").setImageResource(R.drawable.message);
                        textViewMap.get("tag2").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("tag4").setImageResource(R.drawable.subject);
                        textViewMap.get("tag4").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                }
            }
        });
    }

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

    public void applyPermission(){
        String[]PermissionString={
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
        };
        permissionHelper=new PermissionHelper(this);
        permissionHelper.check(PermissionString).onDenied(new Runnable() {
            @Override
            public void run() {
                All.this.finish();
                Log.i("permisson","拒绝权限");
            }
        }).onSuccess(new Runnable() {
            @Override
            public void run() {
                Log.i("permisson","允许权限");
            }
        }).run();
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
    private void locationOption(){
        //1.创建定位服务客户端类的对象
        locationClient=new LocationClient(getApplicationContext());
        //2.创建定位客户端选项类的对象，并设置参数
        locationClientOption=new LocationClientOption();
        //设置定位参数
        //打开GPS
        locationClientOption.setOpenGps(true);
        //定位间隔时间
        //locationClientOption.setScanSpan(1000);
        //定位坐标系
        SDKInitializer.setCoordType(CoordType.GCJ02);
        //设置定位模式
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //需要定位地址数据
        locationClientOption.setIsNeedAddress(true);
        //需要地址描述
        locationClientOption.setIsNeedLocationDescribe(true);
        //需要周边POI信息
        locationClientOption.setIsNeedLocationPoiList(true);
        //3.将定位选项参数应用给定位服务客户端类的对象
        locationClient.setLocOption(locationClientOption);
        //4.开始定位
        locationClient.start();
        //5.给定位客户端类的对象注册定位监听器
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //获取经纬度
                double lat=bdLocation.getLatitude();
                double lng=bdLocation.getLongitude();
                SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("lat", String.valueOf(lat));
                editor.putString("lng", String.valueOf(lng));
                editor.apply();
            }
        });
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

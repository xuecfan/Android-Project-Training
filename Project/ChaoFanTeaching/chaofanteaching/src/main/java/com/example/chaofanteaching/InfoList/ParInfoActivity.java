package com.example.chaofanteaching.InfoList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.chaofanteaching.ChatActivity;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.utils.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.io.InputStream;
import java.net.HttpURLConnection;

import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;


public class ParInfoActivity extends AppCompatActivity {
    private static String path = "/storage/emulated/0/";// sd路径
    private String user;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private BaiduMap baiduMap;
    private MapView mapView;
    private String lat;
    private String lng;
    private TextView nametext;
    private TextView sextext;
    private TextView gradetext;
    private TextView subjecttext;
    private TextView timetext;
    private TextView pricetext;
    private TextView teltext;
    private TextView requiretext;
    private TextView locatetext;
    private Button sendbtn;
    private ImageView img;
    private ConstraintLayout shareLayout;
    private ConstraintLayout starLaylout;
    private String name;
    private SharedPreferences pre;
    private String me;
    private String infoId;
    private ImageView infopar_back;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    String str = msg.obj.toString();
                    String[] s = str.split(",");
                    name = s[0];
                    sextext.setText(s[1]);
                    gradetext.setText(s[2]);
                    subjecttext.setText(s[3]);
                    timetext.setText(s[4]);
                    pricetext.setText(s[5]+"元/小时");
                    teltext.setText(s[6]);
                    requiretext.setText(s[7]);
                    locatetext.setText(s[8]+","+s[9]);//lat,lng经纬度
                    lat=s[8];
                    lng=s[9];
                    infoId = s[10];
                    user=s[11];
                    break;
                case 2:
                    String string = msg.obj.toString();
                    if (string.equals("1")){
                        Toast.makeText(getApplication(),"收藏成功",Toast.LENGTH_SHORT).show();
                    }else if (string.equals("0")){
                        Toast.makeText(getApplication(),"收藏失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.infopar_deatil);

        //状态栏透明
        makeStatusBarTransparent(ParInfoActivity.this);
        //获取当前登录用户的用户名
        pre= getSharedPreferences("login", Context.MODE_PRIVATE);
        me = pre.getString("userName", "");

        //分享
        shareLayout = findViewById(R.id.shareLayout);
        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Share2.Builder(ParInfoActivity.this)
                        .setContentType(ShareContentType.TEXT)
                        .setTextContent("Hello!我在“超凡家教APP”上看到这个家教待遇还不错，分享给你，上超凡家教APP搜索“"+name+"”查看更多")
                        .setTitle("分享到")
                        .build()
                        .shareBySystem();
            }
        });

        //收藏
        starLaylout = findViewById(R.id.starLayout);
        starLaylout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starUser(me,user,name);
            }
        });

        img=findViewById(R.id.img);
        sendbtn=findViewById(R.id.send);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatIn();
            }
        });
        Intent request=getIntent();
        name=request.getStringExtra("name");
        String user=request.getStringExtra("user");
        Bitmap bt = BitmapFactory.decodeFile(path +user+".png");//从Sd中找头像，转换成Bitmap
        @SuppressWarnings("deprecation")
        Drawable drawable = new BitmapDrawable(bt);//转换成drawable
        img.setImageDrawable(drawable);


        //返回
        infopar_back = findViewById(R.id.infopar_back);
        infopar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        locatetext=findViewById(R.id.locate);
        nametext=findViewById(R.id.name);
        nametext.setText(name);
        sextext=findViewById(R.id.sex);
        gradetext=findViewById(R.id.grade);
        subjecttext=findViewById(R.id.subject);
        timetext=findViewById(R.id.freetime);
        pricetext=findViewById(R.id.price);
        teltext=findViewById(R.id.tel);
        requiretext=findViewById(R.id.require);
        ConstraintLayout map123=findViewById(R.id.map123);
        map123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.setClass(ParInfoActivity.this, Info_Map.class);
                startActivity(intent);
            }
        });
//        mapView = findViewById(R.id.bmapView);
//        baiduMap=mapView.getMap();
//        baiduMap.setMyLocationEnabled(true);
        dbKey(name);
//        locationOption();
//        hidelogo();//隐藏logo
//        zoomlevel();//改变比列尺
    }
    //发起聊天
    public void chatIn() {
        String name = user;
        String myName = EMClient.getInstance().getCurrentUser();
        if (!TextUtils.isEmpty(name)) {
            if (name.equals(myName)) {
                ToastUtils.showLong("不能和自己聊天");
                return;
            }
            Intent chat = new Intent(this, ChatActivity.class);
            chat.putExtra(EaseConstant.EXTRA_USER_ID, name);  //对方账号
            chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat); //单聊模式
            startActivity(chat);

        } else {
            ToastUtils.showLong("用户名不可为空");
        }
    }
    private void dbKey(final String key) {

        new Thread() {
            HttpURLConnection connection = null;

            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("InfoDetailServlet?id=1&key="+key);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = str;
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void hidelogo(){
        View child=mapView.getChildAt(1);
        if(null!=child && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
    }
    private void showLocOnMap(double lat, double lng) {
        //获取定位图标
        BitmapDescriptor icon = BitmapDescriptorFactory
                .fromResource(R.mipmap.loc1);
        //设置显示方式
        MyLocationConfiguration config=new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL,
                false,
                icon);
        baiduMap.setMyLocationConfiguration(config);
        //显示
        MyLocationData locData=new MyLocationData.Builder().latitude(lat).longitude(lng).build();
        baiduMap.setMyLocationData(locData);
        //移动到中心位置
        MapStatusUpdate msu= MapStatusUpdateFactory.newLatLng(new LatLng(lat,lng));
        baiduMap.animateMapStatus(msu);
    }
//    private void locationOption(){
//        //1.创建定位服务客户端类的对象
//        locationClient=new LocationClient(getApplicationContext());
//        //2.创建定位客户端选项类的对象，并设置参数
//        locationClientOption=new LocationClientOption();
//        //设置定位参数
//        //打开GPS
//        locationClientOption.setOpenGps(true);
//        //定位间隔时间
//        locationClientOption.setScanSpan(1000);
//        //定位坐标系
//        SDKInitializer.setCoordType(CoordType.GCJ02);
//        //设置定位模式
//        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        //需要定位地址数据
//        locationClientOption.setIsNeedAddress(true);
//        //需要地址描述
//        locationClientOption.setIsNeedLocationDescribe(true);
//        //需要周边POI信息
//        locationClientOption.setIsNeedLocationPoiList(true);
//        //3.将定位选项参数应用给定位服务客户端类的对象
//        locationClient.setLocOption(locationClientOption);
//        //4.开始定位
//        locationClient.start();
//        //5.给定位客户端类的对象注册定位监听器
//        locationClient.registerLocationListener(new BDAbstractLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                showLocOnMap(lat,lng);
//            }
//        });
//    }
    private void zoomlevel(){
        baiduMap.setMaxAndMinZoomLevel(19,13);
        MapStatusUpdate msu= MapStatusUpdateFactory.zoomTo(16);
        baiduMap.setMapStatus(msu);
    }
    private void initView() {
        //初始化控件
    }

    //收藏
    private void starUser(String collector, String collection ,String collectionName){

        new Thread(){
            HttpURLConnection connection =null;
            public void run(){
                try {
                    connection = HttpConnectionUtils
                            .getConnection("InfoDetailServlet?id=2&collector="+collector
                                    +"&collection="+collection+"&collectionName="+collectionName);
                    int code = connection.getResponseCode();
                    if (code == 200){
                        InputStream inputStream = connection.getInputStream();
                        String string = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = string;
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    //状态栏透明
    public static void makeStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}

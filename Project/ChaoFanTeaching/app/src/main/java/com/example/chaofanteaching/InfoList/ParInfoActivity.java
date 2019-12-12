package com.example.chaofanteaching.InfoList;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
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
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class ParInfoActivity extends AppCompatActivity {
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private BaiduMap baiduMap;
    private MapView mapView;
    private double lat;
    private double lng;
    private Handler handler;
    private TextView nametext;
    private TextView sextext;
    private TextView gradetext;
    private TextView subjecttext;
    private TextView timetext;
    private TextView pricetext;
    private TextView teltext;
    private TextView requiretext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.infopar_deatil);
        Intent request=getIntent();
        String name=request.getStringExtra("name");
        nametext=findViewById(R.id.name);
        nametext.setText(name);
        sextext=findViewById(R.id.sex);
        gradetext=findViewById(R.id.grade);
        subjecttext=findViewById(R.id.subject);
        timetext=findViewById(R.id.freetime);
        pricetext=findViewById(R.id.price);
        teltext=findViewById(R.id.tel);
        requiretext=findViewById(R.id.require);
        mapView = findViewById(R.id.bmapView);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        dbKey(name);
        locationOption();
        hidelogo();//隐藏logo
        zoomlevel();//改变比列尺
    }
    private void dbKey(final String key) {
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        String str = msg.obj.toString();
                        String[] s = str.split(",");
                        sextext.setText(s[0]);
                        gradetext.setText(s[1]);
                        subjecttext.setText(s[2]);
                        timetext.setText(s[3]);
                        pricetext.setText(s[4]+"元/小时");
                        teltext.setText(s[5]);
                        requiretext.setText(s[6]);
                        String lat1=s[7];
                        String lng1=s[8];
                        lat=Double.parseDouble(lat1);
                        lng=Double.parseDouble(lng1);
                        break;
                }
            }
        };
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
    private void locationOption(){
        //1.创建定位服务客户端类的对象
        locationClient=new LocationClient(getApplicationContext());
        //2.创建定位客户端选项类的对象，并设置参数
        locationClientOption=new LocationClientOption();
        //设置定位参数
        //打开GPS
        locationClientOption.setOpenGps(true);
        //定位间隔时间
        locationClientOption.setScanSpan(1000);
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
                //获取定位详细数据
                //获取地址信息
                String addr=bdLocation.getAddrStr();
                Log.i("myl","地址"+addr);
                //获取经纬度
//                double lat=bdLocation.getLatitude();
//                double lng=bdLocation.getLongitude();
                Log.i("myl","纬度"+lat+"经度"+lng);
                //获取POI
                List<Poi> pois=bdLocation.getPoiList();
//                for (Poi p:pois){
//                    String name=p.getName();
//                    String paddr=p.getAddr();
//                    Log.i("myl","poi"+name+":"+paddr);
//                }
                String time=bdLocation.getTime();
                Log.i("myl","time"+time);
                //将定位数据显示在地图上
                showLocOnMap(lat,lng);
            }
        });
    }
    private void zoomlevel(){
        baiduMap.setMaxAndMinZoomLevel(19,13);
        MapStatusUpdate msu= MapStatusUpdateFactory.zoomTo(16);
        baiduMap.setMapStatus(msu);
    }
}

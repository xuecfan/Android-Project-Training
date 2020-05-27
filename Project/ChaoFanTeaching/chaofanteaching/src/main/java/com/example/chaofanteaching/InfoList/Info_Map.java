package com.example.chaofanteaching.InfoList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.example.chaofanteaching.R;

public class Info_Map extends AppCompatActivity {
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private BaiduMap baiduMap;
    private MapView mapView;
    private ImageButton btnnavi;
    private double lat;
    private double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_map);
        makeStatusBarTransparent(Info_Map.this);
        setAndroidNativeLightStatusBar(Info_Map.this,true);
        Intent request=getIntent();
        lat=Double.parseDouble(request.getStringExtra("lat"));
        lng=Double.parseDouble(request.getStringExtra("lng"));
        mapView = findViewById(R.id.bmapView);
        btnnavi=findViewById(R.id.btnnavi);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        locationOption();
        hidelogo();//隐藏logo
        zoomlevel();//改变比列尺
        showLocOnMap(lat,lng);
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
                .fromResource(R.mipmap.loc);
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
        //打开GPS
        locationClientOption.setOpenGps(true);
        //定位间隔时间
        locationClientOption.setScanSpan(0);
        //定位坐标系
        SDKInitializer.setCoordType(CoordType.GCJ02);
        //设置定位模式
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //需要定位地址数据
        locationClientOption.setIsNeedAddress(false);
        //需要地址描述
        locationClientOption.setIsNeedLocationDescribe(false);
        //需要周边POI信息
        locationClientOption.setIsNeedLocationPoiList(false);
        //3.将定位选项参数应用给定位服务客户端类的对象
        locationClient.setLocOption(locationClientOption);
        //4.开始定位
        locationClient.start();
        //5.给定位客户端类的对象注册定位监听器
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                double mylat=bdLocation.getLatitude();
                double mylng=bdLocation.getLongitude();
                btnnavi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baidu(mylat,mylng,lat,lng);
                    }
                });
            }
        });
    }

    private void zoomlevel(){
        baiduMap.setMaxAndMinZoomLevel(19,13);
        MapStatusUpdate msu= MapStatusUpdateFactory.zoomTo(16);
        baiduMap.setMapStatus(msu);
    }

    public void baidu(double mylat, double mylng,double lat, double lng){
        //定义起终点坐标
        LatLng startPoint = new LatLng(mylat, mylng);
        LatLng endPoint = new LatLng(lat, lng);
        //构建RouteParaOption参数以及策略
        //也可以通过startName和endName来构造
        RouteParaOption paraOption = new RouteParaOption()
                .startPoint(startPoint)
                .endPoint(endPoint)
                .busStrategyType(RouteParaOption.EBusStrategyType.bus_recommend_way);
        //调起百度地图
        try {
            BaiduMapRoutePlan.openBaiduMapTransitRoute(paraOption, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //调起结束时及时调用finish方法以释放相关资源
        BaiduMapRoutePlan.finish(this);
    }

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

    private static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}
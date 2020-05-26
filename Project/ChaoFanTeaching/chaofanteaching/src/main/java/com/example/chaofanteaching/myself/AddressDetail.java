package com.example.chaofanteaching.myself;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.chaofanteaching.R;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.List;

public class AddressDetail extends AppCompatActivity {
    protected EaseTitleBar titleBar;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private BaiduMap baiduMap;
    private MapView mapView;
    private Button btn1;
    private Button btn2;
    private EditText addresstext;
    private SharedPreferences sharedPreferences;
    private double mylat;
    private double mylng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_detail);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        setTitleBar();
        mapView = findViewById(R.id.bmapView);
        addresstext=findViewById(R.id.address);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        String myAddress=sharedPreferences.getString("addressContent","");
        if(myAddress.isEmpty()){
            locationOption();
        }else{
            Double myLat=Double.parseDouble(sharedPreferences.getString("mylat",""));
            Double myLng=Double.parseDouble(sharedPreferences.getString("mylng",""));
            showLocOnMap(myLat,myLng);
            addresstext.setText(myAddress);
        }
        hidelogo();
        zoomlevel();
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mylat=latLng.latitude;
                mylng=latLng.longitude;
                showLocOnMap(latLng.latitude,latLng.longitude);
                geoCode(latLng);
            }
            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                mylat=mapPoi.getPosition().latitude;
                mylng=mapPoi.getPosition().longitude;
                showLocOnMap(mapPoi.getPosition().latitude,mapPoi.getPosition().longitude);
                addresstext.setText(mapPoi.getName()+"附近");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("addressContent",addresstext.getText().toString());
                editor.putString("mylat",String.valueOf(mylat));
                editor.putString("mylng",String.valueOf(mylng));
                editor.apply();
                finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationOption();
                //finish();
            }
        });
    }

    private void setTitleBar(){
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle("位置");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        locationClient=new LocationClient(getApplicationContext());
        locationClientOption=new LocationClientOption();
        locationClientOption.setOpenGps(true);
        SDKInitializer.setCoordType(CoordType.GCJ02);
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setIsNeedLocationDescribe(true);
        locationClientOption.setIsNeedLocationPoiList(false);
        locationClient.setLocOption(locationClientOption);
        locationClient.start();
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                addresstext.setText(bdLocation.getAddrStr().substring(2));
                mylat=bdLocation.getLatitude();
                mylng=bdLocation.getLongitude();
                showLocOnMap(mylat,mylng);
            }
        });
    }
    private void zoomlevel(){
        baiduMap.setMaxAndMinZoomLevel(19,13);
        MapStatusUpdate msu= MapStatusUpdateFactory.zoomTo(16);
        baiduMap.setMapStatus(msu);
    }
    public void geoCode(LatLng latLng){
        GeoCoder mCoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    return;
                } else {
                    //详细地址
                    String address = reverseGeoCodeResult.getAddress();
                    List<PoiInfo> pois=reverseGeoCodeResult.getPoiList();
                    if(pois!=null){
                        addresstext.setText(pois.get(0).getAddress()+pois.get(0).getName());
                    }else{
                        addresstext.setText(address);
                    }

                }
            }
        };
        mCoder.setOnGetGeoCodeResultListener(listener);
        mCoder.reverseGeoCode(new ReverseGeoCodeOption()
                .location(latLng)
                // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                .radius(500));
        mCoder.destroy();
    }
}

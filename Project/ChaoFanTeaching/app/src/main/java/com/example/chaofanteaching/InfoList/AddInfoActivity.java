package com.example.chaofanteaching.InfoList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
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
import java.net.HttpURLConnection;
import java.util.List;

public class AddInfoActivity extends AppCompatActivity {
    private MapView mapView;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private BaiduMap baiduMap;
    private EditText inName;
    private EditText inLong;
    private EditText inPay;
    private EditText inTel;
    private EditText inRequirement;
    private RadioGroup radioGroup;
    private String sex;
    private Spinner myspinner;
    private Spinner myspinner1;
    private Spinner myspinner2;
    private Spinner myspinner3;
    private Spinner myspinner4;
    private String grade;
    private String subjcet;
    private String week;
    private String hour;
    private String min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.add_info);
        mapView = findViewById(R.id.bmapView);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        locationOption();//定位
        hidelogo();//隐藏logo
        zoomlevel();//改变比列尺
        Button add_finish=findViewById(R.id.add_finish);
        radioGroup=findViewById(R.id.myradio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                sex=radioButton.getText().toString();
            }
        });
        getSpinner();
        add_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inName=findViewById(R.id.name);
                inLong=findViewById(R.id.time);
                inPay=findViewById(R.id.pay);
                inTel=findViewById(R.id.tel);
                inRequirement=findViewById(R.id.require);
                String name=inName.getText().toString();
                String ilong=inLong.getText().toString();
                String pay=inPay.getText().toString();
                String tel=inTel.getText().toString();
                String require=inRequirement.getText().toString();
                String finaltime=week+","+hour+","+min;
                dbKey(name,sex,grade,subjcet,week,hour,min,ilong,pay,tel,require);
                finish();
            }
        });
    }
    private void getSpinner(){
        myspinner=findViewById(R.id.gradespinner);
        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grade=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择年级",Toast.LENGTH_LONG).show();
            }
        });
        myspinner1=findViewById(R.id.subjectspinner);
        myspinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjcet=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择科目",Toast.LENGTH_LONG).show();
            }
        });
        myspinner2=findViewById(R.id.weekspinner);
        myspinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                week=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择时间",Toast.LENGTH_LONG).show();
            }
        });
        myspinner3=findViewById(R.id.hourspinner);
        myspinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hour=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择时间",Toast.LENGTH_LONG).show();
            }
        });
        myspinner4=findViewById(R.id.minspinner);
        myspinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                min=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择时间",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void hidelogo(){
        View child=mapView.getChildAt(1);
        if(null!=child && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
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
                double lat=bdLocation.getLatitude();
                double lng=bdLocation.getLongitude();
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
    private void zoomlevel(){
        baiduMap.setMaxAndMinZoomLevel(19,13);
        MapStatusUpdate msu= MapStatusUpdateFactory.zoomTo(16);
        baiduMap.setMapStatus(msu);
    }
    private void dbKey(final String name, final String sex, final String grade, final String subject, final String week, final String hour, final String min, final String ilong, final String pay, final String tel, final String require) {
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("AddInfoServlet?name="+name+"&sex="+sex+"&grade="+grade+"&subject="+subject+"&week="+week+"&hour="+hour+"&min="+min+"&len="+ilong+"&pay="+pay+"&tel="+tel+"&require="+require);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        Toast.makeText(getApplication(),"添加信息成功",Toast.LENGTH_LONG);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
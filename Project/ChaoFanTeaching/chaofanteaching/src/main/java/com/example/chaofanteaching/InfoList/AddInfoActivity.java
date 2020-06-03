package com.example.chaofanteaching.InfoList;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.comments.UtilHelpers;
import com.example.chaofanteaching.publish.ParentPublishActivity;
import com.example.chaofanteaching.utils.ToastUtils;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class AddInfoActivity extends AppCompatActivity {
    private SharedPreferences pre;
    private SharedPreferences pre1;
    private String a="";
    private TextView hourtext;
    private MapView mapView;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private BaiduMap baiduMap;
    private TextView inName;
    private EditText inLong;
    private EditText inPay;
    private EditText inTel;
    private EditText inRequirement;
    private EditText inlocation;
    private RadioGroup radioGroup;
    private Spinner myspinner;
    private Spinner myspinner1;
    private Spinner myspinner2;
    private String name;
    private String sex;
    private String grade;
    private String subject;
    private String week;
    private String address;
    private String mylat;
    private String mylng;
    private String locate;
    private int mHour;
    private int mMin;
    protected EaseTitleBar titleBar;
    private Button add_finish;
    private ImageButton btn2;
    private ScrollView scrollView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String string = msg.obj.toString();
            switch(msg.what){
                case 0:
                    if (string.equals("1")){
                        Toast.makeText(AddInfoActivity.this, "发布信息成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(AddInfoActivity.this, "发布失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1:
                    Log.e("xcf-handler-case1",string);
                    String[] strings = string.split(",");
                    inName.setText(strings[0]);//设置用户名
                    showLocOnMap(Double.parseDouble(strings[1]),Double.parseDouble(strings[2]));//初始化地图控件
                    geoCode(new LatLng(Double.parseDouble(strings[1]),Double.parseDouble(strings[2])));//初始化位置输入框内容
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.add_info);

        initView();
        pre= getSharedPreferences("login", Context.MODE_PRIVATE);
        pre1= getSharedPreferences("data", Context.MODE_PRIVATE);
        a = pre.getString("userName", "");
        connectDB("MyData?index=rname&name="+a,1);//获取用户名
//        name=pre1.getString("nameContent","");
//        name = inName.getText().toString();//获取用户名
        address=pre1.getString("addressContent","");
        mylat=pre1.getString("mylat","114.53952");
        mylng=pre1.getString("mylng","38.03647");
        locate=mylat+","+mylng;
        showLocOnMap(Double.parseDouble(mylat),Double.parseDouble(mylng));
//        inName.setText(name);
        inlocation.setText(address);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationOption();
            }
        });
        hourtext=findViewById(R.id.hour_min);
        hourtext.setText("8:00");
        TimePickerDialog timePickerDialog=new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour=hourOfDay;
                mMin=minute;
                if(mMin<10){
                    hourtext.setText(mHour+":0"+mMin);
                }else{
                    hourtext.setText(mHour+":"+mMin);
                }
            }
        },8,00,true);
        hourtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        baiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {//解决scroll和map冲突
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    scrollView.requestDisallowInterceptTouchEvent(false);
                }else{
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
            }
        });
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                showLocOnMap(latLng.latitude,latLng.longitude);
                geoCode(latLng);
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                showLocOnMap(mapPoi.getPosition().latitude,mapPoi.getPosition().longitude);
                inlocation.setText(mapPoi.getName()+"附近");
            }
        });
        hidelogo();//隐藏logo
        zoomlevel();//改变比列尺
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
                name = inName.getText().toString();//获取用户名

                String ilong=inLong.getText().toString();
                String pay=inPay.getText().toString();
                String tel=inTel.getText().toString();
                String require=inRequirement.getText().toString();
                String time=hourtext.getText().toString();
                if(!ilong.isEmpty()&&!pay.isEmpty()&&!tel.isEmpty()&&!locate.isEmpty()){
                    connectDB("AddInfoServlet?id=0&name="+name
                            +"&sex="+sex
                            +"&grade="+grade
                            +"&subject="+subject
                            +"&week="+week
                            +"&hour="+time
                            +"&len="+ilong
                            +"&pay="+pay
                            +"&tel="+tel
                            +"&require=" +require
                            +"&user="+a
                            +"&locate="+locate,0);
//                    dbKey(name,sex,grade, subject,week,time,ilong,pay,tel,require,a,locate);
                    finish();
                }else if(ilong.isEmpty()&&!pay.isEmpty()&&!tel.isEmpty()&&!locate.isEmpty()){
                    Toast.makeText(getApplicationContext(),"您提交的补课时长为空,请重新提交",Toast.LENGTH_SHORT).show();
                }else if(!ilong.isEmpty()&&pay.isEmpty()&&!tel.isEmpty()&&!locate.isEmpty()){
                    Toast.makeText(getApplicationContext(),"您提交的课时费用为空,请重新提交",Toast.LENGTH_SHORT).show();
                }else if(!ilong.isEmpty()&&!pay.isEmpty()&&tel.isEmpty()&&!locate.isEmpty()){
                    Toast.makeText(getApplicationContext(),"您提交的联系电话为空,请重新提交",Toast.LENGTH_SHORT).show();
                }else if(!ilong.isEmpty()&&!pay.isEmpty()&&!tel.isEmpty()&&locate.isEmpty()){
                    Toast.makeText(getApplicationContext(),"您提交的位置为空,请重新提交",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"您提交的内容有空值,请重新提交",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     *点击editview外隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                UtilHelpers.hideKeyboard(event,view, AddInfoActivity.this);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
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
                    Toast.makeText(AddInfoActivity.this, "请点击定位按钮定位", Toast.LENGTH_SHORT).show();
                    //没有找到检索结果
                    return;
                } else {
                    //详细地址
                    String address = reverseGeoCodeResult.getAddress();
                    List<PoiInfo> pois=reverseGeoCodeResult.getPoiList();
                    if(pois!=null){
                        inlocation.setText(pois.get(0).getAddress()+pois.get(0).getName());
                    }else{
                        inlocation.setText(address);
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
    public void initView(){
        scrollView=findViewById(R.id.scroll);
        inName=findViewById(R.id.name);
        inLong=findViewById(R.id.time);
        inPay=findViewById(R.id.pay);
        inTel=findViewById(R.id.tel);
        inRequirement=findViewById(R.id.require);
        inlocation=findViewById(R.id.addmylocation);
        add_finish=findViewById(R.id.add_finish);
        radioGroup=findViewById(R.id.myradio);
        mapView = findViewById(R.id.bmapView);
        btn2=findViewById(R.id.btn2);
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle("添加信息");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
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
                subject =parent.getItemAtPosition(position).toString();
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
    }
    private void hidelogo(){
        View child=mapView.getChildAt(1);
        if(null!=child && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
    }
    private void locationOption(){
        zoomlevel();
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
                inlocation.setText(bdLocation.getAddrStr());
                //获取经纬度
                double lat=bdLocation.getLatitude();
                double lng=bdLocation.getLongitude();
                locate= String.valueOf(lat+","+lng);
                //将定位数据显示在地图上
                showLocOnMap(lat,lng);
            }
        });
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
    private void zoomlevel(){
        baiduMap.setMaxAndMinZoomLevel(19,13);
        MapStatusUpdate msu= MapStatusUpdateFactory.zoomTo(16);
        baiduMap.setMapStatus(msu);
    }

    /**
     * 连接数据库
     */
    private void connectDB(String path, int what) {
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection(path);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = str;
                        message.what = what;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void dbKey(final String name, final String sex, final String grade, final String subject, final String week, final String hour, final String ilong, final String pay, final String tel, final String require,final String user,final String locate) {
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("AddInfoServlet?id=0&name="+name+"&sex="+sex+"&grade="+grade+"&subject="+subject+"&week="+week+"&hour="+hour+"&len="+ilong+"&pay="+pay+"&tel="+tel+"&require="+require+"&user="+user+"&locate="+locate);
                    int code = connection.getResponseCode();
                    if (code != 200) {
                        ToastUtils.showLong("网络错误！请稍后再试");
                    }else if(code==200){
                        ToastUtils.showLong("添加成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
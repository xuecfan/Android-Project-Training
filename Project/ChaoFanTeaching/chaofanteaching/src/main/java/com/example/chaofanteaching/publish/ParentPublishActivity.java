package com.example.chaofanteaching.publish;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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
import com.hyphenate.easeui.widget.EaseTitleBar;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class ParentPublishActivity extends AppCompatActivity {

    protected EaseTitleBar titleBar;

    private String userName;
    private String role;

    private TextView[] cues;

    private LinearLayout freeTimeLayout;
    private LinearLayout[] layouts;

    private TextView nameTxt;
    private TextView sexTxt;
    private TextView gradeTxt;
    private TextView courseTxt;
    private TextView freeWeekTxt;
    private TextView freeTimeTxt;
    private TextView durationTxt;
    private TextView[] textViews;

    private EditText feeEdit;
    private EditText phoneEdit;
    private EditText requirementEdit;
//    private EditText locationEdit;
    private EditText[] edits;

    private Button deleteBtn;
    private Button saveBtn;

    //地图
    private ScrollView scrollView;
    private EditText inlocation;
    private MapView mapView;
    private ImageButton btn2;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private String locate;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String string = msg.obj.toString();
            switch(msg.what){
                case 0:
                    Log.e("xcf_handler",string);
                    //strings数组有12个元素，顺序：薛家长,男,小学,数学,2,每周日,8:00,70,19930511535,要求女生,38.005897,114.51474
                    String[] strings = string.split(",");
                    //设初值
                    setTitleBar(strings[0]);//标题栏
                    for (int i=0;i<textViews.length;i++){//设置strings0-6号元素
                        textViews[i].setText(strings[i]);
                    }
                    for (int i=0;i<edits.length;i++){//设置strings7-9号元素
                        edits[i].setText(strings[i+7]);
                    }
                    double lat=Double.parseDouble(strings[10]);
                    double lng=Double.parseDouble(strings[11]);
                    geoCode(new LatLng(lat,lng));//初始化位置编辑框内地址
                    locate=strings[10] + "," + strings[11];//设置locate初始值，避免保存报空
                    showLocOnMap(lat,lng);//设置地图控件初始值
                    setAlert(layouts, textViews, cues, strings);//初始化点击layout弹窗
                    break;
                case 1:
                    if (string.equals("1")){
                        Toast.makeText(ParentPublishActivity.this, "删除信息成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(ParentPublishActivity.this, "删除失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    if (string.equals("1")){
                        Toast.makeText(ParentPublishActivity.this, "修改信息成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(ParentPublishActivity.this, "修改失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_publish);

        initView();//初始化view
        connectDB("MyData?index=init&name="+userName+"&role="+role, 0);//初始化数据
        //地图
//        SharedPreferences pre1= getSharedPreferences("data", Context.MODE_PRIVATE);
//        String mylat=pre1.getString("mylat","114.53952");
//        String mylng=pre1.getString("mylng","38.03647");
//        locate=mylat+","+mylng;
//        showLocOnMap(Double.parseDouble(mylat),Double.parseDouble(mylng));
//        inlocation.setText(pre1.getString("addressContent",""));
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationOption();
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

        //两个按钮
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectDB("MyPublish?op=delete&user="+userName+"&role="+role, 1);//删除信息
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectDB("MyPublish?op=editParent&user="+userName
                        +"&name="+nameTxt.getText()
                        +"&sex="+sexTxt.getText()
                        +"&grade="+gradeTxt.getText()
                        +"&course="+courseTxt.getText()
                        +"&freeWeek="+freeWeekTxt.getText()
                        +"&freeTime="+freeTimeTxt.getText()
                        +"&duration="+durationTxt.getText()
                        +"&fee="+feeEdit.getText()
                        +"&phone="+phoneEdit.getText()
                        +"&requirement="+requirementEdit.getText()
                        +"&location="+locate, 2);//保存信息
            }
        });

        //时间选择器
        freeTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(ParentPublishActivity.this,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int mHour=hourOfDay;
                        int mMin=minute;
                        if(mMin<10){
                            freeTimeTxt.setText(mHour+":0"+mMin);
                        }else{
                            freeTimeTxt.setText(mHour+":"+mMin);
                        }
                    }
                },8,00,true);
                timePickerDialog.show();
            }
        });
    }

    /**
     * 通过经纬度获取详细地址
     * @param latLng
     */
    public void geoCode(LatLng latLng){
        Log.e("xcf-handler","666");
        GeoCoder mCoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(ParentPublishActivity.this, "请点击定位按钮定位", Toast.LENGTH_SHORT).show();
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
     * 初始化点击layout弹窗
     * @param layouts
     * @param textViews
     * @param cues
     * @param strings
     */
    private void setAlert(LinearLayout[] layouts, TextView[] textViews, TextView[] cues, String[] strings) {

//        Log.e("xcf-userNameFromStrings",""+userNameFromStrings);
        String[] item0 = new String[]{strings[0]};
        String[] item1 = new String[]{"男","女"};
        String[] item2 = new String[]{"小学","初一","初二","初三","高一","高二","高三"};
        String[] item3 = new String[]{"语文","数学","英语","物理","化学","生物","历史","政治","地理","音乐","美术","其它"};
        String[] item4 = new String[]{"1","1.5","2","2.5","3","3.5","4"};
        String[] item5 = new String[]{"周日","周一","周二","周三","周四","周五","周六"};
        String[][] items = new String[][]{item0,item1,item2,item3,item4,item5};
        for (int j = 0; j < layouts.length; j++){//j∈(0,9)
            int finalJ = j;
            layouts[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ParentPublishActivity.this)
                            .setItems(items[finalJ], new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.e("xcf-alert","layouts="+layouts[finalJ]+"textViews="+textViews[finalJ]+"items="+items[finalJ][i]+"strings="+strings[finalJ]+"cues="+cues[finalJ]);
//                                    if(finalJ<8){
                                        if (!items[finalJ][i].equals(strings[finalJ])){ cues[finalJ].setTextColor(getResources().getColor(R.color.colorTheme)); }
                                        else { cues[finalJ].setTextColor(getResources().getColor(R.color.black_deep)); }
//                                    } else {
//                                        if (!items[finalJ][i].equals(strings[finalJ])){ cues[8].setTextColor(getResources().getColor(R.color.colorTheme)); }
//                                        else { cues[8].setTextColor(getResources().getColor(R.color.black_deep)); }
//                                    }
                                    textViews[finalJ].setText(items[finalJ][i]);
                                }
                            })
                            .create();
                    alertDialog.show();
                }
            });
        }
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

    /**
     * 初始化view
     */
    private void initView() {
        //获取sharedPreferences
        SharedPreferences pre = getSharedPreferences("login", Context.MODE_PRIVATE);
        userName= pre.getString("userName","null");
        role= pre.getString("role","null");
        //findViewById
        titleBar = findViewById(R.id.title_bar);

        TextView nameCue = findViewById(R.id.parent_publish_name_cue);
        TextView sexCue = findViewById(R.id.parent_publish_sex_cue);
        TextView gradeCue = findViewById(R.id.parent_publish_grade_cue);
        TextView courseCue = findViewById(R.id.parent_publish_course_cue);
        TextView freeCue = findViewById(R.id.parent_publish_free_cue);
        TextView durationCue = findViewById(R.id.parent_publish_duration_cue);
        TextView feeCue = findViewById(R.id.parent_publish_fee_cue);
        TextView phoneCue = findViewById(R.id.parent_publish_phone_cue);
        TextView requirementCue = findViewById(R.id.parent_publish_requirement_cue);
        TextView locationCue = findViewById(R.id.parent_publish_location_cue);
        //补习时长在补习时间前面
        cues = new TextView[]{nameCue, sexCue, gradeCue, courseCue, durationCue, freeCue, feeCue, phoneCue, requirementCue, locationCue};//10个元素

        nameTxt = findViewById(R.id.parent_publish_name);
        sexTxt = findViewById(R.id.parent_publish_sex);
        gradeTxt = findViewById(R.id.parent_publish_grade);
        courseTxt = findViewById(R.id.parent_publish_course);
        freeWeekTxt = findViewById(R.id.parent_publish_free_week);
        freeTimeTxt = findViewById(R.id.parent_publish_free_time);
        durationTxt = findViewById(R.id.parent_publish_duration);
        textViews = new TextView[]{nameTxt,sexTxt,gradeTxt,courseTxt,durationTxt,freeWeekTxt,freeTimeTxt};//6个元素

        LinearLayout nameLayout = findViewById(R.id.parent_publish_name_layout);
        LinearLayout sexLayout = findViewById(R.id.parent_publish_sex_layout);
        LinearLayout gradeLayout = findViewById(R.id.parent_publish_grade_layout);
        LinearLayout courseLayout = findViewById(R.id.parent_publish_course_layout);
        LinearLayout freeWeekLayout = findViewById(R.id.parent_publish_free_week_layout);
        freeTimeLayout = findViewById(R.id.parent_publish_free_time_layout);
        LinearLayout durationLayout = findViewById(R.id.parent_publish_duration_layout);
        layouts = new LinearLayout[]{nameLayout, sexLayout, gradeLayout, courseLayout, durationLayout, freeWeekLayout};//6个元素

        feeEdit = findViewById(R.id.parent_publish_fee_edit);
        phoneEdit = findViewById(R.id.parent_publish_phone_edit);
        requirementEdit = findViewById(R.id.parent_publish_requirement_edit);
//        locationEdit = findViewById(R.id.addmylocation);
        edits = new EditText[]{feeEdit, phoneEdit, requirementEdit};//3个元素

        //地图
        scrollView=findViewById(R.id.scroll);
        inlocation=findViewById(R.id.addmylocation);
        mapView = findViewById(R.id.bmapView);
        btn2=findViewById(R.id.btn2);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        deleteBtn = findViewById(R.id.teacher_publish_delete_btn);
        saveBtn = findViewById(R.id.teacher_publish_save_btn);
    }

    /**
     * 标题栏
     */
    public void setTitleBar(String s){
        titleBar.setTitle(s+"发布的信息");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 点editview外隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                UtilHelpers.hideKeyboard(ev,view, ParentPublishActivity.this);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}

package com.example.chaofanteaching.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.InfoList.Info;
import com.example.chaofanteaching.InfoList.ParInfoActivity;
import com.example.chaofanteaching.InfoList.ParInfoAdapter;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.sign.LoginActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

//展示家长列表
public class ParentList extends Fragment {
    private HashMap<String,Object> map=null;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private java.util.List<Info> infoList = new ArrayList<>();
    private View view;
    private Handler handler;
    private ListView infolist;
    private ParInfoAdapter parInfoAdapter;
    private EditText editText;
    private SharedPreferences pre;
    private String a;
    private SmartRefreshLayout srl;
    private TextView def;
    private TextView dis;
    private TextView dis1;
    private TextView pri;
    private TextView pri1;
    private TextView exp;
    private double lat;
    private double lng;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list, container, false);
        pre= getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        a = pre.getString("userName", "");
        locationOption();
        loadInfo();
        refresh();
        sort();
        serach();
        jumpToDetail();
        return view;
    }

    private void locationOption(){
        //1.创建定位服务客户端类的对象
        locationClient=new LocationClient(getContext());
        //2.创建定位客户端选项类的对象，并设置参数
        locationClientOption=new LocationClientOption();
        //打开GPS
        locationClientOption.setOpenGps(true);
        SDKInitializer.setCoordType(CoordType.GCJ02);
        //设置定位模式
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //3.将定位选项参数应用给定位服务客户端类的对象
        locationClient.setLocOption(locationClientOption);
        //4.开始定位
        locationClient.start();
        //5.给定位客户端类的对象注册定位监听器
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //获取经纬度
                lat=bdLocation.getLatitude();
                lng=bdLocation.getLongitude();
                if(bdLocation.getLocType()==62){
                    locationClient.restart();
                }
                Log.e("myl","定位结果"+bdLocation.getLocType()+","+lat);
            }
        });
    }//获取定位

    public void loadInfo(){
        infolist=view.findViewById(R.id.infolist);
        infoList.clear();
        parInfoAdapter = new ParInfoAdapter(this.getContext(), infoList, R.layout.info_item1);
        infolist.setAdapter(parInfoAdapter);
        dbKey("serach1","");
        parInfoAdapter.notifyDataSetChanged();
    }//加载信息

    public void jumpToDetail(){//跳转到详情页
        infolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (a.equals("")) {
                    Toast.makeText(getContext(),"请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("name", infoList.get(position).getName());
                    intent.putExtra("user",infoList.get(position).getUser());
                    intent.setClass(getActivity(), ParInfoActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void serach(){
        editText=view.findViewById(R.id.input);
        editText.setHint("点击搜索");
        Drawable drawable=getResources().getDrawable(R.drawable.find);
        drawable.setBounds(0,0,60,60);//第一0是距左边距离，第二0是距上边距离
        editText.setCompoundDrawables(drawable,null,null,null);//只放左边
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                editText.setCursorVisible(true);
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String key=editText.getText().toString();
                    dbKey("serach1",key);
                    editText.setCursorVisible(true);
                }
                return false;
            }
        });
    }//搜索

    public void refresh(){
        srl = view.findViewById(R.id.srl);
        srl.setReboundDuration(1000);
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srl.finishRefresh();
                infoList.clear();
                dbKey("serach1","");
                Toast.makeText(getContext(),
                        "刷新完成",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }//刷新

    public void sort() {
        DrawerLayout drawerLayout=view.findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        Button btnadd = view.findViewById(R.id.add);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        def=view.findViewById(R.id.def);
        dis=view.findViewById(R.id.dis);//性别
        dis1=view.findViewById(R.id.dis1);
        pri=view.findViewById(R.id.pri);
        pri1=view.findViewById(R.id.pri1);
        exp=view.findViewById(R.id.exp);
        exp.setVisibility(View.GONE);
        Drawable defimg=getResources().getDrawable(R.drawable.defimg);
        Drawable boy=getResources().getDrawable(R.drawable.sex1);
        Drawable girl=getResources().getDrawable(R.drawable.sex0);
        Drawable up=getResources().getDrawable(R.drawable.up);
        Drawable down=getResources().getDrawable(R.drawable.down);
        Drawable expimg=getResources().getDrawable(R.drawable.exp1);
        defimg.setBounds(0,0,60,60);
        boy.setBounds(0,0,60,60);
        girl.setBounds(0,0,60,60);
        up.setBounds(0,0,50,50);
        down.setBounds(0,0,50,50);
        expimg.setBounds(0,0,50,50);
        def.setCompoundDrawables(null,null,defimg,null);
        dis.setCompoundDrawables(null,null,boy,null);
        dis1.setCompoundDrawables(null,null,girl,null);
        pri.setCompoundDrawables(null,null,up,null);
        pri1.setCompoundDrawables(null,null,down,null);
        //exp.setCompoundDrawables(null,null,expimg,null);
        def.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def.setTextColor(Color.parseColor("#ff9d4d"));
                dis.setTextColor(0x8A000000);
                dis1.setTextColor(0x8A000000);
                pri.setTextColor(0x8A000000);
                pri1.setTextColor(0x8A000000);
                exp.setTextColor(0x8A000000);
                infoList.clear();
                dbKey("serach1","");
            }
        });
        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def.setTextColor(0x8A000000);
                dis.setTextColor(Color.parseColor("#ff9d4d"));
                dis1.setTextColor(0x8A000000);
                pri.setTextColor(0x8A000000);
                pri1.setTextColor(0x8A000000);
                exp.setTextColor(0x8A000000);
                infoList.clear();
                dbKey("sex1","男");
            }
        });
        dis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def.setTextColor(0x8A000000);
                dis1.setTextColor(Color.parseColor("#ff9d4d"));
                dis.setTextColor(0x8A000000);
                pri.setTextColor(0x8A000000);
                pri1.setTextColor(0x8A000000);
                exp.setTextColor(0x8A000000);
                infoList.clear();
                dbKey("sex1","女");
            }
        });
        pri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def.setTextColor(0x8A000000);
                dis1.setTextColor(0x8A000000);
                dis.setTextColor(0x8A000000);
                pri.setTextColor(Color.parseColor("#ff9d4d"));
                pri1.setTextColor(0x8A000000);
                exp.setTextColor(0x8A000000);
                infoList.clear();
                dbKey("upprice1","");
            }
        });
        pri1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def.setTextColor(0x8A000000);
                dis1.setTextColor(0x8A000000);
                dis.setTextColor(0x8A000000);
                pri1.setTextColor(Color.parseColor("#ff9d4d"));
                pri.setTextColor(0x8A000000);
                exp.setTextColor(0x8A000000);
                infoList.clear();
                dbKey("downprice1","");
            }
        });
//        exp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                def.setTextColor(0x8A000000);
//                dis1.setTextColor(0x8A000000);
//                dis.setTextColor(0x8A000000);
//                pri1.setTextColor(0x8A000000);
//                pri.setTextColor(0x8A000000);
//                exp.setTextColor(Color.parseColor("#ff9d4d"));
//                infoList.clear();
//                dbKey("sortexp","");
//            }
//        });
    }//排序

    public String showDisdance(double mylat, double mylng, double lat, double lng){
        int dis=(int) DistanceUtil. getDistance(new LatLng(mylat,mylng),new LatLng(lat,lng));

        if(dis<1000){
            return dis+"m";
        }else {
            return dis/1000+"km";
        }
    }//计算距离

    private void dbKey(final String op,final String key) {
        infoList.clear();
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        Info scanInfo;
                        String str = msg.obj.toString();
                        if(str.isEmpty()){
                            Toast.makeText(getContext(),"这里空空如也",Toast.LENGTH_LONG).show();
                        }else{
                            String[] s = str.split(";");
                            for (int i = 0; i < s.length; i++) {
                                String[] r = s[i].split(",");
                                scanInfo = new Info(r[0], r[1]+"生", r[2], r[3]+"元/小时",r[4],showDisdance(lat,lng,Double.parseDouble(r[5]),Double.parseDouble(r[6])),r[7]);
                                infoList.add(scanInfo);
                                parInfoAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                }
            }
        };
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    if(lat==0.0){
                        Thread.sleep(350);//延迟350ms用于百度地图定位
                    }
                    connection = HttpConnectionUtils.getConnection("ListInfoServlet?op="+op+"&key="+key);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);
                        Message message = Message.obtain();
                        message.obj = str;
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }//查询数据库
}
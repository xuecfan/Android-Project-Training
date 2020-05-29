package com.example.chaofanteaching.order;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.InfoList.Info_Map;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.myself.AddressDetail;
import com.example.chaofanteaching.utils.ToastUtils;
import com.hyphenate.easeui.widget.EaseTitleBar;
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class SubmitOrder extends AppCompatActivity {
    private SharedPreferences pre;
    private SharedPreferences pre1;
    protected EaseTitleBar titleBar;
    private Spinner gradeSpinner;
    private Spinner subjectSpinner;
    private Spinner lengthSpinner;
    private TextView title_pay;
    private TextView title_tel;
    private TextView title_more;
    private EditText paytext;
    private EditText teltext;
    private EditText moretext;
    private TextView info_title;
    private TextView obj;
    private TextView datetext;
    private TextView timetext;
    private TextView loctext;
    private int id;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private String H;
    private String M;
    private int mMin;
    private double lat;
    private double lng;
    private String user;
    private String objuser;
    private String objusername;
    private String grade;
    private String subject;
    private String length;
    private Button btn_sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trialteaching);
        pre=getSharedPreferences("login",MODE_PRIVATE);
        pre1= getSharedPreferences("data",MODE_PRIVATE);
        String role=pre.getString("role","");
        user=pre.getString("userName","");
        initView();
        getSpinner();
        Intent request=getIntent();
        objuser=request.getStringExtra("user");
        if(role.equals("10")){//家长发起试讲
            info_title.setText("试讲老师：");
            objusername=request.getStringExtra("teacher");
            obj.setText(objusername);
            String address=pre1.getString("addressContent","");
            if(address.isEmpty()){
                loctext.setText("点此获取位置");
            }else {
                loctext.setText(address);
            }
            loctext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SubmitOrder.this, AddressDetail.class);
                    startActivityForResult(intent,1);
                }
            });
        }else{//老师发起试讲
            objusername=request.getStringExtra("name");
            obj.setText(objusername);
            String mylat=request.getStringExtra("lat");
            String mylng=request.getStringExtra("lng");
            lat=Double.parseDouble(mylat);
            lng=Double.parseDouble(mylng);
            geoCode(new LatLng(lat,lng));
            loctext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SubmitOrder.this, Info_Map.class);
                    intent.putExtra("lat",mylat);
                    intent.putExtra("lng",mylng);
                    startActivity(intent);
                }
            });
        }
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH)+1;
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        int h=ca.get(Calendar.HOUR_OF_DAY);
        int m=ca.get(Calendar.MINUTE);
        if(h<10){
            H="0"+h;
        }else{
            H=String.valueOf(h);
        }
        if(m<10){
            M="0"+m;
        }else{
            M=String.valueOf(m);
        }
        Random random=new Random();
        int temp_num=random.nextInt(100);
        String temp=mYear+mMonth+mDay+H+M+(temp_num);
        id=Integer.parseInt(temp);
        Log.e("myl","id="+id);
        datetext.setText(mYear+"-"+mMonth+"-"+mDay);
        timetext.setText("8:00");
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month+1;
                        mDay = dayOfMonth;
                        datetext.setText(mYear+"-"+mMonth+"-"+mDay);
                    }
                },
                mYear, mMonth-1, mDay);
        TimePickerDialog timePickerDialog=new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour=hourOfDay;
                mMin=minute;
                if(mMin<10){
                    timetext.setText(mHour+":0"+mMin);
                }else{
                    timetext.setText(mHour+":"+mMin);
                }
            }
        },8,00,true);
        datetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        timetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });
        paytext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Drawable money=getResources().getDrawable(R.drawable.money1);
                money.setBounds(0,0,60,60);
                paytext.setCompoundDrawables(null,null,money,null);
                teltext.setCompoundDrawables(null,null,null,null);
                moretext.setCompoundDrawables(null,null,null,null);
                title_pay.setTextColor(Color.parseColor("#D8900A"));
                title_tel.setTextColor(0x8A000000);
                title_more.setTextColor(0x8A000000);
            }
        });
        teltext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Drawable tel=getResources().getDrawable(R.drawable.tel);
                tel.setBounds(0,0,70,70);
                teltext.setCompoundDrawables(null,null,tel,null);
                paytext.setCompoundDrawables(null,null,null,null);
                moretext.setCompoundDrawables(null,null,null,null);
                title_pay.setTextColor(0x8A000000);
                title_tel.setTextColor(Color.parseColor("#D8900A"));
                title_more.setTextColor(0x8A000000);
            }
        });
        moretext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Drawable more=getResources().getDrawable(R.drawable.more);
                more.setBounds(0,0,60,60);
                title_pay.setTextColor(0x8A000000);
                title_tel.setTextColor(0x8A000000);
                title_more.setTextColor(Color.parseColor("#D8900A"));
                moretext.setCompoundDrawables(null,null,more,null);
                paytext.setCompoundDrawables(null,null,null,null);
                teltext.setCompoundDrawables(null,null,null,null);
            }
        });
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date=datetext.getText().toString();
                String time=timetext.getText().toString();
                String location=loctext.getText().toString();
                String pay=paytext.getText().toString();
                String tel=teltext.getText().toString();
                String more=moretext.getText().toString();
                if(!pay.isEmpty()&&!tel.isEmpty()){
                    submitOrder(id,user,objusername,grade,subject,date,time,location,length,pay,tel,more);
                }
                else if(pay.isEmpty()&&!tel.isEmpty()){
                    Toast.makeText(getApplicationContext(),"您提交的费用为空,请重新提交",Toast.LENGTH_SHORT).show();
                }else if(!pay.isEmpty()&&tel.isEmpty()){
                    Toast.makeText(getApplicationContext(),"您提交的电话为空,请重新提交",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"您提交的内容有空值,请重新提交",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==1){
            String address=pre1.getString("addressContent","");
            if(address.isEmpty()){
                loctext.setText("点此获取位置");
            }else {
                loctext.setText(address);
            }
        }
    }
    public void initView(){
        titleBar=findViewById(R.id.title_bar);
        info_title=findViewById(R.id.info_title);
        gradeSpinner=findViewById(R.id.gradespinner);
        subjectSpinner=findViewById(R.id.subjectspinner);
        lengthSpinner=findViewById(R.id.lengthspinner);
        timetext=findViewById(R.id.time);
        datetext=findViewById(R.id.date);
        obj=findViewById(R.id.object);
        loctext=findViewById(R.id.textloc);
        Drawable place=getResources().getDrawable(R.drawable.place);
        place.setBounds(0,0,60,60);
        loctext.setCompoundDrawables(null,null,place,null);
        title_pay=findViewById(R.id.titile_pay);
        paytext=findViewById(R.id.pay);
        title_tel=findViewById(R.id.titile_tel);
        teltext=findViewById(R.id.tel);
        title_more=findViewById(R.id.titile_more);
        moretext=findViewById(R.id.more);
        btn_sub=findViewById(R.id.submit);
        setTitie();
    }
    public void setTitie(){
        titleBar.setTitle("填写试讲信息");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void getSpinner(){
        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grade=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择年级",Toast.LENGTH_LONG).show();
            }
        });
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择科目",Toast.LENGTH_LONG).show();
            }
        });
        lengthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                length=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择时长",Toast.LENGTH_LONG).show();
            }
        });
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
                        for(PoiInfo p:pois){
                            Log.i("poi",p.getName());
                            Log.i("poi",p.getAddress());
                        }
                        loctext.setText(pois.get(0).getAddress()+pois.get(0).getName());
                    }else{
                        loctext.setText(address);
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
    public void submitOrder(int id,String user,String objusername,String grade,String subject,String date,String time,String location,String length,String pay,String tel,String more){
        new Thread(){
            HttpURLConnection connection =null;
            public void run(){
                try {
                    connection = HttpConnectionUtils
                            .getConnection("SubmitOrder?user="+user+"&id="+id+"&objuser="+objuser+"&objusername="+objusername+"&grade="+grade+"&subject="+subject+"&date="+date+"&time="+time+"&location="+location+"&length="+length+"&pay="+pay+"&tel="+tel+"&more="+more);
                    int code = connection.getResponseCode();
                    if (code != 200){
                        ToastUtils.showLong("网络错误！请稍后再试");
                    }else{
                        Intent i=new Intent(SubmitOrder.this,SubSuccess.class);
                        i.putExtra("id",String.valueOf(id));
                        startActivity(i);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
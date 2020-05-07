package com.example.chaofanteaching.order;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.chaofanteaching.R;
import com.hyphenate.easeui.widget.EaseTitleBar;
import java.util.Calendar;
import java.util.List;

public class TrialteachingByTeacher extends AppCompatActivity {
    private SharedPreferences pre;
    protected EaseTitleBar titleBar;
    private Spinner gradeSpinner;
    private Spinner subjectSpinner;
    private Spinner timeSpinner;
    private EditText lengthtext;
    private EditText paytext;
    private EditText teltext;
    private EditText moretext;
    private TextView student;
    private TextView date;
    private TextView loctext;
    private int mYear;
    private int mMonth;
    private int mDay;
    private double lat;
    private double lng;
    private String grade;
    private String subject;
    private String time;
    private Button btn_sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trialteaching);
        pre=getSharedPreferences("login",MODE_PRIVATE);
        String role=pre.getString("role","");
        initView();
        setTitie();
        getSpinner();
        Intent request=getIntent();
        String studentname=request.getStringExtra("name");
        student.setText(studentname);
        if(role.equals("10")){
            loctext.setText("附近");
        }else{
            lat=Double.parseDouble(request.getStringExtra("lat"));
            lng=Double.parseDouble(request.getStringExtra("lng"));
            geoCode(new LatLng(lat,lng));
        }
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH)+1;
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        date.setText(mYear+"-"+mMonth+"-"+mDay);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        date.setText(mYear+"-"+mMonth+"-"+mDay);
                    }
                },
                mYear, mMonth, mDay);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String length=lengthtext.getText().toString();
                String pay=paytext.getText().toString();
                String tel=teltext.getText().toString();
                String more=moretext.getText().toString();
                String date=mYear+"-"+mMonth+"-"+mDay;
                Log.i("info",length+pay+tel+more+grade+subject+date+time);
            }
        });
    }
    public void initView(){
        titleBar=findViewById(R.id.title_bar);
        gradeSpinner=findViewById(R.id.gradespinner);
        subjectSpinner=findViewById(R.id.subjectspinner);
        timeSpinner=findViewById(R.id.hourspinner);
        date=findViewById(R.id.date);
        student=findViewById(R.id.student);
        loctext=findViewById(R.id.textloc);
        lengthtext=findViewById(R.id.length);
        paytext=findViewById(R.id.pay);
        teltext=findViewById(R.id.tel);
        moretext=findViewById(R.id.more);
        btn_sub=findViewById(R.id.submit);
    }
    public void setTitie(){
        titleBar.setTitle("试讲信息");
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
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择时间",Toast.LENGTH_LONG).show();
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
                        loctext.setText(pois.get(0).getAddress()+pois.get(0).getName()+"附近");
                    }else{
                        loctext.setText(address+"附近");
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

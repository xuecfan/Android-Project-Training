package com.example.chaofanteaching.MyPublish;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class MPInfoActivity extends AppCompatActivity {
    private TextView back;
    private EditText name;
    private EditText inMajor;
    private EditText inPay;
    private EditText inTel;
    private EditText ipintroduce;
    private RadioGroup radioGroup;
    private String sex;
    private String university;
    private String subject;
    private String college;
    private String grade;
    private String time;
    private String week;
    private String id;
    private Spinner myspinner;
    private Spinner myspinner1;
    private Spinner myspinner2;
    private Spinner myspinner3;
    private Spinner myspinner4;
    private Spinner myspinner5;
    private Button save;
    private Button del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mpinfo);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name=findViewById(R.id.name);
        Intent request=getIntent();
        String name1=request.getStringExtra("name");
        id=request.getStringExtra("id");
        name.setText(name1);
        getSpinner();
        save=findViewById(R.id.saveinfo);
        del=findViewById(R.id.delinfo);
        radioGroup=findViewById(R.id.myradio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                sex=radioButton.getText().toString();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inMajor=findViewById(R.id.major);
                inPay=findViewById(R.id.pay);
                inTel=findViewById(R.id.tel);
                ipintroduce=findViewById(R.id.require);
                String name2=name.getText().toString();
                String major=inMajor.getText().toString();
                String price=inPay.getText().toString();
                String tel=inTel.getText().toString();
                String introduce=ipintroduce.getText().toString();
                String sql=id+"&name="+name2+"&sex="+sex+"&grade="+grade+"&subject="+subject+"&week="+week+"&time="+time+"&university="+university+"&price="+price+"&introduce="+introduce+"&college="+college+"&major="+major;
                //Log.e("myl","sql="+sql);
                dbKey("edit",sql);
                Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbKey("del",id);
                Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private void getSpinner(){
        myspinner=findViewById(R.id.schoolspinner);
        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                university=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择学校",Toast.LENGTH_LONG).show();
            }
        });
        myspinner1=findViewById(R.id.collegespinner);
        myspinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                college=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择学院",Toast.LENGTH_LONG).show();
            }
        });
        myspinner2=findViewById(R.id.gradespinner);
        myspinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grade=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择年级",Toast.LENGTH_LONG).show();
            }
        });
        myspinner3=findViewById(R.id.subjectspinner);
        myspinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择学科",Toast.LENGTH_LONG).show();
            }
        });
        myspinner4=findViewById(R.id.timespinner);
        myspinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"请选择空余时间",Toast.LENGTH_LONG).show();
            }
        });
        myspinner5=findViewById(R.id.weekspinner);
        myspinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    private void dbKey(final String op,final String key) {
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("MyPublish?op="+op+"&key="+key);
                    int code = connection.getResponseCode();
                    if (code != 200) {
                        Log.e("Error!","网络连接失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

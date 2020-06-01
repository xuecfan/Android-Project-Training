package com.example.chaofanteaching.InfoList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
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
import com.example.chaofanteaching.comments.UtilHelpers;
import com.example.chaofanteaching.utils.ToastUtils;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class AddStuInfoActivity extends AppCompatActivity {
    private SharedPreferences pre;
    private String a="";
    private TextView inName;
    private EditText inMajor;
    private EditText inPay;
//    private EditText inTel;
    private EditText ipintroduce;
    private RadioGroup radioGroup;
    private String sex;
    private String university;
    private String subject;
    private String college;
    private String grade;
    private String time;
    private String week;
    private String exp;
    private Spinner myspinner;
    private Spinner myspinner1;
    private Spinner myspinner2;
    private Spinner myspinner3;
    private Spinner myspinner4;
    private Spinner myspinner5;
    private Spinner myspinner6;
    protected EaseTitleBar titleBar;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String string = msg.obj.toString();
            switch (msg.what){
                case 0:
                    if (string.equals("1")){
                        Toast.makeText(AddStuInfoActivity.this, "发布信息成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(AddStuInfoActivity.this, "发布失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1:
                    String[] strings = string.split(",");
                    inName.setText(strings[0]);//设置用户名
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_stu_info);
        pre= getSharedPreferences("login", Context.MODE_PRIVATE);
        a = pre.getString("userName", "");
        inName=findViewById(R.id.name);
        connectDB("MyData?index=rname&name="+a,1);//获取用户名
//        inName.setText(a);
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle("添加信息");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        radioGroup=findViewById(R.id.myradio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                sex=radioButton.getText().toString();
            }
        });
        Button add_finish=findViewById(R.id.add_finish);
        getSpinner();
        add_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inMajor=findViewById(R.id.major);
                inPay=findViewById(R.id.pay);
//                inTel=findViewById(R.id.tel);
                ipintroduce=findViewById(R.id.require);
                String name=inName.getText().toString();
                String major=inMajor.getText().toString();
                String pay=inPay.getText().toString();
//                String tel=inTel.getText().toString();
                String introduce=ipintroduce.getText().toString();
                if(!(pay.isEmpty())&&!(introduce.isEmpty())){
                    connectDB("AddInfoServlet?id=1&name="+name
                            +"&sex="+sex
                            +"&grade="+grade
                            +"&subject="+subject
                            +"&week="+week
                            +"&time="+time
                            +"&university="+university
                            +"&pay="+pay
                            +"&require="+introduce
                            +"&college="+college
                            +"&major="+major
                            +"&user="+a
                            +"&exp="+exp,0);//链接数据库，若所有输入项均不空，提交信息
//                    dbKey(name,sex,grade, subject,week,time,university,pay,introduce,college,major,a,exp);
                    finish();
                }else if((pay.isEmpty())&&!(introduce.isEmpty())){
                    Toast.makeText(getApplicationContext(),"您提交的费用为空,请重新提交",Toast.LENGTH_SHORT).show();
                }else if(!(pay.isEmpty())&&introduce.isEmpty()){
                    Toast.makeText(getApplicationContext(),"您提交的介绍为空,请重新提交",Toast.LENGTH_SHORT).show();
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
                UtilHelpers.hideKeyboard(event,view,AddStuInfoActivity.this);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
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
                subject =parent.getItemAtPosition(position).toString();
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
        myspinner6=findViewById(R.id.expspinner);
        myspinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exp=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"是否有经验",Toast.LENGTH_LONG).show();
            }
        });
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

    private void dbKey(final String name, final String sex, final String grade, final String subject, final String week, final String time, final String university, final String pay, final String introduce,final String college,final String major,final String user,final String exp) {
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("AddInfoServlet?id=1&name="+name+"&sex="+sex+"&grade="+grade+"&subject="+subject+"&week="+week+"&time="+time+"&university="+university+"&pay="+pay+"&require="+introduce+"&college="+college+"&major="+major+"&user="+user+"&exp="+exp);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        ToastUtils.showLong("添加成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

package com.example.chaofanteaching.publish;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.comments.UtilHelpers;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class TeacherPublishActivity extends AppCompatActivity {
    protected EaseTitleBar titleBar;

    private SharedPreferences pre;
    private String userName;
    private String role;

    private LinearLayout sexLayout;
    private LinearLayout universityLayout;
    private LinearLayout collegeLayout;
    private LinearLayout majorLayout;
    private LinearLayout gradeLayout;
    private LinearLayout courseLayout;
    private LinearLayout freeWeekLayout;
    private LinearLayout freeTimeLayout;
    private LinearLayout experienceLayout;
    private LinearLayout[] layouts;

    private TextView nameTxt;
    private TextView sexTxt;
    private TextView universityTxt;
    private TextView collegeTxt;
    private TextView majorTxt;
    private TextView gradeTxt;
    private TextView courseTxt;
    private TextView freeWeekTxt;
    private TextView freeTimeTxt;
    private TextView experienceTxt;
    private TextView[] textViews;

    private EditText feeEdit;
//    private EditText phoneEdit;
    private EditText introductionEdit;

    private Button deleteBtn;
    private Button saveBtn;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    String string = msg.obj.toString();
                    Log.e("xcf_teacherPublish",string);
                    String[] strings;
                    strings = string.split(",");
                    //设初值

                    for (int i=0;i<textViews.length;i++){
                        textViews[i].setText(strings[i]);
                    }
//                    nameTxt.setText(strings[0]);
//                    sexTxt.setText(strings[1]);
//                    universityTxt.setText(strings[2]);
//                    collegeTxt.setText(strings[3]);
//                    majorTxt.setText(strings[4]);
//                    gradeTxt.setText(strings[5]);
//                    courseTxt.setText(strings[6]);
//                    freeWeekTxt.setText(strings[7]);
//                    freeTimeTxt.setText(strings[8]);
//                    experienceTxt.setText(strings[9]);

                    feeEdit.setText(strings[10]);
                    introductionEdit.setText(strings[11]);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_publish);

        initView();//初始化view
        setTitleBar();//标题栏
        connectDB("MyData?index=init&name="+userName+"&role="+role);//初始化数据
        //点击layout弹窗
        String[] item0 = new String[]{"男","女"};
        String[] item1 = new String[]{"河北师范大学","河北科技大学","河北经贸大学","石家庄铁道大学"};
        String[] item2 = new String[]{"软件学院","马克思主义学院","生命科学学院","音乐学院"};
        String[] item3 = new String[]{"软件工程","计算机科学与技术","网络工程","信息安全"};
        String[] item4 = new String[]{"大一","大二","大三","大四"};
        String[] item5 = new String[]{"语文","数学","英语","物理","化学","生物","历史","政治","地理","音乐","美术","其它"};
        String[] item6 = new String[]{"周日","周一","周二","周三","周四","周五","周六"};
        String[] item7 = new String[]{"上午","下午"};
        String[] item8 = new String[]{"有经验","无经验"};
        String[][] items = new String[][]{item0,item1,item2,item3,item4,item5,item6,item7,item8};

        setAlert(layouts,items,textViews);//初始化点击layout弹窗
    }

    /**
     * 初始化点击layout弹窗
     * @param layouts
     * @param items
     * @param textViews
     */
    private void setAlert(LinearLayout[] layouts,String[][] items,TextView[] textViews) {
        for (int j = 0; j < layouts.length; j++){
            int finalJ = j;
            layouts[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog = new AlertDialog.Builder(TeacherPublishActivity.this)
                            .setItems(items[finalJ], new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    textViews[finalJ+1].setText(items[finalJ][i]);
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
    private void connectDB(String path) {
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
                        message.what = 0;
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
        pre=getSharedPreferences("login", Context.MODE_PRIVATE);
        userName=pre.getString("userName","null");
        role=pre.getString("role","null");
        //findViewById
        titleBar = findViewById(R.id.title_bar);

        nameTxt = findViewById(R.id.teacher_publish_name);
        sexTxt = findViewById(R.id.teacher_publish_sex);
        universityTxt = findViewById(R.id.teacher_publish_university);
        collegeTxt = findViewById(R.id.teacher_publish_college);
        majorTxt = findViewById(R.id.teacher_publish_major);
        gradeTxt = findViewById(R.id.teacher_publish_grade);
        courseTxt = findViewById(R.id.teacher_publish_course);
        freeWeekTxt = findViewById(R.id.teacher_publish_free_week);
        freeTimeTxt = findViewById(R.id.teacher_publish_free_time);
        experienceTxt = findViewById(R.id.teacher_publish_experience);
        textViews = new TextView[]{nameTxt,sexTxt,universityTxt,collegeTxt,majorTxt,gradeTxt,courseTxt,freeWeekTxt,freeTimeTxt,experienceTxt};

        sexLayout = findViewById(R.id.teacher_publish_sex_layout);
        universityLayout = findViewById(R.id.teacher_publish_university_layout);
        collegeLayout = findViewById(R.id.teacher_publish_college_layout);
        majorLayout = findViewById(R.id.teacher_publish_major_layout);
        gradeLayout = findViewById(R.id.teacher_publish_grade_layout);
        courseLayout = findViewById(R.id.teacher_publish_course_layout);
        freeWeekLayout = findViewById(R.id.teacher_publish_free_week_layout);
        freeTimeLayout = findViewById(R.id.teacher_publish_free_time_layout);
        experienceLayout = findViewById(R.id.teacher_publish_experience_layout);
        layouts = new LinearLayout[]{sexLayout,universityLayout,collegeLayout,majorLayout,gradeLayout,courseLayout,freeWeekLayout,freeTimeLayout,experienceLayout};

        feeEdit = findViewById(R.id.teacher_publish_fee_edit);
//        phoneEdit = findViewById(R.id.teacher_publish_phone_edit);
        introductionEdit = findViewById(R.id.teacher_publish_introduction_edit);

        deleteBtn = findViewById(R.id.teacher_publish_delete_btn);
        saveBtn = findViewById(R.id.teacher_publish_save_btn);


    }

    /**
     * 标题栏
     */
    public void setTitleBar(){
        titleBar.setTitle(userName+"发布的信息");
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
                UtilHelpers.hideKeyboard(ev,view, TeacherPublishActivity.this);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
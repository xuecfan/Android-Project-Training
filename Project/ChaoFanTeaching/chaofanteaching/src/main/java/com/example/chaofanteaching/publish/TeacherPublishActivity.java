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
import android.widget.Toast;

import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.comments.UtilHelpers;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class TeacherPublishActivity extends AppCompatActivity {
    protected EaseTitleBar titleBar;

    private String userName;
    private String role;

    private TextView[] cues;

    private LinearLayout[] layouts;

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
            String string = msg.obj.toString();
            switch(msg.what){
                case 0:
                    Log.e("xcf_teacherPublish",string);
                    //strings数组有12个元素，顺序：薛老师,男,河北师范大学,软件学院,软件工程,大三,英语,有经验,星期四,下午,70,我是可爱的学炒饭，请大家叫我爸爸，哈哈哈，嘿嘿和
                    String[] strings = string.split(",");
                    //设初值
                    setTitleBar(strings[0]);//标题栏
                    for (int i=0;i<textViews.length;i++){//设置strings0-7号元素
                        textViews[i].setText(strings[i]);
                    }
                    //设置strings第10-11号元素
                    feeEdit.setText(strings[10]);
                    introductionEdit.setText(strings[11]);
                    setAlert(layouts, textViews, cues, strings);//初始化点击layout弹窗
                    break;
                case 1:
                    if (string.equals("1")){
                        Toast.makeText(TeacherPublishActivity.this, "删除信息成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(TeacherPublishActivity.this, "删除失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    if (string.equals("1")){
                        Toast.makeText(TeacherPublishActivity.this, "修改信息成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(TeacherPublishActivity.this, "修改失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_publish);

        initView();//初始化view
        connectDB("MyData?index=init&name="+userName+"&role="+role, 0);//初始化数据

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectDB("MyData?index=delete&name="+userName+"&role="+role, 1);//删除信息
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectDB("MyData?index=save&name=" +userName
                        +"&role="+role
                        +"&sex="+sexTxt.getText()
                        +"&university="+universityTxt.getText()
                        +"&college="+collegeTxt.getText()
                        +"&major="+majorTxt.getText()
                        +"&grade="+gradeTxt.getText()
                        +"&course="+courseTxt.getText()
                        +"&freeWeek="+freeWeekTxt.getText()
                        +"&freeTime="+freeTimeTxt.getText()
                        +"&experience="+experienceTxt.getText()
                        +"&fee="+feeEdit.getText()
                        +"&introduction="+introductionEdit.getText(), 2);//保存信息
            }
        });
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
        String[] item2 = new String[]{"河北师范大学","河北科技大学","河北经贸大学","石家庄铁道大学"};
        String[] item3 = new String[]{"软件学院","马克思主义学院","生命科学学院","音乐学院"};
        String[] item4 = new String[]{"软件工程","计算机科学与技术","网络工程","信息安全"};
        String[] item5 = new String[]{"大一","大二","大三","大四"};
        String[] item6 = new String[]{"语文","数学","英语","物理","化学","生物","历史","政治","地理","音乐","美术","其它"};
        String[] item7 = new String[]{"有经验","无经验"};
        String[] item8 = new String[]{"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        String[] item9 = new String[]{"上午","下午"};
        String[][] items = new String[][]{item0,item1,item2,item3,item4,item5,item6,item7,item8,item9};
        for (int j = 0; j < layouts.length; j++){//j∈(0,9)
            int finalJ = j;
            layouts[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog = new AlertDialog.Builder(TeacherPublishActivity.this)
                            .setItems(items[finalJ], new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Log.e("xcf-alert","layouts="+layouts[finalJ]+"textViews="+textViews[finalJ]+"items="+items[finalJ][i]+"strings="+strings[finalJ]+"cues="+cues[finalJ]);
                                    if(finalJ<8){
                                        if (!items[finalJ][i].equals(strings[finalJ])){ cues[finalJ].setTextColor(getResources().getColor(R.color.colorTheme)); }
                                        else { cues[finalJ].setTextColor(getResources().getColor(R.color.black_deep)); }
                                    } else {
                                        if (!items[finalJ][i].equals(strings[finalJ])){ cues[8].setTextColor(getResources().getColor(R.color.colorTheme)); }
                                        else { cues[8].setTextColor(getResources().getColor(R.color.black_deep)); }
                                    }
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

        TextView nameCue = findViewById(R.id.teacher_publish_name_cue);
        TextView sexCue = findViewById(R.id.teacher_publish_sex_cue);
        TextView universityCue = findViewById(R.id.teacher_publish_university_cue);
        TextView collegeCue = findViewById(R.id.teacher_publish_college_cue);
        TextView majorCue = findViewById(R.id.teacher_publish_major_cue);
        TextView gradeCue = findViewById(R.id.teacher_publish_grade_cue);
        TextView courseCue = findViewById(R.id.teacher_publish_course_cue);
        TextView freeCue = findViewById(R.id.teacher_publish_free_cue);
        TextView experienceCue = findViewById(R.id.teacher_publish_experience_cue);
        TextView feeCue = findViewById(R.id.teacher_publish_fee_cue);
        TextView introductionCue = findViewById(R.id.teacher_publish_introduction_cue);
        cues = new TextView[]{nameCue, sexCue, universityCue, collegeCue, majorCue, gradeCue, courseCue, experienceCue, freeCue};//9个元素

        TextView nameTxt = findViewById(R.id.teacher_publish_name);
        sexTxt = findViewById(R.id.teacher_publish_sex);
        universityTxt = findViewById(R.id.teacher_publish_university);
        collegeTxt = findViewById(R.id.teacher_publish_college);
        majorTxt = findViewById(R.id.teacher_publish_major);
        gradeTxt = findViewById(R.id.teacher_publish_grade);
        courseTxt = findViewById(R.id.teacher_publish_course);
        freeWeekTxt = findViewById(R.id.teacher_publish_free_week);
        freeTimeTxt = findViewById(R.id.teacher_publish_free_time);
        experienceTxt = findViewById(R.id.teacher_publish_experience);
        textViews = new TextView[]{nameTxt,sexTxt,universityTxt,collegeTxt,majorTxt,gradeTxt,courseTxt,experienceTxt,freeWeekTxt,freeTimeTxt};//10个元素

        LinearLayout nameLayout = findViewById(R.id.teacher_publish_name_layout);
        LinearLayout sexLayout = findViewById(R.id.teacher_publish_sex_layout);
        LinearLayout universityLayout = findViewById(R.id.teacher_publish_university_layout);
        LinearLayout collegeLayout = findViewById(R.id.teacher_publish_college_layout);
        LinearLayout majorLayout = findViewById(R.id.teacher_publish_major_layout);
        LinearLayout gradeLayout = findViewById(R.id.teacher_publish_grade_layout);
        LinearLayout courseLayout = findViewById(R.id.teacher_publish_course_layout);
        LinearLayout freeWeekLayout = findViewById(R.id.teacher_publish_free_week_layout);
        LinearLayout freeTimeLayout = findViewById(R.id.teacher_publish_free_time_layout);
        LinearLayout experienceLayout = findViewById(R.id.teacher_publish_experience_layout);
        layouts = new LinearLayout[]{nameLayout, sexLayout, universityLayout, collegeLayout, majorLayout, gradeLayout, courseLayout, experienceLayout, freeWeekLayout, freeTimeLayout};//10个元素

        feeEdit = findViewById(R.id.teacher_publish_fee_edit);
//        phoneEdit = findViewById(R.id.teacher_publish_phone_edit);
        introductionEdit = findViewById(R.id.teacher_publish_introduction_edit);

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
                UtilHelpers.hideKeyboard(ev,view, TeacherPublishActivity.this);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
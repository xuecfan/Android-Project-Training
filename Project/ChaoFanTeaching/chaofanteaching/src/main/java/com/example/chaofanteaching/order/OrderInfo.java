package com.example.chaofanteaching.order;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.utils.ToastUtils;
import com.hyphenate.easeui.widget.EaseTitleBar;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class OrderInfo extends AppCompatActivity {
    protected EaseTitleBar titleBar;
    private TextView username;
    private TextView objuser;
    private TextView gradetext;
    private TextView subject;
    private TextView datetext;
    private TextView timetext;
    private TextView loctext;
    private TextView lengthtext;
    private TextView paytext;
    private TextView teltext;
    private TextView moretext;
    private Button btn_commit;
    private int id;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1://4,000,myl,马爸爸,初三,生物,2020-5-12,8:00,河北省石家庄市元氏县青银高速与红旗大街交汇处西南角碧桂园附近,30分钟,50,10086,无
                    String str = msg.obj.toString();
                    String[] s = str.split(",");
                    username.setText(s[1]);
                    objuser.setText(s[2]);
                    gradetext.setText(s[4]);
                    subject.setText(s[5]);
                    datetext.setText(s[6]);
                    timetext.setText(s[7]);
                    loctext.setText(s[8]);
                    lengthtext.setText(s[9]);
                    paytext.setText(s[10]);
                    teltext.setText(s[11]);
                    moretext.setText(s[12]);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info);
        initView();
        Intent request=getIntent();
        id= Integer.parseInt(request.getStringExtra("id"));
        getInfo("LookOrder","id="+id);
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong("确认");
            }
        });
    }
    public void initView(){
        titleBar=findViewById(R.id.title_bar);
        username=findViewById(R.id.username);
        objuser=findViewById(R.id.object);
        gradetext=findViewById(R.id.grade);
        subject=findViewById(R.id.subject);
        datetext=findViewById(R.id.date);
        timetext=findViewById(R.id.time);
        loctext=findViewById(R.id.textloc);
        lengthtext=findViewById(R.id.length);
        paytext=findViewById(R.id.pay);
        teltext=findViewById(R.id.tel);
        moretext=findViewById(R.id.more);
        btn_commit=findViewById(R.id.commit);
        setTitie();
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
    private void getInfo(String op,String parameter){
        new Thread(){
            HttpURLConnection connection =null;
            public void run(){
                try {
                    connection = HttpConnectionUtils
                            .getConnection(op+"?&"+parameter);
                    int code = connection.getResponseCode();
                    if (code == 200){
                        InputStream inputStream = connection.getInputStream();
                        String string = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = string;
                        message.what =1;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
package com.example.chaofanteaching.order;

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
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class OrderInfo extends AppCompatActivity {
    protected EaseTitleBar titleBar;
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
                case 1:
                    String str = msg.obj.toString();
                    String[] s = str.split(",");
                    objuser.setText(s[0]);
                    gradetext.setText(s[1]);
                    subject.setText(s[2]);
                    datetext.setText(s[3]);
                    timetext.setText(s[4]);
                    loctext.setText(s[5]);
                    lengthtext.setText(s[6]);
                    paytext.setText(s[7]);
                    teltext.setText(s[8]);
                    moretext.setText(s[9]);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        initView();
//        getInfo("LookOrder","id="+id);
//        btn_commit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
    public void initView(){
        titleBar=findViewById(R.id.title_bar);
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

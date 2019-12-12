package com.example.chaofanteaching.InfoList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class InfoDetailActivity extends AppCompatActivity {
    private Handler handler;
    private TextView nametext;
    private TextView sextext;
    private TextView universitytext;
    private TextView collegetext;
    private TextView majortext;
    private TextView gradetext;
    private TextView subjecttext;
    private TextView timetext;
    private TextView pricetext;
    private TextView introducetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_detail);
        Intent request=getIntent();
        String name=request.getStringExtra("name");
        nametext=findViewById(R.id.name);
        sextext=findViewById(R.id.sex);
        universitytext=findViewById(R.id.university);
        collegetext=findViewById(R.id.college);
        majortext=findViewById(R.id.major);
        gradetext=findViewById(R.id.grade);
        subjecttext=findViewById(R.id.subject);
        timetext=findViewById(R.id.freetime);
        pricetext=findViewById(R.id.price);
        introducetext=findViewById(R.id.introduce);
        nametext.setText(name);
        dbKey(name);
    }
    private void dbKey(final String key) {
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        String str = msg.obj.toString();
                        String[] s = str.split(",");
                        sextext.setText(s[1]);
                        universitytext.setText(s[2]);
                        collegetext.setText(s[3]);
                        majortext.setText(s[4]);
                        gradetext.setText(s[5]);
                        subjecttext.setText(s[6]);
                        timetext.setText(s[7]);
                        pricetext.setText(s[8]);
                        introducetext.setText(s[9]);
                        break;
                }
            }
        };
        new Thread() {
            HttpURLConnection connection = null;

            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("InfoDetailServlet?id=0&key="+key);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = str;
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
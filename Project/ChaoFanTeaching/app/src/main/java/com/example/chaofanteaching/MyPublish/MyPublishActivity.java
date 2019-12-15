package com.example.chaofanteaching.MyPublish;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.InfoList.Info;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MyPublishActivity extends AppCompatActivity {
    private TextView back;
    private ListView infolist;
    private ArrayAdapter adapter;
    private List<String> infos=new ArrayList<>();
    private Handler handler;
    private SharedPreferences pre;
    private String a="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_publish);
        pre=getSharedPreferences("login", Context.MODE_PRIVATE);
        a = pre.getString("userName", "");
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        infolist=findViewById(R.id.infolist);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,infos);
        infolist.setAdapter(adapter);
        dbKey(a);
    }

    private void dbKey(final String key) {
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        String str = msg.obj.toString();
                        String[] s = str.split(",");
                        for (int i = 0; i < s.length; i++) {
                            infos.add(s[i]);
                            adapter.notifyDataSetChanged();
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
                    connection = HttpConnectionUtils.getConnection("ListInfoServlet?op=my0&key="+key);
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

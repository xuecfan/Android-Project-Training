package com.example.chaofanteaching.MyPublish;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MyPublishActivity extends AppCompatActivity {

    protected EaseTitleBar titleBar;
    private ListView infolist;
    private ArrayAdapter adapter;
    private List<String> infos=new ArrayList<>();
    private List<String> ids=new ArrayList<>();
    private Handler handler;
    private SharedPreferences pre;
    private String a="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_publish);
        pre=getSharedPreferences("login", Context.MODE_PRIVATE);
        a = pre.getString("userName", "");
        String role=pre.getString("role","");
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle("我发布的");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        infolist=findViewById(R.id.infolist);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,infos);
        infolist.setAdapter(adapter);
        if(role.equals("11")){//role=11表示学生
            dbKey("my0",a);
        }else{
            dbKey("my1",a);
        }
        infolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("name", infos.get(position));
                intent.putExtra("id", ids.get(position));
                intent.setClass(MyPublishActivity.this, MPInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void dbKey(final String op,final String key) {
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        String str = msg.obj.toString();
                        if(str.isEmpty()){
                            Toast.makeText(getApplicationContext(),"没有任何东西",Toast.LENGTH_LONG).show();
                        }else{
                            String[] s = str.split(",");
                            for (int i = 0; i < s.length; i+=2) {
                                infos.add(s[i]);
                                ids.add(s[i+1]);
                                adapter.notifyDataSetChanged();
                            }
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
                    connection = HttpConnectionUtils.getConnection("MyPublish?op="+op+"&key="+key);
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

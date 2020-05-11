package com.example.chaofanteaching.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.InfoList.InfoDetailActivity;
import com.example.chaofanteaching.InfoList.ParInfoActivity;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.hyphenate.easeui.widget.EaseTitleBar;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MyCollection extends AppCompatActivity {

    private List<String> favoriteUsername=new ArrayList<>();
    private List<String> favoriteUser=new ArrayList<>();
    protected EaseTitleBar titleBar;
    private ListView infolist;
    private ArrayAdapter adapter;
    private Handler handler;
    private SharedPreferences pre;
    private String delname;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collection);
        setTitleBar();

        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        String str = msg.obj.toString();
                        if(str.isEmpty()){
                            Toast.makeText(getApplicationContext(),"没有任何东西",Toast.LENGTH_SHORT).show();
                        }else{
                            String[] s = str.split(",");
                            for (int i = 0; i < s.length; i+=2) {
                                favoriteUsername.add(s[i]);
                                favoriteUser.add(s[i+1]);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        break;
                }
            }
        };

        pre=getSharedPreferences("login", Context.MODE_PRIVATE);
        String role=pre.getString("role","");
        user=pre.getString("userName","");
        infolist=findViewById(R.id.infolist);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,favoriteUsername);
        infolist.setAdapter(adapter);
        dbKey(user+"&op=scan");
        infolist.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("是否取消收藏?");
                menu.add(0,0,0,"是");
                menu.add(0,1,0,"否");
                delname= (String) infolist.getItemAtPosition(((AdapterView.AdapterContextMenuInfo) menuInfo).position);
            }
        });
        infolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("user", favoriteUser.get(position));
                intent.putExtra("name", favoriteUsername.get(position));
                if(role.equals("11")){//role=11表示学生
                    intent.setClass(MyCollection.this,ParInfoActivity.class);
                }else{
                    intent.setClass(MyCollection.this, InfoDetailActivity.class);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                dbKey(user+"&op=del&delname="+delname);
                favoriteUser.clear();
                favoriteUsername.clear();
                dbKey(user+"&op=scan");
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void setTitleBar(){
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle("我的收藏");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void dbKey(final String user) {
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("MyStar?user="+user);
                    int code = connection.getResponseCode();
                    if (code == 200 && user.contains("scan")) {
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

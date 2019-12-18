package com.example.chaofanteaching.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.InfoList.AddInfoActivity;
import com.example.chaofanteaching.InfoList.Info;
import com.example.chaofanteaching.InfoList.InfoAdapter;
import com.example.chaofanteaching.InfoList.InfoDetailActivity;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.sign.LoginActivity;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class List extends Fragment {

    private java.util.List<Info> infoList = new ArrayList<>();
    private View view;
    private Handler handler;
    private ListView infolist;
    private InfoAdapter infoAdapter;
    private EditText editText;
    private SharedPreferences pre;
    private String a="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list, container, false);
        infoList.clear();
        infolist = view.findViewById(R.id.infolist);
        infoAdapter = new InfoAdapter(this.getContext(), infoList, R.layout.info_item);
        infolist.setAdapter(infoAdapter);
        dbKey("");
        infoAdapter.notifyDataSetChanged();
        editText=view.findViewById(R.id.input);
        Drawable drawable=getResources().getDrawable(R.drawable.find);
        drawable.setBounds(0,0,60,60);//第一0是距左边距离，第二0是距上边距离
        editText.setCompoundDrawables(drawable,null,null,null);//只放左边
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                editText.setCursorVisible(true);
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String key=editText.getText().toString();
                    dbKey(key);
                    editText.setCursorVisible(false);
                }
                return false;
            }
        });
        pre= getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        a = pre.getString("userName", "");
        infolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (a.equals("")) {
                    Toast.makeText(getContext(),"请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("name", infoList.get(position).getName());
                    intent.setClass(getActivity(), InfoDetailActivity.class);
                    startActivity(intent);
                }
            }
        });
        Button btnadd = view.findViewById(R.id.add);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a.equals("")) {
                    Toast.makeText(getContext(),"请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                }else{
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), AddInfoActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    private void dbKey(final String key) {
        infoList.clear();
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        Info scanInfo;
                        String str = msg.obj.toString();
                        if(str.isEmpty()){
                            Toast.makeText(getContext(),"没有搜到任何东西",Toast.LENGTH_LONG).show();
                        }else{
                            String[] s = str.split(";");
                            for (int i = 0; i < s.length; i++) {
                                String[] r = s[i].split(",");
                                scanInfo = new Info(r[0], r[1], r[2], "擅长"+r[3]);
                                infoList.add(scanInfo);
                                infoAdapter.notifyDataSetChanged();
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
                    connection = HttpConnectionUtils.getConnection("ListInfoServlet?op=serach&key="+key);
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
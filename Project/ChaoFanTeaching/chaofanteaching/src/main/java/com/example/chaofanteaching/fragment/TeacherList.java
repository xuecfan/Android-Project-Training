package com.example.chaofanteaching.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.InfoList.Info;
import com.example.chaofanteaching.InfoList.InfoAdapter;
import com.example.chaofanteaching.InfoList.InfoDetailActivity;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.sign.LoginActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

//展示老师列表
public class TeacherList extends Fragment {

    private List<Info> infoList = new ArrayList<>();
    private View view;
    private Handler handler;
    private ListView infolist;
    private InfoAdapter infoAdapter;
    private EditText editText;
    private SharedPreferences pre;
    private String a="";
    private SmartRefreshLayout srl;
    private TextView def;
    private TextView dis;
    private TextView dis1;
    private TextView pri;
    private TextView pri1;
    private TextView exp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list, container, false);
        pre= getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        a = pre.getString("userName", "");
        refresh();
        sort();
        serach();
        loadInfo();
        jumpToDetail();
        return view;
    }

    public void serach(){
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
                    dbKey("serach",key);
                    editText.setCursorVisible(false);
                }
                return false;
            }
        });
    }//搜索

    public void sort(){
        DrawerLayout drawerLayout=view.findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        Button btnadd = view.findViewById(R.id.add);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        def=view.findViewById(R.id.def);
        dis=view.findViewById(R.id.dis);
        dis1=view.findViewById(R.id.dis1);
        pri1=view.findViewById(R.id.pri1);
        pri=view.findViewById(R.id.pri);
        exp=view.findViewById(R.id.exp);
        Drawable defimg=getResources().getDrawable(R.drawable.defimg);
        Drawable boy=getResources().getDrawable(R.drawable.sex1);
        Drawable girl=getResources().getDrawable(R.drawable.sex0);
        Drawable up=getResources().getDrawable(R.drawable.up);
        Drawable down=getResources().getDrawable(R.drawable.down);
        Drawable expimg=getResources().getDrawable(R.drawable.exp1);
        defimg.setBounds(0,0,60,60);
        boy.setBounds(0,0,60,60);
        girl.setBounds(0,0,60,60);
        up.setBounds(0,0,50,50);
        down.setBounds(0,0,50,50);
        expimg.setBounds(0,0,50,50);
        def.setCompoundDrawables(null,null,defimg,null);
        dis.setCompoundDrawables(null,null,boy,null);
        dis1.setCompoundDrawables(null,null,girl,null);
        pri.setCompoundDrawables(null,null,up,null);
        pri1.setCompoundDrawables(null,null,down,null);
        exp.setCompoundDrawables(null,null,expimg,null);
        def.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def.setTextColor(Color.parseColor("#ff9d4d"));
                dis.setTextColor(0x8A000000);
                dis1.setTextColor(0x8A000000);
                pri.setTextColor(0x8A000000);
                pri1.setTextColor(0x8A000000);
                exp.setTextColor(0x8A000000);
                infoList.clear();
                dbKey("serach","");
            }
        });
        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def.setTextColor(0x8A000000);
                dis.setTextColor(Color.parseColor("#ff9d4d"));
                dis1.setTextColor(0x8A000000);
                pri.setTextColor(0x8A000000);
                pri1.setTextColor(0x8A000000);
                exp.setTextColor(0x8A000000);
                infoList.clear();
                dbKey("sex","男");
            }
        });
        dis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def.setTextColor(0x8A000000);
                dis1.setTextColor(Color.parseColor("#ff9d4d"));
                dis.setTextColor(0x8A000000);
                pri.setTextColor(0x8A000000);
                pri1.setTextColor(0x8A000000);
                exp.setTextColor(0x8A000000);
                infoList.clear();
                dbKey("sex","女");
            }
        });
        pri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def.setTextColor(0x8A000000);
                dis1.setTextColor(0x8A000000);
                dis.setTextColor(0x8A000000);
                pri.setTextColor(Color.parseColor("#ff9d4d"));
                pri1.setTextColor(0x8A000000);
                exp.setTextColor(0x8A000000);
                infoList.clear();
                dbKey("upprice","");
            }
        });
        pri1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def.setTextColor(0x8A000000);
                dis1.setTextColor(0x8A000000);
                dis.setTextColor(0x8A000000);
                pri1.setTextColor(Color.parseColor("#ff9d4d"));
                pri.setTextColor(0x8A000000);
                exp.setTextColor(0x8A000000);
                infoList.clear();
                dbKey("downprice","");
            }
        });
        exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def.setTextColor(0x8A000000);
                dis1.setTextColor(0x8A000000);
                dis.setTextColor(0x8A000000);
                pri1.setTextColor(0x8A000000);
                pri.setTextColor(0x8A000000);
                exp.setTextColor(Color.parseColor("#ff9d4d"));
                infoList.clear();
                dbKey("sortexp","");
            }
        });
    }//排序

    public void loadInfo(){
        infoList.clear();
        infolist = view.findViewById(R.id.infolist);
        infoAdapter = new InfoAdapter(this.getContext(), infoList, R.layout.info_item);
        infolist.setAdapter(infoAdapter);
        dbKey("serach","");
        infoAdapter.notifyDataSetChanged();
    }//加载信息

    public void jumpToDetail(){
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
                    intent.putExtra("user",infoList.get(position).getUser());
                    intent.setClass(getActivity(), InfoDetailActivity.class);
                    startActivity(intent);
                }
            }
        });
    }//跳转到详情

    public void refresh(){
        srl = view.findViewById(R.id.srl);
        srl.setReboundDuration(1000);
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srl.finishRefresh();
                infoList.clear();
                dbKey("serach","");
                Toast.makeText(getContext(),
                        "刷新完成",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }//刷新

    private void dbKey(final String op,final String key) {
        infoList.clear();
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        Info scanInfo;
                        String str = msg.obj.toString();
                        if(str.isEmpty()){
                            Toast.makeText(getContext(),"这里空空如也",Toast.LENGTH_LONG).show();
                        }else{
                            String[] s = str.split(";");
                            for (int i = 0; i < s.length; i++) {
                                String[] r = s[i].split(",");
                                scanInfo = new Info(r[0], r[1], r[2], "擅长"+r[3],r[4]+"元/小时",r[5],r[6]);
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
                    connection = HttpConnectionUtils.getConnection("ListInfoServlet?op="+op+"&key="+key);
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
    }//查询数据库
}
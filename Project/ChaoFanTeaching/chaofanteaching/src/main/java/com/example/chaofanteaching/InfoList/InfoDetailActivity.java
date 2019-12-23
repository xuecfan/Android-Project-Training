package com.example.chaofanteaching.InfoList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.ChatActivity;
import com.example.chaofanteaching.utils.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.io.InputStream;
import java.net.HttpURLConnection;

import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;

public class InfoDetailActivity extends AppCompatActivity {
    private static String path = "/storage/emulated/0/";// sd路径
    private String user;
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
    private ImageView img;
    private Button sendbtn;
    private SharedPreferences pre;
    protected EaseTitleBar titleBar;
    private ConstraintLayout shareLayout;
    private ConstraintLayout starLaylout;
    private String name;
    private String me;
    private String infoId;
    private String data[] = {"老师教的很好，支持。",
            "讲的孩子都听不懂",
            "老师教的不错，大家可以请他哦",
            "老师教课后，孩子成绩有很大提高"};
    private ListView comment_listView;

    private Handler handler = new Handler() {
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
                    introducetext.setText(s[10]);
                    infoId = s[11];
                    user=s[12];
                    break;
                case 2:
                    String string = msg.obj.toString();
                    if (string.equals("1")){
                        Toast.makeText(getApplication(),"收藏成功",Toast.LENGTH_LONG).show();
                    }else if (string.equals("0")){
                        Toast.makeText(getApplication(),"收藏失败",Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_detail);
        pre= getSharedPreferences("login", Context.MODE_PRIVATE);
        me = pre.getString("userName", "");

        //分享
        shareLayout = findViewById(R.id.shareLayout);
        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Share2.Builder(InfoDetailActivity.this)
                        .setContentType(ShareContentType.TEXT)
                        .setTextContent("Hello!我在“超凡家教APP”上看到这个老师还不错，分享给你，上超凡家教APP搜索“"+name+"”查看更多")
                        .setTitle("分享到")
                        .build()
                        .shareBySystem();
            }
        });
        //收藏
        starLaylout = findViewById(R.id.starLayout);
        starLaylout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starUser(me,user,name);
            }
        });

        //评论
        comment_listView = findViewById(R.id.comment_listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                data);
        comment_listView.setAdapter(adapter);

        sendbtn=findViewById(R.id.send);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatIn();
            }
        });
        img=findViewById(R.id.img);
        Intent request=getIntent();
        name=request.getStringExtra("name");
        String user=request.getStringExtra("user");
        Bitmap bt = BitmapFactory.decodeFile(path +user+".png");//从Sd中找头像，转换成Bitmap
        @SuppressWarnings("deprecation")
        Drawable drawable = new BitmapDrawable(bt);//转换成drawable
        img.setImageDrawable(drawable);
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle(name);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
    //发起聊天
    public void chatIn() {
        String name = user;
        String myName = EMClient.getInstance().getCurrentUser();
        if (!TextUtils.isEmpty(name)) {
            if (name.equals(myName)) {
                ToastUtils.showLong("不能和自己聊天");
                return;
            }
            Intent chat = new Intent(this, ChatActivity.class);
            chat.putExtra(EaseConstant.EXTRA_USER_ID, name);  //对方账号
            chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat); //单聊模式
            startActivity(chat);

        } else {
            ToastUtils.showLong("用户名不可为空");
        }
    }
    private void dbKey(final String key) {
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
    //收藏
    private void starUser(String collector, String collection,String collectionName){

        new Thread(){
            HttpURLConnection connection =null;
            public void run(){
                try {
                    connection = HttpConnectionUtils
                            .getConnection("InfoDetailServlet?id=2&collector="+collector
                                    +"&collection="+collection+"&collectionName="+collectionName);
                    int code = connection.getResponseCode();
                    if (code == 200){
                        InputStream inputStream = connection.getInputStream();
                        String string = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = string;
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
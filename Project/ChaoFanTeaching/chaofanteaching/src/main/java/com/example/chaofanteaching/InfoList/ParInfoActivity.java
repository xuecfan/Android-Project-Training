package com.example.chaofanteaching.InfoList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.chaofanteaching.ChatActivity;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.order.SubmitOrder;
import com.example.chaofanteaching.utils.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;

import java.io.InputStream;
import java.net.HttpURLConnection;
import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;


public class ParInfoActivity extends AppCompatActivity {

    private String user;
    private String lat;
    private String lng;
    private TextView nametext;
    private TextView loctext;
    private TextView gradetext;
    private TextView subjecttext;
    private TextView timetext;
    private TextView pricetext;
    private TextView teltext;
    private TextView requiretext;
    private TextView starName;
    private Button sendbtn;
    private ImageView img;
    private ImageView star;
    private ConstraintLayout shareLayout;
    private ConstraintLayout starLaylout;
    private String name;
    private SharedPreferences pre;
    private String me;
    private ImageView infopar_back;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {//马爸爸,男,初三,化学,星期六 9:30 2小时,50,178,男生,37.914853,114.463643,myl
            switch (msg.what) {
                case 1:
                    String str = msg.obj.toString();
                    String[] s = str.split(",");
                    name = s[0];
                    if(s[1].equals("男")){
                        Drawable man=getResources().getDrawable(R.drawable.man);
                        man.setBounds(0,0,60,60);
                        nametext.setCompoundDrawables(null,null,man,null);
                    }else{
                        Drawable woman=getResources().getDrawable(R.drawable.woman);
                        woman.setBounds(0,0,60,60);
                        nametext.setCompoundDrawables(null,null,woman,null);
                    }
                    //sextext.setText(s[1]);
                    gradetext.setText(s[2]);
                    subjecttext.setText(s[3]);
                    timetext.setText(s[4]);
                    pricetext.setText(s[5]+"元/小时");
                    teltext.setText(s[6]);
                    requiretext.setText(s[7]);
                    lat=s[8];
                    lng=s[9];
                    user=s[10];
                    break;
                case 2:
                    String string2 = msg.obj.toString();
                    switch(string2){
                        case "0":
                            star.setColorFilter(Color.RED);
                            starName.setText("已收藏");
                            Toast.makeText(getApplication(),"收藏成功",Toast.LENGTH_SHORT).show();
                            break;
                        case "1":
                            Toast.makeText(getApplication(),"收藏失败",Toast.LENGTH_SHORT).show();
                            break;
                        case "2":
                            star.setColorFilter(Color.YELLOW);
                            starName.setText("收藏");
                            Toast.makeText(getApplication(),"取消收藏成功",Toast.LENGTH_SHORT).show();
                            break;
                        case "3":
                            Toast.makeText(getApplication(),"取消收藏失败",Toast.LENGTH_SHORT).show();
                            break;

                    }

                    break;
                case 3:
                    String string3 = msg.obj.toString();
                    if (string3.equals("1")){//显示已收藏
                        star.setColorFilter(Color.RED);
                        starName.setText("已收藏");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infopar_deatil);
        Button trial_teaching=findViewById(R.id.trial_teaching);
        trial_teaching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParInfoActivity.this, SubmitOrder.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.putExtra("name",name);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        //状态栏透明
        makeStatusBarTransparent(ParInfoActivity.this);
        //获取当前登录用户的用户名
        pre= getSharedPreferences("login", Context.MODE_PRIVATE);
        me = pre.getString("userName", "");

        //分享
        shareLayout = findViewById(R.id.shareLayout);
        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Share2.Builder(ParInfoActivity.this)
                        .setContentType(ShareContentType.TEXT)
                        .setTextContent("Hello!我在“超凡家教APP”上看到这个家教待遇还不错，分享给你，上超凡家教APP搜索“"+name+"”查看更多")
                        .setTitle("分享到")
                        .build()
                        .shareBySystem();
            }
        });
        img=findViewById(R.id.img);
        sendbtn=findViewById(R.id.send);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatIn();
            }
        });
        Intent request=getIntent();
        name=request.getStringExtra("name");
        String user=request.getStringExtra("user");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.boy1).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(getApplicationContext()).load("http://39.107.42.87:8080/ChaoFanTeaching/img/"+user+".png").apply(requestOptions).into(img);
        //返回
        infopar_back = findViewById(R.id.infopar_back);
        infopar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        nametext=findViewById(R.id.name);
        nametext.setText(name);
        loctext=findViewById(R.id.locate);
        Drawable place=getResources().getDrawable(R.drawable.place);
        place.setBounds(0,0,60,60);
        loctext.setCompoundDrawables(null,null,place,null);
        gradetext=findViewById(R.id.grade);
        subjecttext=findViewById(R.id.subject);
        timetext=findViewById(R.id.freetime);
        pricetext=findViewById(R.id.price);
        teltext=findViewById(R.id.tel);
        Drawable tel=getResources().getDrawable(R.drawable.tel0);
        tel.setBounds(0,0,60,60);
        teltext.setCompoundDrawables(null,null,tel,null);
        requiretext=findViewById(R.id.require);
        ConstraintLayout map123=findViewById(R.id.map123);
        map123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.setClass(ParInfoActivity.this, Info_Map.class);
                startActivity(intent);
            }
        });
        dbKey(name);
        //判断是否收藏
        judgeStar(me,user,name);
        //收藏
        starLaylout = findViewById(R.id.starLayout);
        star = findViewById(R.id.star);//收藏的图标
        starName = findViewById(R.id.starName);//收藏的展示文本
        starLaylout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starUser(me,user,name);
            }
        });
    }

    //发起聊天
    public void chatIn() {
        //String name = user;
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
                    connection = HttpConnectionUtils.getConnection("InfoDetailServlet?id=1&key="+key);
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

    private void initView() {
        //初始化控件
    }

    //判断是否收藏
    private void judgeStar(String collector, String collection,String collectionName){

        new Thread(){
            HttpURLConnection connection =null;
            public void run(){
                try {
                    connection = HttpConnectionUtils
                            .getConnection("InfoDetailServlet?id=3&collector="+collector
                                    +"&collectionName="+collectionName+"&collection="+collection);

                    int code = connection.getResponseCode();
                    if (code == 200){
                        InputStream inputStream = connection.getInputStream();
                        String string = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = string;
                        message.what = 3;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
    //收藏
    private void starUser(String collector, String collection ,String collectionName){

        new Thread(){
            HttpURLConnection connection =null;
            public void run(){
                try {
                    connection = HttpConnectionUtils
                            .getConnection("InfoDetailServlet?id=2&collector="+collector
                                    +"&collectionName="+collectionName+"&collection="+collection);
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

    //状态栏透明
    public static void makeStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}

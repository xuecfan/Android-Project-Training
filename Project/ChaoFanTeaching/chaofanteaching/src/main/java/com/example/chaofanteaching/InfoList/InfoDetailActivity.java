package com.example.chaofanteaching.InfoList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.ChatActivity;
import com.example.chaofanteaching.order.SubmitOrder;
import com.example.chaofanteaching.utils.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import java.io.InputStream;
import java.net.HttpURLConnection;
import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;

public class InfoDetailActivity extends AppCompatActivity {

    private String user;
    private TextView nametext;
    private TextView universitytext;
    private TextView collegetext;
    private TextView majortext;
    private TextView gradetext;
    private TextView subjecttext;
    private TextView timetext;
    private TextView exptext;
    private TextView pricetext;
    private TextView introducetext;
    private TextView starName;
    private TextView on_time;
    private TextView qualitytext;
    private ImageView img;
    private ImageView star;
    private Button sendbtn;
    private SharedPreferences pre;
    private ConstraintLayout shareLayout;
    private ConstraintLayout starLaylout;
    private String name;
    private String me;
    private String data[] = {"老师教的很好，支持。",
            "讲的孩子都听不懂",
            "老师教的不错，大家可以请他哦",
            "老师教课后，孩子成绩有很大提高"};
    private ListView comment_listView;
    private ImageView infopar_back;
    private RatingBar onTime;
    private RatingBar quality;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    String str = msg.obj.toString();
                    String[] s = str.split(",");
                    if(s[1].equals("男")){
                        Drawable man=getResources().getDrawable(R.drawable.man);
                        man.setBounds(0,0,60,60);
                        nametext.setCompoundDrawables(null,null,man,null);
                    }else{
                        Drawable woman=getResources().getDrawable(R.drawable.woman);
                        woman.setBounds(0,0,60,60);
                        nametext.setCompoundDrawables(null,null,woman,null);
                    }
                    universitytext.setText(s[2]);
                    collegetext.setText(s[3]);
                    majortext.setText(s[4]);
                    gradetext.setText(s[5]);
                    subjecttext.setText(s[6]);
                    timetext.setText(s[7]);
                    pricetext.setText(s[8]+"元/小时");
                    introducetext.setText(s[10]);
                    user=s[11];
                    if(s[12].equals("1")){
                        Drawable authorized=getResources().getDrawable(R.drawable.authorized);
                        authorized.setBounds(0,5,128,128);
                        universitytext.setCompoundDrawables(null,null,authorized,null);
                    }
                    exptext.setText(s[13]);
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
                case 4:
                    String string4 = msg.obj.toString();
                    String[] c = string4.split(",");
                    if(!c[1].equals("n")){
                        onTime.setRating(Float.parseFloat(c[0]));
                        on_time.setText(c[0]);
                        quality.setRating(Float.parseFloat(c[1]));
                        qualitytext.setText(c[1]);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_detail);
        Button trial_teaching=findViewById(R.id.trial_teaching);
        trial_teaching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoDetailActivity.this, SubmitOrder.class);
                intent.putExtra("teacher",name);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        pre= getSharedPreferences("login", Context.MODE_PRIVATE);
        me = pre.getString("userName", "");

        //状态栏透明
        makeStatusBarTransparent(InfoDetailActivity.this);
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

        //评论
        onTime=findViewById(R.id.on_time_ratingBar);
        quality=findViewById(R.id.quality_ratingBar);
        on_time=findViewById(R.id.on_time_value);
        qualitytext=findViewById(R.id.alue);
        //onTime.setRating();
//        comment_listView = findViewById(R.id.comment_listView);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_list_item_1,
//                data);
//        comment_listView.setAdapter(adapter);

        //返回
        infopar_back = findViewById(R.id.infopar_back);
        infopar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


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
        user=request.getStringExtra("user");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.tea).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(getApplicationContext()).load("http://39.107.42.87:8080/ChaoFanTeaching/img/"+user+".png").apply(requestOptions).into(img);
        nametext=findViewById(R.id.name);
        universitytext=findViewById(R.id.university);
        collegetext=findViewById(R.id.college);
        majortext=findViewById(R.id.major);
        gradetext=findViewById(R.id.grade);
        subjecttext=findViewById(R.id.subject);
        timetext=findViewById(R.id.freetime);
        exptext=findViewById(R.id.exp);
        pricetext=findViewById(R.id.price);
        introducetext=findViewById(R.id.introduce);
        nametext.setText(name);
        dbKey(name);
        dbComment(user);
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
    private void dbComment(String user) {
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("avgComment?objuser="+user);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = str;
                        message.what = 4;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
    private void starUser(String collector, String collection,String collectionName){

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
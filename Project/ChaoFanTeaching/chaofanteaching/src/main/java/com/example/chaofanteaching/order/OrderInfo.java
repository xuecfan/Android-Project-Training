package com.example.chaofanteaching.order;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.InfoList.Info_Map;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.comments.CommentingActivity;
import com.example.chaofanteaching.utils.ToastUtils;
import com.hyphenate.easeui.widget.EaseTitleBar;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class OrderInfo extends AppCompatActivity {
    protected EaseTitleBar titleBar;
    private SharedPreferences pre;
    private TextView username;
    private TextView objuser;
    private TextView subject;
    private TextView timetext;
    private TextView loctext;
    private TextView lengthtext;
    private TextView paytext;
    private TextView teltext;
    private TextView moretext;
    private TextView statustext;
    private Button btn_commit;
    private Button btn_finish;
    private Button btn_toCommenting;
    private int id;
    private double lat;
    private double lng;
    private String user;
    private String arr[];//存放username和objuser的数组

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    String str = msg.obj.toString();
                    String[] s = str.split(",");
                    if(user.equals(s[2]) && s[13].equals("待确认")){
                        btn_commit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getInfo("editOrder","id="+id);
                                ToastUtils.showShort("确认成功");
                                btn_commit.setVisibility(View.GONE);
                                statustext.setText("进行中");
                            }
                        });
                    }else {
                        btn_commit.setVisibility(View.GONE);
                    }
                    if(s[13].equals("进行中")){
                        btn_finish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getInfo("edit1Order","id="+id);
                                ToastUtils.showShort("试讲已完成");
                                btn_finish.setVisibility(View.GONE);
                                statustext.setText("待评价");
                            }
                        });
                    }else{
                        btn_finish.setVisibility(View.GONE);
                    }
                    if(s[13].equals("待评价")){
                        btn_toCommenting.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(OrderInfo.this, CommentingActivity.class);
                                intent.putExtra("id",id);
                                intent.putExtra("user",arr);
                                startActivity(intent);
                            }
                        });
                    }else {
                        btn_toCommenting.setVisibility(View.GONE);
                    }
                    username.setText(s[1]);
                    objuser.setText(s[2]);
                    subject.setText(s[4]+" "+s[5]);
                    timetext.setText(s[6]+" "+s[7]);
                    loctext.setText(s[8]);
                    reGeo(s[8]);
                    lengthtext.setText(s[9]);
                    paytext.setText(s[10]+"元");
                    teltext.setText(s[11]);
                    moretext.setText(s[12]);
                    statustext.setText(s[13]);
                    arr = new String[]{s[1], s[2]};
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info);
        initView();
        Intent request=getIntent();
        id= Integer.parseInt(request.getStringExtra("id"));
        getInfo("LookOrder","id="+id);
        pre=getSharedPreferences("login", Context.MODE_PRIVATE);
        user=pre.getString("userName", "");
        teltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num="tel:"+teltext.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse(num));
                startActivity(intent);
            }
        });
        loctext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderInfo.this, Info_Map.class);
                intent.putExtra("lat",String.valueOf(lat));
                intent.putExtra("lng",String.valueOf(lng));
                startActivity(intent);
            }
        });
    }
    public void initView(){
        titleBar=findViewById(R.id.title_bar);
        username=findViewById(R.id.username);
        objuser=findViewById(R.id.object);
        subject=findViewById(R.id.subject);
        timetext=findViewById(R.id.time);
        loctext=findViewById(R.id.textloc);
        Drawable place=getResources().getDrawable(R.drawable.place);
        place.setBounds(0,0,60,60);
        loctext.setCompoundDrawables(null,null,place,null);
        lengthtext=findViewById(R.id.length);
        paytext=findViewById(R.id.pay);
        teltext=findViewById(R.id.tel);
        Drawable tel=getResources().getDrawable(R.drawable.tel0);
        tel.setBounds(0,0,60,60);
        teltext.setCompoundDrawables(null,null,tel,null);
        moretext=findViewById(R.id.more);
        statustext=findViewById(R.id.status);
        btn_commit=findViewById(R.id.commit);
        btn_finish=findViewById(R.id.finish);
        btn_toCommenting=findViewById(R.id.ToCommentingBtn);
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
                    if (code == 200 && op.equals("LookOrder")){
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
    public void reGeo(String address){
        GeoCoder mCoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                lat=geoCodeResult.getLocation().latitude;
                lng=geoCodeResult.getLocation().longitude;
            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            }
        };
        mCoder.setOnGetGeoCodeResultListener(listener);
        mCoder.geocode(new GeoCodeOption().address(address).city("石家庄"));
        mCoder.destroy();
    }
}
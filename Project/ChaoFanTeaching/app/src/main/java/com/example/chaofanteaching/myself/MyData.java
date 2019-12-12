package com.example.chaofanteaching.myself;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaofanteaching.All;
import com.example.chaofanteaching.R;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MyData extends AppCompatActivity {

    private Button btn;
    private LinearLayout name;
    private LinearLayout phone;
    private LinearLayout address;
    private LinearLayout fanhui;
    private TextView show;
    private TextView name_content;
    private TextView phone_content;
    private TextView address_content;
    private RadioGroup rg;
    private String user="";
    private SharedPreferences pre;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  1:
                    Toast.makeText(getApplicationContext(),"信息保存成功",Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);
        pre=getSharedPreferences("login",MODE_PRIVATE);
        user=pre.getString("userName","");
        btn=findViewById(R.id.btn);
        fanhui=findViewById(R.id.fanhui);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        show = findViewById(R.id.show);
        name_content=findViewById(R.id.name_content);
        phone_content=findViewById(R.id.phone_content);
        address_content=findViewById(R.id.address_content);
        rg = findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                String tip = checkedId == R.id.male ? "男" : "女";
                show.setVisibility(View.VISIBLE);
                show.setText(tip);
                SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("sexContent",tip);
                editor.apply();
            }
        });
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MyData.this,All.class);
                i.putExtra("all","3");
                startActivity(i);

            }
        });
        SharedPreferences pre=getSharedPreferences("data",MODE_PRIVATE);
        final String name1=pre.getString("nameContent","");
        final String phone1=pre.getString("phoneContent","");
        final String address1=pre.getString("addressContent","");
        final String sex=pre.getString("sexContent","");
        show.setText(sex);
        name_content.setText(name1);
        phone_content.setText(phone1);
        address_content.setText(address1);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), NameDetail.class);
                startActivity(i);
                finish();
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), PhoneDetail.class);
                startActivity(i);
                finish();
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), AddressDetail.class);
                startActivity(i);
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        asynchttpform(name1,phone1,address1,sex);
                        android.os.Message msg= Message.obtain();
                        msg.what=1;
                        handler.sendMessage(msg);
                    }
                }.start();
//
            }
        });

//        Intent intent = new Intent("android.intent.action.CART_BROADCAST");
//        intent.putExtra("data","refresh");
//        LocalBroadcastManager.getInstance(MyData.this).sendBroadcast(intent);
//        sendBroadcast(intent);
    }
    private void asynchttpform(String name1,String phone1,String address1,String sex){
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().
                url("http://175.24.102.160:8080/ChaoFanTeaching/MyData?name="+user+"&nameContent="+name1+"&phoneContent="+phone1+"&addressContent="+address1+"&sexContent="+sex)
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("yxt","失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("data",response.body().string());
            }
        });

    }




}

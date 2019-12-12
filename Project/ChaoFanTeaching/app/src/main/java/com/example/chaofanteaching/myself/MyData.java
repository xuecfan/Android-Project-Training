package com.example.chaofanteaching.myself;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaofanteaching.ActivityCollector;
import com.example.chaofanteaching.All;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.fragment.My;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MyData extends AppCompatActivity {

    private boolean run = false;
    private final Handler handler = new Handler();
    private LinearLayout name;
    private LinearLayout phone;
    private LinearLayout address;
    private LinearLayout fanhui;
    private TextView show;
    private TextView name_content;
    private TextView phone_content;
    private TextView address_content;
    private RadioGroup rg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);

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
        String name1=pre.getString("nameContent","");
        String phone1=pre.getString("phoneContent","");
        String address1=pre.getString("addressContent","");
        String sex=pre.getString("sexContent","");
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
//        Intent intent = new Intent("android.intent.action.CART_BROADCAST");
//        intent.putExtra("data","refresh");
//        LocalBroadcastManager.getInstance(MyData.this).sendBroadcast(intent);
//        sendBroadcast(intent);
    }




}

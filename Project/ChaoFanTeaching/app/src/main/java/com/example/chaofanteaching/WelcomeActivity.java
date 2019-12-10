package com.example.chaofanteaching;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.chaofanteaching.sign.ChooseIdentityActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.welcome);

        //判断是否登录
        SharedPreferences preferences = getSharedPreferences("login",MODE_PRIVATE);
        String string = preferences.getString("loginOrNot","");
        Log.e("login",string);
        Thread myThread=new Thread(){//创建子线程
            @Override
            public void run() {
                try{
                    sleep(2000);//使程序休眠2秒
                    Intent it=new Intent(getApplicationContext(), ChooseIdentityActivity.class);//启动MainActivity
                    startActivity(it);
                    finish();//关闭当前活动
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程
    }
}

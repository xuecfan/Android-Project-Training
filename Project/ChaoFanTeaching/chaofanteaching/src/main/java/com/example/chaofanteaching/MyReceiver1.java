package com.example.chaofanteaching;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver1 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String title = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        if(!title.equals("")){
        SharedPreferences id = context.getSharedPreferences("id", context.MODE_PRIVATE);
        id.edit().putString("id",title).commit();
        Log.e("aaa",id.getString("id",""));}
    }
}

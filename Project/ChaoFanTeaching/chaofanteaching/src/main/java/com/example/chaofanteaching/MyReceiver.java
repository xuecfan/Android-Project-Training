package com.example.chaofanteaching;



import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.chaofanteaching.InfoList.InfoDetailActivity;
import com.example.chaofanteaching.about.About;
import com.example.chaofanteaching.myself.RenZheng;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class MyReceiver extends JPushMessageReceiver {
    //当收到自定义消息时回调
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);

        //获取收到的消息
        String title= customMessage.title;
        String msg= customMessage.message;//消息内容
        String extra= customMessage.extra;//附加消息
        Log.i("111","自定义消息"+title);
        Log.i("111","msg"+msg);
        Log.i("111","extra"+extra);
        //收到的消息显示在页面上
//        if(title.equals("message")){

//        }
    }

    //当打开通知消息时回调
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        String content=notificationMessage.notificationContent;
        String extras=notificationMessage.notificationExtras;

        Intent i=new Intent(context,RenZheng.class);
        i.putExtra("extras",extras);
        i.putExtra("content",content);
        context.startActivity(i);

    }

}


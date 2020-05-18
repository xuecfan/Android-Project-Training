package com.example.chaofanteaching;



import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.chaofanteaching.InfoList.InfoDetailActivity;
import com.example.chaofanteaching.about.About;
import com.example.chaofanteaching.myself.RenZheng;
import com.example.chaofanteaching.order.OrderInfo;

import org.json.JSONException;
import org.json.JSONObject;

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

    }

    //当打开通知消息时回调
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        String extras=notificationMessage.notificationExtras;
        try {
            JSONObject jsonObject=new JSONObject(extras);
            String op=jsonObject.getString("op");
            if(op.equals("order")){
                String id=jsonObject.getString("id");
                Intent i=new Intent(context, OrderInfo.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                i.putExtra("id",id);
                context.startActivity(i);
            }else if(op.equals("renzheng")){
                Intent i=new Intent(context, RenZheng.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity(i);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


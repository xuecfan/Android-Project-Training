package com.example.chaofanteaching;


import android.content.Context;
import android.util.Log;
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
//        Intent i=new Intent(context,All.class);
//        i.putExtra("msg",msg);
//        i.putExtra("extra",extra);
//        context.startActivity(i);
    }

    //当打开通知消息时回调
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        if(notificationMessage.isRichPush){//如果是富媒体消息
            //获取收到的富媒体消息的路径
            String webPath=notificationMessage._webPagePath;
            Log.i("111","富媒体消息存储路径："+webPath);
        }else {
            //普通通知消息
            //获取通知消息的内容
            String content=notificationMessage.notificationContent;
            Log.i("111","通知内容："+content);
            //在main里显示内容
//            Intent i=new Intent(context,MainActivity.class);
//            i.setAction("notifymsg");
//            i.putExtra("content",content);
//            context.startActivity(i);
        }

    }

    //当收到通知消息时回调
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        if(notificationMessage.isRichPush){//如果是富媒体消息
            //获取收到的富媒体消息的路径
            String webPath=notificationMessage._webPagePath;
            Log.i("111","富媒体消息存储路径："+webPath);
        }else {
            //普通通知消息
            //获取通知消息的内容
            String content=notificationMessage.notificationContent;
            Log.i("111","通知内容："+content);
        }
    }

}

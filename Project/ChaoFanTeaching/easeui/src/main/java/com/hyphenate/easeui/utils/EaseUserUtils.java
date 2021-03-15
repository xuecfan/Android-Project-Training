package com.hyphenate.easeui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EaseUserUtils {
    
    static EaseUserProfileProvider userProvider;

//    private static Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            String string = msg.obj.toString();
//            switch (msg.what){
//                case 0:
//                    Log.e("xcf-handler-case1",string);
//                    String[] strings = string.split(",");
//                    textView.setText(strings[0]);//设置用户名
//                    break;
//            }
//        }
//    };
    
    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }
    
    /**
     * get EaseUser according username
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username){
        if(userProvider != null)
            return userProvider.getUser(username);
        
        return null;
    }
    
    /**
     * set user avatar
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
//    	EaseUser user = getUserInfo(username);
//        if(user != null && user.getAvatar() != null){
//            try {
//                int avatarResId = Integer.parseInt(user.getAvatar());
//                Glide.with(context).load(avatarResId).into(imageView);
//            } catch (Exception e) {
//                //use default avatar
//                Glide.with(context).load(user.getAvatar())
//                        .apply(RequestOptions.placeholderOf(R.drawable.ease_default_avatar)
//                                .diskCacheStrategy(DiskCacheStrategy.ALL))
//                        .into(imageView);
//            }
//        }else{
//            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
//        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.boy).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context).load(
                "http://8.131.122.37:8080/ChaoFanTeaching/img/"
                        +username
                        +".png")
                .apply(requestOptions).into(imageView);//头像
    }
    
    /**
     * set user's nickname
     */
    public static void setUserNick(final String username, final TextView textView){
        //原代码
        if(textView != null){
            EaseUser user = getUserInfo(username);
            if(user != null && user.getNickname() != null){
                textView.setText(user.getNickname());
            }else{
                textView.setText(username);
            }
        }
        Log.e("xcf-setUserNick",username+" 666");
//        connectDB("MyData?index=rname&name="+username,1);//获取用户名
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String string = msg.obj.toString();
                switch (msg.what){
                    case 0:
                        Log.e("xcf-handler-case1",string);
                        String[] strings = string.split(",");
                        textView.setText(strings[0]);//设置用户名
                        break;
                }
            }
        };
        new Thread() {
            //            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    //获取互联网连接
                    URL url = new URL("http://8.131.122.37:8080/ChaoFanTeaching/"+"MyData?index=rname&name="+username);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");//设置post请求
                    connection.setDoOutput(true);//允许输出
                    connection.setDoInput(true);//允许输入
//                    connection = HttpConnectionUtils.getConnection(path);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        //流转换为字符串
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] b = new byte[1024];
                        int len = -1;
                        while((len=inputStream.read(b))!=-1){
                            bos.write(b,0,len);
                        }
                        inputStream.close();
                        String str = new String(bos.toByteArray());
//                        String str = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = str;
                        message.what = 0;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

//    /**
//     * 连接数据库
//     */
//    private static void connectDB(final String path, final int what) {
//        new Thread() {
//            //            HttpURLConnection connection = null;
//            @Override
//            public void run() {
//                try {
//                    //获取互联网连接
//                    URL url = new URL("http://39.107.42.87:8080/ChaoFanTeaching/"+path);
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("POST");//设置post请求
//                    connection.setDoOutput(true);//允许输出
//                    connection.setDoInput(true);//允许输入
////                    connection = HttpConnectionUtils.getConnection(path);
//                    int code = connection.getResponseCode();
//                    if (code == 200) {
//                        InputStream inputStream = connection.getInputStream();
//                        //流转换为字符串
//                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                        byte[] b = new byte[1024];
//                        int len = -1;
//                        while((len=inputStream.read(b))!=-1){
//                            bos.write(b,0,len);
//                        }
//                        inputStream.close();
//                        String str = new String(bos.toByteArray());
////                        String str = StreamChangeStrUtils.toChange(inputStream);
//                        android.os.Message message = Message.obtain();
//                        message.obj = str;
//                        message.what = what;
//                        handler.sendMessage(message);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
    
}

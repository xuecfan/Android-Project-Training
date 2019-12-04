package com.example.chaofanteaching;
import java.net.HttpURLConnection;
import java.net.URL;

/*
* 获取互联网连接
* */
public class HttpConnectionUtils {
    public static HttpURLConnection getConnection(String path) throws Exception{

        URL url = new URL("http://175.24.102.160:8080/ChaoFanTeaching/"+path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");//设置post请求
        connection.setDoOutput(true);//允许输出
        connection.setDoInput(true);//允许输入
        return connection;
    }
}
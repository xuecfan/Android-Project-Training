package com.example.chaofanteaching;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/*
* 流转换为字符串的工具类
* */
public class StreamChangeStrUtils {
    public static String toChange(InputStream inputStream) throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = -1;
        while((len=inputStream.read(b))!=-1){
            bos.write(b,0,len);
        }
        inputStream.close();
        String str = new String(bos.toByteArray());
        return str;
    }
}

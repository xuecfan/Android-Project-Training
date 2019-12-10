package com.example.chaofanteaching;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpLoadFile extends AsyncTask<String,Void,String> {
    private Context context;
    private String filePath;

    public UpLoadFile(Context context, String filePath) {
        this.context = context;
        this.filePath = filePath;
    }
    //  访问服务器，上传文件，接收响应码滨并返回
    @Override
    protected String doInBackground(String... strings) {
        //创建OKhttpclient对象
        OkHttpClient okHttpClient=new OkHttpClient();
        //创建request对象
        //得到文件的mime类型
        MediaType mediaType=MediaType.parse("image/png");
        //创建requbody对象
        File file=new File(filePath);
//        if(file.exists()){
//            Log.i("lww","文件不存在");
//        }
        RequestBody requestBody=RequestBody.create(file,mediaType);

        //创建请求对象
        Request request=new Request.Builder().post(requestBody).url(strings[0]).build();
        //创建call对象
        Call call=okHttpClient.newCall(request);
        //发起请求并接收响应
        Response response=null;
        try {
            response=call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}

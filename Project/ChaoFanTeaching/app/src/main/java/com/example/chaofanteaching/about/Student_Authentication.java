package com.example.chaofanteaching.about;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.chaofanteaching.R;
import com.example.chaofanteaching.UpLoadFile;
import com.example.chaofanteaching.fragment.My;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;

public class Student_Authentication extends AppCompatActivity {
    private OkHttpClient okHttpClient;
    private SharedPreferences pre;
    private static String path = "/storage/emulated/0/";// sd路径
    private String a="";
    private ImageButton au_fanhui;
    private ImageButton student1;
    private ImageButton student2;
    private Button shangchuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.student_authenticaation);
        okHttpClient=new OkHttpClient();
        pre=getSharedPreferences("login", Context.MODE_PRIVATE);
        a = pre.getString("userName", "");

        au_fanhui = findViewById(R.id.au_fanhui);
        student1 = findViewById(R.id.student1);
        student2 = findViewById(R.id.student2);
        shangchuan = findViewById(R.id.shangchuang);

        au_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        student1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent cameraIntent =
                        new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 8888);
            }
        });
        student2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent cameraIntent =
                        new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 8889);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 8888 && resultCode == RESULT_OK) {
            //获取系统摄像头拍照的结果
            Bitmap img = (Bitmap) data.getExtras().get("data");
            student1.setImageBitmap(img);
            setPicToView(img);
            asyncupop();
        }
        if(requestCode == 8889 && resultCode == RESULT_OK) {
            //获取系统摄像头拍照的结果
            Bitmap img = (Bitmap) data.getExtras().get("data");
            student2.setImageBitmap(img);
            setPicToView1(img);
            asyncupop1();
        }
    }
    private void setPicToView(Bitmap mBitmap){
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + a+"0.png";// 图片名字
        Log.e("11",fileName);
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void setPicToView1(Bitmap mBitmap){
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + a+"1.png";// 图片名字
        Log.e("11",fileName);
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void asyncupop() {
        String filepath=path + a+"0.png";
        //创建上传异步任务类的对象
        UpLoadFile task=new UpLoadFile(this,filepath);
        //开始执行异步任务
        task.execute("http://175.24.102.160:8080/ChaoFanTeaching/PhotoInsert?index=student0&name="+a);
    }
    private void asyncupop1() {
        String filepath=path + a+"1.png";
        //创建上传异步任务类的对象
        UpLoadFile task=new UpLoadFile(this,filepath);
        //开始执行异步任务
        task.execute("http://175.24.102.160:8080/ChaoFanTeaching/PhotoInsert?index=student1&name="+a);
    }
}

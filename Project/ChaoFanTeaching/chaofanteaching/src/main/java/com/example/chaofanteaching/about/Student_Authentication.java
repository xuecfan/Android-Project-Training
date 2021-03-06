package com.example.chaofanteaching.about;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.UpLoadFile;
import com.hyphenate.easeui.widget.EaseTitleBar;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Student_Authentication extends AppCompatActivity {
    protected EaseTitleBar titleBar;
    private SharedPreferences.Editor editor;
    private SharedPreferences pre;
    private static String path = "/storage/emulated/0/";// sd路径
    private String a="";
    private ImageView student1;
    private ImageView student2;
    private TextView front;
    private TextView behind;
    private Button shangchuan;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  0:
                    Log.i("file","学生证正面上传成功");
                    Toast.makeText(getApplicationContext(),"上传成功，请等待认证",Toast.LENGTH_SHORT).show();
                    break;
                case  1:
                    Log.i("file","学生证背面上传成功");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_authenticaation);
        pre=getSharedPreferences("login", Context.MODE_PRIVATE);
        a = pre.getString("userName", "");
        String b=pre.getString("role","");
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle("身份认证");
        student1 = findViewById(R.id.student1);
        student2 = findViewById(R.id.student2);
        front=findViewById(R.id.front);
        behind=findViewById(R.id.behind);
        if(b.equals("10")&&pre.getString("grade","").equals("0")){titleBar.setTitle("身份认证");}
        else if(b.equals("11")&&pre.getString("grade","").equals("0")){titleBar.setTitle("学生认证");}
        else if(pre.getString("grade","").equals("1")){titleBar.setTitle("成绩上传");}
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if(b.equals("10")){front.setText("身份证正面");behind.setText("身份证背面");}
        if(pre.getString("grade","").equals("1")){
            front.setText("学生绩点");
            behind.setText("学期成绩");
            editor=pre.edit();
            editor.putString("grade","0");
            editor.commit();
        }
        shangchuan = findViewById(R.id.shangchuang);
        shangchuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        asyncupop();
                        android.os.Message msg= Message.obtain();
                        msg.what=0;
                        handler.sendMessage(msg);
                    }
                }.start();
                new Thread(){
                    @Override
                    public void run() {
                        asyncupop1();
                        android.os.Message msg= Message.obtain();
                        msg.what=1;
                        handler.sendMessage(msg);
                    }
                }.start();
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

        }
        if(requestCode == 8889 && resultCode == RESULT_OK) {
            //获取系统摄像头拍照的结果
            Bitmap img = (Bitmap) data.getExtras().get("data");
            student2.setImageBitmap(img);
            setPicToView1(img);

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
        task.execute("http://8.131.122.37:8080/ChaoFanTeaching/PhotoInsert?index=student0&name="+a);
    }
    private void asyncupop1() {
        String filepath=path + a+"1.png";
        //创建上传异步任务类的对象
        UpLoadFile task=new UpLoadFile(this,filepath);
        //开始执行异步任务
        task.execute("http://8.131.122.37:8080/ChaoFanTeaching/PhotoInsert?index=student1&name="+a);
    }
}

package com.example.chaofanteaching.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaofanteaching.All;
import com.example.chaofanteaching.BottomPopupOption;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.InfoList.InfoDetailActivity;
import com.example.chaofanteaching.MyPublish.MyPublishActivity;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.UpLoadFile;
import com.example.chaofanteaching.about.About;
import com.example.chaofanteaching.about.Student_Authentication;
import com.example.chaofanteaching.myself.AboutUs;
import com.example.chaofanteaching.myself.MyData;
import com.example.chaofanteaching.myself.RenZheng;
import com.example.chaofanteaching.myself.Setting;
import com.example.chaofanteaching.robot.Chatrobot;
import com.example.chaofanteaching.sign.LoginActivity;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.chaofanteaching.InfoList.InfoDetailActivity.makeStatusBarTransparent;


public class My extends Fragment {
    private List<Map<String, String>> dataSource = new ArrayList<>();
    private MyAdapter adapter;
    private OkHttpClient okHttpClient;
    private static final int PHOTO_REQUEST_CUT =3 ;
    private static String path = "/storage/emulated/0/";// sd路径
    protected static Uri uritempFile;
    private BottomPopupOption bottomPopupOption;
    private TextView name;
    private ImageView image;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private Bitmap bitmap;
    private ImageView img;
    private TextView renzheng;
    private SharedPreferences pre,pre1,pre2;
    private String a="";
    private SharedPreferences.Editor editor;
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  1:
                    Log.i("file","头像已更新");
                    break;
                case 2:
                    String str = msg.obj.toString();
                    if(str.equals("null")){
                        editor=pre1.edit();
                        editor.putString("nameContent","");
                        editor.commit();
                        name.setText("用户名");
                    }else{
                    editor=pre1.edit();
                    editor.putString("nameContent",str);
                    editor.commit();
                    name.setText(str);}
                    break;
                case 3:
                    String result=  msg.obj.toString();
                    if(result.equals("1")){
                        editor=pre1.edit();
                        editor.putString("renzheng",result);
                        editor.commit();
                        renzheng.setText("已认证");
                        img.setImageDrawable(getResources().getDrawable(R.drawable.v1));
                    }
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.my,container,false);
        okHttpClient=new OkHttpClient();
        pre= getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        a = pre.getString("userName", "");
        name=view.findViewById(R.id.name);
        image=view.findViewById(R.id.image);
        img=view.findViewById(R.id.img);
        img1=view.findViewById(R.id.img1);
        img2=view.findViewById(R.id.img2);
        img3=view.findViewById(R.id.img3);
        img4=view.findViewById(R.id.img4);
        renzheng=view.findViewById(R.id.renzheng);

        //状态栏透明
//        makeStatusBarTransparent(this.getActivity());

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pre.getString("userName","").equals("")){
                    Toast.makeText(getContext(),"请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                }else{
                if(pre1.getString("renzheng","").equals("1")){
                    Intent intent1=new Intent(getContext(), RenZheng.class);
                    startActivity(intent1);
                }else{
                    Intent intent1=new Intent(getContext(), Student_Authentication.class);
                    startActivity(intent1);
                }}
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pre.getString("userName","").equals("")){
                    Toast.makeText(getContext(),"请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                }else{
                    Intent intent=new Intent(getContext(),MyCollection.class);
                    intent.putExtra("user",a);
                    startActivity(intent);
                }
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pre.getString("userName","").equals("")){
                    Toast.makeText(getContext(),"请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                }else{
                    Intent i=new Intent(getContext(),Student_Authentication.class);
                    editor=pre.edit();
                    editor.putString("grade","1");
                    editor.commit();
                    startActivity(i);
                }
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pre.getString("userName","").equals("")){
                    Toast.makeText(getContext(),"请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                }
            }
        });


        dataSource.clear();
        Map<String,String> map=new HashMap<>();
        Map<String,String> map1=new HashMap<>();
        Map<String,String> map2=new HashMap<>();
        Map<String,String> map3=new HashMap<>();
        Map<String,String> map4=new HashMap<>();
        Map<String,String> map5=new HashMap<>();
        Map<String,String> map6=new HashMap<>();
        map.put("text","个人资料");
        map.put("img","person_data");
        map1.put("text","我发布的");
        map1.put("img","send");
        map3.put("text","关于我们");
        map3.put("img","authentication");
        map2.put("text","我的客服");
        map2.put("img","customer_service");
        map4.put("text","关于软件");
        map4.put("img","about");
        map5.put("text","插件");
        map5.put("img","plug");
        map6.put("text","设置");
        map6.put("img","setting");
        dataSource.add(map);
        dataSource.add(map1);
        dataSource.add(map2);
        dataSource.add(map3);
        dataSource.add(map4);
        dataSource.add(map5);
        dataSource.add(map6);

        adapter=new MyAdapter(getContext(),dataSource,R.layout.my_list);
        ListView listView=view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(a.equals("")){
                    Toast.makeText(getContext(),"请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                }else{
                    switch (position){
                        case 0:
                            Intent i=new Intent();
                            i.setClass(getContext(), MyData.class);
                            startActivity(i);
                            break;
                        case 1:
                            Intent intent=new Intent(getContext(), MyPublishActivity.class);
                            startActivity(intent);
                            break;
                        case 2:
                            Intent intent3=new Intent(getContext(), Chatrobot.class);
                            startActivity(intent3);
                            break;
                        case 3:
                            Intent intent4=new Intent(getContext(), AboutUs.class);
                            startActivity(intent4);
                            break;
                        case 4:
                            Intent intent1=new Intent(getContext(), About.class);
                            startActivity(intent1);
                            break;
                        case 5:
                            Toast.makeText(getContext(),"暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
                            break;
                        case 6:
                            Intent intent2=new Intent(getContext(), Setting.class);
                            startActivity(intent2);
                            break;
                    }
                }
            }
        });

        if(a.equals("")){
            image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.boy));
        }else{initView();}

        pre1=getContext().getSharedPreferences("data",Context.MODE_PRIVATE);
        String name1=pre1.getString("nameContent","");
        if(pre1.getString("renzheng","").equals("")){
            renzheng();
        }
        if(!a.equals("")){
        pre2=getContext().getSharedPreferences("id",Context.MODE_PRIVATE);
        String id1=pre2.getString("id","");
        saveid(id1);}


        if(!a.equals("")){
            if(name1.equals("")){
                look();
            }else{name.setText(name1);}
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a.equals("")) {
                    Toast.makeText(getContext(), "请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                } else {
                    bottomPopupOption = new BottomPopupOption(getActivity());
                    bottomPopupOption.setItemText("拍照", "相册");
                    bottomPopupOption.showPopupWindow();
                    bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            bottomPopupOption.dismiss();
                            switch (position) {
                                case 0:
                                    Intent cameraIntent =
                                            new Intent(
                                                    MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(cameraIntent, 8888);
                                    break;
                                case 1:
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                    //开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                                    startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                                    break;
                            }
                        }
                    });

                }
            }
        });
        return view;
    }

    private void asyncdownop() {
        new Thread(){
            @Override
            public void run() {
                try {
                    downimg();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                android.os.Message msg= Message.obtain();
                msg.what=1;
                handler.sendMessage(msg);
            }
        }.start();
    }

    private void downimg() throws IOException {
        String fileName = path + a+".png";// 图片名字
        Request request=new Request.Builder().url("http://175.24.102.160:8080/ChaoFanTeaching/DownImg?name="+a).build();
        Call call=okHttpClient.newCall(request);
        Response response=call.execute();
        Log.i("photo", String.valueOf(response.body().byteStream()));
        InputStream in=response.body().byteStream();
        FileOutputStream out=new FileOutputStream(fileName);
        byte[] bytes=new byte[1024];
        int n=-1;
        while ((n=in.read(bytes))!=-1){
            out.write(bytes,0,n);
            out.flush();
        }
        in.close();
        out.close();
    }

    private void initView() {

        //初始化控件
        Bitmap bt = BitmapFactory.decodeFile(path + a+".png");//从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            image.setImageDrawable(drawable);
        } else {
            //如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
            asyncdownop();

        }
    }

    /*
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//输出路径
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);

        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + a + ".png");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);

        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 8888 ) {
            //获取系统摄像头拍照的结果
            bitmap = data.getParcelableExtra("data");
            image.setImageBitmap(bitmap);
            setPicToView(bitmap);
            asyncupop();
        }
        if (requestCode == PHOTO_REQUEST_GALLERY) {
        // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    crop(uri);
                }
            } else if (requestCode == PHOTO_REQUEST_CUT) {
                // 从剪切图片返回的数据
                if (data != null) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uritempFile));
                    image.setImageBitmap(bitmap);
                    setPicToView(bitmap);
                    asyncupop();
                    }  catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void setPicToView(Bitmap mBitmap){
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + a+".png";// 图片名字
        Log.i("11",fileName);
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
        String filepath=path + a+".png";
        //创建上传异步任务类的对象
        UpLoadFile task=new UpLoadFile(getContext(),filepath);
        //开始执行异步任务
        task.execute("http://175.24.102.160:8080/ChaoFanTeaching/PhotoInsert?index=photo&name="+a);
    }

    @Override
    public void onResume() {
        super.onResume();
        pre1=getContext().getSharedPreferences("data",Context.MODE_PRIVATE);
        String name1=pre1.getString("nameContent","");
        if(name1.equals("")||name1.equals("null")||name1==null){name.setText("用户名");}
        else{name.setText(name1);}
        if(pre1.getString("renzheng","").equals("1")){
            renzheng.setText("已认证");
            img.setImageDrawable(getResources().getDrawable(R.drawable.v1));
        }
    }


    private void look(){
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("MyData?index=name&name="+a);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = str;
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }



    private void renzheng(){
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("MyData?index=renzheng&name="+a);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = str;
                        message.what = 3;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void saveid(String id){
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().
                url("http://175.24.102.160:8080/ChaoFanTeaching/MyData?name="+a+"&index=id&id="+id)
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {Log.i("aaa","");}
            @Override
            public void onResponse(Call call, Response response) throws IOException {Log.i("aaa","存入id");}
        });

    }

    //状态栏透明
    public static void makeStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}

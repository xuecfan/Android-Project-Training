package com.example.chaofanteaching.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chaofanteaching.BottomPopupOption;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.UpLoadFile;
import com.example.chaofanteaching.about.Student_Authentication;
import com.example.chaofanteaching.myself.MyData;
import com.example.chaofanteaching.myself.Setting;
import com.example.chaofanteaching.sign.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class My extends Fragment {

    private static final int PHOTO_REQUEST_CUT =3 ;
    private static String path = "/storage/emulated/0/";// sd路径
    protected static Uri uritempFile;
    private BottomPopupOption bottomPopupOption;
    private LinearLayout myself;
    private LinearLayout student;
    private LinearLayout setting;
    private LinearLayout customer_service;
    private LinearLayout send;
    private ImageView image;
    private Bitmap bitmap;
    private SharedPreferences pre;
    private String a="";

    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.my,container,false);

         pre= getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
         a = pre.getString("loginOrNot", "");

        customer_service=view.findViewById(R.id.customer_service);
        send=view.findViewById(R.id.send);
        myself=view.findViewById(R.id.myself);
        student=view.findViewById(R.id.student);
        setting=view.findViewById(R.id.setting);
        image=view.findViewById(R.id.image);
        if(a.equals("")){
            image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.boy));
        }else{initView();}

        customer_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a.equals("")) {
                    Toast.makeText(getContext(),"请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getContext(),"暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a.equals("")) {
                    Toast.makeText(getContext(),"请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                }else{}
            }
        });
        myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a.equals("")) {
                    Toast.makeText(getContext(),"请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                }else{
                Intent i=new Intent();
                i.setClass(getContext(), MyData.class);
                startActivity(i);}
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a.equals("")) {
                    Toast.makeText(getContext(), "请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                }else {
                Intent i=new Intent();
                i.setClass(getContext(), Student_Authentication.class);
                startActivity(i);}
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a.equals("")) {
                    Toast.makeText(getContext(),"请您先登录", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                }else{
                Intent i=new Intent();
                i.setClass(getContext(),Setting.class);
                startActivity(i);}
            }
        });


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


    private void initView() {
        //初始化控件
        Bitmap bt = BitmapFactory.decodeFile(path + a+".png");//从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            image.setImageDrawable(drawable);
        } else {
            /**
             *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
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

        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".png");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);

        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }




//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 8888 ) {
//            //获取系统摄像头拍照的结果
//            bitmap = data.getParcelableExtra("data");
//            image.setImageBitmap(bitmap);
//            uploadPic(bitmap);
//            setPicToView(bitmap);
//        }
//        if (requestCode == PHOTO_REQUEST_GALLERY) {
//            try {
//                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
//                //setPicToView(bitmap);
//                image.setImageBitmap(bitmap);
//    uploadPic(bitmap);
//    setPicToView(bitmap);
//} catch (IOException e) {
//        e.printStackTrace();
//        }
////            if (data != null) {
////
////                Bundle extras = data.getExtras();
////                head = extras.getParcelable("data");
////                if (head != null) {
////                    /**
////                     * 上传服务器代码
////                     */
////
////                    // head = toRoundBitmap1(head);//调用圆角处理方法
////                    setPicToView(head);// 保存在SD卡中
////                    image.setImageBitmap(head);// 用ImageView显示出来
////                    if (head != null && head.isRecycled()) {
////                        head.recycle();
////                    }
////
////                }
////            }
//        }
//        // 从相册返回的数据
////            if (data != null) {
////                // 得到图片的全路径
////                Uri uri = data.getData();
////                crop(uri);
////            }
////        } else if (requestCode == PHOTO_REQUEST_CUT) {
////            // 从剪切图片返回的数据
////            if (data != null) {
////                Bitmap bitmap = data.getParcelableExtra("data");
////                image.setImageBitmap(bitmap);
////            }
////            try {
////                // 将临时文件删除
////                tempFile.delete();
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////
////        }
//        super.onActivityResult(requestCode, resultCode, data);
//        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 8888 ) {
            //获取系统摄像头拍照的结果
            bitmap = data.getParcelableExtra("data");
            image.setImageBitmap(bitmap);
            //uploadPic(bitmap);
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

                    //Bitmap bitmap = data.getParcelableExtra("data");
                    image.setImageBitmap(bitmap);
                    //uploadPic(bitmap);
                    setPicToView(bitmap);
                    asyncupop();
                    //uploadImage();
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



//    private void uploadPic(Bitmap bitmap) {
//        // 上传至服务器
//        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
//        // 注意这里得到的图片已经是圆形图片了
//        // bitmap是没有做个圆形处理的，但已经被裁剪了
//
//
//        String imagePath = savePhoto(bitmap, Environment
//                .getExternalStorageDirectory().getAbsolutePath(), String
//                .valueOf(System.currentTimeMillis()));
//        Log.e("imagePath", imagePath+"");
//        if(imagePath != null){
//            // 拿着imagePath上传了
//            // ...
//        }
//    }

    public String uploadImage() throws IOException, JSONException {
        String host="10.7.89.221";
        String imagePath=path + a+".png";
        OkHttpClient okHttpClient = new OkHttpClient();
        Log.d("imagePath", imagePath);
        File file = new File(imagePath);
        RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", imagePath, image)
                .build();
        Request request = new Request.Builder()
                .url("http://"+host+":8080/androidhttp/upfile")
                .post(requestBody)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        return jsonObject.optString("image");}



    private void asyncupop() {
        String host="192.168.137.1";
        String filepath=path + a+".png";
        //创建上传异步任务类的对象
        UpLoadFile task=new UpLoadFile(getContext(),filepath);
        //开始执行异步任务
        task.execute("http://"+host+":8080/androidhttp/upfile");
    }

//    private void downimg() throws IOException {
//
//        String host="10.7.89.221";
//        Request request=new Request.Builder().url("http://"+host+":8080/androidhttp/downimg").build();
//        Call call=okHttpClient.newCall(request);
//        Response response=call.execute();
//        InputStream in=response.body().byteStream();
//        String fileName = path + a+".png";// 图片名字
//        OutputStream out=new FileOutputStream(fileName);
//        byte[] bytes=new byte[1024];
//        int n=-1;
//        while ((n=in.read(bytes))!=-1){
//            out.write(bytes,0,n);
//            out.flush();
//        }
//        in.close();
//        out.close();
//    }

//    public static String savePhoto(Bitmap photoBitmap, String path,
//                                   String photoName) {
//        String localPath = null;
//        if (android.os.Environment.getExternalStorageState().equals(
//                android.os.Environment.MEDIA_MOUNTED)) {
//            File dir = new File(path);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//
//            File photoFile = new File(path, photoName + ".png");
//            FileOutputStream fileOutputStream = null;
//            try {
//                fileOutputStream = new FileOutputStream(photoFile);
//                if (photoBitmap != null) {
//                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
//                            fileOutputStream)) { // 转换完成
//                        localPath = photoFile.getPath();
//                        fileOutputStream.flush();
//                    }
//                }
//            } catch (FileNotFoundException e) {
//                photoFile.delete();
//                localPath = null;
//                e.printStackTrace();
//            } catch (IOException e) {
//                photoFile.delete();
//                localPath = null;
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (fileOutputStream != null) {
//                        fileOutputStream.close();
//                        fileOutputStream = null;
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return localPath;
//    }
}

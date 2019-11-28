package com.example.chaofanteaching.fragment;

import android.Manifest;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;


import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chaofanteaching.BottomPopupOption;
import com.example.chaofanteaching.R;


import com.example.chaofanteaching.about.About;
import com.example.chaofanteaching.about.Student_Authentication;
import com.example.chaofanteaching.myself.MyData;
import com.example.chaofanteaching.sign.LoginActivity;
import java.io.File;
import java.io.IOException;


public class My extends Fragment {

    private BottomPopupOption bottomPopupOption;
    private ImageButton myself;
    private ImageButton student;
    private ImageButton about;
    private Button btn;
    private ImageView image;
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    //private static final int PHOTO_REQUEST_CUT = 3;// 结果
    //private File tempFile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.my,container,false);
        myself=view.findViewById(R.id.myself);
        student=view.findViewById(R.id.student);
        about=view.findViewById(R.id.about);
        btn=view.findViewById(R.id.btn);
        image=view.findViewById(R.id.image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomPopupOption = new BottomPopupOption(getActivity());
                bottomPopupOption.setItemText("拍照","相册");
                bottomPopupOption.showPopupWindow();
                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        bottomPopupOption.dismiss();
                        switch (position){
                            case 0:
                                Intent cameraIntent =
                                        new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
//                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
//                                        Environment.getExternalStorageDirectory(), "hand.jpg")));
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
        });
        myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(getContext(), MyData.class);
                startActivity(i);
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(getContext(), Student_Authentication.class);
                startActivity(i);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(getContext(),About.class);
                startActivity(i);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        return view;
    }





    /*
     * 剪切图片
     */
//    private void crop(Uri uri) {
//        // 裁剪图片意图
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        // 裁剪框的比例，1：1
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // 裁剪后输出图片的尺寸大小
//        intent.putExtra("outputX", 250);
//        intent.putExtra("outputY", 250);
//        // intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//输出路径
//        intent.putExtra("outputFormat", "JPEG");// 图片格式
//        intent.putExtra("noFaceDetection", true);// 取消人脸识别
//        intent.putExtra("return-data", true);
//
//        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
//        startActivityForResult(intent, PHOTO_REQUEST_CUT);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 8888 ) {
            //获取系统摄像头拍照的结果
            Bitmap bitmap = data.getParcelableExtra("data");
            image.setImageBitmap(bitmap);
        }
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            try {
                image.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            // 从相册返回的数据

//            if (data != null) {
//                // 得到图片的全路径
//                Uri uri = data.getData();
//                crop(uri);
//            }
//        } else if (requestCode == PHOTO_REQUEST_CUT) {
//            // 从剪切图片返回的数据
//            if (data != null) {
//                Bitmap bitmap = data.getParcelableExtra("data");
//                image.setImageBitmap(bitmap);
//            }
//            try {
//                // 将临时文件删除
//                tempFile.delete();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }



//    private void setPicToView(Bitmap mBitmap) {
//        String sdStatus = Environment.getExternalStorageState();
//        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
//            return;
//        }
//        FileOutputStream b = null;
//        File file = new File(path);
//        file.mkdirs();// 创建文件夹
//        String fileName = path + "head.jpg";// 图片名字
//        try {
//            b = new FileOutputStream(fileName);
//            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // 关闭流
//                b.flush();
//                b.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//     //从本地的文件中以保存的图片中 获取图片的方法
//    private Bitmap getBitmap(String pathString) {
//        Bitmap bitmap = null;
//        try {
//            File file = new File(pathString);
//            if (file.exists()) {
//                bitmap = BitmapFactory.decodeFile(pathString);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }




}

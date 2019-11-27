package com.example.chaofanteaching.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chaofanteaching.BottomPopupOption;
import com.example.chaofanteaching.MainActivity;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.RoundImageView;
import com.example.chaofanteaching.about.About;
import com.example.chaofanteaching.about.Student_Authentication;
import com.example.chaofanteaching.myself.MyData;
import com.example.chaofanteaching.sign.LoginActivity;

import java.io.File;

public class My extends Fragment {

    private BottomPopupOption bottomPopupOption;
    private static final int REQUEST_TAKE_PHOTO_PERMISSION=4;
    private LinearLayout myself;
    private LinearLayout student;
    private LinearLayout about;
    private Button btn;
    private ImageView image;
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private File tempFile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.my,container,false);
        myself=view.findViewById(R.id.myself);
        student=view.findViewById(R.id.student);
        about=view.findViewById(R.id.about);
        btn=view.findViewById(R.id.btn);
        image=view.findViewById(R.id.image);
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
//                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
//            }
//        });
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
                                Toast.makeText(getActivity(),"拍照",Toast.LENGTH_SHORT).show();
                                Intent cameraIntent =
                                        new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, 8888);
                                //choseHeadImageFromCameraCapture();

                                break;
                            case 1:
                                Toast.makeText(getActivity(),"相册",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

                               // choseHeadImageFromGallery();

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
        // intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//输出路径
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);

        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 8888 ) {
            //获取系统摄像头拍照的结果
            Bitmap img = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(img);
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
                Bitmap bitmap = data.getParcelableExtra("data");
                image.setImageBitmap(bitmap);
            }
            try {
                // 将临时文件删除
                tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        //6.0以上动态获取权限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_TAKE_PHOTO_PERMISSION);

        }else {
            Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//            // 判断存储卡是否可用，存储照片文件
//            if (hasSdcard()) {
//
//                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
//                        .fromFile(new File(Environment
//                                .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
//            }
            startActivityForResult(intentFromCapture,PHOTO_REQUEST_GALLERY);// CODE_CAMERA_REQUEST);
        }

    }
//    // 从本地相册选取图片作为头像
//    private void choseHeadImageFromGallery() {
//        Intent intentFromGallery = new Intent();
//        // 设置文件类型
//        intentFromGallery.setType("image/*");
//        intentFromGallery.setAction(Intent.ACTION_PICK);
//        startActivityForResult(intentFromGallery, PHOTO_REQUEST_GALLERY);//CODE_GALLERY_REQUEST);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode,
//                                    Intent intent) {
//
//        // 用户没有进行有效的设置操作，返回
//        if (resultCode == RESULT_CANCELED) {
////            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        switch (requestCode) {
//            case CODE_GALLERY_REQUEST:
//                cropRawPhoto(intent.getData());
//                break;
//
//            case CODE_CAMERA_REQUEST:
//                if (hasSdcard()) {
//                    File tempFile = new File(
//                            Environment.getExternalStorageDirectory(),
//                            IMAGE_FILE_NAME);
//                    cropRawPhoto(Uri.fromFile(tempFile));
//                } else {
//                    Toast.makeText(getActivity(),"没有sd卡",Toast.LENGTH_SHORT).show();
//                }
//
//                break;
//
//            case CODE_RESULT_REQUEST:
//                if (intent != null) {
//                    setImageToHeadView(intent);
//                    File file = new File(
//                            Environment.getExternalStorageDirectory(),
//                            IMAGE_FILE_NAME);
//                    if (file.exists()&&!file.isDirectory()){
//                        file.delete();
//                    }
//                }
//
//                break;
//        }
//
//        super.onActivityResult(requestCode, resultCode, intent);
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功，可以拍照
                choseHeadImageFromCameraCapture();
            } else {
                Toast.makeText(getActivity()," 你拒绝了权限,该功能不可用\n可在应用设置里授权拍照哦",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}

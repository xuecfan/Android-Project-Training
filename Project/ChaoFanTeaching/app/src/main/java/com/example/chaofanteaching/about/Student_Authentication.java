package com.example.chaofanteaching.about;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.chaofanteaching.R;
import com.example.chaofanteaching.fragment.My;

public class Student_Authentication extends AppCompatActivity {
    private ImageButton au_fanhui;
    private ImageButton student1;
    private ImageButton student2;
    private Button shangchuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.student_authenticaation);

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
        }
        if(requestCode == 8889 && resultCode == RESULT_OK) {
            //获取系统摄像头拍照的结果
            Bitmap img = (Bitmap) data.getExtras().get("data");
            student2.setImageBitmap(img);
        }
    }
}

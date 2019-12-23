package com.example.chaofanteaching;



import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class Video extends AppCompatActivity {

    private Button btnstart;
    private Button pause;
    private VideoView vv;
    private customclicklistener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getviews();
        regists();

    }

    private void getviews(){
        btnstart=findViewById(R.id.btnstart);
        vv=findViewById(R.id.vv);
        pause=findViewById(R.id.pause);
    }
    private void regists(){
        listener=new customclicklistener();
        btnstart.setOnClickListener(listener);
        vv.setOnClickListener(listener);
        pause.setOnClickListener(listener);
    }
    class customclicklistener implements View.OnClickListener{
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnstart:
                    File videopath=new File(getFilesDir()+"/a.mp4");
                    if(videopath.exists()) {
                        vv.setVideoPath(videopath.getAbsolutePath());
                        vv.start();
                    }
                    break;
                case R.id.pause:
                    if(vv.isPlaying()){
                        vv.pause();
                        pause.setText("继续播放");
                    }else {
                        vv.start();
                        pause.setText("暂停播放");
                    }
                    break;
            }
        }
    }









}

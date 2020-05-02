package com.example.chaofanteaching.sign;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import com.example.chaofanteaching.ActivityCollector;
import com.example.chaofanteaching.All;
import com.example.chaofanteaching.R;

import ru.alexbykov.nopermission.PermissionHelper;

public class ChooseIdentityActivity extends Activity implements View.OnTouchListener,View.OnClickListener{

    private Button mTestBtn;
    private Button mTestBtn2;
    private PermissionHelper permissionHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_identity);
        String[]PermissionString={
                Manifest.permission.CAMERA,
                Manifest.permission.LOCATION_HARDWARE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_SYNC_SETTINGS,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        };
        permissionHelper=new PermissionHelper(this);
        permissionHelper.check(PermissionString).onSuccess(new Runnable() {
            @Override
            public void run() {
                Log.i("permisson","允许权限");
            }
        }).onDenied(new Runnable() {
            @Override
            public void run() {
                ChooseIdentityActivity.this.finish();
                Log.i("permisson","拒绝权限");
            }
        }).run();
        //将此页添加到Activity控制器列表中
        ActivityCollector.addActivity(this);

        mTestBtn = findViewById(R.id.btn_test);
        mTestBtn2 = findViewById(R.id.btn_test2);

        mTestBtn.setOnClickListener(this);
        mTestBtn.setOnTouchListener(this);

        mTestBtn2.setOnClickListener(this);
        mTestBtn2.setOnTouchListener(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (v.getId() == R.id.btn_test){
                    changeStatus(mTestBtn,0.8,1f);
                }else if (v.getId() == R.id.btn_test2){
                    changeStatus(mTestBtn2,0.8,1f);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(v.getId() == R.id.btn_test){
                    changeStatus(mTestBtn,1,1f);
                    BtnToIntent(mTestBtn);
                }else if (v.getId() == R.id.btn_test2){
                    changeStatus(mTestBtn2,1,1f);
                    BtnToIntent(mTestBtn2);
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
    }

    //设置按钮大小、透明度变化
    public void changeStatus(Button button, double size, float alpha) {
        button.setScaleX((float) size);
        button.setScaleY((float) size);
        button.setAlpha(alpha);
    }
    //将按钮放大变透明，并跳转
    public void BtnToIntent(final Button button) {
        Intent intent = new Intent(ChooseIdentityActivity.this, All.class);
        intent.setAction("true");
        if(button == mTestBtn){
            //EventBus.getDefault().postSticky("parent");
            intent.putExtra("status",0);
        }else if (button == mTestBtn2){
            //EventBus.getDefault().postSticky("teacher");
            intent.putExtra("status",1);
        }
        startActivity(intent);
        ActivityCollector.finishAll();
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

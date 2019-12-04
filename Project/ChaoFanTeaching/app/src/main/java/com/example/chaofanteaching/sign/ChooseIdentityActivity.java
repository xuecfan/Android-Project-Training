package com.example.chaofanteaching.sign;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.chaofanteaching.All;
import com.example.chaofanteaching.R;

import java.util.Timer;
import java.util.TimerTask;

public class ChooseIdentityActivity extends Activity implements View.OnTouchListener,View.OnClickListener{

    private Button mTestBtn;
    private Button mTestBtn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_identity);

        mTestBtn = findViewById(R.id.btn_test);
        mTestBtn2 = findViewById(R.id.btn_test2);

        mTestBtn.setOnClickListener(this);
        mTestBtn.setOnTouchListener(this);

        mTestBtn2.setOnClickListener(this);
        mTestBtn2.setOnTouchListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置恢复原大小
        changeStatus(mTestBtn,1,1f);
        changeStatus(mTestBtn2,1,1f);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (v.getId() == R.id.btn_test){
                    changeStatus(mTestBtn,0.95,1f);
                }else if (v.getId() == R.id.btn_test2){
                    changeStatus(mTestBtn2,0.95,1f);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(v.getId() == R.id.btn_test){
                    changeStatusAndIntent(mTestBtn);
                }else if (v.getId() == R.id.btn_test2){
                    changeStatusAndIntent(mTestBtn2);
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
    public void changeStatusAndIntent(Button button) {
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(button, "scaleX",
                        1f, 10f);
        animator.setDuration(1000);
        animator.start();
        ObjectAnimator animator1 = ObjectAnimator
                .ofFloat(button, "scaleY",
                        1f, 10f);
        animator1.setDuration(1000);
        animator1.start();
        ObjectAnimator animator2 = ObjectAnimator
                .ofFloat(button, "alpha",
                        1f, 0f);
        animator2.setDuration(1000);
        animator2.start();


        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(ChooseIdentityActivity.this, All.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        };
        timer.schedule(task, 600);
    }
}

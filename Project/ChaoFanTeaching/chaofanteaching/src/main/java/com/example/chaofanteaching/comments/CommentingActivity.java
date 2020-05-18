package com.example.chaofanteaching.comments;

import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.hyphenate.easeui.widget.EaseTitleBar;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class CommentingActivity extends AppCompatActivity {
    //标题栏
    private EaseTitleBar titleBar;
    private TextView publishButton;
    //评论者和被评论者姓名
    private TextView commentor1Name;
    private TextView commentor2Name;

    //评星条
    private TextView isOnTimeValue;
    private RatingBar isOnTimeRatingBar;
    private TextView teachingQualityValue;
    private RatingBar teachingQualityRatingBar;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:

            }
        }
    };

    public CommentingActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commenting);

        //初始化view
        initView();
        //标题栏
        setTitleBar();
        //评星条
        makeStar();

    }

    /**
     * 通过id获取view
     */
    public void initView(){
        titleBar=findViewById(R.id.commenting_titleBar);

        commentor1Name = findViewById(R.id.commentor1_name);
        commentor2Name = findViewById(R.id.commentor2_name);

        isOnTimeValue = findViewById(R.id.is_on_time_value);
        isOnTimeRatingBar = findViewById(R.id.is_on_time_ratingBar);
        teachingQualityValue = findViewById(R.id.teaching_quality_value);
        teachingQualityRatingBar = findViewById(R.id.teaching_quality_ratingBar);

        publishButton = findViewById(R.id.publish_button);
    }

    /**
     * 标题栏
     */
    public void setTitleBar(){
        titleBar.setTitle("发表评价");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //发布按钮
        publishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new Thread(){
                    HttpURLConnection connection = null;

                    @Override
                    public void run() {
                        try {
                            connection = HttpConnectionUtils.getConnection("Comment?id=0&");
                            int code = connection.getResponseCode();
                            if (code == 200){
                                InputStream inputStream = connection.getInputStream();
                                String string = StreamChangeStrUtils.toChange(inputStream);
                                Message message = Message.obtain();
                                message.obj = string;
                                message.what = 0;
                                handler.sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    /**
     * 评星条
     */
    public void makeStar(){
        isOnTimeRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                final int r;
                r = (int) ratingBar.getRating();
                judgeR(r,isOnTimeValue);
            }
        });
        teachingQualityRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                final int r;
                r = (int) ratingBar.getRating();
                judgeR(r,teachingQualityValue);
            }
        });

    }
    public void judgeR(int r , TextView textView){
        switch(r){
            case 1:
                textView.setText("差评");
                break;
            case 2:
                textView.setText("一般");
                break;
            case 3:
                textView.setText("好评");
                break;
        }
    }

    /**
     * 点击editview外隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                UtilHelpers.hideKeyboard(event,view,CommentingActivity.this);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}

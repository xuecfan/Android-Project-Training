package com.example.chaofanteaching.sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaofanteaching.ActivityCollector;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.utils.ToastUtils;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class LogonActivity extends AppCompatActivity {
    ProgressDialog mDialog;
    private int status = 2;//表示身份是家长还是老师，0为家长，1为老师，2为初值表示status还未修改过
    private int landingStatus = 0;//表示登录状态，刚注册时默认为0，登录中为，未登录为0
    private EditText myId;
    private EditText myPW;
    private EditText myPWAgain;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();//隐藏标题栏
        setStatusBar();//设置状态栏
        setContentView(R.layout.activity_logon);


        ActivityCollector.addActivity(this);

        //获取id
        ImageView backToLogin = findViewById(R.id.backToLogin);
        final TextView imParent = findViewById(R.id.imParent);
        final TextView imTeacher = findViewById(R.id.imTeacher);
        myId = findViewById(R.id.myId);
        myPW = findViewById(R.id.myPW);
        myPWAgain = findViewById(R.id.myPWAgain);
        ImageView logonBtn = findViewById(R.id.logonBtn);

        //返回登陆页面
        backToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
//                Intent intent = new Intent();
//                intent.setClass(LogonActivity.this,LoginActivity.class);
//                startActivity(intent);
            }
        });

        //切换身份
        imParent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //更改背景
                imParent.setBackground(getResources().getDrawable(R.drawable.leftround2));
                imTeacher.setBackground(getResources().getDrawable(R.drawable.rightround));
                //更改字的颜色
                imParent.setTextColor(Color.parseColor("#ffffff"));
                imTeacher.setTextColor(Color.parseColor("#888888"));

//                imParent.setBackgroundColor(Color.parseColor("#D8900A"));
//                imTeacher.setBackgroundColor(Color.parseColor("#F2F3F6"));
                status = 0;
            }
        });
        imTeacher.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //更改背景
                imParent.setBackground(getResources().getDrawable(R.drawable.leftround));
                imTeacher.setBackground(getResources().getDrawable(R.drawable.rightround2));
                //更改字的颜色
                imParent.setTextColor(Color.parseColor("#888888"));
                imTeacher.setTextColor(Color.parseColor("#ffffff"));

//                imTeacher.setBackgroundColor(Color.parseColor("#D8900A"));
//                imParent.setBackgroundColor(Color.parseColor("#F2F3F6"));
                status = 1;
            }
        });

        //注册按钮
        logonBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
//                String loginOrNot = sharedPreferences.getString("loginOrNot", null);//(key,若无数据需要赋的值)
//                //Log.e("xcf",loginOrNot);

                String user=myId.getText().toString();
                String pasd=myPW.getText().toString();
                String pasdAgain = myPWAgain.getText().toString();
                if ((user.isEmpty()) || (pasd.isEmpty()) || (pasdAgain.isEmpty())){
                    Toast.makeText(getApplication(),R.string.IdAndPWIsEmpty,Toast.LENGTH_SHORT).show();
                }else if (!pasd.equals(pasdAgain)){
                    Toast.makeText(getApplication(),R.string.PWDiffer,Toast.LENGTH_SHORT).show();
                }else if (status == 2){
                    Toast.makeText(getApplication(),R.string.status,Toast.LENGTH_SHORT).show();

                }else{
                    addUser(user,pasd,status,landingStatus);
                    register();
                }

            }
        });
    }

    //注册聊天服务器
    public void register() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("注册中，请稍后......");
        mDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(myId.getText().toString().trim(), myPW.getText().toString().trim());

                    if (!LogonActivity.this.isFinishing()) {
                        mDialog.dismiss();
                    }
                    //ToastUtils.showLong("注册成功，用户名是:" + myId.getText().toString() + "  快开始聊天吧");

                } catch (final HyphenateException e) {
                    e.printStackTrace();

                    if (!LogonActivity.this.isFinishing()) {
                        mDialog.dismiss();
                    }
                    /**
                     * 关于错误码可以参考官方api详细说明
                     * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                     */
                    int errorCode = e.getErrorCode();
                    String message = e.getMessage();
                    switch (errorCode) {
                        case EMError.NETWORK_ERROR:
                            ToastUtils.showLong("网络异常，请检查网络！ code: " + errorCode + "，message: " + message);
                            break;
                        case EMError.USER_ALREADY_EXIST:
                            ToastUtils.showLong("用户名已存在,请尝试登录！ code: " + errorCode + "，message: " + message);
                            break;
                        case EMError.USER_ALREADY_LOGIN:
                            ToastUtils.showLong("用户已登录！ code: " + errorCode + "，message: " + message);
                            break;
                        case EMError.USER_AUTHENTICATION_FAILED:
                            ToastUtils.showLong("用户id或密码错误！ code: " + errorCode + "，message: " + message);
                            break;
                        case EMError.SERVER_UNKNOWN_ERROR:
                            ToastUtils.showLong("服务器位置错误！ code: " + errorCode + "，message: " + message);
                            break;
                        case EMError.USER_REG_FAILED:
                            ToastUtils.showLong("注册失败！ code: " + errorCode + "，message: " + message);
                            break;
                        default:
                            ToastUtils.showLong("ml_sign_up_failed  code: " + errorCode + "，message: " + message);
                            break;

                    }


                }
            }
        }).start();


    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    //设置状态栏为白色，图标和字体为暗色
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));//设置状态栏颜色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
    }

    //在数据库里添加用户，判断是否注册成功
    private void addUser(final String a, final String b, final int c,final int d){
        handler = new Handler(){
            public void handleMessage(android.os.Message message){
                switch (message.what){
                    case 1:
                        String string = message.obj.toString();
                        String para = String.valueOf(1);
                        if (string.equals(para)){
                            Toast.makeText(getApplication(),"注册成功，请登录",Toast.LENGTH_LONG).show();
//                            finish();

                            ActivityCollector.finishAll();
                            Intent intent = new Intent();
                            intent.setClass(LogonActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else if (string.equals("0")){
                            Toast.makeText(getApplication(),"注册失败，此账号已存在",Toast.LENGTH_SHORT).show();

                        }
                }
            }
        };
        new Thread(){
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("AccountServlet?name="+a+"&pasd="+b+"&role="+c+"&landingStatus="+d);
                    int code = connection.getResponseCode();
                    if(code!=200){
                        Log.e("error","网络连接失败");
                    }else {
                        InputStream inputStream = connection.getInputStream();
                        String string = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = string;
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

package com.example.chaofanteaching.sign;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chaofanteaching.ActivityCollector;
import com.example.chaofanteaching.All;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.comments.UtilHelpers;
import com.example.chaofanteaching.utils.ToastUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class LoginActivity extends AppCompatActivity {
    private EditText myId;
    private EditText myPW;

    private String myid;
    ProgressDialog mDialog;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message message){
            switch (message.what){
                case 0:
                    String string = message.obj.toString();
                    System.out.println("从服务器传来的servlet页面数字："+string);
                    if (string.equals("10") || string.equals("11")){
                        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("role", string);
                        editor.apply();

                        //保存用户名
                        SharedPreferences.Editor editor1 = sharedPreferences.edit();
                        editor1.putString("userName",myid);
                        editor1.apply();

                        login();
                        Intent intent = new Intent();
                        intent.setClass( LoginActivity.this, All.class);
                        intent.setAction("true");
                        startActivity(intent);

                        ActivityCollector.finishAll();
                    }else if(string.equals("0")){
                        Toast.makeText(getApplication(),"用户名或密码错误",Toast.LENGTH_LONG).show();
                        myPW.setText("");
                    }else if (string.equals("900")){
                        Toast.makeText(getApplication(),"此账号登陆中，请检查",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStatusBar();//设置状态栏
        setContentView(R.layout.activity_login);
        //将此页添加到Activity控制器列表中
        ActivityCollector.addActivity(this);
        //获取id
        myId = findViewById(R.id.myId);
        myPW = findViewById(R.id.myPW);
        ImageView loginBtn = findViewById(R.id.myLoginBtn);
        TextView toLogonBtn=findViewById(R.id.toLogonBtn);
        TextView findMyPW = findViewById(R.id.findMyPW);
        TextView serviceAgreement = findViewById(R.id.serviceAgreement);
        //登陆按钮
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myid = myId.getText().toString();
                String mypw = myPW.getText().toString();
                String IdAndPW = myid +","+ mypw;
                System.out.println(myid+"   "+mypw+"   "+IdAndPW);
                if ((myid.isEmpty()) || (mypw.isEmpty())){
                    Toast.makeText(getApplication(),"邮箱和密码不能为空",Toast.LENGTH_LONG).show();
                } else{
                    testUser(myid,mypw);
                }

            }
        });

        //忘记密码
        findMyPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,FindMyPWActivity.class);
                startActivity(intent);
            }
        });
        //用户注册
        toLogonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(LoginActivity.this,LogonActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     *点击editview外隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                UtilHelpers.hideKeyboard(event,view,LoginActivity.this);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    //登陆聊天服务器
    public void login() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("正在登陆请稍后......");
        mDialog.show();
        EMClient.getInstance().login(myId.getText().toString(), myPW.getText().toString(), new EMCallBack() {
            @Override
            public void onSuccess() {
                if (!LoginActivity.this.isFinishing()) {
                    mDialog.dismiss();
                }
                // 加载所有群组到内存，如果使用了群组的话
//                EMClient.getInstance().groupManager().loadAllGroups();
                // 加载所有会话到内存
                EMClient.getInstance().chatManager().loadAllConversations();
                //ToastUtils.showLong("登录成功");
                //ECMainActivity.show(LoginActivity.this);
                finish();
            }

            @Override
            public void onError(final int i, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                        ToastUtils.showLong("登录失败 code: " + i + ",message: " + s);
                        switch (i) {
                            case EMError.NETWORK_ERROR:
                                ToastUtils.showLong("网络异常，请检查网络！ code: " + i + "，message: " + s);
                                break;
                            case EMError.INVALID_USER_NAME:
                                ToastUtils.showLong("无效用户名！ code: " + i + "，message: " + s);
                                break;
                            case EMError.INVALID_PASSWORD:
//                                ToastUtils.showLong("用户密码不正确！ code: " + i + "，message: " + s);
                                break;
                            case EMError.USER_AUTHENTICATION_FAILED:
//                                ToastUtils.showLong("用户名或密码不正确！ code: " + i + "，message: " + s);
                                break;
                            case EMError.USER_NOT_FOUND:
                                ToastUtils.showLong("用户不存在！ code: " + i + "，message: " + s);
                                break;
                            case EMError.SERVER_NOT_REACHABLE:
                                ToastUtils.showLong("无法连接到服务器！ code: " + i + "，message: " + s);
                                break;
                            case EMError.SERVER_BUSY:
                                ToastUtils.showLong("服务器繁忙，请稍后.... code: " + i + "，message: " + s);
                                break;
                            case EMError.SERVER_TIMEOUT:
                                ToastUtils.showLong("等待服务器响应超时！ code: " + i + "，message: " + s);
                                break;
                            case EMError.SERVER_UNKNOWN_ERROR:
                                ToastUtils.showLong("未知服务器错误！ code: " + i + "，message: " + s);
                                break;
                            case EMError.USER_ALREADY_LOGIN:
                                ToastUtils.showLong("用户已登录！ code: " + i + "，message: " + s);
                                break;
                        }
                    }
                });
            }
            @Override
            public void onProgress(int i, String s) {
            }
        });
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

    private void testUser(final String a, final String b){

        new Thread(){
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("LoginServlet?myid="+a+"&mypw="+b);
                    int code = connection.getResponseCode();
                    if(code!=200){
                        Log.e("error","网络连接失败");
                    }else{
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = str;
                        message.what = 0;
                        handler.sendMessage(message);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
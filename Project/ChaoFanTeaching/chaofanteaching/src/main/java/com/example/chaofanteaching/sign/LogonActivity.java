package com.example.chaofanteaching.sign;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chaofanteaching.ActivityCollector;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.comments.UtilHelpers;
import com.example.chaofanteaching.myself.MyData;
import com.example.chaofanteaching.utils.ToastUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class LogonActivity extends AppCompatActivity {
    ProgressDialog mDialog;
    private String role = "2";//表示身份是家长还是老师，0为家长，1为老师，2为初值表示status还未修改过
    private int landingStatus = 0;//表示登录状态，刚注册时默认为1（直接进入登录状态并跳转到个人资料页），已登录为1，未登录为0
    private EditText myId;
    private EditText myPW;
    private EditText myPWAgain;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message message){
            String string = message.obj.toString();
            switch (message.what){
                case 0:
                    if (string.equals("1")){
                        Toast.makeText(getApplication(),"注册成功",Toast.LENGTH_SHORT).show();
                        ActivityCollector.finishAll();
                        Toast.makeText(LogonActivity.this, "请完善个人资料", Toast.LENGTH_LONG).show();
                    }else if (string.equals("0")){
                        Toast.makeText(getApplication(),"注册失败，此账号已存在",Toast.LENGTH_SHORT).show();
                    }
                case 1:
                    System.out.println("从服务器传来的servlet页面数字："+string);
                    if (string.equals("10") || string.equals("11")){
                        //保存全局变量（role和userName）
                        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("role", string);
                        editor.apply();
                        SharedPreferences.Editor editor1 = sharedPreferences.edit();
                        editor1.putString("userName",myId.getText().toString());
                        editor1.apply();

                        login();//登录聊天

                        Intent intent = new Intent(LogonActivity.this, MyData.class);
                        intent.putExtra("firstTimeToMyData",1);
                        startActivity(intent);

                        ActivityCollector.finishAll();
                    }else if(string.equals("0")){
                        Toast.makeText(getApplication(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    }else if (string.equals("900")){
                        Toast.makeText(getApplication(),"此账号登陆中，请检查",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();//设置状态栏
        setContentView(R.layout.activity_logon);

        //加入销毁队列
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

                role = "0";
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

                role = "1";
            }
        });

        //注册按钮
        logonBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user=myId.getText().toString();
                String pasd=myPW.getText().toString();
                String pasdAgain = myPWAgain.getText().toString();
                if ((user.isEmpty()) || (pasd.isEmpty()) || (pasdAgain.isEmpty())){
                    Toast.makeText(getApplication(),R.string.IdAndPWIsEmpty,Toast.LENGTH_SHORT).show();
                }else if (!pasd.equals(pasdAgain)){
                    Toast.makeText(getApplication(),R.string.PWDiffer,Toast.LENGTH_SHORT).show();
                }else if (role.equals("2")){
                    Toast.makeText(getApplication(),R.string.status,Toast.LENGTH_SHORT).show();
                }else{
                    //注册账号
                    addUser("AccountServlet?name="+user+"&pasd="+pasd+"&role="+ role +"&landingStatus="+landingStatus,0);
                    register();//注册聊天服务器
                    testUser(user,pasd);//登录账号
                }
            }
        });
    }

    //登陆聊天服务器
    public void login() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("正在登陆请稍后......");
        mDialog.show();
        EMClient.getInstance().login(myId.getText().toString(), myPW.getText().toString(), new EMCallBack() {
            @Override
            public void onSuccess() {
                if (!LogonActivity.this.isFinishing()) {
                    mDialog.dismiss();
                }
                // 加载所有群组到内存，如果使用了群组的话
//                EMClient.getInstance().groupManager().loadAllGroups();
                // 加载所有会话到内存
                EMClient.getInstance().chatManager().loadAllConversations();
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

    /**
     * 登录操作
     * @param a
     * @param b
     */
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
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     *点击editview外隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                UtilHelpers.hideKeyboard(event,view,LogonActivity.this);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
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
//                    int errorCode = e.getErrorCode();
//                    String message = e.getMessage();
//                    switch (errorCode) {
//                        case EMError.NETWORK_ERROR:
//                            ToastUtils.showLong("网络异常，请检查网络！ code: " + errorCode + "，message: " + message);
//                            break;
//                        case EMError.USER_ALREADY_EXIST:
//                            ToastUtils.showLong("用户名已存在,请尝试登录！ code: " + errorCode + "，message: " + message);
//                            break;
//                        case EMError.USER_ALREADY_LOGIN:
//                            ToastUtils.showLong("用户已登录！ code: " + errorCode + "，message: " + message);
//                            break;
//                        case EMError.USER_AUTHENTICATION_FAILED:
//                            ToastUtils.showLong("用户id或密码错误！ code: " + errorCode + "，message: " + message);
//                            break;
//                        case EMError.SERVER_UNKNOWN_ERROR:
//                            ToastUtils.showLong("服务器位置错误！ code: " + errorCode + "，message: " + message);
//                            break;
//                        case EMError.USER_REG_FAILED:
//                            ToastUtils.showLong("注册失败！ code: " + errorCode + "，message: " + message);
//                            break;
//                        default:
//                            ToastUtils.showLong("ml_sign_up_failed  code: " + errorCode + "，message: " + message);
//                            break;
//                    }
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
    private void addUser(String path, int what){
        new Thread(){
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection(path);
                    int code = connection.getResponseCode();
                    if(code!=200){
                        Log.e("error","网络连接失败");
                    }else {
                        InputStream inputStream = connection.getInputStream();
                        String string = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = string;
                        message.what = what;
                        handler.sendMessage(message);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

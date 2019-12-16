package com.example.chaofanteaching.about;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.chaofanteaching.R;

public class A2 extends AppCompatActivity {
    private Button Btn_tj;
    private ImageButton TP;
    private EditText Problem_cj;
    private EditText Problem_ms;
    private EditText zhanghao1;
    private EditText zhanghao2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a2);

        Btn_tj=findViewById(R.id.Btn_tj);
        TP=findViewById(R.id.TP);
        Problem_cj=findViewById(R.id.Problem_cj);
        Problem_ms=findViewById(R.id.Problem_ms);
        zhanghao1=findViewById(R.id.zhanghao1);
        zhanghao2=findViewById(R.id.zhanghao2);

        MyClickListener myClickListener = new MyClickListener();
        Btn_tj.setOnClickListener(myClickListener);
    }
    public class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.Btn_tj:
                    EditText tv1=(EditText)findViewById(R.id.zhanghao1);
                    String zhanghao=tv1.getText().toString().trim();
                    if(zhanghao.isEmpty()) {
                        Toast.makeText(getApplicationContext(),
                                "请输入您的账号",
                                Toast.LENGTH_SHORT).show();

                    }
                    else {
                        showAlertDialog();
                    }
                    break;
            }
        }
    }

    public void showAlertDialog() {
        new AlertDialog.Builder(this).setTitle("温馨提示")
                .setMessage("感谢您提供的信息，我们将会第一时间处理问题，并以短信的形式反馈处理结果！")
//                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        A2.this.finish();
                    }
                }).create().show();
    }
    @Override
    //当显示一个对话框的时候回调
    protected Dialog onCreateDialog(int id) {
        if (id == 0){
            showAlertDialog();
        }
        return super.onCreateDialog(id);
    }
}
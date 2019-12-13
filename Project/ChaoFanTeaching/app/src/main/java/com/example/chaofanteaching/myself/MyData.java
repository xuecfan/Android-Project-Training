package com.example.chaofanteaching.myself;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chaofanteaching.BottomPopupOption;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MyData extends AppCompatActivity {

    private BottomPopupOption bottomPopupOption;
    private Button btn;
    private LinearLayout name;
    private LinearLayout phone;
    private LinearLayout address;
    private LinearLayout fanhui;
    private LinearLayout person;
    private TextView name_content;
    private TextView phone_content;
    private TextView address_content;
    private TextView sex;
    private String user="";
    private SharedPreferences pre;
    private SharedPreferences.Editor editor = null;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  1:
                    Toast.makeText(getApplicationContext(),"信息保存成功",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String str = msg.obj.toString();
                    String[] s = str.split(";");
                    for (int i = 0; i < s.length; i++) {
                        String[] r = s[i].split(",");
                        if(r[0].equals("null")){name_content.setText("");}
                        else{name_content.setText(r[0]);
                            editor=pre.edit();
                            editor.putString("nameContent",r[0]);
                            editor.commit();}
                        if(!r[1].equals("null")){sex.setText(r[1]);
                            editor=pre.edit();
                            editor.putString("sexContent",r[1]);
                            editor.commit();}
                        else{sex.setText("");}
                        if(r[2].equals("null")){phone_content.setText("");}
                        else{phone_content.setText(r[2]);
                            editor=pre.edit();
                            editor.putString("phoneContent",r[2]);
                            editor.commit();}
                        if(r[3].equals("null")){address_content.setText("");}
                        else{address_content.setText(r[3]);
                            editor=pre.edit();
                            editor.putString("addressContent",r[3]);
                            editor.commit();}
                        break;
                    }}
            }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);
        pre=getSharedPreferences("login",MODE_PRIVATE);
        user=pre.getString("userName","");
        btn=findViewById(R.id.btn);
        fanhui=findViewById(R.id.fanhui);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        person=findViewById(R.id.person);

        name_content=findViewById(R.id.name_content);
        sex=findViewById(R.id.sex);
        phone_content=findViewById(R.id.phone_content);
        address_content=findViewById(R.id.address_content);
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomPopupOption = new BottomPopupOption(MyData.this);
                bottomPopupOption.setItemText("男", "女");
                bottomPopupOption.showPopupWindow();
                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        bottomPopupOption.dismiss();
                        switch (position) {
                            case 0:
                                sex.setText("男");
                                pre=getSharedPreferences("data",MODE_PRIVATE);
                                editor=pre.edit();
                                editor.putString("sexContent","男");
                                editor.apply();
                                break;
                            case 1:
                                sex.setText("女");
                                pre=getSharedPreferences("data",MODE_PRIVATE);
                                editor=pre.edit();
                                editor.putString("sexContent","女");
                                editor.apply();
                                break;
                        }
                    }
                });
            }
        });
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        pre=getSharedPreferences("data",MODE_PRIVATE);
        final String name1=pre.getString("nameContent","");
        final String phone1=pre.getString("phoneContent","");
        final String address1=pre.getString("addressContent","");
        final String sex1=pre.getString("sexContent","");
        if(name1.equals("")||phone1.equals("")||sex1.equals("")||address1.equals("")){
            look();
        }else{
        sex.setText(sex1);
        name_content.setText(name1);
        phone_content.setText(phone1);
        address_content.setText(address1);}
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), NameDetail.class);
                startActivity(i);

            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), PhoneDetail.class);
                startActivity(i);

            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), AddressDetail.class);
                startActivity(i);

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name1.equals("")||phone1.equals("")||address1.equals("")||sex1.equals("")){
                    Toast.makeText(getApplicationContext(),"您的信息有空值，请重新填写",Toast.LENGTH_SHORT).show();
                }else{
                new Thread(){
                    @Override
                    public void run() {
                            insert(name1,phone1,address1,sex1);
                            //pre.edit().clear().commit();
                            android.os.Message msg= Message.obtain();
                            msg.what=1;
                            handler.sendMessage(msg);
                    }
                }.start();
                    finish();}
            }
        });
    }
    private void insert(String name1,String phone1,String address1,String sex){
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().
                url("http://175.24.102.160:8080/ChaoFanTeaching/MyData?name="+user+"&nameContent="+name1+"&phoneContent="+phone1+"&addressContent="+address1+"&sexContent="+sex+"&index=insert")
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("data","失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("data",response.body().string());
            }
        });

    }
    private void look(){
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("MyData?index=look&name="+user);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = str;
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        String name= pre.getString("nameContent","");
        String sex1=pre.getString("sexContent","");
        String phone=pre.getString("phoneContent","");
        String address=pre.getString("addressContent","");
        name_content.setText(name);
        sex.setText(sex1);
        phone_content.setText(phone);
        address_content.setText(address);
        editor=pre.edit();
        editor.putString("nameContent",name);
        editor.putString("sexContent",sex1);
        editor.putString("phoneContent",phone);
        editor.putString("addressContent",address);
        editor.commit();
    }
}

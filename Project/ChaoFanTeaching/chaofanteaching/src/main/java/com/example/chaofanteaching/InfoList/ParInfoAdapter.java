package com.example.chaofanteaching.InfoList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaofanteaching.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ParInfoAdapter extends BaseAdapter{
    private static String path = "/storage/emulated/0/";// sd路径
    private List<Info> infoList;
    private int itemLayoutId;
    private Context context;
    private ImageView header;
    private int pos;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  1:
                    Log.i("file","..");
                    break;
            }
        }
    };
    public ParInfoAdapter(Context context, List<Info> infoList, int itemLayoutId){
        this.context = context;
        this.infoList= infoList;
        this.itemLayoutId = itemLayoutId;
    }
    @Override
    public int getCount() {
        if(infoList!=null){
            return infoList.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(infoList!=null){
            return infoList.get(position);
        }else{
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutId, null);
        }
        pos=position;
        TextView infoname=convertView.findViewById(R.id.name);
        TextView infoschool=convertView.findViewById(R.id.school);
        TextView infocollege=convertView.findViewById(R.id.college);
        TextView infosubject=convertView.findViewById(R.id.subject);
        TextView infotime=convertView.findViewById(R.id.time);
        TextView infodis=convertView.findViewById(R.id.dis);
        header=convertView.findViewById(R.id.header);
        initView();
        Drawable drawable=context.getResources().getDrawable(R.drawable.dis);
        drawable.setBounds(0,0,45,45);//第一0是距左边距离，第二0是距上边距离
        infodis.setCompoundDrawables(drawable,null,null,null);//只放左边
        infoname.setText(infoList.get(position).getName());
        infoschool.setText(infoList.get(position).getSchool());
        infocollege.setText(infoList.get(position).getCollege());
        infosubject.setText(infoList.get(position).getSubject());
        infotime.setText(infoList.get(position).getPrice());
        infodis.setText(infoList.get(position).getExperience());
        return convertView;
    }
    private void asyncdownop() {
        new Thread(){
            @Override
            public void run() {
                try {
                    downimg();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                android.os.Message msg= Message.obtain();
                msg.what=1;
                handler.sendMessage(msg);
            }
        }.start();
    }

    private void downimg() throws IOException {
        OkHttpClient okHttpClient=new OkHttpClient();
        String fileName = path +infoList.get(pos).getUser()+".png";// 图片名字
        Request request=new Request.Builder().url("http://175.24.102.160:8080/ChaoFanTeaching/DownImg?name="+infoList.get(pos).getUser()).build();
        Call call=okHttpClient.newCall(request);
        Response response=call.execute();
        Log.i("photo", String.valueOf(response.body().byteStream()));
        InputStream in=response.body().byteStream();
        FileOutputStream out=new FileOutputStream(fileName);
        byte[] bytes=new byte[1024];
        int n=-1;
        while ((n=in.read(bytes))!=-1){
            out.write(bytes,0,n);
            out.flush();
        }
        in.close();
        out.close();
    }

    private void initView() {

        //初始化控件
        Bitmap bt = BitmapFactory.decodeFile(path + infoList.get(pos).getUser()+".png");//从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            drawable.setBounds(0,0,150,150);
            header.setImageDrawable(drawable);
        } else {
            //如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
            asyncdownop();

        }
    }
}
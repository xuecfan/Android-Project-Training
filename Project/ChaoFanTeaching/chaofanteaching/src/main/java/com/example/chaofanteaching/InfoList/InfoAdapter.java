package com.example.chaofanteaching.InfoList;

import android.content.Context;
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

import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;


public class InfoAdapter extends BaseAdapter{
    private Drawable str;
    private Handler handler;
    private List<Info> infoList;
    private int itemLayoutId;
    private Context context;
    private ImageView header;
    public InfoAdapter(Context context, List<Info> infoList, int itemLayoutId){
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

    static class ViewHolder {
        TextView infoname;
        TextView infoschool;
        TextView infocollege;
        TextView infosubject;
        TextView infoprice;
        TextView infoexp;
        ImageView header;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutId, null);
            viewHolder=new ViewHolder();
            viewHolder.infoname=convertView.findViewById(R.id.name);
            viewHolder.infoschool=convertView.findViewById(R.id.school);
            viewHolder.infocollege=convertView.findViewById(R.id.college);
            viewHolder.infosubject=convertView.findViewById(R.id.subject);
            viewHolder.infoprice=convertView.findViewById(R.id.price);
            viewHolder.infoexp=convertView.findViewById(R.id.exp);
            viewHolder.header=convertView.findViewById(R.id.header);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
//        TextView infoname=convertView.findViewById(R.id.name);
//        TextView infoschool=convertView.findViewById(R.id.school);
//        TextView infocollege=convertView.findViewById(R.id.college);
//        TextView infosubject=convertView.findViewById(R.id.subject);
//        TextView infoprice=convertView.findViewById(R.id.price);
//        TextView infoexp=convertView.findViewById(R.id.exp);
//        header=convertView.findViewById(R.id.header);
        Drawable drawable=context.getResources().getDrawable(R.drawable.exp);
        drawable.setBounds(0,0,70,50);//第一0是距左边距离，第二0是距上边距离
        viewHolder.infoexp.setCompoundDrawables(drawable,null,null,null);//只放左边
        viewHolder.infoname.setText(infoList.get(position).getName());
        viewHolder.infoschool.setText(infoList.get(position).getSchool());
        viewHolder.infocollege.setText(infoList.get(position).getCollege());
        viewHolder.infosubject.setText(infoList.get(position).getSubject());
        viewHolder.infoprice.setText(infoList.get(position).getPrice());
        viewHolder.infoexp.setText(infoList.get(position).getExperience());
        //dbKey(infoList.get(position).getUser());
        viewHolder.header.setImageDrawable(dbKey(infoList.get(position).getUser()));
        Log.e("用户",infoList.get(position).getName());
        return convertView;
    }
    private Drawable dbKey(final String key) {
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        str = (Drawable) msg.obj;
                        break;
                }
            }
        };
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("DownImg?name="+key);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        Drawable drawable = Drawable.createFromStream(inputStream, key);
                        android.os.Message message = Message.obtain();
                        message.obj = drawable;
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return str;
    }
}

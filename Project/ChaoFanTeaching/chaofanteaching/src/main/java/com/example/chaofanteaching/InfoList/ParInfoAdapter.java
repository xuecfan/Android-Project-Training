package com.example.chaofanteaching.InfoList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.chaofanteaching.R;
import java.util.List;

public class ParInfoAdapter extends BaseAdapter{

    private List<Info> infoList;
    private int itemLayoutId;
    private Context context;
    private ImageView header;
    private int pos;

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

    private void initView() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.boy1).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context.getApplicationContext()).load("http:/81.70.134.107:8080/ChaoFanTeaching/img/"+infoList.get(pos).getUser()+".png").apply(requestOptions).into(header);
    }
}
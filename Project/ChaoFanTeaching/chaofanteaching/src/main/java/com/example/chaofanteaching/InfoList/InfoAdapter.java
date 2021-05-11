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

public class InfoAdapter extends BaseAdapter{

    private int pos;
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
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        };
        pos=position;
        header=convertView.findViewById(R.id.header);
        initView();
        Drawable drawable=context.getResources().getDrawable(R.drawable.exp);
        drawable.setBounds(0,0,70,50);//第一0是距左边距离，第二0是距上边距离
        viewHolder.infoexp.setCompoundDrawables(drawable,null,null,null);//只放左边
        viewHolder.infoname.setText(infoList.get(position).getName());
        viewHolder.infoschool.setText(infoList.get(position).getSchool());
        viewHolder.infocollege.setText(infoList.get(position).getCollege());
        viewHolder.infosubject.setText(infoList.get(position).getSubject());
        viewHolder.infoprice.setText(infoList.get(position).getPrice());
        viewHolder.infoexp.setText(infoList.get(position).getExperience());
        return convertView;
    }

    private void initView() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.tea).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context.getApplicationContext()).load("http:///81.70.134.107:8080/ChaoFanTeaching/img/"+infoList.get(pos).getUser()+".png").apply(requestOptions).into(header);
    }
}

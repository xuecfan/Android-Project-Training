package com.example.chaofanteaching.InfoList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chaofanteaching.R;

import java.util.List;

public class ParInfoAdapter extends BaseAdapter{
    private List<Info> infoList;
    private int itemLayoutId;
    private Context context;
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
        TextView infoname=convertView.findViewById(R.id.name);
        TextView infoschool=convertView.findViewById(R.id.school);
        TextView infocollege=convertView.findViewById(R.id.college);
        TextView infosubject=convertView.findViewById(R.id.subject);
        TextView infotime=convertView.findViewById(R.id.time);
        TextView infodis=convertView.findViewById(R.id.dis);
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
}

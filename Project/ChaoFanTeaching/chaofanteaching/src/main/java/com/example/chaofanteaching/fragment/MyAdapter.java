package com.example.chaofanteaching.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.chaofanteaching.R;
import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> dataSource;
    private int item_layout_id;



    public MyAdapter(Context context, List<Map<String, String>> dataSource,
                       int item_layout_id) {
        this.context = context;
        this.dataSource = dataSource;
        this.item_layout_id = item_layout_id;
    }
    public int getCount() {return dataSource.size();}
    public Object getItem(int position) { return dataSource.get(position); }
    public long getItemId(int position) { return position; }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(null == convertView){
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(item_layout_id, null);
        }

        TextView textView1=convertView.findViewById(R.id.a5text);
        ImageView imageView=convertView.findViewById(R.id.a5);

        final Map<String, String> mItemData = dataSource.get(position);
        textView1.setText(mItemData.get("text"));
        imageView.setImageResource(context.getResources().getIdentifier(mItemData.get("img"),"drawable",context.getPackageName()));




        return convertView;
}}

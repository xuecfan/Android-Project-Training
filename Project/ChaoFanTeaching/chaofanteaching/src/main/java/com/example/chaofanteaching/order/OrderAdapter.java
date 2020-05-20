package com.example.chaofanteaching.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chaofanteaching.R;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private int position;
    private List<Order> orderList;
    private int itemLayoutId;
    private Context context;

    public OrderAdapter(Context context, List<Order> orderList, int itemLayoutId){
        this.context = context;
        this.orderList= orderList;
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        if(orderList!=null){
            return orderList.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(orderList!=null){
            return orderList.get(position);
        }else{
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView id;
        TextView status;
        TextView user;
        TextView price;
        TextView time;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutId, null);
            viewHolder=new ViewHolder();
            viewHolder.id=convertView.findViewById(R.id.id);
            viewHolder.status=convertView.findViewById(R.id.status);
            viewHolder.user=convertView.findViewById(R.id.user);
            viewHolder.price=convertView.findViewById(R.id.price);
            viewHolder.time=convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.id.setText("订单编号："+orderList.get(position).getId());
        viewHolder.status.setText(orderList.get(position).getStatus());
        viewHolder.user.setText(orderList.get(position).getUser());
        viewHolder.price.setText(orderList.get(position).getPrice());
        viewHolder.time.setText(orderList.get(position).getTime());
        return convertView;
    }
}

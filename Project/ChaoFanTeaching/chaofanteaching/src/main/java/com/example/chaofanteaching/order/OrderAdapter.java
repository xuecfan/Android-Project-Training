package com.example.chaofanteaching.order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaofanteaching.R;
import com.example.chaofanteaching.utils.ToastUtils;

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
        Button btn;
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
            viewHolder.btn=convertView.findViewById(R.id.order_btn);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.id.setText("订单编号："+orderList.get(position).getId());
        viewHolder.status.setText(orderList.get(position).getStatus());
        viewHolder.user.setText(orderList.get(position).getUser());
        viewHolder.price.setText(orderList.get(position).getPrice());
        viewHolder.time.setText(orderList.get(position).getTime());
        viewHolder.btn.setText("123");
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong("点击按钮");
            }
        });
        return convertView;
    }
}

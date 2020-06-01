package com.example.chaofanteaching.order;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.comments.CommentingActivity;
import com.example.chaofanteaching.utils.ToastUtils;
import java.net.HttpURLConnection;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;

public class OrderAdapter extends BaseAdapter {
    private int position;
    private List<Order> orderList;
    private int itemLayoutId;
    private Context context;
    private ImageView header;
    private String user;
    private String arr[];//存放username和objuser的数组
    private SharedPreferences pre;

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
        header=convertView.findViewById(R.id.header);
        initView();
        String status=orderList.get(position).getStatus();
        viewHolder.id.setText("订单编号："+orderList.get(position).getId());
        viewHolder.status.setText(status);
        viewHolder.user.setText("用户："+orderList.get(position).getUser());
        viewHolder.price.setText("￥"+orderList.get(position).getPrice());
        viewHolder.time.setText(orderList.get(position).getTime());
        switch (status){
            case "待评价":
                viewHolder.btn.setText("评价");
                break;
            case "已完成":
                viewHolder.btn.setText("删除订单");
                break;
            default:
                viewHolder.btn.setText("取消试讲");
        }
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status){
                    case "待评价":
                        viewHolder.btn.setText("评价");
                        pre=context.getSharedPreferences("login",MODE_PRIVATE);
                        user=pre.getString("userName","");
                        arr = new String[]{user, orderList.get(position).getUser()};
                        Intent intent=new Intent(context, CommentingActivity.class);
                        intent.putExtra("id",orderList.get(position).getId());
                        intent.putExtra("user",arr);
                        context.startActivity(intent);
                        break;
                    case "已完成":
                        new AlertDialog.Builder(context)
                                .setTitle("提示")
                                .setMessage("确认要删除此订单吗?")
                                .setNegativeButton(R.string.cancel,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                DbOrder(orderList.get(position).getId());
                                                notifyDataSetChanged();
                                            }
                                        })
                                .setCancelable(false)
                                .show();
                        break;
                    default:
                        new AlertDialog.Builder(context)
                                .setTitle("提示")
                                .setMessage("确认要取消此次试讲吗?")
                                .setNegativeButton(R.string.cancel,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                DbOrder(orderList.get(position).getId());
                                                notifyDataSetChanged();
                                            }
                                        })
                                .setCancelable(false)
                                .show();
                        break;
                }
            }
        });
        return convertView;
    }
    private void initView() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.tea).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context.getApplicationContext()).load("http://39.107.42.87:8080/ChaoFanTeaching/img/"+orderList.get(position).getUser()+".png").apply(requestOptions).into(header);
    }

    public void DbOrder(int id){
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("deleteOrder?id="+id);
                    int code = connection.getResponseCode();
                    if(code!=200){
                        ToastUtils.showLong("网络错误！请稍后再试");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }//查询数据库
}

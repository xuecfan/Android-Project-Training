package com.example.chaofanteaching.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;
import com.example.chaofanteaching.utils.ToastUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ReceivedOrder extends Fragment {

    private SharedPreferences pre;
    private String user;
    private View view;
    private ListView orderlist;
    private List<Order> orderList=new ArrayList<>();
    private OrderAdapter orderAdapter;
    private Handler handler;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order_list, container, false);
        pre=getContext().getSharedPreferences("login",MODE_PRIVATE);
        user=pre.getString("userName","");
        loadInfo();
        jumpToDetail();
        return view;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ToastUtils.showShort("收到");
        }
    }

    public void loadInfo(){
        orderList.clear();
        orderlist=view.findViewById(R.id.orderlist);
        orderAdapter = new OrderAdapter(this.getContext(), orderList,R.layout.order_list_item);
        orderlist.setAdapter(orderAdapter);
        DbOrder(user);
        orderAdapter.notifyDataSetChanged();
    }//加载信息

    public void jumpToDetail(){//跳转到详情页
        orderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("id", String.valueOf(orderList.get(position).getId()));
                intent.setClass(getActivity(), OrderInfo.class);
                startActivity(intent);
            }
        });
    }

    public void DbOrder(String user){
        orderList.clear();
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        Order order;
                        String str = msg.obj.toString();
                        if(str.isEmpty()){
                            //Toast.makeText(getContext(),"这里空空如也",Toast.LENGTH_SHORT).show();
                        }else{
                            String[] s = str.split(";");
                            for (int i = 0; i < s.length; i++) {
                                String[] r = s[i].split(",");
                                order = new Order(Integer.parseInt(r[0]), r[1], r[2], r[3],r[4]);
                                orderList.add(order);
                                orderAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                }
            }
        };
        new Thread() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try {
                    connection = HttpConnectionUtils.getConnection("objuserOrder?user="+user);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);
                        android.os.Message message = Message.obtain();
                        message.obj = str;
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }//查询数据库
}
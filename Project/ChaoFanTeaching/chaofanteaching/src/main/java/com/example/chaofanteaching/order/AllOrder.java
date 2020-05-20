package com.example.chaofanteaching.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.chaofanteaching.R;

import java.util.ArrayList;
import java.util.List;

public class AllOrder extends Fragment {

    private View view;
    private ListView orderlist;
    private List<Order> orderList=new ArrayList<>();
    private OrderAdapter orderAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order_list, container, false);
        loadInfo();
        return view;
    }
    public void loadInfo(){
        orderList.clear();
        orderlist=view.findViewById(R.id.orderlist);
        orderList.add(new Order(12345,"待确认","myl","50元/小时","2020-05-20-14:30"));
        orderList.add(new Order(12346,"已完成","000","50元/小时","2020-05-20-14:30"));
        orderAdapter = new OrderAdapter(this.getContext(), orderList,R.layout.order_list_item);
        orderlist.setAdapter(orderAdapter);
//        dbKey("serach","");
        orderAdapter.notifyDataSetChanged();
    }//加载信息
}
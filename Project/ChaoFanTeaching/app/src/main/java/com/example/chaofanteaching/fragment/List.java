package com.example.chaofanteaching.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.chaofanteaching.InfoList.AddInfoActivity;
import com.example.chaofanteaching.InfoList.Info;
import com.example.chaofanteaching.InfoList.InfoAdapter;
import com.example.chaofanteaching.InfoList.InfoDetailActivity;
import com.example.chaofanteaching.R;

import java.util.ArrayList;

public class List extends Fragment {
    private java.util.List<Info> infoList=new ArrayList<>();
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(R.layout.list,container,false);
            initView();

        }else{
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        }
        return view;
    }
    public void initView(){
        final ListView infolist=view.findViewById(R.id.infolist);
        InfoAdapter infoAdapter=new InfoAdapter(this.getContext(),infoList,R.layout.info_item);
        infolist.setAdapter(infoAdapter);
        Info info=new Info("张三","化学学院","语文、数学","50");
        Info info1=new Info("李四","物理学院","语文、数学","60");
        Info info2=new Info("王五","软件学院","语文、数学","70");
        infoList.add(info);
        infoList.add(info1);
        infoList.add(info2);
        infoAdapter.notifyDataSetChanged();
        infolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("name",infoList.get(position).getName());
                intent.setClass(getActivity(),InfoDetailActivity.class);
                startActivity(intent);
            }
        });
        Button btnadd=view.findViewById(R.id.add);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), AddInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
package com.example.chaofanteaching;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class InfoListActivity extends Activity {

    private List<Info> infoList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_list);
//        ListView infolist=findViewById(R.id.infolist);
//        InfoAdapter infoAdapter=new InfoAdapter(this,infoList,R.layout.info_item);
//        infolist.setAdapter(infoAdapter);
//        Info info=new Info("张三","化学学院","语文、数学","50");
//        Info info1=new Info("李四","物理学院","语文、数学","60");
//        Info info2=new Info("王五","软件学院","语文、数学","70");
//        infoList.add(info);
//        infoList.add(info1);
//        infoList.add(info2);
//        infoAdapter.notifyDataSetChanged();
    }
}
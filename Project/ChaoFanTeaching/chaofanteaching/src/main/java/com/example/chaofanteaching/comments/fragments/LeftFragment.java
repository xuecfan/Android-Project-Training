package com.example.chaofanteaching.comments.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaofanteaching.HttpConnectionUtils;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.StreamChangeStrUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class LeftFragment extends Fragment {

    private java.util.List<Comment> commentList = new ArrayList<>();
    private Handler handler;
    private CommentAdapter commentAdapter = null;
    //列表无内容显示内容
    private ImageView findNothingImg;
    private TextView findNothingTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        ListView list_comment = view.findViewById(R.id.left_list);
        findNothingImg = view.findViewById(R.id.frag_find_nothing_img);
        findNothingTxt = view.findViewById(R.id.frag_find_nothing_txt);

        commentAdapter = new CommentAdapter(commentList,this.getContext());
        list_comment.setAdapter(commentAdapter);
        //获取全局变量
        SharedPreferences pre = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String myid = pre.getString("userName", "");//当前用户id

        dbKey(myid);
        return view;
    }

    /**
     * 根据当前用户id查询我收到的评价
     * @param key
     */
    private void dbKey(final String key) {
        commentList.clear();
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        Comment scanInfo;
                        String str = msg.obj.toString();
                        if(str.equals("")){
                            findNothingImg.setImageResource(R.drawable.find_nothing);
                            findNothingTxt.setText("没有搜到任何东西");
                        }else{
                            String[] s = str.split(";");
                            for (int i = 0; i < s.length; i++) {
                                String[] r = s[i].split(",");
                                scanInfo = new Comment(r[0], r[1], r[2], r[3],r[4]);
                                commentList.add(scanInfo);
                                commentAdapter.notifyDataSetChanged();
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
                    connection = HttpConnectionUtils.getConnection("userComment?user="+key);
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
    }
}

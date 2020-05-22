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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeftFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeftFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * 以下是我的变量
     */
    private java.util.List<Comment> commentList = new ArrayList<>();
    //    private ListView infolist;
//    private InfoAdapter infoAdapter;
    private Handler handler;

    private List<Comment> mData = null;
    private Context mContext;
    private CommentAdapter commentAdapter = null;
    private ListView list_comment;

    //获取全局变量
    private SharedPreferences pre;
    private String role;//用户角色
    private String myid;//当前用户id
    //列表无内容显示内容
    private ImageView findNothingImg;
    private TextView findNothingTxt;
    public LeftFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeftFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeftFragment newInstance(String param1, String param2) {
        LeftFragment fragment = new LeftFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        mContext = this.getContext();
        list_comment = (ListView) view.findViewById(R.id.left_list);
        findNothingImg = view.findViewById(R.id.frag_find_nothing_img);
        findNothingTxt = view.findViewById(R.id.frag_find_nothing_txt);

        commentAdapter = new CommentAdapter(commentList,this.getContext());
        list_comment.setAdapter(commentAdapter);
        //获得当前用户角色和id
        pre=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        role=pre.getString("role","");//11是老师,10是家长
        myid = pre.getString("userName","");

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
                        Log.e("str",str+"xuexuexue");
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

package com.example.chaofanteaching.comments.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.chaofanteaching.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentAdapter extends BaseAdapter {

    private Context mContext;
    private int pos;//从getView中获取的position
    private ImageView uImage;//从getView中获取的ImageView
    private static String path = "/storage/emulated/0/";// sd路径
    private List<Comment> CommentList;

    //获取全局变量
    private SharedPreferences pre;
    private String role;//用户角色
    private String myid;//当前用户id
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message){
            switch (message.what){
                case 1:
                    Log.i("file","..");
                    break;
            }
        }
};


    public CommentAdapter(List<Comment> CommentList, Context mContext) {
        this.CommentList = CommentList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return CommentList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ImageView uImage;

        TextView uId;
        TextView commentIsOnTime;
        TextView commentTeachQuality;
        TextView commentContent;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.comment_list_item,viewGroup,
                            false);

            viewHolder = new ViewHolder();
            viewHolder.uImage = view.findViewById(R.id.u_image);
            viewHolder.uId = view.findViewById(R.id.u_id);
            viewHolder.commentIsOnTime = view.findViewById(R.id.comment_is_on_time);
            viewHolder.commentTeachQuality = view.findViewById(R.id.comment_teach_quality);
            viewHolder.commentContent = view.findViewById(R.id.comment_content);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        pos = position;
        uImage = view.findViewById(R.id.u_image);

        initView();//头像

        //获得当前用户角色和id
        pre=mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        role=pre.getString("role","");//11是老师,10是家长
        myid = pre.getString("userName","");
        if (myid == CommentList.get(position).getUser()){
            viewHolder.uId.setText(CommentList.get(position).getUser());
        }else {
            viewHolder.uId.setText(CommentList.get(position).getObjUser());
        }
//        viewHolder.uId.setText(CommentList.get(position).getUser());
        viewHolder.commentIsOnTime.setText(CommentList.get(position).getIsOnTime());
        viewHolder.commentTeachQuality.setText(CommentList.get(position).getTeachingQuality());
        viewHolder.commentContent.setText(CommentList.get(position).getContent());

        return view;
    }

    private void initView() {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.tea).diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(mContext.getApplicationContext()).load("http://39.107.42.87:8080/ChaoFanTeaching/img/"+CommentList.get(pos).getUser()+".png").apply(requestOptions).into(uImage);
    }

}

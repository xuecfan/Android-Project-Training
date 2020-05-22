package com.example.chaofanteaching.comments.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
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
    private List<Comment> CommentList;

    //获取全局变量
    private SharedPreferences pre;
    private String myid;//当前用户id
    private String role;//当前用户角色

    private ViewHolder viewHolder = null;

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

        ConstraintLayout listItem;
        TextView uId;
        RatingBar commentIsOnTime;
        RatingBar commentTeachQuality;
        TextView commentContent;
        TextView commentIsOnTimeTxt;
        TextView commentTeachQualityTxt;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //获得当前用户角色和id
        pre=mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        myid = pre.getString("userName","");
        role = pre.getString("role","");//11是老师,10是家长
        //判断view是否存在，不存在就创建，否则复用
        if (view == null){
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.comment_list_item,viewGroup,
                            false);
            viewHolder = new ViewHolder();
            viewHolder.uImage = view.findViewById(R.id.u_image);
            viewHolder.listItem = view.findViewById(R.id.comment_item);
            viewHolder.uId = view.findViewById(R.id.u_id);
            viewHolder.commentIsOnTime = view.findViewById(R.id.comment_is_on_time);
            viewHolder.commentTeachQuality = view.findViewById(R.id.comment_teach_quality);
            viewHolder.commentContent = view.findViewById(R.id.comment_content);
            viewHolder.commentIsOnTimeTxt = view.findViewById(R.id.comment_is_on_time_txt);
            viewHolder.commentTeachQualityTxt = view.findViewById(R.id.comment_teach_quality_txt);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        initView(position);//头像

        return view;
    }

    /**
     * 初始化view
     * @param position
     */
    private void initView(int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.tea).diskCacheStrategy(DiskCacheStrategy.NONE);

        //判断显示谁的头像
        if (myid.equals(CommentList.get(position).getUser())){//我发出的，显示objUser
            Glide.with(mContext.getApplicationContext()).load(
                    "http://39.107.42.87:8080/ChaoFanTeaching/img/"
                            +CommentList.get(position).getObjUser()
                            +".png")
                    .apply(requestOptions).into(viewHolder.uImage);//头像
            viewHolder.uId.setText(CommentList.get(position).getObjUser());//用户名
            if(role.equals("10")){
                viewHolder.commentIsOnTime.setRating(Float.parseFloat(CommentList.get(position).getIsOnTime()));
                viewHolder.commentTeachQuality.setRating(Float.parseFloat(CommentList.get(position).getTeachingQuality()));
            }else {
                viewHolder.commentIsOnTimeTxt.setVisibility(View.GONE);
                viewHolder.commentIsOnTime.setVisibility(View.GONE);
                viewHolder.commentTeachQualityTxt.setVisibility(View.GONE);
                viewHolder.commentTeachQuality.setVisibility(View.GONE);
            }
        }else {//我收到的，显示user
            Glide.with(mContext.getApplicationContext()).load(
                    "http://39.107.42.87:8080/ChaoFanTeaching/img/"
                            +CommentList.get(position).getUser()
                            +".png")
                    .apply(requestOptions).into(viewHolder.uImage);//头像
            viewHolder.uId.setText(CommentList.get(position).getUser());//用户名
            if(role.equals("10")){
                viewHolder.commentIsOnTimeTxt.setVisibility(View.GONE);
                viewHolder.commentIsOnTime.setVisibility(View.GONE);
                viewHolder.commentTeachQualityTxt.setVisibility(View.GONE);
                viewHolder.commentTeachQuality.setVisibility(View.GONE);

            }else {
                viewHolder.commentIsOnTime.setRating(Float.parseFloat(CommentList.get(position).getIsOnTime()));
                viewHolder.commentTeachQuality.setRating(Float.parseFloat(CommentList.get(position).getTeachingQuality()));
            }
        }

        viewHolder.commentContent.setText(CommentList.get(position).getContent());

        //设置content宽度
        ViewTreeObserver vto2 = viewHolder.listItem.getViewTreeObserver();
        ViewHolder finalViewHolder = viewHolder;
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                finalViewHolder.listItem.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                finalViewHolder.commentContent.setWidth(finalViewHolder.listItem.getWidth()-260);
            }
        });
    }
}

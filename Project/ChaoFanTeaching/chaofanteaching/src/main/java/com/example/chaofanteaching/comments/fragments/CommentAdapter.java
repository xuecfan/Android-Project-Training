package com.example.chaofanteaching.comments.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chaofanteaching.R;

import java.util.LinkedList;

public class CommentAdapter extends BaseAdapter {

    private LinkedList<Comment> mData;
    private Context mContext;

    public CommentAdapter(LinkedList<Comment> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.comment_list_item,viewGroup,false);
        TextView comment_name = (TextView) view.findViewById(R.id.comment_name);
        TextView comment_date = (TextView) view.findViewById(R.id.comment_date);
        TextView comment_content = (TextView) view.findViewById(R.id.comment_content);
        comment_name.setText(mData.get(position).getTeacherName());
        comment_date.setText(mData.get(position).getDate());
        comment_content.setText(mData.get(position).getContent());

        return view;
    }
}

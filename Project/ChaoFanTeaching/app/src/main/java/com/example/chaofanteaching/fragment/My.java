package com.example.chaofanteaching.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.chaofanteaching.R;
import com.example.chaofanteaching.myself.MyData;
import com.example.chaofanteaching.sign.LoginActivity;

public class My extends Fragment {
    private LinearLayout myself;
    private LinearLayout student;
    private LinearLayout about;
    private Button btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.my,container,false);
        myself=view.findViewById(R.id.myself);
        student=view.findViewById(R.id.student);
        about=view.findViewById(R.id.about);
        btn=view.findViewById(R.id.btn);
        myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(getContext(), MyData.class);
                startActivity(i);
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
               // i.setClass(getContext(), .class);
                startActivity(i);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
              //  i.setClass(getContext(), .class);
                startActivity(i);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}

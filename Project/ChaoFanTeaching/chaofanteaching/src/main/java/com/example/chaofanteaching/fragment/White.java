package com.example.chaofanteaching.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.chaofanteaching.R;
import com.example.chaofanteaching.Video.VideoChinese;
import com.example.chaofanteaching.Video.VideoEnglish;
import com.example.chaofanteaching.Video.VideoMath;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class White extends Fragment {

    protected EaseTitleBar titleBar;
    private Banner mBanner;
    private ImageView img2;
    private ImageView img1;
    private ImageView img3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.white,container,false);
        titleBar=view.findViewById(R.id.title_bar);
        titleBar.setTitle("科目");
        img1=view.findViewById(R.id.img1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), VideoMath.class);
                startActivity(i);
            }
        });
        img2=view.findViewById(R.id.img2);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), VideoChinese.class);
                startActivity(i);
            }
        });
        img3=view.findViewById(R.id.img3);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), VideoEnglish.class);
                startActivity(i);
            }
        });
        mBanner=view.findViewById(R.id.mBanner);
        int[] imageResourceID = new int[]{R.drawable.z1, R.drawable.z2, R.drawable.z3, R.drawable.z4,R.drawable.z5};
        java.util.List<Integer> imgeList = new ArrayList<>();
        //轮播标题
        String[] mtitle = new String[]{"超凡家教", "超凡家教", "超凡家教", "超凡家教","超凡家教"};
        List<String> titleList = new ArrayList<>();

        for (int i = 0; i < imageResourceID.length; i++) {
            imgeList.add(imageResourceID[i]);//把图片资源循环放入list里面
            titleList.add(mtitle[i]);//把标题循环设置进列表里面
            //设置图片加载器，通过Glide加载图片
            mBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(getContext()).load(path).into(imageView);
                }
            });
            //设置轮播的动画效果,里面有很多种特效,可以到GitHub上查看文档。
            mBanner.setBannerAnimation(Transformer.Accordion);
            mBanner.setImages(imgeList);//设置图片资源
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);//设置banner显示样式（带标题的样式）
            mBanner.setBannerTitles(titleList); //设置标题集合（当banner样式有显示title时）
            //设置指示器位置（即图片下面的那个小圆点）
            mBanner.setIndicatorGravity(BannerConfig.CENTER);
            mBanner.setDelayTime(3000);//设置轮播时间3秒切换下一图
            //mBanner.setOnBannerListener((OnBannerListener) getActivity());//设置监听
            mBanner.start();//开始进行banner渲染
        }
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                switch (position){
                    case 0:
                        Uri uri = Uri.parse("http://baidu.com");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

}

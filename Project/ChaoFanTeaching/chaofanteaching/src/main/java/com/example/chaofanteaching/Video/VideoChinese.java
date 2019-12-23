package com.example.chaofanteaching.Video;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.chaofanteaching.R;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class VideoChinese extends AppCompatActivity {
    private WebView wv;
    private WebView wv1;
    private WebView wv11;
    private WebView wv0;
    private WebView wv10;
    private WebView wv110;
    private WebView wv2;
    private WebView wv12;
    private WebView wv112;
    protected EaseTitleBar titleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videochinese);
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle("语文");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        wv = findViewById(R.id.wv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebChromeClient(new WebChromeClient());
        wv.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setDefaultTextEncodingName("UTF-8");
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.setVisibility(View.VISIBLE);
        wv.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wv.loadUrl("https://vdept.bdstatic.com/7435786733355753773168367263706e/374e5a624432664e/bbe2a362f7b45892dd4e750b677d150cf964bbba0326edd14064ed0a735be29173aa6b0ce8f185430fc0400afb9ef948.mp4?auth_key=1577077918-0-0-fbac132242421fde80d0835edcd89d4c");
        wv1 = (WebView) findViewById(R.id.wv1);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setWebChromeClient(new WebChromeClient());
        wv1.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv1.getSettings().setAllowFileAccess(true);
        wv1.getSettings().setDefaultTextEncodingName("UTF-8");
        wv1.getSettings().setLoadWithOverviewMode(true);
        wv1.getSettings().setUseWideViewPort(true);
        wv1.setVisibility(View.VISIBLE);
        wv1.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wv1.loadUrl("https://vdept.bdstatic.com/487a633575666e677a6332344b6a4656/57624c7632714645/2943ef02e6f4b2ffb205860c80b5927ae357c3a6fc28272e30ba8eb2720cb5d712132ba824a07d5f01c12d2a23166ccb.mp4?auth_key=1577075624-0-0-cf65fc259ddad97ab8e033988ce8602f");
        wv11 = (WebView) findViewById(R.id.wv11);
        wv11.getSettings().setJavaScriptEnabled(true);
        wv11.setWebChromeClient(new WebChromeClient());
        wv11.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv11.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv11.getSettings().setAllowFileAccess(true);
        wv11.getSettings().setDefaultTextEncodingName("UTF-8");
        wv11.getSettings().setLoadWithOverviewMode(true);
        wv11.getSettings().setUseWideViewPort(true);
        wv11.setVisibility(View.VISIBLE);
        wv11.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wv11.loadUrl("https://vdept.bdstatic.com/74584472595333633278763158374377/7156336452717635/aea95cfe5bc799168ea796c2dc69934007806d5e8e63e5740f18a8a388584fe53ca01d03adc94c35ddf44438c829b347.mp4?auth_key=1577078013-0-0-be3ed1afb99a0c0ea986dab9bb990cda");


        wv0 = (WebView) findViewById(R.id.wv0);
        wv0.getSettings().setJavaScriptEnabled(true);
        wv0.setWebChromeClient(new WebChromeClient());
        wv0.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv0.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv0.getSettings().setAllowFileAccess(true);
        wv0.getSettings().setDefaultTextEncodingName("UTF-8");
        wv0.getSettings().setLoadWithOverviewMode(true);
        wv0.getSettings().setUseWideViewPort(true);
        wv0.setVisibility(View.VISIBLE);
        wv0.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wv0.loadUrl("https://vdept.bdstatic.com/63744d5369516b687158715379784a38/36656956554e7776/ebf9fe2d17a24f6bbb00ffb441dcf9c06287330f06019814ac97c4d0bfca31474540953b7b72af7165fa11d0c528944c.mp4?auth_key=1577078948-0-0-a641a2a7daff76eda8133e30f57c4459");
        wv10 = (WebView) findViewById(R.id.wv10);
        wv10.getSettings().setJavaScriptEnabled(true);
        wv10.setWebChromeClient(new WebChromeClient());
        wv10.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv10.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv10.getSettings().setAllowFileAccess(true);
        wv10.getSettings().setDefaultTextEncodingName("UTF-8");
        wv10.getSettings().setLoadWithOverviewMode(true);
        wv10.getSettings().setUseWideViewPort(true);
        wv10.setVisibility(View.VISIBLE);
        wv10.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wv10.loadUrl("https://vdept.bdstatic.com/424a384e54496a4d6a65473454674453/3256346863727368/4d22c8bc61b37e4e9a0e5f0684868d7a89bff2ac1c764c0c5db2e6d0149834e2a8707d35ee4c6fe2034c23418b2ac518.mp4?auth_key=1577078981-0-0-13e6468b1da664c65d9daf430034d459");
        wv110 = (WebView) findViewById(R.id.wv110);
        wv110.getSettings().setJavaScriptEnabled(true);
        wv110.setWebChromeClient(new WebChromeClient());
        wv110.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv110.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv110.getSettings().setAllowFileAccess(true);
        wv110.getSettings().setDefaultTextEncodingName("UTF-8");
        wv110.getSettings().setLoadWithOverviewMode(true);
        wv110.getSettings().setUseWideViewPort(true);
        wv110.setVisibility(View.VISIBLE);
        wv110.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wv110.loadUrl("https://vdept.bdstatic.com/67526b4955514e715657784151486131/6a6147556e6c5175/f87357c8e110501613f22cd9fd0b4a1a252a888dc5256349ef6eb0063193fa17cf3075daa23e3ad216782716487e19ac.mp4?auth_key=1577079135-0-0-2f5ed38f1a8b2d4059ddd315d26ce61b");

        wv2 = (WebView) findViewById(R.id.wv2);
        wv2.getSettings().setJavaScriptEnabled(true);
        wv2.setWebChromeClient(new WebChromeClient());
        wv2.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv2.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv2.getSettings().setAllowFileAccess(true);
        wv2.getSettings().setDefaultTextEncodingName("UTF-8");
        wv2.getSettings().setLoadWithOverviewMode(true);
        wv2.getSettings().setUseWideViewPort(true);
        wv2.setVisibility(View.VISIBLE);
        wv2.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wv2.loadUrl("https://vdept.bdstatic.com/64347269705555455853694869425334/50524d6364756141/18c2cd056db38e3f2151adb9f7e703c3e5677dfe366565f448c191af1b2d7b9f24d7fbf53be562fb940277f976ab15c9.mp4?auth_key=1577079257-0-0-44a17dc036a446254b2ea9883ec95d9c");
        wv12 = (WebView) findViewById(R.id.wv12);
        wv12.getSettings().setJavaScriptEnabled(true);
        wv12.setWebChromeClient(new WebChromeClient());
        wv12.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv12.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv12.getSettings().setAllowFileAccess(true);
        wv12.getSettings().setDefaultTextEncodingName("UTF-8");
        wv12.getSettings().setLoadWithOverviewMode(true);
        wv12.getSettings().setUseWideViewPort(true);
        wv12.setVisibility(View.VISIBLE);
        wv12.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wv12.loadUrl("https://vdept.bdstatic.com/6c425555734246675a6d487948624b5a/4952797355344857/ee93e65fe4fe08a295054219970ab8ee2bf41c0adc0c98ce88714a6f6c008246cf5d1f6758220e400139458a669482f1.mp4?auth_key=1577079286-0-0-9ab083a5ca671ff4186fd516b3e89697");
        wv112 = (WebView) findViewById(R.id.wv112);
        wv112.getSettings().setJavaScriptEnabled(true);
        wv112.setWebChromeClient(new WebChromeClient());
        wv112.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv112.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv112.getSettings().setAllowFileAccess(true);
        wv112.getSettings().setDefaultTextEncodingName("UTF-8");
        wv112.getSettings().setLoadWithOverviewMode(true);
        wv112.getSettings().setUseWideViewPort(true);
        wv112.setVisibility(View.VISIBLE);
        wv112.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wv112.loadUrl("https://vdept.bdstatic.com/4e4d6a38474752786c42536177614d56/53464751684c474a/7c79870827080b132f975f5d21f0dcd20ca85688eeea517e971c65b3e07a25c160165e52e8b131a04dd8693f82495d12.mp4?auth_key=1577079325-0-0-c98642f25a9269cf927dc4ef9c6d3e0e");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv.loadData("", "text/html; charset=UTF-8", null);
            VideoChinese.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv1.loadData("", "text/html; charset=UTF-8", null);
            VideoChinese.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv11.loadData("", "text/html; charset=UTF-8", null);
            VideoChinese.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv0.loadData("", "text/html; charset=UTF-8", null);
            VideoChinese.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv10.loadData("", "text/html; charset=UTF-8", null);
            VideoChinese.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv110.loadData("", "text/html; charset=UTF-8", null);
            VideoChinese.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv2.loadData("", "text/html; charset=UTF-8", null);
            VideoChinese.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv12.loadData("", "text/html; charset=UTF-8", null);
            VideoChinese.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv112.loadData("", "text/html; charset=UTF-8", null);
            VideoChinese.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wv.onPause();
        wv1.onPause();
        wv11.onPause();
        wv0.onPause();
        wv10.onPause();
        wv110.onPause();
        wv2.onPause();
        wv12.onPause();
        wv112.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        wv.onResume();
        wv1.onResume();
        wv11.onResume();
        wv0.onResume();
        wv10.onResume();
        wv110.onResume();
        wv2.onResume();
        wv12.onResume();
        wv112.onResume();
    }
}
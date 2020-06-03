package com.example.chaofanteaching.Video;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.chaofanteaching.R;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class VideoMath extends AppCompatActivity {
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
        setContentView(R.layout.activity_video_math);
        titleBar=findViewById(R.id.title_bar);
        titleBar.setTitle("数学");
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        wv0 = findViewById(R.id.wv0);
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
        wv0.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        wv0.loadUrl("https:haokan.baidu.com/v?vid=7649246158102970750&pd=bjh&fr=bjhauthor&type=video");
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
        wv10.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        wv10.loadUrl("https://haokan.baidu.com/v?vid=10453568675485455363&pd=bjh&fr=bjhauthor&type=video");
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
        wv110.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        wv110.loadUrl("https://haokan.baidu.com/v?vid=10977578522784829962&pd=bjh&fr=bjhauthor&type=video");


        wv = (WebView) findViewById(R.id.wv);
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
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        wv.loadUrl("https://haokan.baidu.com/v?vid=2405182298945701603&pd=bjh&fr=bjhauthor&type=video");
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
        wv1.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        wv1.loadUrl("https://haokan.baidu.com/v?vid=8272629701185452782&pd=bjh&fr=bjhauthor&type=video");
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
        wv11.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        wv11.loadUrl("https:haokan.baidu.com/v?vid=7649246158102970750&pd=bjh&fr=bjhauthor&type=video");

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
        wv2.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        wv2.loadUrl("https://haokan.baidu.com/v?vid=4066868653219187926&pd=bjh&fr=bjhauthor&type=video");
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
        wv12.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        wv12.loadUrl("https://haokan.baidu.com/v?vid=10126090124672451797&pd=bjh&fr=bjhauthor&type=video");
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
        wv112.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        wv112.loadUrl("https://haokan.baidu.com/v?vid=6724657513488513945&pd=bjh&fr=bjhauthor&type=video");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv.loadData("", "text/html; charset=UTF-8", null);
            VideoMath.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv1.loadData("", "text/html; charset=UTF-8", null);
            VideoMath.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv11.loadData("", "text/html; charset=UTF-8", null);
            VideoMath.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv0.loadData("", "text/html; charset=UTF-8", null);
            VideoMath.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv10.loadData("", "text/html; charset=UTF-8", null);
            VideoMath.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv110.loadData("", "text/html; charset=UTF-8", null);
            VideoMath.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv2.loadData("", "text/html; charset=UTF-8", null);
            VideoMath.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv12.loadData("", "text/html; charset=UTF-8", null);
            VideoMath.this.finish();
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            wv112.loadData("", "text/html; charset=UTF-8", null);
            VideoMath.this.finish();
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

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
        wv.loadUrl("https://vdept.bdstatic.com/4c4932504a3469443746385551416363/5667453842444a44/90f905c2bbebba427693b19769d683aec13e2c819a128b6f14ca7e447456c8db923fa86c265c393faea2b1d604e9490b.mp4?auth_key=1577178199-0-0-4cbecb5eb34f7ab83a771c0adf11b06b");//http://player.youku.com/embed/XNTM5MTUwNDA0
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
        wv1.loadUrl("https://vdept.bdstatic.com/6b355a6b5358794c7251696c58334248/366b323759546469/ab0c1181817bc45f943f5feb0552f60f811d79745dec917f9b6f8f039196fb0c194c25a27071e0d72e83f1f9dec33142.mp4?auth_key=1577178380-0-0-a1760a161df330a72e3c66c840d34c05");
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
        wv11.loadUrl("https://vdept.bdstatic.com/733766365a536c7531355736344a5253/486144336d6d5671/0aa239f13591373557a0cedb9e7274d82a847af959906a0517896b837540fd7098edf5b3bddff6f11ad4577c2a55f9f8.mp4?auth_key=1577178394-0-0-2f66b6fb855e5c84261ccd8b648bf3e6");


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
        wv2.loadUrl("https://vdept.bdstatic.com/714c38386b6272583132414b70743547/6a33483170643832/26e9026ecaeaec8a15ce6d9eb3ecb33664fe1261ab0b15d16aad7c659dcfef9b63a236707eecc6384a7300dd74bec353.mp4?auth_key=1577178700-0-0-995bc9a28c13e14f002124a4cd407fbc");
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
        wv12.loadUrl("https://vdept.bdstatic.com/7378383336753471517358464c6c6743/51644d5139316a4a/d27cc33522e8e47f8923a21c7e6fbb54625aac1b5e54545a4af8920c95ab8cf9a6ac8033e677e3f9b2f15e25fe218358.mp4?auth_key=1577178742-0-0-b334bc1d6b7eaf0a6a189607e498a985");
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
        wv112.loadUrl("https://vdept.bdstatic.com/446139577138744e3766314536353755/4e4c764e394b566b/7e89ce6acdc2d2655bf5863b152cbbad69ce404c64bb18fda430a208c28621a4aa83895b7641c9d73623bbc77e55794d.mp4?auth_key=1577178786-0-0-5ce90fbb4c1682c6fdb8cd8cf1841aaa");
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
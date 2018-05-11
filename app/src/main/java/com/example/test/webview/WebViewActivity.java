package com.example.test.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Set;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by wudh on 2018/5/11.
 */

public class WebViewActivity extends Activity{

    public static final String APP_CACAHE_DIRNAME="/cache";
    private ProgressBar pb;
    private WebView webView;
    private TextView tv;
    private WebSettings settings;
    private Button btnAcallJS;
    private String type="net";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
        webSettings();
        setCacheMode();
        initURL();
    }
    private void initURL(){
        Intent intent=getIntent();
        type= (String) intent.getExtras().get("url");
        String url="";
        switch (type){
            case "net":
            //方式1. 加载一个网页：
            url="http://www.baidu.com";
                break;
            case "assets":
            //方式2：加载apk包中的html页面
            url="file:///android_asset/404.html";
                break;
            case "sdcard":
            //方式3：加载手机本地的html页面
            url="file:///mnt/sdcard/404.html";
                break;
            case "AcallJS1":
            //方式4：Android调用JS方法
            url="file:///android_asset/javascript.html";
                btnAcallJS.setVisibility(View.VISIBLE);
                break;
            case "AcallJS2":
            //方式5：Android调用JS方法
            url="file:///android_asset/javascript.html";
                btnAcallJS.setVisibility(View.VISIBLE);
                break;
            case "JScallA1":
            //方式6：JS调用Android方法
            url="file:///android_asset/javascript1.html";
                // 通过addJavascriptInterface()将Java对象映射到JS对象
                //参数1：Javascript对象名
                //参数2：Java对象名
            webView.addJavascriptInterface(new AndroidtoJs(), "test");//AndroidtoJS类对象映射到js的test对象
                //控制台输出结果
                break;
            case "JScallA2":
                //方式7：JS调用Android方法
                url="file:///android_asset/javascript2.html";
                break;
            case "JScallA3":
                //方式8：JS调用Android方法
                url="file:///android_asset/javascript3.html";
                break;
            default:
                url="";
                break;
        }
        if (url.equals("")){
            Toast.makeText(WebViewActivity.this,"URL地址获取失败",Toast.LENGTH_SHORT).show();
        }else {
            loadWebView(url);
        }
    }
    public void loadWebView(final String url){
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                //方式7：JS调用Android方法
                if (type.equals("JScallA2")) {
                    // 步骤2：根据协议的参数，判断是否是所需要的url
                    // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                    //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

                    Uri uri = Uri.parse(url);
                    // 如果url的协议 = 预先约定的 js 协议
                    // 就解析往下解析参数
                    if (uri.getScheme().equals("js")) {

                        // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                        // 所以拦截url,下面JS开始调用Android需要的方法
                        if (uri.getAuthority().equals("webview")) {

                            //  步骤3：
                            // 执行JS所需要调用的逻辑
                            System.out.println("js调用了Android的方法");
                            // 可以在协议上带有参数并传递到Android上
                            HashMap<String, String> params = new HashMap<>();
                            Set<String> collection = uri.getQueryParameterNames();
                        }
                    }
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.loadUrl("file:///android_assets/404.html");
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    pb.setVisibility(GONE);
                } else {
                    if (pb.getVisibility() == GONE) {
                        pb.setVisibility(VISIBLE);
                    }
                    pb.setProgress(newProgress);
                }
            }

            //方式8
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                // 根据协议的参数，判断是否是所需要的url(原理同方式2)
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

                Uri uri = Uri.parse(message);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if ( uri.getScheme().equals("js")) {

                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {

                        //
                        // 执行JS所需要调用的逻辑
                        System.out.println("js调用了Android的方法");
                        // 可以在协议上带有参数并传递到Android上
                        HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();

                        //参数result:代表消息框的返回值(输入值)
                        result.confirm("js调用了Android的方法成功啦");
                    }
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
    }
    private void initView(){
        webView=(WebView)findViewById(R.id.webview);
        pb=(ProgressBar)findViewById(R.id.pb);
        tv=(TextView)findViewById(R.id.tv_title);
        btnAcallJS=(Button)findViewById(R.id.btn_acalljs);
        btnAcallJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (type.equals("AcallJS1")) {
                        //方式4：Android调用JS方法
                        webView.post(new Runnable() {
                            @Override
                            public void run() {
                                webView.loadUrl("javascript:callJS()");
                            }
                        });
                    }else if (type.equals("AcallJS2")){
                        //方式5：Android调用JS方法
                        webView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为 js 返回的结果
                            }
                        });
                    }
            }
        });
    }

    private void webSettings(){
        settings=webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开弹窗口
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        //支持插件
        //settings.setPluginsEnabled(true);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        //LOAD_NO_CACHE  不使用缓存:

        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }
    private void setCacheMode(){
        ConnectivityManager manager=(ConnectivityManager)this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager!=null){
            NetworkInfo networkinfo = manager.getActiveNetworkInfo();
            if (networkinfo!=null&&networkinfo.isAvailable()){
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
            }else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
            }
            settings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
            settings.setDatabaseEnabled(true);   //开启 database storage API 功能
            settings.setAppCacheEnabled(true);//开启 Application Caches 功能

            String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
            settings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录

        }
    }
    //网页按返回键后退，前进同理
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (null!=webView&&webView.canGoBack()){
            webView.goBack();
            return true;
        }else {
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //当页面被失去焦点被切换到后台不可见状态，需要执行onPause
        //通过onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。
        webView.onPause();
        //当应用程序(存在webview)被切换到后台时，这个方法不仅仅针对当前的webview而是全局的全应用程序的webview
        //它会暂停所有webview的layout，parsing，javascripttimer。降低CPU功耗。
//        webView.pauseTimers();
        //恢复pauseTimers状态
//        webView.resumeTimers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        settings.setJavaScriptEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (settings!=null){
            settings=null;
        }
        //在关闭了Activity时，如果Webview的音乐或视频，还在播放。就必须销毁Webview
        //但是注意：webview调用destory时,webview仍绑定在Activity上
        //这是由于自定义webview构建时传入了该Activity的context对象
        //因此需要先从父容器中移除webview,然后再销毁webview:
        webView.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //激活WebView为活跃状态，能正常执行网页的响应
        webView.onResume();
        settings.setJavaScriptEnabled(true);
    }
}

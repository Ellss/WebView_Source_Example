package com.example.test.webview;

import android.webkit.JavascriptInterface;

/**
 * Created by wudh on 2018/5/9.
 */

// 继承自Object类
public class AndroidtoJs extends Object {
    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void print(String msg) {
        System.out.println("JS调用了Android的hello方法"+msg);
    }
}

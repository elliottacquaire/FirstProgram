package com.example.firstprogram.pdf;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstprogram.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 086 on 2018/1/25.
 * PDF.js 下载pdf文档后，读取本地文档通过 webview 显示 成功
 * successful
 */

public class PWebViewActivity extends AppCompatActivity {
    WebView pdfViewerWeb;
    private String downloadUrl="https://pos.miller8.top/pos/test.pdf";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_web_view);
        pdfViewerWeb=findViewById(R.id.webView);

        WebSettings settings = pdfViewerWeb.getSettings();
        settings.setSavePassword(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        //js 调用Android 方法
        pdfViewerWeb.addJavascriptInterface(new AndroidtoJs(), "android");//AndroidtoJS类对象映射到js的test对象
        pdfViewerWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;

            }

        });
        pdfViewerWeb.setWebChromeClient(new WebChromeClient());


        new Thread(new Runnable() {
            @Override
            public void run() {
//                final String download = download(); //先下载PDF文件到本地，再打开
                final String download = Environment.getExternalStorageDirectory() + "/MyDownLoad/invoice.pdf";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //api >= 19
                        pdfViewerWeb.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + download);
                    }
                });
            }
        }).start();

        //webview 调用js 方法
        pdfViewerWeb.loadUrl("javascript:funFromjs()");

    }
    public class AndroidtoJs extends Object {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void back() {
            PWebViewActivity.this.finish();
        }
    }
    //下载具体操作
    private String download() {
        try {
            URL url = new URL(downloadUrl);
            //打开连接
            URLConnection conn = url.openConnection();
            //打开输入流
            InputStream is = conn.getInputStream();
            //获得长度
            int contentLength = conn.getContentLength();
            //创建文件夹 MyDownLoad，在存储卡下
            String dirName = Environment.getExternalStorageDirectory() + "/MyDownLoad/";
            File file = new File(dirName);
            //不存在创建
            if (!file.exists()) {
                file.mkdir();
            }
            //下载后的文件名
            final String fileName = dirName + "invoice.pdf";
            File file1 = new File(fileName);
            if (file1.exists()) {
                file1.delete();
            }
            //创建字节流
            byte[] bs = new byte[1024];
            int len;
            OutputStream os = new FileOutputStream(fileName);
            //写数据
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            //完成后关闭流
            os.close();
            is.close();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

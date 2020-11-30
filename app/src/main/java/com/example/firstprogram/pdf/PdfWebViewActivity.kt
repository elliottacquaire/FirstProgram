package com.example.firstprogram.pdf

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Browser
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.firstprogram.R
import kotlinx.android.synthetic.main.activity_pdf_web_view.*

/**
 * 显示pdf 有一些问题
 */
class PdfWebViewActivity : AppCompatActivity() {
    private val downloadUrl = "https://pos.miller8.top/pos/test.pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_web_view)

        initData()
    }

    private fun initData() {
        initWebSettings()
        var url = "http://xxxx"
        //wv.loadUrl("file:///android_asset/mypdf.html?pdfpath=" + url)
//        webView.loadUrl("file:///android_asset/mypdf.html?pdfpath="+"http://housing.dev.shangyouth.com/api/resource/8a80807d6a4def9e016a4e5ca6040003")
//        url = "http://192.168.12.221:61267/viewer?url=杭州滨江试驾协议模板_WP0CA2981LS210761_20201027 (1) (1).pdf"
        url = "file:///android_asset/pdfviewer/viewer.html?url=杭州滨江试驾协议模板_WP0CA2981LS210761_20201027 (1) (1).pdf"
        url = "file:///android_asset/pdfviewer/viewer.html?url=" + Environment.getExternalStorageDirectory().toString() + "/MyDownLoad/invoice.pdf"

        webView.loadUrl(url)

        openPDFInBrowser(this,downloadUrl)
//        openPDFInBrowser(
//            this,
//            Environment.getExternalStorageDirectory().toString() + "/MyDownLoad/invoice.pdf"
//        )
    }

    private fun initWebSettings() {
        //去掉横向滚动条
        webView.setHorizontalScrollBarEnabled(false)
        //去掉纵向滚动条
        webView.setVerticalScrollBarEnabled(false)

        val webSettings = webView!!.settings ?: return
        //设置字体缩放倍数，默认100
        webSettings.textZoom = 100
        // 支持 Js 使用
        webSettings.javaScriptEnabled = true
        // 开启DOM缓存
        webSettings.domStorageEnabled = true
        // 开启数据库缓存
        webSettings.databaseEnabled = true
        // 支持启用缓存模式
        webSettings.setAppCacheEnabled(true)
        // 设置 AppCache 最大缓存值(现在官方已经不提倡使用，已废弃)
        webSettings.setAppCacheMaxSize((8 * 1024 * 1024).toLong())
        // Android 私有缓存存储，如果你不调用setAppCachePath方法，WebView将不会产生这个目录
        webSettings.setAppCachePath(cacheDir.absolutePath)
        // 关闭密码保存提醒功能
        webSettings.savePassword = false
        // 支持缩放
        webSettings.setSupportZoom(true)
        //设置内置的缩放控件
        webSettings.setBuiltInZoomControls(true)
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        // 设置 UserAgent 属性
        webSettings.userAgentString = ""
        // 允许加载本地 html 文件/false
        webSettings.allowFileAccess = true
    }

    //浏览器打开PDF
    fun openPDFInBrowser(context: Context, url: String?) {
        val uri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.packageName)
//        val uri = Uri.parse("https://www.baidu.com")
//        val intent = Intent(Intent.ACTION_VIEW, uri)
        //intent.setClassName("com.UCMobile","com.uc.browser.InnerUCMobile");//打开UC浏览器
//        intent.setClassName("com.tencent.mtt","com.tencent.mtt.MainActivity");//打开QQ浏览器
//        startActivity(intent)

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.w("error", "Activity was not found for intent, " + intent.toString())
        }
    }

}
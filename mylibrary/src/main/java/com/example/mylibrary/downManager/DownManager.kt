package com.example.mylibrary.downManager

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.mylibrary.R
import java.io.File
import java.lang.ref.WeakReference
import java.util.*


object DownManager {
    private var downloadManager: DownloadManager? = null
    private var mContext: WeakReference<Context>? = null

    //下载的ID
    private var downloadId: Long = 0
//    private var name: String? = null
    private var pathStr: String? = null
    private var stateListener:DownLoadStateListener? = null


    //下载
    fun downloadFile(context: Context,url: String, fileName: String,token:String,listener:DownLoadStateListener) {
        this.stateListener = listener
        if (url.isBlank())return
//        mContext = WeakReference(context)
        //创建下载任务
        val request = DownloadManager.Request(Uri.parse(url))
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false)
        if (token.isNotEmpty()){
            request.addRequestHeader("Authorization","Bearer $token")
        }

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        request.setTitle(mContext?.get()?.getString(R.string.app_name))
        request.setDescription("下载中...")

        //设置下载的路径
        val file = File(mContext?.get()?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), System.currentTimeMillis().toString()+"_"+fileName)
        request.setDestinationUri(Uri.fromFile(file))
        pathStr = file.absolutePath
        //获取DownloadManager
        if (downloadManager == null) downloadManager =
            mContext?.get()?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        downloadManager?.let {
            downloadId = it.enqueue(request)
        }

//        //注册广播接收者，监听下载状态
//        mContext?.get()?.registerReceiver(
//            receiver,
//            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
//        )
    }

    //广播监听下载的各个状态
    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            checkStatus()
        }
    }

    //检查下载状态
    private fun checkStatus() {
        val query = DownloadManager.Query()
        //通过下载的id查找
        query.setFilterById(downloadId)
        val cursor: Cursor = downloadManager!!.query(query)
        if (cursor.moveToFirst()) {
            val status: Int =
                cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            when (status) {
                DownloadManager.STATUS_PAUSED -> {
                }
                DownloadManager.STATUS_PENDING -> {
                }
                DownloadManager.STATUS_RUNNING -> {
//                    tv_down.text = "下载中"
//                    img_down.setImageResource(R.drawable.icon_loader_spinner)
                    val mDownloadProgressSize: Int =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    val mDownloadAllSize: Int =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                    val progress = (mDownloadProgressSize*100 / mDownloadAllSize)
                    stateListener?.stateRunning(progress)
//                    numberProgress.progress = progress
                }
                DownloadManager.STATUS_SUCCESSFUL -> {
                    stateListener?.stateSuccess()
//                    img_down.setImageResource(R.drawable.download)
//                    tv_down.text = "下载完成"
//                    numberProgress.progress = 100
                    openFile()
                    cursor.close()
//                    mContext.unregisterReceiver(this)
                }
                DownloadManager.STATUS_FAILED -> {
                    stateListener?.stateFailed()
//                    Toast.makeText(mContext,"下载失败",Toast.LENGTH_LONG).show()
                    cursor.close()
//                    mContext.unregisterReceiver(this)
                }
            }
        }

    }

    //下载
    fun downloadApk(context: Context,url: String, fileName: String,token:String,listener:DownLoadStateListener) {
        this.stateListener = listener
        if (url.isBlank())return
        mContext = WeakReference(context)
        //创建下载任务
        val request = DownloadManager.Request(Uri.parse(url))
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false)
        if (token.isNotEmpty()){
            request.addRequestHeader("Authorization","Bearer $token")
        }

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        request.setTitle(mContext?.get()?.getString(R.string.app_name))
        request.setDescription("下载中...")

        //设置下载的路径
        val file = File(mContext?.get()?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), System.currentTimeMillis().toString()+"_"+fileName)
        request.setDestinationUri(Uri.fromFile(file))
        pathStr = file.absolutePath
        //获取DownloadManager
        if (downloadManager == null) downloadManager =
            mContext?.get()?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        downloadManager?.let {
            downloadId = it.enqueue(request)
        }

//        //注册广播接收者，监听下载状态
        mContext?.get()?.registerReceiver(
            receiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    fun init(context: Context){
        mContext = WeakReference(context)
        //注册广播接收者，监听下载状态
        mContext?.get()?.registerReceiver(
            receiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    fun closeRequest(){
        downloadManager?.remove(downloadId)
        mContext?.get()?.unregisterReceiver(receiver)
    }

//    private fun openFileApk() {
////        setPermission(pathstr)
//        val intent = Intent(Intent.ACTION_VIEW)
//        // 由于没有在Activity环境下启动Activity,设置下面的标签
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        //Android 7.0以上要使用FileProvider
//        if (Build.VERSION.SDK_INT >= 24) {
//            val file = File(pathstr)
//            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
//            val apkUri: Uri =
//                FileProvider.getUriForFile(mContext!!, "com.lxtwsw.weather.fileprovider", file)
//            //添加这一句表示对目标应用临时授权该Uri所代表的文件
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
//        } else {
//            intent.setDataAndType(
//                Uri.fromFile(
//                    File(
//                        Environment.DIRECTORY_DOWNLOADS,
//                        name
//                    )
//                ), "application/vnd.android.package-archive"
//            )
//        }
//        mContext!!.startActivity(intent)
//    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_VIEW)
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        //Android 7.0以上要使用FileProvider
        val file = File(pathStr)
        val type = getMIMEType(file)
        mContext?.get()?.let {
            val apkUri: Uri = FileProvider.getUriForFile(it, "cn.porsche.dealer.pos.fileProvider", file)
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, type)
            try {
                it.startActivity(intent)
            }catch (e : Exception){
                Toast.makeText(mContext?.get(),"没有找到合适的软件打开",Toast.LENGTH_LONG).show()
            }
        }

    }

    //依据文件名得到MIME类型
    private fun getMIMEType(file: File) : String{
        val type = "*/*"
        val fName = file.name
        //获取后缀名前的分隔符"."在fName中的位置。
        val dotIndex = fName.lastIndexOf(".")
        if (dotIndex < 0) {
            return type
        }
        val end = fName.substring(dotIndex, fName.length).toLowerCase(Locale.getDefault())
        if (end === "") return type
        for (item in mimeMap.keys){
            if (item == end){
                return mimeMap[item] ?: type
            }
        }
        return type

    }

    //MIME类型
    private val mimeMap = mapOf(
        ".apk" to  "application/vnd.android.package-archive",
        ".doc" to "application/msword",
        ".docx" to  "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        ".pdf" to "application/pdf",
        ".zip" to "application/x-zip-compressed",
        ".txt" to "text/plain"
    )

    interface DownLoadStateListener{
        fun stateRunning(progress:Int)
        fun stateSuccess()
        fun stateFailed()
    }

}
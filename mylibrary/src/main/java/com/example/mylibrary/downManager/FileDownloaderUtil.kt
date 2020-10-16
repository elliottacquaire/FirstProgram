package com.example.mylibrary.downManager

import android.content.Context
import android.os.Environment
import android.util.Log
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadSampleListener
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.util.FileDownloadUtils
import java.io.File
import java.lang.ref.WeakReference


object FileDownloaderUtil {
    private var mContext: WeakReference<Context>? = null
    var singleTask: BaseDownloadTask? = null
    var singleTaskId = 0
    var apkUrl = "http://cdn.llsapp.com/android/LLS-v4.0-595-20160908-143200.apk"
    var singleFileSaveName = "liulishuo.apk"
    var mSinglePath =
        (FileDownloadUtils.getDefaultSaveRootPath() + File.separator.toString() + "feifei_save"
                + File.separator.toString() + singleFileSaveName)
    var mSaveFolder =
        FileDownloadUtils.getDefaultSaveRootPath() + File.separator.toString() + "feifei_save"

    fun startDown(token: String,fileName: String){
        //设置下载的路径
        val file = File(mContext?.get()?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), System.currentTimeMillis().toString()+"_"+fileName)

        singleTask = FileDownloader.getImpl().create(apkUrl)
//                .setPath(mSinglePath,false)
            .setPath(mSinglePath, true)
            .addHeader("Authorization", "Bearer $token")
            .setCallbackProgressTimes(300)
            .setMinIntervalUpdateSpeed(400)
//            .setTag()
            .setListener(object : FileDownloadSampleListener() {
                override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                    super.pending(task, soFarBytes, totalBytes)
                }

                override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                    super.progress(task, soFarBytes, totalBytes)
                    Log.d(
                        "feifei",
                        "progress taskId:" + task?.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:"
                                + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes + ",speed:" + task?.getSpeed()
                    );
                }

                override fun blockComplete(task: BaseDownloadTask?) {
                    super.blockComplete(task)
                }

                override fun completed(task: BaseDownloadTask?) {
                    super.completed(task)
                    Log.d(
                        "feifei",
                        "completed taskId:" + task?.getId() + ",isReuse:" + task?.reuse()
                    );
                }

                override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                    super.paused(task, soFarBytes, totalBytes)
                }

                override fun error(task: BaseDownloadTask?, e: Throwable?) {
                    super.error(task, e)
                    Log.d(
                        "feifei",
                        "error taskId:" + task?.getId() + ",e:" + e?.getLocalizedMessage()
                    );
                }

                override fun warn(task: BaseDownloadTask?) {
                    super.warn(task)
                }
            })

        singleTaskId =  singleTask?.start()!!
    }


    fun pause_single() {
        Log.d("feifei", "pause_single task:$singleTaskId")
        FileDownloader.getImpl().pause(singleTaskId)
    }

    fun delete_single() {

        //删除单个任务的database记录
        val deleteData = FileDownloader.getImpl().clear(singleTaskId, mSaveFolder)
        val targetFile = File(mSinglePath)
        var delate = false
        if (targetFile.exists()) {
            delate = targetFile.delete()
        }
        Log.d(
            "feifei",
            "delete_single file,deleteDataBase:$deleteData,mSinglePath:$mSinglePath,delate:$delate"
        )
        File(FileDownloadUtils.getTempPath(mSinglePath)).delete()
    }

}
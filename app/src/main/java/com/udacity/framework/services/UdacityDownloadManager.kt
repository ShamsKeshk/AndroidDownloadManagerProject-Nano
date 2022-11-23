package com.udacity.framework.services

import android.app.Application
import android.app.DownloadManager
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.udacity.R
import com.udacity.framework.model.SingleEvent

class UdacityDownloadManager(val app: Application): DefaultLifecycleObserver {

    var downloadLiveData = MutableLiveData<SingleEvent<Int>>()

    private val udacityDownloadReceiver = UdacityDownloadReceiver(loadingStateLiveData = downloadLiveData)

    private val downloadManager = app.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager


    private fun createDownloadRequest(url: String): DownloadManager.Request{
        return DownloadManager.Request(Uri.parse(url))
            .setTitle(app.getString(R.string.app_name))
            .setDescription(app.getString(R.string.app_description))
            .setRequiresCharging(false)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
    }

    private fun startEnqueueDownloadRequest(request: DownloadManager.Request): Long{
        return downloadManager.enqueue(request)
    }

    fun downloadResourceUrl(url: String): Long{
        val request = createDownloadRequest(url)
        val downloadId = startEnqueueDownloadRequest(request)
        udacityDownloadReceiver.downloadId = downloadId
        udacityDownloadReceiver.downloadUrl = url
        return downloadId
    }

    fun getDownloadTimeInMillSec(downloadId: Long): Long {
        val cursor = DownloadManager.Query().apply {
            setFilterById(downloadId)
        }.let {
            downloadManager.query(it)
        }

        cursor.moveToFirst()
        val downloadedData: Int = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR).let {
            cursor.getInt(it)
        }

        val dataToBeDownloaded: Int = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES).let {
            cursor.getInt(it)
        }

        return ((downloadedData * 100L) / dataToBeDownloaded)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        app.registerReceiver(udacityDownloadReceiver, intentFilter)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        app.unregisterReceiver(udacityDownloadReceiver)
    }
}
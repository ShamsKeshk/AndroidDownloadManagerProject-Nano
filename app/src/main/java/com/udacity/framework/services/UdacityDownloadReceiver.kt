package com.udacity.framework.services

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.udacity.framework.model.SingleEvent


class UdacityDownloadReceiver(var downloadId: Long = -1L,var loadingStateLiveData: MutableLiveData<SingleEvent<Int>>) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (downloadId == -1L)
            return

        val downloadManager = context?.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager

        val query = DownloadManager.Query().apply {
            setFilterById(downloadId)
        }

        downloadManager.query(query).let { cursor ->
            if (cursor.moveToFirst()) {
                val downloadStatus = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS).let {
                    cursor.getInt(it)
                }
                loadingStateLiveData.postValue(SingleEvent(downloadStatus))
            }
        }
    }
}
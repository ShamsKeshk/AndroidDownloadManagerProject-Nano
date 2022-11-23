package com.udacity.framework.services

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.udacity.R
import com.udacity.framework.model.SingleEvent
import com.udacity.framework.notification.cancelNotifications
import com.udacity.framework.notification.sendNotification


class UdacityDownloadReceiver(var downloadId: Long = -1L,
                              var downloadUrl: String? = null,
                              var loadingStateLiveData: MutableLiveData<SingleEvent<Int>>) : BroadcastReceiver() {

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
                if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){
                    displayNotification(context,downloadUrl,context.getString(R.string.download_status_succeeded))
                }
                loadingStateLiveData.postValue(SingleEvent(downloadStatus))
            }
        }
    }

    private fun displayNotification(context: Context,fileUrl: String?, downloadStatus: String){
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.cancelNotifications()
        notificationManager.sendNotification(context.getText(R.string.notification_download_success_message).toString(), fileUrl,downloadStatus, context)
    }
}
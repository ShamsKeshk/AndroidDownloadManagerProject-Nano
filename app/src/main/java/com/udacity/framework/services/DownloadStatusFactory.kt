package com.udacity.framework.services

import android.app.DownloadManager
import com.udacity.customDownloaderViews.model.ButtonState

class DownloadStatusFactory {

    companion object{
        fun getButtonDownloadStatus(downloadStatus: Int?): ButtonState {
            return when(downloadStatus){
                DownloadManager.STATUS_SUCCESSFUL -> ButtonState.Completed
                DownloadManager.STATUS_FAILED -> ButtonState.Failed
                DownloadManager.STATUS_PAUSED -> ButtonState.Paused
                DownloadManager.STATUS_PENDING -> ButtonState.Pending
                DownloadManager.STATUS_RUNNING -> ButtonState.Loading
                else -> ButtonState.Failed
            }
        }
    }

}
package com.udacity.ui

import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import com.udacity.R
import com.udacity.customDownloaderViews.model.ButtonState
import com.udacity.databinding.ActivityMainBinding
import com.udacity.framework.services.DownloadStatusFactory
import com.udacity.framework.services.UdacityDownloadManager

class MainActivity : AppCompatActivity() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    private lateinit var binding: ActivityMainBinding

    private lateinit var udacityDownloadManager: UdacityDownloadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        udacityDownloadManager = UdacityDownloadManager(application)
        this.lifecycle.addObserver(udacityDownloadManager)

        binding.contentMain.btnDownload.setOnClickListener {
            binding.contentMain.btnDownload.downloaderButtonState = ButtonState.Clicked
            binding.contentMain.btnDownload.downloaderButtonState = ButtonState.Loading
            download(getSelectedUrl())
        }

        initDownloadObserver()
    }

    private fun initDownloadObserver(){
        udacityDownloadManager.downloadLiveData.observe(this) {
            it.contentIfNotHandled?.let { status ->
                binding.contentMain.btnDownload.downloaderButtonState =
                    DownloadStatusFactory.getButtonDownloadStatus(status)
            }
        }
    }

    private fun getSelectedUrl(): String{
        return  binding.contentMain.rgGroupOfUrls.checkedRadioButtonId.let {
             binding.root.findViewById<RadioButton>(it).text.toString()
        }
    }

    private fun download(url: String) {
        val downloadId = udacityDownloadManager.downloadResourceUrl(url)
        getDownloadTimeInMillSec(downloadId,udacityDownloadManager)
    }

    private fun getDownloadTimeInMillSec(downloadId: Long,udacityDownloadManager: UdacityDownloadManager){
        ButtonState.Loading.apply {
            downloadingTimeDurationInMill = udacityDownloadManager.getDownloadTimeInMillSec(downloadId)
            binding.contentMain.btnDownload.downloaderButtonState = this
        }


    }

    companion object {
        private const val CHANNEL_ID = "channelId"
    }

}

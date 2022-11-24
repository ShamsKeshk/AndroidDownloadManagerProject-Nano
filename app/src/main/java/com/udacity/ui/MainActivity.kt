package com.udacity.ui


import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.udacity.R
import com.udacity.customDownloaderViews.model.ButtonState
import com.udacity.databinding.ActivityMainBinding
import com.udacity.framework.notification.NotificationUtils
import com.udacity.framework.services.DownloadStatusFactory
import com.udacity.framework.services.UdacityDownloadManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var udacityDownloadManager: UdacityDownloadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        NotificationUtils.createChannel(this,getString(R.string.notification_download_channel_id),getString(R.string.notification_download_channel_name))

        udacityDownloadManager = UdacityDownloadManager(application)
        this.lifecycle.addObserver(udacityDownloadManager)

        binding.contentMain.btnDownload.setOnClickListener {
            val selectedUrl = getSelectedUrl()
            if (selectedUrl.isNullOrEmpty()){
                Toast.makeText(applicationContext,getString(R.string.donwload_no_repo_selected_message),Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            binding.contentMain.btnDownload.downloaderButtonState = ButtonState.Clicked
            binding.contentMain.btnDownload.downloaderButtonState = ButtonState.Loading
            download(selectedUrl)
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

    private fun getSelectedUrl(): String?{
        return  binding.contentMain.rgGroupOfUrls.checkedRadioButtonId.let {
            if (it == -1)
                return null

             binding.root.findViewById<RadioButton>(it)?.text.toString()
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

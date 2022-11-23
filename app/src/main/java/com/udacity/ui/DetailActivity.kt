package com.udacity.ui

import android.app.NotificationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.udacity.R
import com.udacity.databinding.ActivityDetailBinding
import com.udacity.framework.notification.cancelNotifications

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        setSupportActionBar(binding.toolbar)

        val fileUrl = intent.getStringExtra(KEY_DOWNLOAD_URL)
        val downloadStatus = intent.getStringExtra(KEY_DOWNLOAD_STATUS)
        binding.clContentDetails.repoUrl = fileUrl
        binding.clContentDetails.downloadStatus = downloadStatus

        binding.clContentDetails.btnDownloadAnotherResource.setOnClickListener {
            finish()
        }

        val notificationManager =
            ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        notificationManager.cancelNotifications()
    }

    companion object{
        const val KEY_DOWNLOAD_URL = "DOWNLOADED_FILE_URL"
        const val KEY_DOWNLOAD_STATUS = "DOWNLOADED_FILE_STATUS"
    }

}

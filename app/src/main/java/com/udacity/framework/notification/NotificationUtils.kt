/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.udacity.framework.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.udacity.R
import com.udacity.ui.DetailActivity
import com.udacity.ui.MainActivity

private const val  NOTIFICATION_ID = 0
private const val REQUEST_CODE = 0

fun NotificationManager.sendNotification(messageBody: String, fileUrl: String?, downloadStatus: String, applicationContext: Context) {



    val intentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    }else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }

    val homeScreenIntent = Intent(applicationContext, MainActivity::class.java)

    val homeScreenPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        homeScreenIntent,
        intentFlags)

    val downloadDetailsIntent = Intent(applicationContext, DetailActivity::class.java).apply {
        putExtra(DetailActivity.KEY_DOWNLOAD_URL,fileUrl)
        putExtra(DetailActivity.KEY_DOWNLOAD_STATUS,downloadStatus)
    }

    val snoozePendingIntent = PendingIntent.getActivity(
        applicationContext,
        REQUEST_CODE,
        downloadDetailsIntent,
        0)


    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_download_channel_id))
        .setSmallIcon(R.drawable.ic_download_image)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(homeScreenPendingIntent)
        .setAutoCancel(true)
        .setStyle(NotificationCompat.BigTextStyle())
        .addAction(
            R.drawable.ic_data_exploration,
            applicationContext.getString(R.string.notification_download_details_action),
            snoozePendingIntent
        ).setPriority(NotificationCompat.PRIORITY_HIGH)

    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}

class NotificationUtils{

    companion object{
        fun createChannel(context: Context,channelId: String, channelName: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                )
                    .apply {
                        setShowBadge(false)
                    }

                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.RED
                notificationChannel.enableVibration(true)
                notificationChannel.description = context.getString(R.string.notification_download_channel_description)

                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }
    }
}

package com.example.kotlinproject.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.kotlinproject.Main2Activity
import com.example.kotlinproject.R

const val ALARM_NOTIFICATION_ID = 3
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            // content를 눌렀을 때 Main2 Activity가 떠야한다.
            val contentIntent = Intent(context, Main2Activity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_IMMUTABLE)

            val notification = NotificationCompat.Builder(context, App.ALERT_CHANNEL_ID)
                .setContentTitle("Food Service")
                .setContentText("안녕하세요 반갑습니다.")
                .setSmallIcon(R.drawable.ic_baseline_fastfood_24)
                .setContentIntent(pendingIntent)
                .build()

            context.getSystemService(NotificationManager::class.java).notify(ALARM_NOTIFICATION_ID, notification)
        }
    }
}
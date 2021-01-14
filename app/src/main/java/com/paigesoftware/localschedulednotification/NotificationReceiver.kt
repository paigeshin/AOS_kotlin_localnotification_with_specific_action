package com.paigesoftware.localschedulednotification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class NotificationReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {

        val repeatingIntent = Intent(context, RepeatingActivity::class.java)
        repeatingIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP //replace the same old activity when activity is already opened
        val pendingIntent = PendingIntent.getActivity(context, 100, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        val notificationBuilder = NotificationCompat.Builder(context!!, "notify")
        val notification = notificationBuilder.setContentTitle("Demo App Notification")
            .setContentIntent(pendingIntent)
            .setContentText("New Notification From Demo App")
            .setTicker("New Message Alert!")
            .setAutoCancel(false)
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()

        notificationManager?.notify(0, notification)


    }
}
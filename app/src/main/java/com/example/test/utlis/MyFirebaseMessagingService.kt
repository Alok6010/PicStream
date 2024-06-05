package com.example.test.utlis

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.test.MainActivity
import com.example.test.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val channelId = "myAppid"

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(manager)

        val intent1 = PendingIntent.getActivities(this,0,
            arrayOf(intent), PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this,channelId)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSmallIcon(R.drawable.notification_bell)
            .setAutoCancel(true)
            .setContentIntent(intent1)
            .build()

        manager.notify(Random.nextInt(), notification)

    }

    private fun createNotificationChannel(manager: NotificationManager){
        val channel = NotificationChannel(channelId, "myAppidchat", NotificationManager.IMPORTANCE_HIGH)

        channel.description = "New Chat"
        channel.enableLights(true)
        manager.createNotificationChannel(channel)
    }

}
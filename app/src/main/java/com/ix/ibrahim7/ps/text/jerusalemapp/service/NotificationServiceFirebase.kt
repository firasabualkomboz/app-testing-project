package com.ix.ibrahim7.ps.text.jerusalemapp.service

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ix.ibrahim7.ps.text.jerusalemapp.other.ALERT
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.activity.MainActivity
import com.ix.ibrahim7.ps.text.jerusalemapp.util.NotificationManager


class NotificationServiceFirebase : FirebaseMessagingService() {

    lateinit var notificationManager: NotificationManager
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("FCM", "Token: $token")
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        notificationManager = NotificationManager(this)

        if (remoteMessage.data[ALERT] == null) {
                    Log.e("eee title", remoteMessage.notification!!.title.toString())
                    Log.e("eee data", remoteMessage.data.toString())
                    notificationManager.showNotification(
                        1,
                        remoteMessage.notification!!.title!!,
                        remoteMessage.notification!!.body!!,
                        Intent(
                            applicationContext,
                            MainActivity::class.java
                        )
                    )
        } else {
            Log.e("eee", remoteMessage.notification.toString())
        }

    }



}
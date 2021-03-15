package com.wsoteam.horoscopes.utils.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.SplashActivity
import java.util.*

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        showNotification(p0)
    }

    private fun showNotification(p0: RemoteMessage) {
        var intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        var pendingIntent = PendingIntent
            .getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)


        var largeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_horos_bnv)
        var notificationBuilder = NotificationCompat.Builder(this, "com.wsoteam.horoscopes")
            .setContentTitle("Daily Horoscope")
            .setContentText(getNotificationText())
            .setSmallIcon(R.drawable.ic_horos_bnv)
            .setLargeIcon(largeIcon)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0, 500))
            .setLights(Color.MAGENTA, 500, 1000)
            .setContentIntent(pendingIntent)
        var notification = notificationBuilder.build()
        /*var notificationTarget = NotificationTarget(this,
                R.id.ivAvatarNotification, collapsedView,
                notification, 0)
        Handler(Looper.getMainLooper()).post(Runnable {
            Glide.with(MyApp.getInstance().applicationContext).asBitmap().load(p0.data["url"]).into(notificationTarget)
        })*/

        var notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("com.jundev.diets",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }*/

        notificationManager.notify(0, notification)
    }

    fun getNotificationText(): String {
        var calendar = Calendar.getInstance()
        var nameMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var dayString = "$day"
        if (day < 10){
            dayString = "0$dayString"
        }
        return "${App.getInstance().applicationContext.getString(R.string.first_part_notif)} $nameMonth $dayString ${App.getInstance().applicationContext.getString(R.string.second_part_notif)}"
    }
}
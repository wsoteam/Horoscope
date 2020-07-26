package com.wsoteam.horoscopes.notification

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.SplashActivity
import com.wsoteam.horoscopes.utils.PreferencesProvider
import java.lang.NumberFormatException
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private val CHANNEL_ID = ".channelId"

        fun startNotification(context: Context?){
            val calendar = Calendar.getInstance()
            val now = Calendar.getInstance()

            Log.d("kkk", "PreferencesProvider.getNotifTime() = ${PreferencesProvider.getNotifTime()}")

            try {
                val (hours, minutes) = PreferencesProvider.getNotifTime().split(":").map { it.toInt() }

                Log.d("kkk", "hours = $hours, minutes = $minutes")

                calendar.set(Calendar.HOUR_OF_DAY, hours)
                calendar.set(Calendar.MINUTE, minutes)
                calendar.set(Calendar.SECOND, 0)
            } catch (e: NumberFormatException){
                e.printStackTrace()
                return
            }


            if (now.after(calendar)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent
                .getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
            val alarmManager = context?.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationIntent = Intent(context, SplashActivity::class.java)
//            .putExtra(Config.OPEN_FROM_NOTIFY, Config.OPEN_FROM_NOTIFY_DAY)

        val VIBRATE_PATTERN = longArrayOf(0, 500)
        val NOTIFICATION_COLOR = Color.RED
        val NOTIFICATION_SOUND_URI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntent(notificationIntent)

        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = Notification.Builder(context)
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notification = builder.setContentTitle("Title")
            .setContentText("Content Text")
            .setAutoCancel(true)
            .setVibrate(VIBRATE_PATTERN)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setSound(NOTIFICATION_SOUND_URI)
            .setContentIntent(pendingIntent).build()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID)
        }

        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                context.resources.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            channel.enableLights(true)
            channel.setSound(alarmSound, audioAttributes)
            channel.lightColor = NOTIFICATION_COLOR
            channel.vibrationPattern = VIBRATE_PATTERN
            channel.enableVibration(true)
            channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(channel)
        }

//        var count = PreferencesProvider.getNotifyCount()!!
//        count ++
//        PreferencesProvider.setNotifyCount(count)
//        Analytics.setCountNotify(count.toString())
//        Analytics.showNotification()

        notificationManager.notify(0, notification)
    }
}
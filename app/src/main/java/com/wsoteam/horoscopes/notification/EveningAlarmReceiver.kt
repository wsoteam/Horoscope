package com.wsoteam.horoscopes.notification

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.SplashActivity
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import java.lang.NumberFormatException
import java.util.*

class EveningAlarmReceiver : BroadcastReceiver() {

    companion object {
        private val CHANNEL_ID = ".channelId"

        fun startNotification(context: Context?, hours: Int, minutes: Int) {
            val calendar = Calendar.getInstance()
            val now = Calendar.getInstance()
            try {
                calendar.set(Calendar.HOUR_OF_DAY, hours)
                calendar.set(Calendar.MINUTE, minutes)
                calendar.set(Calendar.SECOND, 0)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return
            }
            if (now.after(calendar)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            val intent = Intent(context, EveningAlarmReceiver::class.java)
            val pendingIntent = PendingIntent
                .getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
            val alarmManager = context?.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        /*if (isShowTodayNotifEarly()) {
            Analytic.showEveningNotif()
            val notificationIntent = Intent(context, SplashActivity::class.java)
                .putExtra(Config.OPEN_FROM_NOTIFY, Config.OPEN_FROM_EVENING_NOTIF)

            val VIBRATE_PATTERN = longArrayOf(0, 500)
            val NOTIFICATION_COLOR = Color.RED
            val NOTIFICATION_SOUND_URI =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addNextIntent(notificationIntent)

            val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = Notification.Builder(context)
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val notification = builder.setContentTitle("Daily Horoscope")
                .setContentText(context!!.getString(R.string.evening_alarm))
                .setAutoCancel(true)
                .setVibrate(VIBRATE_PATTERN)
                //.setSmallIcon(R.drawable.ic_notifications)
                .setSmallIcon(R.drawable.ic_horos_bnv)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSound(NOTIFICATION_SOUND_URI)
                .setContentIntent(pendingIntent).build()


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setChannelId(CHANNEL_ID)
            }

            val notificationManager =
                context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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

            notificationManager.notify(0, notification)
        }*/
    }

    private fun isShowTodayNotifEarly(): Boolean {
        return PreferencesProvider.getLastDayNotification()!! == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)

    }


}
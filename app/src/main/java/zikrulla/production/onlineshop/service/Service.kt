package zikrulla.production.onlineshop.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_VIBRATE
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.orhanobut.hawk.BuildConfig
import com.orhanobut.hawk.BuildConfig.APPLICATION_ID
import zikrulla.production.onlineshop.R
import zikrulla.production.onlineshop.ui.MainActivity
import zikrulla.production.onlineshop.util.PrefUtils
import zikrulla.production.onlineshop.util.Util.TAG

class AppFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(TAG, "onNewToken: $token")
        PrefUtils.setToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        try {
            val body = message.notification?.body.toString()
            val title = message.notification?.title.toString()
            val from = message.from.toString()
            val data = message.data.toString()
            showMessage(title, body)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @SuppressLint("ResourceAsColor")
    private fun showMessage(title: String, body: String, id: Long = System.currentTimeMillis(), withSound: Boolean = false) {
        val defaultSoundUri = if (withSound) RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) else null
        val intent = Intent(this, MainActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = APPLICATION_ID

        val builder = NotificationCompat.Builder(this, channelId)
            .setDefaults(DEFAULT_VIBRATE)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setSmallIcon(R.drawable.ic_menu)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.ic_menu
                )
            )
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setColor(R.color.white)
            .setSound(defaultSoundUri)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel =
                NotificationChannel(channelId, "$channelId channel", IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(id.toInt(), builder.build())
    }

}
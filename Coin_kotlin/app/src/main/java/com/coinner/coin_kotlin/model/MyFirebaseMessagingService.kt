package com.coinner.coin_kotlin.model

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.activity.MainActivity
import com.coinner.coin_kotlin.info.Post
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseMsgService"

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // notifiation은 background
        // data는 bacground,forground 따라서 data로 보내줘야겠는데, 방식이 어떤지 알아봐야겠다. , 그리고 테스트기기를 사용해서 테스트해봐야겠음.
        // 결론: noti, noti+data, data 세 경우로 나뉘는데 data만 보내면 백그라운드에서도 onmessagereceived를 거치게 되고 나머진 아님.
        val title = remoteMessage.data["title"]
        val body = remoteMessage.data["body"]
        val post = Gson().fromJson(remoteMessage.data["fcmPost"],Post::class.java)
        Log.e(TAG,"title: $title  body: $body fcmPost: $post")
        Log.e(TAG,"post: $postNoti coin: $coinNoti")

        if (!title.isNullOrEmpty() && !body.isNullOrEmpty())
            if(post == null && coinNoti) //코인시세 알림
                sendNotification(title,body,null)
            else if(post != null && postNoti)//댓글 달린경우
                sendNotification(title,body,post)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("UnspecifiedImmutableFlag")
    fun sendNotification(title: String, body: String,post:Post?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            if(post != null) {
                putExtras(Bundle().apply {
                    putParcelable("fcmPost", post) })
//                putExtra("fcmPost",post)
            }
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
             PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            //MUTABLE을 사용하면 새로운 액티비티가 실행되면서 인텐트가 새로운 액티비티의 클래스 인텐트로 교체되므로 IMMUTALE해야함
            //FLAG_UPDATE_CURRENT 사용해야 인텐트 안에 번들이 정확하게 들어감
        )
        val channel = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //채널생성
        NotificationChannel(
            resources.getString(R.string.default_notification_channel_id), //채널 ID
            "COIN", //채널명
            IMPORTANCE_HIGH //알림음이 울리며 헤드업 알림 표시
        ).apply {
            enableLights(true)
            lightColor= Color.RED
            enableVibration(true)
            description = "notification"
            notificationManager.createNotificationChannel(this)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channel)
            .setSmallIcon(R.drawable.o)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        notificationManager.notify(
            Calendar.getInstance().timeInMillis.toInt(), notificationBuilder.build()
        )
    }

    fun saveToken(newToken: String) {
        val originToken = PreferenceManager.getString(this, "fcmToken")

        if (originToken != newToken)
            PreferenceManager.setString(this, "fcmToken", newToken)
    }

    override fun onNewToken(newtToken: String) {
        Log.e(TAG,newtToken)
        val id = PreferenceManager.getString(this,"id")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                if(id != null)
                    Repository.updateToken(id,newtToken)
                saveToken(newtToken)
            }catch (e:Exception){
                Log.e(TAG,e.message+"")
            }
        }
    }

    private val allNoti: Boolean
        get() = PreferenceManager.getNoti(this,"allNoti")

    private val postNoti: Boolean
        get() = PreferenceManager.getNoti(this,"postNoti")

    private val coinNoti: Boolean
        get() = PreferenceManager.getNoti(this,"coinNoti")
}
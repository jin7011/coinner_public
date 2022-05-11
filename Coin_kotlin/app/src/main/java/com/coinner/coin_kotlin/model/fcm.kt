package com.coinner.coin_kotlin.model

import android.util.Log
import com.coinner.coin_kotlin.info.Post
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object fcm {
    val JSON = MediaType.parse("application/json; charset=utf-8")

    suspend fun sendNotification(token: String, title: String, message: String,post:Post){
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            try {
                val client = OkHttpClient()
                val dataJson = JSONObject().run {
                    put("title", title)
                    put("body", message)
                    put("fcmPost",Gson().toJson(post))
                }
                val json = JSONObject().run {
                    put("priority", "high")
                    put("data", dataJson)
//                    put("notification:",dataJson)
                    put("to", token)
                }

                val reqBody = RequestBody.create(JSON, json.toString())
                val req = Request.Builder()
                    .header(
                        "Authorization",
                        "key=" + "AAAA0b9Dx7o:APA91bGyxtDqDggU6I5gRiKxuFW6tld7cpPERm53_WkFwD7tSHZcEp8p3l6slrRQAJEk34Q8XVjJXYvUkKDBmX9y--rHCBAMr5hb-oGFukYvVkAM7Vdhrl72y822SR-LnvDGuk9oWjrZ"
                    )
                    .url("https://fcm.googleapis.com/fcm/send")
                    .post(reqBody)
                    .build()

                val response = client.newCall(req).enqueue(object : Callback{
                    override fun onResponse(call: Call, response: Response) {
                        Log.e("FCM","success")
                    }
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("FCM","onFailure: "+e.message)
                    }
                })
            } catch (e: Exception) {
                Log.e("FCM",e.message+"")
            }
        }
    }
}
package com.coinner.coin_kotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.admob.MyApplication
import com.coinner.coin_kotlin.databinding.ActivityAdmobBinding
import com.coinner.coin_kotlin.model.PreferenceManager
import com.coinner.coin_kotlin.utility.NetworkStatus
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class AdmobActivity : AppCompatActivity() {

    lateinit var binding: ActivityAdmobBinding
    var isAdShown = false
    var isAdDismissed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getFcm()
    }

    private fun setView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admob)
        checkNetWork()
        (application as MyApplication).getAdManager()
            .showAdIfAvailable(object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                isAdDismissed = true
                launchMainActivity()
            }
            override fun onAdShowedFullScreenContent() {
                isAdShown = true
            }
        })
        loadResources()
    }

    private fun loadResources() {
        val timer = object : CountDownTimer(1500L, 1000L) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                if (!isAdShown)
                    launchMainActivity()
            }
        }
        timer.start()
    }

    private fun getFcm(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Log.e("getFcm","token: $token")
            saveToken(token)
            setView()
        })
    }

    private fun saveToken(newToken: String){
        val originToken = PreferenceManager.getString(this,"fcmToken")

        if(originToken != newToken)
            PreferenceManager.setString(this,"fcmToken",newToken)
    }

    fun launchMainActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun checkNetWork() {
        if (!NetworkStatus.isConnected(this)) {
            Log.e("splash act", "network is disconnected")
            Handler(Looper.getMainLooper()).postDelayed({ Toast("인터넷 연결이 되어있지 않습니다.") }, 0)
            return
        }
    }

    fun Toast(str: String) {
        android.widget.Toast.makeText(this, str, android.widget.Toast.LENGTH_SHORT).show();
    }

}
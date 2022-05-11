package com.coinner.coin_kotlin.admob

import android.app.Application
import com.google.android.gms.ads.MobileAds

class MyApplication : Application() {

    private val adManager = AppOpenAdManager()

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this){}
        adManager.initialize(this)
    }

    fun getAdManager(): AppOpenAdManager{
        return adManager
    }
}
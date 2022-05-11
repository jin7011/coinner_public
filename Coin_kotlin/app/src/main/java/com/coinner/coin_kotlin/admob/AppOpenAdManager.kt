package com.coinner.coin_kotlin.admob

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import java.util.*

class AppOpenAdManager : LifecycleObserver {

    private val TAG = "AppOpenAdManager"
    private var appOpenAd: AppOpenAd? = null
    private var currentActivity: Activity? = null
    private val AD_UNIT_ID = "ca-app-pub-2931857165077003/1819092941"
    private val adRequest: AdRequest by lazy { AdRequest.Builder().build() }
    private lateinit var application: Application
    private var isShowingAd = false
    private val isAdAvailable: Boolean
        get() = appOpenAd != null && wasLoadTimeLessThanNHoursAgo()
    private var loadTime: Long = 0
    val isTimetoAd: Boolean
        get() = wasLoadTimeMoreThanNHoursAgo()

    fun initialize(application: Application) {
        this.application = application

        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {
                Log.e(TAG,"ACT START")
                currentActivity = activity
            }

            override fun onActivityResumed(activity: Activity) {
                currentActivity = activity
            }

            override fun onActivityStopped(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {
                currentActivity = null
            }
        })
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    /**
     * Request an ad
     * Have unused ad, no need to fetch another.
     */
    fun fetchAd(){
        fetchAd(null)
    }
    fun fetchAd(loadlistener: AppOpenAd.AppOpenAdLoadCallback?) {
        if (isAdAvailable) {
            Log.e(TAG,"is not adAvailable")
            return
        }
        /**
         * Called when an app open ad has loaded.
         * @param ad the loaded app open ad.
         */
        /**
         * Called when an app open ad has failed to load.
         * @param loadAdError the error.
         * Handle the error.
         */
        val loadCallback: AppOpenAd.AppOpenAdLoadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {
            /**
             * Called when an app open ad has loaded.
             * @param ad the loaded app open ad.
             */
            override fun onAdLoaded(ad: AppOpenAd) {
                loadlistener?.onAdLoaded(ad)
                Log.e(TAG,"adload")
                appOpenAd = ad
                loadTime = Date().time
            }

            /**
             * Called when an app open ad has failed to load.
             * @param loadAdError the error.
             * Handle the error.
             */
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {}
        }

        Log.e(TAG,"adavailable")
        AppOpenAd.load(
            application,
            AD_UNIT_ID,
            adRequest,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            loadCallback
        )
    }

    /**
     * Only show ad if there is not already an app open ad currently showing
     * and an ad is available.
     */
    fun showAdIfAvailable(){
        showAdIfAvailable(null)
    }
    fun showAdIfAvailable(listener: FullScreenContentCallback?) {
        if (!isShowingAd && isAdAvailable) {
            Log.e(TAG,"show")
            appOpenAd!!.apply {
                fullScreenContentCallback = object : FullScreenContentCallback() {
                    /**
                     * Set the reference to null so isAdAvailable() returns false.
                     */
                    override fun onAdDismissedFullScreenContent() {
                        listener?.onAdDismissedFullScreenContent()
                        appOpenAd = null
                        isShowingAd = false
                        fetchAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        listener?.onAdFailedToShowFullScreenContent(adError)
                    }
                    override fun onAdShowedFullScreenContent() {
                        listener?.onAdShowedFullScreenContent()
                        isShowingAd = true
                    }
                }
                show(currentActivity)
            }
        } else {
            Log.e(TAG,"no show")
            fetchAd()
        }
    }

    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long = 4): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    private fun wasLoadTimeMoreThanNHoursAgo(numHours: Long = 1): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour = 3600000
        return dateDifference >= numMilliSecondsPerHour * numHours
    }
}




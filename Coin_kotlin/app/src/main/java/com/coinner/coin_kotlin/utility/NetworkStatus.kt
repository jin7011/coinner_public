package com.coinner.coin_kotlin.utility

import android.content.Context
import android.net.ConnectivityManager

object NetworkStatus {

    fun isConnected(context: Context):Boolean{
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as (ConnectivityManager)

        val net = manager.activeNetwork
        val acnw = manager.getNetworkCapabilities(net)

//        return when{
//            acnw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//            acnw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//            acnw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//            else -> false
//        }

         return acnw != null
    }
}
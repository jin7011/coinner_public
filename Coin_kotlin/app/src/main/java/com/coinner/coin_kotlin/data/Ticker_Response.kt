package com.coinner.coin_kotlin.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Ticker_Response(
    @SerializedName("data") @Expose
    val data: Map<String?, Any?>,

    @SerializedName("status")
    val status: String?
)
package com.coinner.coin_kotlin.data

import com.google.gson.annotations.SerializedName

data class Candle_List(
    @SerializedName("data")
    val data : ArrayList<ArrayList<String>>,
    @SerializedName("status")
    val status : String
)
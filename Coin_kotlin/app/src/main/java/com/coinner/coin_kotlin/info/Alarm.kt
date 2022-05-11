package com.coinner.coin_kotlin.info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Alarm(
    @SerializedName("price")
    @Expose
    val price: String,
    @SerializedName("coin")
    @Expose
    val coin:String,
    @SerializedName("id")
    @Expose
    val id:String,
    @SerializedName("pk")
    @Expose
    val pk: Int,
    @SerializedName("msg")
    @Expose
    val msg:String?,
)

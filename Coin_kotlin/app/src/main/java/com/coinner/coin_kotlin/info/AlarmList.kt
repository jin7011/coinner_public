package com.coinner.coin_kotlin.info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AlarmList(
    @SerializedName("msg")
    @Expose
    val msg:String?,
    @SerializedName("result")
    @Expose
    val Alarmlist:ArrayList<Alarm>
)

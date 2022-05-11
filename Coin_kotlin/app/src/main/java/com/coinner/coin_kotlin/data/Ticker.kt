package com.coinner.coin_kotlin.data

import com.google.gson.annotations.SerializedName

data class Ticker(
    @SerializedName("closing_price")
    val closing_price //현재가
    : String,

    @SerializedName("acc_trade_value_24H")
    val acc_trade_value_24H //최근 24시간 거래금액
    : String,

    @SerializedName("fluctate_rate_24H")
    val fluctate_rate_24H //최근 24시간 변동률
    : String,

    var name:String,
    var sub_name:String
)
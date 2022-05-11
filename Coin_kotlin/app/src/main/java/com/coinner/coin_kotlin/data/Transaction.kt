package com.coinner.coin_kotlin.data

import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("transaction_date")
    val transaction_date: String?,
    @SerializedName("price")
    val price //Currency 거래가
    : String?,

//    거래 유형
//    bid : 매수 ask : 매도
    @SerializedName("type")
    val type: String?
)
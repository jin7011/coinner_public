package com.coinner.coin_kotlin.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class Transaction_List_Response(
    @SerializedName("data")
    val data: ArrayList<Transaction?>?,
    @SerializedName("status")
    val status: String?
)
package com.coinner.coin_kotlin.info

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        @SerializedName("id") @Expose
        val id:String,
        @SerializedName("nickname") @Expose
        val nickname:String,
        @SerializedName("mail") @Expose
        val mail:String,
        @SerializedName("token") @Expose
        val token:String,
        @SerializedName("msg") @Expose
        val msg:String,
        @SerializedName("result") @Expose
        val result:Boolean
) : Parcelable

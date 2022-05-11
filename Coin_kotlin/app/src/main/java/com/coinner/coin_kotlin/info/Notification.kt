package com.coinner.coin_kotlin.info

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Notification(
    @SerializedName("postid")
    @Expose
    val postid: String,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("content")
    @Expose
    val content: String,
    @SerializedName("createdat")
    @Expose
    val createdat: Date,
    @SerializedName("nickname")
    @Expose
    val nickname: String,
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("token") @Expose
    val token:String,
    var dateFormate_for_layout: String?
): Parcelable

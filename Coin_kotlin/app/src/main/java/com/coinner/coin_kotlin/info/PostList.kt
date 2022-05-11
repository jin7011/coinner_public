package com.coinner.coin_kotlin.info

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlin.collections.ArrayList

@Parcelize
data class PostList(
        @SerializedName("result")
        @Expose
        val postList:ArrayList<Post>,
        @SerializedName("msg")
        @Expose
        val msg:String,
        @SerializedName("issuccess")
        @Expose
        val isuccess:Boolean

): Parcelable

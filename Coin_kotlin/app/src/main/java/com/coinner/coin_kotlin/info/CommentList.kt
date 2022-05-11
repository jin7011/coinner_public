package com.coinner.coin_kotlin.info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommentList(
        @SerializedName("result")
        @Expose
        val commentlist:ArrayList<Comment>,
        @SerializedName("msg")
        @Expose
        val msg:String?,
        @SerializedName("issuccess")
        @Expose
        val issuccess:Boolean,
)

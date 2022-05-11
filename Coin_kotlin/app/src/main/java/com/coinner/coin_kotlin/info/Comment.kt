package com.coinner.coin_kotlin.info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class Comment(
        @SerializedName("commentid")
        @Expose
        val commentid:String,
        @SerializedName("postid")
        @Expose
        val postid:String,
        @SerializedName("nickname")
        @Expose
        val nickname:String,
        @SerializedName("love")
        @Expose
        val love:Int,
        @SerializedName("content")
        @Expose
        val content:String,
        @SerializedName("msg")
        @Expose
        val msg:String,
        @SerializedName("id")
        @Expose
        val id:String,
        @SerializedName("createdat")
        @Expose
        val createdat: Date,
        var dateFormate_for_layout: String?

)

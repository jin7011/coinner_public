package com.coinner.coin_kotlin.utility

import androidx.recyclerview.widget.DiffUtil
import com.coinner.coin_kotlin.info.Post
import com.coinner.coin_kotlin.utility.Named.HOUR
import com.coinner.coin_kotlin.utility.Named.MIN
import com.coinner.coin_kotlin.utility.Named.SEC
import java.text.SimpleDateFormat
import java.util.*

class PostInfo_DiffUtil(
        val oldPosts: ArrayList<Post>,
        val newPosts: ArrayList<Post>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldPosts.size
    }

    override fun getNewListSize(): Int {
        return newPosts.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldpost: Post = oldPosts[oldItemPosition]
        val newpost: Post = newPosts[newItemPosition]

        return oldpost.postid == newpost.postid
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean { //item이 같아도 수정된다면 내용이 다르다는 것을 인식시켜줘야 내용이 바뀜
        val oldpost: Post = oldPosts[oldItemPosition]
        val newpost: Post = newPosts[newItemPosition]

        return oldpost.love.equals(newpost.love)
                && oldpost.commentNum.equals(newpost.commentNum)
                && oldpost.title.equals(newpost.title)
                && oldpost.content.equals(newpost.content)
    }

    companion object {
        fun formatTimeString(postdate: Date, nowDate: Date): String {

            val ctime = nowDate.time
            val regTime = postdate.time
            var diffTime = (ctime - regTime) / 1000
            lateinit var msg: String

            if (diffTime < SEC) {
                msg = "방금 전"
            } else if (SEC.let({ diffTime /= it; diffTime }) < MIN) {
                msg = diffTime.toString() + "분 전"
            } else if (MIN.let({ diffTime /= it; diffTime }) < HOUR) {
                msg = SimpleDateFormat("HH:mm").format(postdate)
                //        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
//            msg = (diffTime) + "일 전";
//        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
//            msg = (diffTime) + "달 전";
            } else {
                msg = SimpleDateFormat("MM월dd일").format(postdate)
            }
            return msg
        }
    }
}
package com.coinner.coin_kotlin.utility

import androidx.recyclerview.widget.DiffUtil
import com.coinner.coin_kotlin.info.Comment
import com.coinner.coin_kotlin.utility.Named.HOUR
import com.coinner.coin_kotlin.utility.Named.MIN
import com.coinner.coin_kotlin.utility.Named.SEC
import java.text.SimpleDateFormat
import java.util.*


class CommentInfo_DiffUtil(
    val OldPost: ArrayList<Comment>,
    val NewPost: ArrayList<Comment>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return OldPost.size
    }

    override fun getNewListSize(): Int {
        return NewPost.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldpost: Comment = OldPost[oldItemPosition]
        val newpost: Comment = NewPost[newItemPosition]
        return oldpost.postid.equals(newpost.postid)
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean { //item이 같아도 수정된다면 내용이 다르다는 것을 인식시켜줘야 내용이 바뀜
        val oldpost: Comment = OldPost[oldItemPosition]
        val newpost: Comment = NewPost[newItemPosition]

        return (oldpost.content.equals(newpost.content) && oldpost.nickname
            .equals(newpost.nickname)
                && oldpost.love == newpost.love)
    }

    companion object {
        fun formatTimeString(postdate: Date, nowDate: Date): String? {
            val ctime = nowDate.time
            val regTime = postdate.time
            var diffTime = (ctime - regTime) / 1000
            var msg: String? = null
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
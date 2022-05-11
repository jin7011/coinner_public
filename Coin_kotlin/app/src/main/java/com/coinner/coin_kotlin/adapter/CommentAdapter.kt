package com.coinner.coin_kotlin.adapter

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.activity.PostActivity
import com.coinner.coin_kotlin.info.Comment
import com.coinner.coin_kotlin.info.Post
import com.coinner.coin_kotlin.utility.CommentInfo_DiffUtil
import com.coinner.coin_kotlin.utility.Named
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*


class CommentAdapter(
    val activity: Activity,
    private val post:Post,
    private val comments: ArrayList<Comment>,
    private val goodListener: GoodListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val auth = FirebaseAuth.getInstance().currentUser

    interface GoodListener{
        fun pressed(comment:Comment)
    }

    fun commentDiffUtil(NewCommentList:ArrayList<Comment>){

        val diffCalback = CommentInfo_DiffUtil(comments,NewCommentList)
        val diffRes = DiffUtil.calculateDiff(diffCalback)

        this.comments.clear()
        this.comments.addAll(NewCommentList)

        diffRes.dispatchUpdatesTo(this)
    }

    inner class CommentHolder(val view:View) : RecyclerView.ViewHolder(view) {

        val contentT:TextView = itemView.findViewById(R.id.comment_commentT)
        val dateT:TextView = itemView.findViewById(R.id.date_commentT)
        val nicknameT:TextView = itemView.findViewById(R.id.nickname_commentT)
        val goodNumLayout:LinearLayout = itemView.findViewById(R.id.comment_GoodLayout)
        val goodNum:TextView = itemView.findViewById(R.id.goodNum_commentT)
        val option_btn:ImageButton = itemView.findViewById(R.id.commentOptBtn)
        val good_btn:ImageButton = itemView.findViewById(R.id.goodCommentBtn)

        fun bind(item:Comment){
            contentT.text = item.content
            dateT.text = formatTimeString(item.createdat,Date())

            if(item.love > 0){
                goodNumLayout.visibility = View.VISIBLE
                goodNum.text = item.love.toString()
            }else{
                goodNumLayout.visibility = View.GONE
                goodNum.text = "0"
            }

            if(post.id == item.id){
                nicknameT.run{
                    text = "글쓴이"
                    setTextColor(ContextCompat.getColor(activity, R.color.maincolor))
                }
            }else{
                nicknameT.run{
                    text = item.nickname
                    setTextColor(ContextCompat.getColor(activity, R.color.textcolor))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_comments,parent,false)
        val holder = CommentHolder(view)

        holder.run {
            good_btn.setOnClickListener {
                goodListener.pressed(comments[holder.absoluteAdapterPosition])
            }

            option_btn.setOnClickListener {
                val comment = comments[holder.absoluteAdapterPosition]
                val commentPublisher = comment.id
                if(auth != null && (auth.uid == commentPublisher)){
                    deleteDialog(comment)
                }else{
                    othersDialog(comment)
                }
            }
        }

        return holder
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CommentHolder){
            holder.bind(comments[position])
        }
    }

    private fun deleteDialog(comment: Comment) {
        val items = arrayOf("삭제")
        val alert: AlertDialog.Builder = AlertDialog.Builder(activity)
        alert.setItems(items) { dialog, which ->
            when (which) {
                0 -> {
                    if(activity is PostActivity){
                        activity.livedataComment.delComment(comment)
                    }
                }
            }
        }
        val alertDialog: AlertDialog = alert.create()
        alertDialog.show()
    }


    private fun othersDialog(comment: Comment) {
        val items = arrayOf("신고")
        val alert: AlertDialog.Builder = AlertDialog.Builder(activity)
        alert.setItems(items) { dialog, which -> //todo 기능추가해줘야함.
            when (which) {
                0 -> {

                }
            }
        }
        val alertDialog: AlertDialog = alert.create()
        alertDialog.show()
    }

    companion object {
        fun formatTimeString(postdate: Date, nowDate: Date): String? {
            val ctime = nowDate.time
            val regTime = postdate.time
            var diffTime = (ctime - regTime) / 1000
            var msg: String? = null
            if (diffTime < Named.SEC) {
                msg = "방금 전"
            } else if (Named.SEC.let({ diffTime /= it; diffTime }) < Named.MIN) {
                msg = diffTime.toString() + "분 전"
            } else if (Named.MIN.let({ diffTime /= it; diffTime }) < Named.HOUR) {
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
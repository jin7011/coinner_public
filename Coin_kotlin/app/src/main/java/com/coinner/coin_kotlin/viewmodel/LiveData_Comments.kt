package com.coinner.coin_kotlin.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.activity.PostActivity
import com.coinner.coin_kotlin.adapter.CommentAdapter
import com.coinner.coin_kotlin.info.Comment
import com.coinner.coin_kotlin.info.Post
import com.coinner.coin_kotlin.model.Repository
import com.coinner.coin_kotlin.model.fcm
import com.coinner.coin_kotlin.utility.Named.POSTDELETE
import com.coinner.coin_kotlin.utility.Named.Time_to_String
import com.coinner.coin_kotlin.utility.NetworkStatus
import com.coinner.coin_kotlin.utility.Utility
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception

class LiveData_Comments(
    val activity: PostActivity
) : ViewModel() {

    lateinit var adapter: CommentAdapter
    private val TAG = "LiveData_Comments"

    class Factory(val activity: PostActivity) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LiveData_Comments(activity) as T
        }
    }

    val comments: MutableLiveData<ArrayList<Comment>> by lazy {
        MutableLiveData<ArrayList<Comment>>()
    }

    fun onCreate() {
        adapter = CommentAdapter(activity, activity.post ,ArrayList(),object : CommentAdapter.GoodListener{
            override fun pressed(comment: Comment) {
                val uid = FirebaseAuth.getInstance().currentUser?.uid
                if(uid != null){
                    commentLove(comment.commentid,comment.postid,uid)
                }else{
                    Toast("실패하였습니다.")
                }
            }
        })
        val utility = Utility(activity, activity.findViewById(R.id.commentRecycler), adapter)
        utility.RecyclerInit("VERTICAL")
    }

    fun getPost(postid: String) {
        checkNetWork()
        loadingvisible(true)

        viewModelScope.launch {
            try {
                val post = Repository.getPost(postid)
                val issuccess = post.issuccess
                val msg = post.msg

                if(issuccess){
                    post.dateFormate_for_layout = Time_to_String(post.createdat)
                    activity.binding.post = post
                    val commentList = Repository.getCommentList(post.postid)
                    for (c in commentList.commentlist)
                        c.dateFormate_for_layout = Time_to_String(c.createdat)
                    comments.value = commentList.commentlist
                    loadingvisible(false)
                }else{
                    if (msg != null) {
                        Toast(msg)
                    }
                    activity.finish()
                    loadingvisible(false)
                }
            }catch (e:Exception){
                Log.e(TAG, "onFailure getPost: ${e.message}")
            }
        }
    }

    fun addComment(
        uid: String,
        postid: String,
        commentid: String,
        content: String,
    ) {
        checkNetWork()
        loadingvisible(true)

        //getuser -> writeCom -> getPost(날짜처리) -> getCom(날짜처리)

        viewModelScope.launch{
            try {
                val user = Repository.getUser(uid)
                Repository.writeComment(postid,commentid,content,user.nickname,uid)
                val post = Repository.getPost(postid)
                val postuser = Repository.getUser(post.id)
                val issuccess = post.issuccess
                val msg = post.msg

                Log.e(TAG, "isss: $issuccess msg: $msg ")

                if(issuccess){
                    post.dateFormate_for_layout = Time_to_String(post.createdat)
                    activity.binding.post = post
                    val commentList = Repository.getCommentList(post.postid)
                    for (c in commentList.commentlist) {
                        c.dateFormate_for_layout = Time_to_String(c.createdat)
                    }
                    comments.value = commentList.commentlist
                    if(uid != postuser.id)
                        fcm.sendNotification(postuser.token,"댓글이 달렸어요!",content,post)
                    loadingvisible(false)
                }else{
                    if (msg != null) {
                        Toast(msg)
                    }
                    activity.finish()
                    loadingvisible(false)
                }
            }catch (e: Exception){
                Log.e(TAG, "onFailure addComment: ${e.message}")
            }
        }
    }

    fun getComment(postid: String) {
        checkNetWork()

        viewModelScope.launch {
            try {
                val commentList = Repository.getCommentList(postid)
                for (comment in commentList.commentlist)
                    comment.dateFormate_for_layout = Time_to_String(comment.createdat)
                comments.value = commentList.commentlist
            }catch (t: Exception){
                Log.e("getComment", "onFailure: " + t.message)
            }
        }
    }

    fun commentLove(commentid: String, postid: String, id:String){
        checkNetWork()

        viewModelScope.launch {
            try {
                val apply = Repository.clove(commentid, postid, id)
                if(apply.msg != null){
                    Toast(apply.msg)
                    getPost(postid)
                }
            }catch (e:Exception){
                Log.e("clove onfailure", "err: " + e.message)
            }
        }
    }

    fun postLove(postid: String,id: String){
        checkNetWork()

        viewModelScope.launch {
            try {
                val apply = Repository.plove(postid, id)
                if(apply.msg != null){
                    Toast(apply.msg)
                    getPost(postid)
                }
            }catch (e:Exception){
                Log.e("clove onfailure", "err: " + e.message)
            }
        }

    }

    fun delPost(postid: String){
        Repository.deletePost(postid).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.body() != null)
                    Toast(response.body()!!.msg?:"")
                activity.ResultFinish(POSTDELETE)
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("delete post", "err: " + t.message)
            }

        })
    }

    fun delComment(comment: Comment){
        Repository.deleteComment(comment.commentid,comment.postid).enqueue(object : Callback<Comment>{
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if (response.isSuccessful && response.body() != null){
                    Toast(response.body()!!.msg)
                    getPost(comment.postid)
                }
            }
            override fun onFailure(call: Call<Comment>, t: Throwable) {
                Log.e("delComment","fail: " + t.message)
            }

        })
    }

    fun loadingvisible(visibiltity: Boolean) {
        if (visibiltity)
            (activity as PostActivity).binding.postLoadingview.loaderLyaout.visibility = View.VISIBLE
        else
            (activity as PostActivity).binding.postLoadingview.loaderLyaout.visibility = View.GONE
    }

    fun checkNetWork() {
        if (!NetworkStatus.isConnected(activity)) {
            Log.e("main_network", "network is disconnected")
            (activity as PostActivity).run {
                Handler(Looper.getMainLooper()).postDelayed({ toast("인터넷 연결이 되어있지 않습니다.") }, 0)
            }
            return
        }
    }

    fun Toast(str: String) {
        android.widget.Toast.makeText(activity, str, android.widget.Toast.LENGTH_SHORT).show();
    }

}
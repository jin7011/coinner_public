package com.coinner.coin_kotlin.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.admob.MyApplication
import com.coinner.coin_kotlin.databinding.ActivityPostBinding
import com.coinner.coin_kotlin.info.Post
import com.coinner.coin_kotlin.model.PreferenceManager
import com.coinner.coin_kotlin.model.Repository
import com.coinner.coin_kotlin.utility.Named.POSTACTIVITY
import com.coinner.coin_kotlin.viewmodel.LiveData_Comments
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class PostActivity : AppCompatActivity() {

    private val activity  = this
    private val auth = FirebaseAuth.getInstance().currentUser
    lateinit var binding: ActivityPostBinding
    lateinit var post:Post
    lateinit var livedataComment: LiveData_Comments
    private val TAG = "PostActivity"

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.e(TAG,"onNewIntent")
        try{
            val fcmPost = intent?.getParcelableExtra<Post>("fcmPost")
            if(fcmPost != null){
                post = fcmPost
                setView(this)
            }
        } catch (e:Exception){
            Log.e(TAG,e.message+"")
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG,"onRestart")
        (application as MyApplication).getAdManager().run {
            if (isTimetoAd)
                showAdIfAvailable()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG,"onCreate")
        setFcmPost()
        setView(this)

        binding.AddCommentBtn.setOnClickListener {
            auth?.run {
                val content = binding.AddCommentT.text.toString()
                val commentid = post.postid + Date().time

                if (content.isNotEmpty()) {
                    livedataComment.addComment(auth.uid,post.postid, commentid, content)
                }else{
                    toast("내용을 적어주세요.")
                }
            }
        }

        binding.goodBtnFrame.setOnClickListener {
            if (!auth?.uid.isNullOrEmpty()) {
                livedataComment.postLove(post.postid, auth?.uid!!)
            }
            else {
                toast("로그인 후에 이용가능합니다..")
            }
        }
    }

    private fun setView(activity: PostActivity) {
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getBooleanExtra("notice",false).let {
            if(it)
                binding.commentFrame.visibility = View.GONE
            else
                binding.commentFrame.visibility = View.VISIBLE
        }
        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            binding.AddCommentBtn.isEnabled = false
            binding.AddCommentT.run {
                isEnabled = false
                background = ContextCompat.getDrawable(activity, R.drawable.corner_dark)
                setHintTextColor(ContextCompat.getColor(activity, R.color.white))
                hint = " 로그인이 필요합니다."
            }
        }

        toolbar()
        binding.post = post

        livedataComment =
            ViewModelProvider(this, LiveData_Comments.Factory(this))[LiveData_Comments::class.java]
        livedataComment.run {
            onCreate()
            getComment(post.postid)
            livedataComment.comments.observe(activity, Observer {
                livedataComment.adapter.commentDiffUtil(it)
                textclear()
            })
        }
    }

    fun setFcmPost(){
        val fcmPost = intent.extras?.getParcelable<Post>("fcmPost")
        Log.e(TAG,"fcmPost: $fcmPost")
        if(fcmPost != null){
            post = fcmPost
        }else{
            post = intent.extras?.getParcelable<Post>("post")!!
            Log.e(TAG,"post: $post")
        }
    }

    fun toolbar() {
        setSupportActionBar(findViewById(R.id.toolbar_post))
        val actionbar = supportActionBar
        actionbar?.run {
            setDisplayShowCustomEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (auth != null && auth.uid == post.id)
            menuInflater.inflate(R.menu.mypost_menu, menu)
        else
            menuInflater.inflate(R.menu.otherspost_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.delete -> {
               livedataComment.delPost(post.postid)
            }

            R.id.autonew -> {
                item.isEnabled = false

                livedataComment.getPost(post.postid)
                toast("게시물을 불러옵니다.")

                Handler(Looper.getMainLooper()).postDelayed({
                    item.isEnabled = true
                }, 2000)
            }

            R.id.submission -> {
                CoroutineScope(Dispatchers.Main).launch {
                    val res = Repository.submission(post.postid,PreferenceManager.getString(activity,"id")!!)
                    if(res)
                        toast("신고되었습니다.\n(신고 누적시 관리자판단에 따라 삭제 처리됩니다.)")
                    else
                        toast("이미 신고하였습니다.")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun textclear() {
        binding.AddCommentT.text.clear()

        (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(binding.AddCommentT.windowToken, 0)
    }

    override fun onBackPressed() {
        ResultFinish(POSTACTIVITY)
    }

    fun ResultFinish(response:Int){
        setResult(response,Intent().putExtra("postid",post.postid))
        finish()
    }

    fun toast(str: String) {
        android.widget.Toast.makeText(this, str, android.widget.Toast.LENGTH_SHORT).show();
    }

}
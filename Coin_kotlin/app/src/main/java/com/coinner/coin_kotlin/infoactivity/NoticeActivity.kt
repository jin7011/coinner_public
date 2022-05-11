package com.coinner.coin_kotlin.infoactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.databinding.ActivityNoticeBinding
import com.coinner.coin_kotlin.viewmodel.LiveData_Posts

class NoticeActivity : AppCompatActivity() {

    lateinit var binding:ActivityNoticeBinding
    lateinit var liveNoticePosts: LiveData_Posts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
    }

    fun setView(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_notice)

        liveNoticePosts = ViewModelProvider(this, LiveData_Posts.Factory(this))[LiveData_Posts::class.java]
        liveNoticePosts.posts.observe(this){
            liveNoticePosts.adapter.PostDiffUtil(it)
        }
        liveNoticePosts.run {
            initRecyclerView()
            liveNoticePosts.searchPostList("notice","코이너")
        }
    }
}
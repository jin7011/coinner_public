package com.coinner.coin_kotlin.infoactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.admob.MyApplication
import com.coinner.coin_kotlin.databinding.ActivityMypostBinding
import com.coinner.coin_kotlin.info.User
import com.coinner.coin_kotlin.viewmodel.LiveData_Posts
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class MypostActivity : AppCompatActivity() {

    lateinit var binding: ActivityMypostBinding
    val user by lazy { intent.extras?.getParcelable<User>("user") }
    lateinit var livedataPosts: LiveData_Posts

    override fun onRestart() {
        super.onRestart()
        (application as MyApplication).getAdManager().run {
            if (isTimetoAd)
                showAdIfAvailable()
        }
    }

    override fun onResume() {
        super.onResume()
        livedataPosts.run{
            initRecyclerView()
            myPost(user!!.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
    }

    fun setView(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_mypost)
        MobileAds.initialize(this)
        binding.ads.run {
//            adSize = AdSize.BANNER
//            adUnitId = "ca-app-pub-3940256099942544/6300978111"
            loadAd(AdRequest.Builder().build())
        }
        toolbar()

        livedataPosts =
            ViewModelProvider(this, LiveData_Posts.Factory(this))[LiveData_Posts::class.java]
        livedataPosts.posts.observe(this, Observer {
            if(it.isNotEmpty() && it != null){
                binding.run {
                    myPostRecyclerView.visibility = View.VISIBLE
                    nothingSearch.root.visibility = View.GONE
                    livedataPosts.adapter.PostDiffUtil(it)
                }
            }else{
                binding.run {
                    myPostRecyclerView.visibility = View.GONE
                    nothingSearch.root.visibility = View.VISIBLE
                }
            }
        })
    }

    fun toolbar() {
        setSupportActionBar(findViewById(R.id.toolbar_mypost))
        val actionbar = supportActionBar
        actionbar?.run {
            setDisplayShowCustomEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
        }
    }
}
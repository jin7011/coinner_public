package com.coinner.coin_kotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.admob.MyApplication
import com.coinner.coin_kotlin.databinding.ActivitySearchBinding
import com.coinner.coin_kotlin.utility.Named.SEARCHACTIVITY
import com.coinner.coin_kotlin.utility.RxAndroidUtils
import com.coinner.coin_kotlin.viewmodel.LiveData_Posts
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding
    lateinit var livedataPostinfo: LiveData_Posts
    val coin_name: String by lazy {
        intent.extras?.getString("coin_name")!!
    }

    override fun onRestart() {
        super.onRestart()
        livedataPostinfo.searchPostList(coin_name,binding.searchET.text.toString())
        (application as MyApplication).getAdManager().run {
            if (isTimetoAd)
                showAdIfAvailable()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()

        RxAndroidUtils.getEditTextObservable(binding.searchET)
            .debounce(700, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                if (it.isNotEmpty())
                    livedataPostinfo.searchPostList(coin_name, it)
            })
    }

    fun setView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        MobileAds.initialize(this)
        binding.ads.run {
//            adSize = AdSize.BANNER
//            adUnitId = "ca-app-pub-3940256099942544/6300978111"
            loadAd(AdRequest.Builder().build())
        }

        livedataPostinfo = ViewModelProvider(this, LiveData_Posts.Factory(this))[LiveData_Posts::class.java]
        livedataPostinfo.initRecyclerView() //recyclerview adapter init
        livedataPostinfo.posts.observe(this, Observer {
            if(it.isNotEmpty()){
                binding.run {
                    searchRecyclerView.visibility = View.VISIBLE
                    nothingSearch.root.visibility = View.GONE
                    livedataPostinfo.adapter.PostDiffUtil(it)
                }
            }else{
                binding.run {
                    searchRecyclerView.visibility = View.GONE
                    nothingSearch.root.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onBackPressed() {
        setResult(SEARCHACTIVITY)
        finish()
    }

    fun Toast(str: String) {
        android.widget.Toast.makeText(this, str, android.widget.Toast.LENGTH_SHORT).show();
    }
}
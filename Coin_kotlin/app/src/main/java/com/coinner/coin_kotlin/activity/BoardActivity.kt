package com.coinner.coin_kotlin.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.admob.MyApplication
import com.coinner.coin_kotlin.databinding.ActivityBoardBinding
import com.coinner.coin_kotlin.model.PreferenceManager
import com.coinner.coin_kotlin.utility.Named.ALARMACTIVITY
import com.coinner.coin_kotlin.utility.Named.FAVORIT_LIST
import com.coinner.coin_kotlin.utility.Named.POSTACTIVITY
import com.coinner.coin_kotlin.utility.Named.POSTDELETE
import com.coinner.coin_kotlin.utility.Named.SEARCHACTIVITY
import com.coinner.coin_kotlin.utility.Named.WRITEACTIVITY
import com.coinner.coin_kotlin.viewmodel.LiveData_Posts
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth


class BoardActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance().currentUser
    lateinit var binding: ActivityBoardBinding
    val coin_name: String by lazy {
        intent.extras?.getString("coin_name")!!
    }
    private lateinit var toolbar: Toolbar
    private lateinit var livedataPostinfo: LiveData_Posts

    override fun onRestart() {
        super.onRestart()
        (application as MyApplication).getAdManager().run {
            if (isTimetoAd)
                showAdIfAvailable()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board)

        Toolbar()
        MobileAds.initialize(this)
        binding.ads.run {
            loadAd(AdRequest.Builder().build())
        }
        livedataPostinfo =
            ViewModelProvider(this, LiveData_Posts.Factory(this))[LiveData_Posts::class.java]
        livedataPostinfo.Get_Candle_Posts(coin_name)
        livedataPostinfo.posts.observe(this, Observer {
            if(it.isNotEmpty() && it != null){
                binding.run {
                    BoardRecycler.visibility = View.VISIBLE
                    nothing.root.visibility = View.GONE
                    livedataPostinfo.adapter.PostDiffUtil(it)
                }
            }else{
                binding.run {
                    BoardRecycler.visibility = View.GONE
                    nothing.root.visibility = View.VISIBLE
                }
            }
        })
    }

    fun Toolbar() {
        toolbar = findViewById(R.id.toolbar_board)
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.run {
            setDisplayShowCustomEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
//            setLogo(bitmapDrawable)
        }

        toolbar.title = coin_name
        toolbar.titleMarginStart = 3
        toolbar.setTitleTextColor(Color.WHITE)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean { //onCreateOptionsMenu 이후에 관심목록 확인 후 액션바의 별 모양 아이콘을 알맞게 바꿔줌
        val menuitem = menu?.findItem(R.id.toolbar_board_addfavorit)

        if (!PreferenceManager.getStringSet(this, FAVORIT_LIST)!!.contains(coin_name))
            menuitem?.icon = ContextCompat.getDrawable(this, R.drawable.x)
        else
            menuitem?.icon = ContextCompat.getDrawable(this, R.drawable.o)

        return super.onPrepareOptionsMenu(menu);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_board_write_post_btn -> {
                if (auth == null)
                    Toast("로그인이 필요합니다.")
                else {
                    startActivityForResult(Intent(this, WriteActivity::class.java).run {
                        putExtra("coin_name", coin_name)
                    }, WRITEACTIVITY)
                }
            }

            R.id.toolbar_board_addfavorit -> {
                if (!PreferenceManager.getStringSet(this, FAVORIT_LIST)!!.contains(coin_name)) {
                    item.icon = ContextCompat.getDrawable(this, R.drawable.o)
                    PreferenceManager.setStringSet(this, FAVORIT_LIST, coin_name)
                } else {
                    item.icon = ContextCompat.getDrawable(this, R.drawable.x)
                    PreferenceManager.removeSetElement(this, FAVORIT_LIST, coin_name)
                }
            }

            R.id.toolbar_board_search -> {
                startActivityForResult(Intent(this, SearchActivity::class.java).run {
                    putExtra("coin_name", coin_name)
                }, SEARCHACTIVITY)
            }

            R.id.toolbar_board_reset -> {
                item.isEnabled = false

                livedataPostinfo.Get_Candle_Posts(coin_name)
                Toast("새로고침")

                Handler(Looper.getMainLooper()).postDelayed({
                    item.isEnabled = true
                }, 2000)
            }

            R.id.toolbar_board_alarm -> {
                if(auth != null){
                    startActivityForResult(Intent(this, AlarmActivity::class.java).run {
                        putExtra("coin_name", coin_name)
                        putExtra("id",auth.uid)
                    }, ALARMACTIVITY)
                }else{
                    Toast("로그인 후에 이용가능합니다.")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            POSTACTIVITY -> {
                val postid = data?.getStringExtra("postid")
                if (!postid.isNullOrEmpty()){
                    livedataPostinfo.updatePost(postid)
                }
            }

            WRITEACTIVITY -> {
                livedataPostinfo.Get_Candle_Posts(coin_name)
            }

            SEARCHACTIVITY -> {
                livedataPostinfo.Get_Candle_Posts(coin_name)
            }

            POSTDELETE -> {
                val postid = data?.getStringExtra("postid")
                if (!postid.isNullOrEmpty()){
                    livedataPostinfo.delPost(postid)
                }
            }

            ALARMACTIVITY -> {
//                livedataPostinfo.Get_Candle_Posts(coin_name)
            }
        }
    }

    fun Toast(str: String) {
        android.widget.Toast.makeText(this, str, android.widget.Toast.LENGTH_SHORT).show();
    }

}
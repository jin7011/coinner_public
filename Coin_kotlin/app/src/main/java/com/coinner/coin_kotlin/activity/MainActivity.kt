package com.coinner.coin_kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.adapter.CoinAdapter
import com.coinner.coin_kotlin.admob.MyApplication
import com.coinner.coin_kotlin.data.Ticker
import com.coinner.coin_kotlin.databinding.ActivityMainBinding
import com.coinner.coin_kotlin.info.Post
import com.coinner.coin_kotlin.info.User
import com.coinner.coin_kotlin.infoactivity.InfoActivity
import com.coinner.coin_kotlin.infoactivity.NoticeActivity
import com.coinner.coin_kotlin.infoactivity.PolicyActivity
import com.coinner.coin_kotlin.infoactivity.SourceActivity
import com.coinner.coin_kotlin.model.PreferenceManager
import com.coinner.coin_kotlin.model.Repository
import com.coinner.coin_kotlin.utility.Named.FAVORIT_LIST
import com.coinner.coin_kotlin.utility.Named.SETTING_FAVORIT
import com.coinner.coin_kotlin.utility.RxAndroidUtils
import com.coinner.coin_kotlin.utility.Utility
import com.coinner.coin_kotlin.viewmodel.LiveData_TickerMap
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val auth = FirebaseAuth.getInstance()
    lateinit var user: User
    private var backKeyPressedTime: Long = 0
    private lateinit var binding: ActivityMainBinding
    private lateinit var ET_Observable_Disposable: Disposable
    private lateinit var liveData_tickerMap: LiveData_TickerMap
    private lateinit var adapter: CoinAdapter
    private var thread_all: NetworkThread? = null
    private var thread_search: NetworkThread? = null
    lateinit var post:Post
    private val TAG = "MainActivity"

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val fcmPost = intent?.getParcelableExtra<Post>("fcmPost")
        Log.e(TAG,"onNewIntent: $fcmPost")

        if(fcmPost != null){
            startActivity(Intent(this,PostActivity::class.java).apply {
                putExtra("fcmPost",fcmPost)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        auth.uid?.let { id ->
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val res_user = Repository.getUser(id)
                    if(res_user.result)
                        user = res_user
                    else
                        loginActivity()
                }catch (e:Exception){
                    Log.e(TAG, "onResume in failed: " + e.message)
                    Toast("로그인에 실패했습니다.")
                }
            }
        }
    }

    override fun onRestart() { //다시 돌아왔을 경우 마지막에 사용하던 Thread를 다시 시작
        super.onRestart()
        Log.e("onRestart", "onRestart : " + binding.searchET.text.toString())
        Set_threads(
            binding.searchET.text.toString(),
            PreferenceManager.getBoolean(this, SETTING_FAVORIT)
        )
        (application as MyApplication).getAdManager().run {
            if (isTimetoAd)
                showAdIfAvailable()
        }
    }

    override fun onPause() { //화면 밖으로 나갈 경우 모든 Thread 종료
        super.onPause()
        Log.e("onStop", "onStop")
        Interrupt_threads()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("onDestroy", "onDestroy")
        thread_search = null
        thread_all = null
        ET_Observable_Disposable.dispose() //single이고, VM에 있어서 굳이 필요한가 싶기도 함
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startFcmPost()

        liveData_tickerMap =
            ViewModelProvider(
                this,
                LiveData_TickerMap.Factory(this)
            )[LiveData_TickerMap::class.java] //VM의 LiveData를 set하면서 Adapter를 Notify할 것입니다.
        setView()

        liveData_tickerMap.coins.observe(this, Observer {
            adapter.setData(Sort(it))
        })

        ET_Observable_Disposable = //RxAndroidUtil에서 검색창의 정보가 바뀔 때마다 자동으로 api를 가져올 것입니다.
            RxAndroidUtils.getEditTextObservable(binding.searchET)
                .debounce(700, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    Set_threads(it, PreferenceManager.getBoolean(this, SETTING_FAVORIT))
                })
    }

    private fun setView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (PreferenceManager.getBoolean(this, SETTING_FAVORIT)) {
            binding.favoritBtn.background =
                ContextCompat.getDrawable(this, R.drawable.o)
        } else {
            binding.favoritBtn.background =
                ContextCompat.getDrawable(this, R.drawable.x)
        }

        binding.favoritBtn.setOnClickListener {
            if (PreferenceManager.getBoolean(
                    this,
                    SETTING_FAVORIT
                )
            ) { //관심목록만 보기 설정이 되어있다면 -> 클릭시 -> 관심목록보기 제거
                PreferenceManager.setBoolean(this, SETTING_FAVORIT, false)
                binding.favoritBtn.background =
                    ContextCompat.getDrawable(this, R.drawable.x)
                Set_threads(binding.searchET.text.toString(), false)
            } else {
                PreferenceManager.setBoolean(this, SETTING_FAVORIT, true)
                binding.favoritBtn.background =
                    ContextCompat.getDrawable(this, R.drawable.o)
                Set_threads(binding.searchET.text.toString(), true)
            }
        }

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {
                if (auth.currentUser != null) { //로그인 상태라면 navi-drawer의 header에 이름과 email을 적자
                    binding.navigationView.run {
                        menu.clear()
                        inflateMenu(R.menu.navi_usermenu)
                    }
                    val headerview = binding.navigationView.getHeaderView(0)
                    (headerview.findViewById<TextView>(R.id.name_T)).text = user.nickname
                    (headerview.findViewById<TextView>(R.id.mail_T)).text = user.mail
                } else {
                    binding.navigationView.run {
                        menu.clear()
                        inflateMenu(R.menu.navi_guestmenu)
                    }
                    val headerview = binding.navigationView.getHeaderView(0)
                    (headerview.findViewById<TextView>(R.id.name_T)).text = "로그인이 필요합니다."
                    (headerview.findViewById<TextView>(R.id.mail_T)).text = "로그인이 필요합니다."
                }
            }

            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {}
        })

        binding.navigationView.setNavigationItemSelectedListener(this)

        binding.naviFbtn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }

        adapter = CoinAdapter(this, ArrayList())

        val utility = Utility(this, binding.CoinRecyclerView, adapter) //리사이클러뷰 적용하는 것
        utility.RecyclerInit("VERTICAL")
    }

    inner class NetworkThread( //Thread에 sleep을 주어서 딜레이를 주고 그 외엔 계속 돌립니다.
        private val search_ET: String,
        private val favoritSet: HashSet<String>?
    ) : Thread() {

        var isRunning = true

        override fun run() {
            while (isRunning) {
                try {
                    Log.d("NetworkThread", "running")
                    liveData_tickerMap.Get_API(search_ET, favoritSet)
                    sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun startFcmPost(){
        val fcmPost = intent.extras?.getParcelable<Post>("fcmPost")
        Log.e(TAG,"fcmPost: $fcmPost")
        if(fcmPost != null){
            startActivity(Intent(this,PostActivity::class.java).apply {
                putExtra("fcmPost",fcmPost)
            })
        }
    }

    private fun Sort(arr: ArrayList<Ticker>): ArrayList<Ticker> { // 가져온 data를 거래금액 순으로 정렬해줍니다.
        val list: ArrayList<Ticker> = ArrayList(arr)
        list.sortByDescending { it.acc_trade_value_24H.toDouble() }
        return list
    }

    fun Interrupt_threads() { //Thread 중단

        thread_all = thread_all?.run {
            this.isRunning = false
            if (!this.isInterrupted)
                this.interrupt()

            null
        }

        thread_search = thread_search?.run {
            this.isRunning = false
            if (!this.isInterrupted)
                this.interrupt()

            null
        }
        Log.d("search", "thread_all thread is null? : " + thread_all?.run { "false" })
        Log.d("search", "thread_search thread is null? : " + thread_search?.run { "false" })
    }

    private fun Set_threads(search: String, isFavorit: Boolean) { //Thread 시작

        if (isFavorit) { //관심목록만 보길 원할 때
            runThread(search, PreferenceManager.getStringSet(this, FAVORIT_LIST) as HashSet<String>)
        } else { //관심목록이 아닌 전체목록을 볼 때
            runThread(search, null)
        }
    }

    fun runThread(search: String, favoritSet: HashSet<String>?) {
        if (search.length >= 2) {
            Log.d("search", "search something")
            Interrupt_threads()

            liveData_tickerMap.disposable?.run {
                this.dispose()
            }

            if (thread_all == null) {
                thread_search = NetworkThread(search, favoritSet).apply {
                    Log.d("search", "thread_search thread is starting: $search")
                    this.start()
                }
            }

        } else if (search.isEmpty()) {
            Log.d("search", "no search")
            Interrupt_threads()

            liveData_tickerMap.disposable?.run {
                this.dispose()
            }

            if (thread_search == null) {
                thread_all = NetworkThread(search, favoritSet).apply {
                    Log.d("search", "thread_all thread is starting")
                    this.start()
                }
            }
        } else {
            Toast.makeText(this, "\"2글자 이상 입력해주세요.\"", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_logout -> {
                Toast("logout")
                if (auth.currentUser != null) {
                    val opt =
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                    val client = GoogleSignIn.getClient(this, opt)
                    client.signOut()
                    auth.signOut()
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                } else {
                    Toast("로그인이 되어있지 않습니다.")
                }
            }

            R.id.item_login -> {
                loginActivity()
            }

            R.id.item_info -> {
                startActivity(Intent(this, InfoActivity::class.java))
            }

            R.id.item_notice -> {
                startActivity(Intent(this,NoticeActivity::class.java))
            }

            R.id.item_private -> {
                startActivity(Intent(this,PolicyActivity::class.java).apply {
                    putExtra("policy","private")
                })
            }

            R.id.item_policy -> {
                startActivity(Intent(this,PolicyActivity::class.java).apply {
                    putExtra("policy","policy")
                })
            }

            R.id.item_source -> {
                startActivity(Intent(this,SourceActivity::class.java))
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.END)
        return true
    }

    private fun loginActivity() {
        startActivity(Intent(this, LoginActivity::class.java).run {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        })
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
            return
        }

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
        if (System.currentTimeMillis() > backKeyPressedTime + 1500) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast("\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.")
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 1500) {
            //아래 3줄은 프로세스 종료
//            moveTaskToBack(true)
//            android.os.Process.killProcess(android.os.Process.myPid())
//            exitProcess(1)
            finish()
        }
    }

    fun Toast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}
package com.coinner.coin_kotlin.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.coinner.coin_kotlin.admob.MyApplication
import com.coinner.coin_kotlin.databinding.ActivityWriteBinding
import com.coinner.coin_kotlin.info.User
import com.coinner.coin_kotlin.model.Repository
import com.coinner.coin_kotlin.utility.Named.WRITEACTIVITY
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class WriteActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance().currentUser
    lateinit var user: User
    private var backKeyPressedTime: Long = 0
    private val coin_name: String by lazy {
        intent.extras?.getString("coin_name")!!
    }
    private lateinit var binding: ActivityWriteBinding
    private val TAG = "WriteActivity"

    override fun onRestart() {
        super.onRestart()
        (application as MyApplication).getAdManager().run {
            if (isTimetoAd)
                showAdIfAvailable()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.writeToolbar.postBtn.setOnClickListener {
            binding.writeLoadingview.loaderLyaout.visibility = View.VISIBLE
            upload()
        }
    }

    fun upload() {
        auth?.run {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val user = Repository.getUser(uid)
                    if(user.result){
                        val postid = Date().time.toString() + user.id
                        val title = binding.titleEdit.text.toString()
                        val content = binding.contentEdit.text.toString()
                        val apply = Repository.writePost(postid,title,content,user.nickname,user.id,coin_name)
                        if (apply.issuccess){
                            apply.msg?.let { Toast(it) }
                            setResultAndFinish()
                        }else
                            Toast("???????????? ??????????????????.")
                    }else{
                        Toast(user.msg)
                    }
                    binding.writeLoadingview.loaderLyaout.visibility = View.GONE
                }catch (e:Exception){
                    Log.e(TAG, "onResume in failed: " + e.message)
                    Toast("???????????? ??????????????????.")
                    binding.writeLoadingview.loaderLyaout.visibility = View.GONE
                }
            }
        }
    }

    override fun onBackPressed() {
        // ??????????????? ???????????? ????????? ????????? ?????? ??????
        if (System.currentTimeMillis() > backKeyPressedTime + 1500) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast("\'??????\' ????????? ?????? ??? ???????????? \'?????????\'??? ???????????????.")
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 1500) {
            //?????? 3?????? ???????????? ??????
            finish()
        }
    }

    fun setResultAndFinish(){
        setResult(WRITEACTIVITY)
        finish()
    }

    fun Toast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
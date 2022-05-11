package com.coinner.coin_kotlin.infoactivity

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.databinding.ActivityChangeNicknameBinding
import com.coinner.coin_kotlin.info.User
import com.coinner.coin_kotlin.model.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ChangeNicknameActivity : Activity() {

    lateinit var binding: ActivityChangeNicknameBinding
    val user by lazy { intent.extras?.getParcelable<User>("user") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
    }

    fun setView() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_nickname)

        binding.myNickT.run {
            val nick = user?.nickname!!
            text = parseNick(nick)
            requestFocus()
        }

        binding.ChangeNickBtn.setOnClickListener {

            if (user != null && user?.id != null) {
                var tag = ""
                for(i in 0..1){
                    tag+=user!!.id[i]
                }

                if(!isNickPossible(binding.changeNickET.text.toString())){
                    Toast("닉네임을 확인해주세요.")
                    return@setOnClickListener
                }

                val nick = binding.changeNickET.text.toString().replace(" ","") + " (#" + tag + ")"

                Repository.updateNick(user!!.id, nick).enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful && response.body() != null) {
                            Toast("변경되었습니다.")
                            finish()
                        }
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast("실패하였습니다.")
                    }
                })
            } else {
                Toast("회원정보가 확인되지 않습니다.")
            }
        }

        binding.CancelChangeNickBtn.setOnClickListener {
            finish()
        }
    }

    fun parseNick(nick:String):String{
        val tocken = StringTokenizer(nick)
        return tocken.nextToken()
    }

    fun isNickPossible(nick: String):Boolean{
        val n = nick.replace(" ","")
        val result = n.matches("[0-9|a-z|A-Z|가-힝| ]*".toRegex())
//        Toast(n)
        return result
    }

    fun Toast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
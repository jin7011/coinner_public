package com.coinner.coin_kotlin.infoactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.databinding.ActivityAlarmSettingBinding
import com.coinner.coin_kotlin.model.PreferenceManager

class AlarmSettingActivity : AppCompatActivity() {

    lateinit var binding:ActivityAlarmSettingBinding
    private val TAG = "AlarmSettingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
    }

    private fun setView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_alarm_setting)

        binding.run {
//            allNotiChek.run {
//                isChecked = getAllNoti()
//                setOnCheckedChangeListener { _, isChecked ->
//                    setAllNoti(isChecked)
//                    Log.e(TAG,"$isChecked ${getAllNoti()}")
//                }
//            }
            postNotiChek.run {
                isChecked = getPostNoti()
                setOnCheckedChangeListener { _, isChecked ->
                    setPostNoti(isChecked)
                    Log.e(TAG,"$isChecked ${getPostNoti()}")
                }
            }
            coinNotiChek.run {
                isChecked = getCoinNoti()
                setOnCheckedChangeListener { _, isChecked ->
                    setCoinNoti(isChecked)
                    Log.e(TAG,"$isChecked ${getCoinNoti()}")
                }
            }
        }
    }

    fun getAllNoti(): Boolean{
        return PreferenceManager.getNoti(this,"allNoti")
    }

    fun setAllNoti(boolean: Boolean){
        PreferenceManager.setNoti(this,"allNoti",boolean)
    }

    fun getPostNoti():Boolean{
        return PreferenceManager.getNoti(this,"postNoti")
    }

    fun setPostNoti(boolean: Boolean){
        PreferenceManager.setNoti(this,"postNoti",boolean)
    }

    fun getCoinNoti():Boolean{
        return PreferenceManager.getNoti(this,"coinNoti")
    }

    fun setCoinNoti(boolean: Boolean){
        PreferenceManager.setNoti(this,"coinNoti",boolean)
    }
}
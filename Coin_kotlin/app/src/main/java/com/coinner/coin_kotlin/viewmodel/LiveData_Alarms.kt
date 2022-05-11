package com.coinner.coin_kotlin.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.activity.AlarmActivity
import com.coinner.coin_kotlin.activity.PostActivity
import com.coinner.coin_kotlin.adapter.AlarmAdapter
import com.coinner.coin_kotlin.info.Alarm
import com.coinner.coin_kotlin.model.Repository
import com.coinner.coin_kotlin.utility.NetworkStatus
import com.coinner.coin_kotlin.utility.Utility
import kotlinx.coroutines.launch

class LiveData_Alarms(
    val activity: AlarmActivity
) : ViewModel() {
    lateinit var adapter: AlarmAdapter
    private val TAG = "LiveData_Alarms"

    class Factory(val activity: AlarmActivity) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LiveData_Alarms(activity) as T
        }
    }

    val alarms: MutableLiveData<ArrayList<Alarm>> by lazy {
        MutableLiveData<ArrayList<Alarm>>()
    }

    fun onCreate() {
        adapter = AlarmAdapter(activity, ArrayList<Alarm>())
        val utility = Utility(activity, activity.findViewById(R.id.alarmRecycler), adapter)
        utility.RecyclerInit("VERTICAL")
    }

    fun addAlarm(price:String,id:String,coin:String){
        checkNetWork()

        viewModelScope.launch {
            val apply = Repository.addAlarm(price, id, coin)
            activity.Toast(apply.msg?:"실패했습니다.")
            getAlarms(id,coin)
        }

    }

    fun getAlarms(id:String,coin:String){
        checkNetWork()

        viewModelScope.launch {
            val newAlarms = Repository.getAlarms(id, coin).Alarmlist
            alarms.value = newAlarms
        }
    }

    fun delAlarm(price:String,id:String,coin:String){
        checkNetWork()

        viewModelScope.launch {
            Repository.delAlarm(price,id, coin)
            getAlarms(id,coin)
        }
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
}
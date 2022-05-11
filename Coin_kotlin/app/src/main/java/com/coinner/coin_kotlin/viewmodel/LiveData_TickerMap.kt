package com.coinner.coin_kotlin.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coinner.coin_kotlin.activity.MainActivity
import com.coinner.coin_kotlin.data.NameMap
import com.coinner.coin_kotlin.data.Ticker
import com.coinner.coin_kotlin.data.Ticker_Response
import com.coinner.coin_kotlin.model.Repository
import com.coinner.coin_kotlin.utility.NetworkStatus
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class LiveData_TickerMap(val activity: MainActivity):ViewModel(){

    class Factory(val activity: MainActivity) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LiveData_TickerMap(activity) as T
        }
    }

    var disposable: Disposable? = null
    val coins: MutableLiveData<ArrayList<Ticker>> by lazy {
        MutableLiveData<ArrayList<Ticker>>()
    }
    val single: Single<Ticker_Response> = Repository.get_Ticker("ALL")
    private val TAG = "LiveData_TickerMap"

    fun Get_API(search_str:String, favoritSet:HashSet<String>?){

        if (!NetworkStatus.isConnected(activity)){
            Log.e("main_network","network is disconnected")
            (activity as MainActivity).run {
                Handler(Looper.getMainLooper()).postDelayed({ Toast("인터넷 연결이 되어있지 않습니다.") },0)
                Interrupt_threads()
            }
            return
        }

        disposable = single
            .retryWhen{ e:Flowable<Throwable> -> //에러시에 1초단위로 100번까지 재시도
                val counter = AtomicInteger()
                return@retryWhen e
                    .takeWhile{e->counter.getAndIncrement() != 100}
                    .flatMap { e->
                        return@flatMap Flowable.timer(
                            counter.get().toLong(),
                            TimeUnit.SECONDS
                        )
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Consumer {

                val arr = ArrayList<Ticker>()

//                Log.d("accept", "search_str: $search_str")
//                Log.d("accept", "data.size: "+it.data.size)

                //todo  빗썸 점검중일 때 처리방법 생각해야함.
//                if(it.data.isNullOrEmpty()){
//                    activity.Toast("점검 중입니다..")
//                    return@Consumer
//                }

                for(entry in it.data){

                    val name = entry.key!! //ex) ETH,BTC
                    val obj = entry.value!! //ticker
                    val ko_name = NameMap.nameEn_To_Ko[name]?:name

                    if(favoritSet != null){ //관심목록을 볼 때
                        if(name != "date" && favoritSet.contains(name)){ //api 마지막에 껴있는 불필요한 정보 date 제외,관심목록에 있는 데이터만
                            if( (search_str.length >= 2 && (name.contains(search_str,ignoreCase = true)|| ko_name.contains(search_str)))
                                    || search_str.isEmpty()){

                                val gson = Gson()
                                val json = gson.toJson(obj)
                                val ticker = gson.fromJson(json,Ticker::class.java)
                                ticker.name = name
                                ticker.sub_name = name

                                arr.add(ticker)
                            }
                        }
                    }else{ //전체목록을 볼 때
                        if(name != "date"){ //api 마지막에 껴있는 불필요한 정보 date 제외
                            if( (search_str.length >= 2 && (name.contains(search_str,ignoreCase = true)|| ko_name.contains(search_str)))
                                    || search_str.isEmpty()){

                                val gson = Gson()
                                val json = gson.toJson(obj)
                                val ticker = gson.fromJson(json,Ticker::class.java)
                                ticker.name = name
                                ticker.sub_name = name

                                arr.add(ticker)
                            }
                        }
                    }
                }
                Log.d("accept", "data.size(): " + arr.size)
                coins.value = arr
            })
    }
}
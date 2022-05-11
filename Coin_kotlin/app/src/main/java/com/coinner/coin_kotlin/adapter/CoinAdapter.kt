package com.coinner.coin_kotlin.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.activity.BoardActivity
import com.coinner.coin_kotlin.data.NameMap
import com.coinner.coin_kotlin.data.Ticker
import com.coinner.coin_kotlin.utility.Coin_DiffUtil
import com.coinner.coin_kotlin.utility.Named.PRICE_HIGH
import com.coinner.coin_kotlin.utility.Named.PRICE_UNDER
import com.coinner.coin_kotlin.utility.Named.RATE_HIGH
import com.coinner.coin_kotlin.utility.Named.RATE_UNDER
import com.google.firebase.auth.FirebaseAuth

class CoinAdapter (
    val activity: Activity,
    val coins: ArrayList<Ticker>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun CoinDiffUtil(newCoins:ArrayList<Ticker>){
        Log.d("DIFF", "old size: ${coins.size} new size: ${newCoins.size}".trimIndent())

        val diffCallback = Coin_DiffUtil(this.coins,newCoins)
        val diffResult:DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)

        coins.clear()
        coins.addAll(newCoins)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setData(newCoins:ArrayList<Ticker>){
       if(issame(newCoins)){ //기존의 리스트와 목록이 같다면
//           Log.e("setdata","same")
           notify(newCoins)
           coins.clear()
           coins.addAll(newCoins)
       }
       else{ //검색 또는 관심목록으로 인해 새로운 리스트가 들어왔다면
//           Log.e("setdata","diff")
           coins.clear()
           coins.addAll(newCoins)
           notifyDataSetChanged()
       }
    }

    fun issame(newCoins:ArrayList<Ticker>):Boolean{
        if(newCoins.size != coins.size)
            return false

        val set_new = HashSet<String>()
        val set_old = HashSet<String>()

        for(i in 0 until newCoins.size){
            set_new.add(newCoins[i].name)
            set_old.add(coins[i].name)
        }

        if(set_new.equals(set_old))
            return true
        else
            return true

    }

    fun notify(newCoins:ArrayList<Ticker>){
        for(i in 0 until coins.size){
            val ticker_old = coins[i]
            val ticker_new = newCoins[i]

            if(ticker_old.name.equals(ticker_new.name)){
                val price_new = ticker_new.closing_price
                val price_old = ticker_old.closing_price
                val rate_new = ticker_new.fluctate_rate_24H
                val rate_old = ticker_old.fluctate_rate_24H
//                val total_new = ticker_new.acc_trade_value_24H
//                val total_old = ticker_old.acc_trade_value_24H

                if(!price_new.equals(price_old)){
                    if(price_old.toDouble() < price_new.toDouble()){
                        notifyItemChanged(i, PRICE_HIGH)
                    }else if(price_old.toDouble() > price_new.toDouble()){
                        notifyItemChanged(i, PRICE_UNDER)
                    }
                }

                if(!rate_new.equals(rate_old)){
                    if(rate_old.toDouble() < rate_new.toDouble()){
                        notifyItemChanged(i, RATE_HIGH)
                    }else if(rate_old.toDouble() > rate_new.toDouble()){
                        notifyItemChanged(i, RATE_UNDER)
                    }
                }
            }else{
                val changedIdx = findIdx(ticker_old.name)
                if(changedIdx != -1)
                    notifyItemMoved(i,changedIdx)
            }
        }
    }

    fun findIdx(name:String):Int{
        for(i in 0 until coins.size){
            val cname = coins[i].name
            if(cname.equals(name))
                return i
        }
        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coin,parent,false)

        val holder = Coin_ViewHolder(view)

        holder.View_Layout.setOnClickListener {
//                Log.e("holder","clicked: "+holder.Name.text.toString())
            val auth = FirebaseAuth.getInstance()
            if(auth.currentUser == null){
                Toast.makeText(activity,"로그인 후 게시물 이용이 가능합니다.",Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(activity,BoardActivity::class.java)
                    .apply { putExtra("coin_name",holder.Name_sub.text.toString()) }
                activity.startActivity(intent)
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return coins.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
//        Log.e("payload", "payload is null:" + payloads.isNullOrEmpty())
        if(payloads.isNullOrEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else{
            for(payload in payloads){
                if(payload is Int && holder is Coin_ViewHolder){
                    val coin = coins[position]
                    holder.run {
                        bind(coin)
                        underline(payload)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Coin_ViewHolder).run {
            val coin = coins[position]
            bind(coin)
        }
    }

    inner class Coin_ViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        val Name:TextView = itemView.findViewById(R.id.Name_T)
        val Name_sub:TextView = itemView.findViewById(R.id.Name_sub_T)
        val Rate:TextView = itemView.findViewById(R.id.fluctate_rate_T)
        val Price:TextView = itemView.findViewById(R.id.Currency_price_T)
        val Total:TextView = itemView.findViewById(R.id.total_T)
        val View_Layout:ConstraintLayout = itemView.findViewById(R.id.Layout)

        fun underline(payload:Int){
            when(payload){
                PRICE_HIGH -> {
                    Price.background = ContextCompat.getDrawable(activity,R.drawable.underline_red)

                    Handler(Looper.getMainLooper()).postDelayed({
                        Price.background = null
                    },300)
                }

                PRICE_UNDER -> {
                    Price.background = ContextCompat.getDrawable(activity,R.drawable.underline_blue)

                    Handler(Looper.getMainLooper()).postDelayed({
                        Price.background = null
                    },300)
                }

                RATE_HIGH -> {
                    Rate.background = ContextCompat.getDrawable(activity,R.drawable.underline_red)

                    Handler(Looper.getMainLooper()).postDelayed({
                        Rate.background = null
                    },300)
                }

                RATE_UNDER -> {
                    Rate.background = ContextCompat.getDrawable(activity,R.drawable.underline_blue)

                    Handler(Looper.getMainLooper()).postDelayed({
                        Rate.background = null
                    },300)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Ticker){

            Name.text = NameMap.nameEn_To_Ko.getOrDefault(item.name,item.name)
            Name_sub.text = item.sub_name
            Rate.text = item.fluctate_rate_24H + "%"
            Price.text = item.closing_price
            Total.text = String.format("%d",(item.acc_trade_value_24H.toDouble()/1000000).toInt()) + "백만"

            if(item.fluctate_rate_24H.toDouble() > 0.0){
                Rate.setTextColor(ContextCompat.getColor(view.context,R.color.colorAccent))
                Price.setTextColor(ContextCompat.getColor(view.context,R.color.colorAccent))
            }
            else if(item.fluctate_rate_24H.toDouble() < 0.0){
                Rate.setTextColor(ContextCompat.getColor(view.context,R.color.darger_blue))
                Price.setTextColor(ContextCompat.getColor(view.context,R.color.darger_blue))
            }
            else{
                Rate.setTextColor(ContextCompat.getColor(view.context,R.color.night_white))
                Price.setTextColor(ContextCompat.getColor(view.context,R.color.night_white))
            }
        }

    }
}
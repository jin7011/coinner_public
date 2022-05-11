package com.coinner.coin_kotlin.utility

import androidx.recyclerview.widget.DiffUtil
import com.coinner.coin_kotlin.data.Ticker
import java.util.*

class Coin_DiffUtil(
    val oldcoins: ArrayList<Ticker>,
    val newcoins: ArrayList<Ticker>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldcoins.size
    }

    override fun getNewListSize(): Int {
        return newcoins.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldcoin = oldcoins[oldItemPosition]
        val newcoin = newcoins[newItemPosition]

        return oldcoin.name.equals(newcoin.name)
//        return true
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean { //item이 같아도 수정된다면 내용이 다르다는 것을 인식시켜줘야 내용이 바뀜
        val oldcoin = oldcoins[oldItemPosition]
        val newcoin = newcoins[newItemPosition]

        return oldcoin.closing_price.equals(newcoin.closing_price)
                && oldcoin.fluctate_rate_24H.equals(newcoin.fluctate_rate_24H)
                && oldcoin.acc_trade_value_24H.equals(newcoin.acc_trade_value_24H)

//        return false
    }

//    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//
//        val oldcoin = oldcoins[oldItemPosition]
//        val newcoin = newcoins[newItemPosition]
//
//        if(oldcoin.name.equals(newcoin.name)){
//            if(!oldcoin.closing_price.equals(newcoin.closing_price)) {
//                Log.e("getChangePayload", "old price: " + oldcoin.closing_price + " new price: " + newcoin.closing_price)
//                return oldcoin.name
//            }
//            return null
//        }else{
//            Log.e("getChangePayload","not same name")
//            return null
//        }
//    }

}
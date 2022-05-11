package com.coinner.coin_kotlin.utility

import android.app.Activity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

class Utility(
    val activity:Activity,
    val recyclerView:RecyclerView,
    var adapter:RecyclerView.Adapter<RecyclerView.ViewHolder>
){
    fun RecyclerInit(orientation:String){

        val layoutManager: LinearLayoutManager = LinearLayoutManager(activity)

        when(orientation){
            "VERTICAL" -> {
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
                recyclerView.layoutManager = layoutManager
            }

            "HORIZEN" -> {
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                recyclerView.layoutManager = layoutManager
            }

            "GRID" -> {
                val gridLayoutManager = GridLayoutManager(activity, 3)
                recyclerView.layoutManager = gridLayoutManager
            }
        }

//        if(adapter is Coin_Adapter){
//            adapter.setLinearLayoutManager(layoutManager)
//        }

        recyclerView.adapter = adapter
        val animator: RecyclerView.ItemAnimator? = recyclerView.itemAnimator;
        if(animator is SimpleItemAnimator){
            animator.supportsChangeAnimations = false
        }
    }

}
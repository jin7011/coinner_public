package com.coinner.coin_kotlin.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.activity.AlarmActivity
import com.coinner.coin_kotlin.info.Alarm

class AlarmAdapter(
    val activity: Activity,
    var alarms:ArrayList<Alarm>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class AlarmHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val price: TextView = itemView.findViewById(R.id.alarmPriceT)
        val deleteBtn: Button = itemView.findViewById(R.id.alarmDeleteBtn)

        fun bind(item: Alarm){
            price.text = item.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alarm, parent, false)
        val holder = AlarmHolder(view)

        holder.deleteBtn.setOnClickListener {
            val alarm = alarms[holder.absoluteAdapterPosition]
            (activity as AlarmActivity).liveDate_alarm.delAlarm(alarm.price,alarm.id,alarm.coin)
        }

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is AlarmHolder -> {
                holder.bind(alarms[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return alarms.size
    }
}
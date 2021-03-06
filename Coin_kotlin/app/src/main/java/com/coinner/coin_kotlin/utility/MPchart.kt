package com.coinner.coin_kotlin.utility

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.coinner.coin_kotlin.R
import com.coinner.coin_kotlin.activity.BoardActivity
import com.coinner.coin_kotlin.data.Candle
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MPchart(val candleStickChart: CandleStickChart,val activity:Activity) {

    private val TAG = "MPchart"

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    fun Set_priceData(candles:ArrayList<Candle>){
        val priceChart = candleStickChart
        val setcolor: (id:Int) -> Int = {
            ContextCompat.getColor(priceChart.context,it)
        }
        val LIMIT_NUM = 75
        Log.d("Set_priceData", "candles: " + candles.size)
        val candleEntries: MutableList<CandleEntry> = ArrayList()
        val date : (idx:Int) -> String = {
            val res = SimpleDateFormat("yy/MM/dd")
                .format(Date(candles[it].createdAt!!.toLong()))
            val ss = StringBuffer(res).apply {
                if(this[0] == '2' && this[1] == '2'){
                    this.delete(0,3)
                }
            }
            ss.toString()
        }

        for (x in candles.indices) {

            val entry =  CandleEntry(
                x.toFloat(),
                candles[x].high!!.toFloat(),
                candles[x].low!!.toFloat(),
                candles[x].open!!.toFloat(),
                candles[x].close!!.toFloat()
                ,date(x)
            )
            candleEntries.add(entry)
        }

        val candleDataSet = CandleDataSet(candleEntries, "????????????")

        candleDataSet.run {
            axisDependency = YAxis.AxisDependency.RIGHT
            //??????
            shadowColorSameAsCandle = true
            shadowWidth = 0.85f

            //??????
            decreasingColor = Color.BLUE
            increasingPaintStyle = Paint.Style.FILL

            //??????
            increasingColor = Color.RED
            increasingPaintStyle = Paint.Style.FILL
        }

        //x???
        priceChart.xAxis.run {
            isEnabled = true
            textColor = setcolor(R.color.night_white)
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 7f
            valueFormatter = object : ValueFormatter(){
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    if(value.toInt() <= candleEntries.size){
                        return candleEntries[value.toInt()].data.toString()
                    }
                    return super.getFormattedValue(value)
                }
            }
        }

        //????????? y???
        priceChart.axisRight.run {
            isEnabled = true
            textColor = setcolor(R.color.night_white)
            setLabelCount(5, false)
            setDrawGridLines(true) //????????? ????????????

            // ????????? ????????? ????????? ?????? ??????
            setDrawAxisLine(true)
        }

        //?????? y???
        priceChart.axisLeft.run {
            isEnabled = false
        }

        priceChart.run {
            setPinchZoom(true)
            isAutoScaleMinMaxEnabled = true
            legend.isEnabled = false
            isDragDecelerationEnabled = false

//            ?????????
            description.run {
                isEnabled = true
                text = "????????????"
                xOffset = 10f
                yOffset = 3f
                textColor = setcolor(R.color.night_white)
            }

            dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
            SystemClock.uptimeMillis(),MotionEvent.ACTION_DOWN,0f,0f,0))

            data = CandleData(candleDataSet)
            //?????? ????????? ???????????? ???????????????.
//            moveViewToX(data.entryCount.toFloat())
            setMaxVisibleValueCount(0)
            setVisibleXRange(1f, LIMIT_NUM.toFloat()) //??? ????????? ????????? ??????

            //????????? x??? label ??????
            priceChart.setOnChartValueSelectedListener(object: OnChartValueSelectedListener {
                @SuppressLint("SetTextI18n")
                override fun onValueSelected(e: Entry, h: Highlight){
                    val xAxisLabel = e.x.let{
                        priceChart.xAxis.valueFormatter.getAxisLabel(it, priceChart.xAxis)
                    }
                    val x = e.x.toInt()

                    if(activity is BoardActivity){
                        activity.binding.run {
                            chartDate.text = "??????: $xAxisLabel"
                            chartHigh.text = "??????: ${candles[x].high}"
                            chartLow.text = "??????: ${candles[x].low}"
                            chartClose.text = "??????: ${candles[x].close}"
                        }
                    }

                    Log.e(TAG,"x: $xAxisLabel, " +
                            "\nhigh: ${candles[x].high!!.toFloat()} " +
                            "\nlow: ${candles[x].low!!.toFloat()} " +
                            "\nclose: ${candles[x].close!!.toFloat()}")
                }
                override fun onNothingSelected() {
                    val idx = candles.size-1
                    val xAxisLabel = priceChart.xAxis.valueFormatter.getAxisLabel(idx.toFloat(), priceChart.xAxis)

                    if(activity is BoardActivity){
                        activity.binding.run {
                            chartDate.text = "??????: $xAxisLabel"
                            chartHigh.text = "??????: ${candles[idx].high}"
                            chartLow.text = "??????: ${candles[idx].low}"
                            chartClose.text = "??????: ${candles[idx].close}"
                        }
                    }
                }
            })

            Handler(Looper.getMainLooper()).postDelayed({
                val idx = candles.size-1
                val xAxisLabel = priceChart.xAxis.valueFormatter.getAxisLabel(idx.toFloat(), priceChart.xAxis)

                if(activity is BoardActivity){
                    activity.binding.run {
                        chartDate.text = "??????: $xAxisLabel"
                        chartHigh.text = "??????: ${candles[idx].high}"
                        chartLow.text = "??????: ${candles[idx].low}"
                        chartClose.text = "??????: ${candles[idx].close}"
                    }
                }
            }, 800)

//            postInvalidateDelayed(2000L)
//            invalidate()
//            notifyDataSetChanged()

//            Log.e("tox","x:"+(data.entryCount - LIMIT_NUM).toFloat())
//            Log.e("tox","min:"+lowestVisibleX + " max:" +highestVisibleX)

        }
    }

}
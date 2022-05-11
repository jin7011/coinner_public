package com.coinner.coin_kotlin.utility

import java.text.SimpleDateFormat
import java.util.*

object Named {
    const val VERSION = 1
    var NOTIFICATION = true

    const val HORIZEN = 400
    const val VERTICAL = 401
    const val GRID = 402
    const val LOADING_VIEWTYPE = 1919
    const val POSTING_VIEWTYPE = 1920
    const val CHART_VIEWTYPE = 1921

    const val PRICE_UNDER = 200
    const val PRICE_HIGH = 201
    const val RATE_UNDER = 202
    const val RATE_HIGH = 203

    const val SETTING_FAVORIT = "SETTING_FAVORIT"
    const val FAVORIT_LIST = "FAVORIT_LIST"

    const val POSTACTIVITY = 300
    const val WRITEACTIVITY = 301
    const val SEARCHACTIVITY = 302
    const val ALARMACTIVITY = 303

    const val BOARD_FRAGMENT = 1201
    const val NOTIFICATION_FRAGMENT = 1202
    const val LETTER_FRAGMENT = 1203
    const val PROFILE_FRAGMENT = 1204

    const val OTHER = 1111
    const val MINE = 1112
    var FIRST_BRING = false
    var NEW_MESSAGE = false

    const val POSTDELETE = 1113
    const val CHANGED = 1114
    const val SET = 1115

    const val POSTHODER_TO_POSTACTIVITY = 1001

    const val DOWN_SROLLED = 500
    const val UP_SROLLED = 501
    const val SEARCH_LIMIT = 500
    const val UPLOAD_LIMIT = 20

    const val DELETE_RESULT = 1001
    const val SOMETHING_IN_POST = 1002
    const val CHANGED_LOCATION = 1003
    const val NONE = "None"

    const val NOT_EXIST = 1101
    const val ALREADY_DONE = 1102
    const val SUCCESS = 1103

    const val WRITE_RECOMMENT = 3000
    const val GOOD_COMMENT = 3001
    const val DELETE_COMMENT = 3002
    const val GOOD_RECOMMENT = 3003
    const val DELETE_RECOMMENT = 3004

    const val SEC = 60
    const val MIN = 60
    const val HOUR = 24
    const val DAY = 30
    const val MONTH = 12

    const val POST_INIT_COMMENT = 2000
    const val POST_ADD_COMMENT = 2001

    const val VIDEO_TYPE = 4001
    const val IMAGE_TYPE = 4002

    fun Time_to_String(postdate: Date): String{

        val ctime = Date().time
        val regtime = postdate.time
        val difftime = Math.abs(ctime-regtime)/1000

        val msg: () -> String = {
            if(difftime < Named.SEC)
                "방금 전"
            else if ((difftime / Named.SEC) < Named.MIN)
                (difftime/ Named.SEC).toString() + "분 전"
            else if((difftime / Named.MIN) < Named.HOUR)
                SimpleDateFormat("HH:mm").format(postdate)
            else
                SimpleDateFormat("yyyy년MM월dd일").format(postdate)
        }

        return msg()
    }

}
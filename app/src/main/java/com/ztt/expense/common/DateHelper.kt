package com.ztt.expense.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateHelper {
    companion object {
        fun getFormatedDate(time: Long): String {
            if (time == 0L) {
                return ""
            }
            val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            return format.format(Date(time))
        }
    }
}
package com.ali.hacker_demo.extensions

import java.text.SimpleDateFormat
import java.util.*

    fun Long.toDateUtils(): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return sdf.format(calendar.timeInMillis)
    }
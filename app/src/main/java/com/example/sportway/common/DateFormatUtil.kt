package com.example.sportway.common

import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtil {
    @JvmStatic
    fun formatDateTo(date: String, patternIn: String, patternOut: String): String {
        val inputFormat = SimpleDateFormat(patternIn, Locale.getDefault())
        val outputDayFormat = SimpleDateFormat(patternOut, Locale.getDefault())
        return inputFormat.parse(date)?.let {
            outputDayFormat.format(it)
        } ?: ""
    }

    @JvmStatic
    fun formatDateToMillis(date: String, patternIn: String): Long {
        val inputFormat = SimpleDateFormat(patternIn, Locale.getDefault())
        return inputFormat.parse(date)?.time ?: 0
    }
}
package com.example.sportway.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val id: String,
    val status: String,
    val awayScore: Int,
    val awayTeam: String,
    val homeScore: Int,
    val homeTeam: String,
    val location: String,
    val matchDay: String,
    val matchHour: String,
    var details: EventDetails? = null
): Parcelable
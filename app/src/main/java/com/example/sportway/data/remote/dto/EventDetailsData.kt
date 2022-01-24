package com.example.sportway.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EventDetailsData(
    @SerializedName("__belong")
    val belong: Any,
    val availableWidgets: Any,
    val awayFormation: Any,
    val awayScore: Int,
    val awayTeam: Any,
    val eventStatus: Any,
    val homeFormation: Any,
    val homeScore: Int,
    val homeTeam: Any,
    val location: Any,
    val programmingData: Any,
    val startDate: String,
    val stats: List<StatDto>,
    val statusCategory: String,
    val timeline: Any,
    val tournament: Tournament,
    val videos: Any,
    val wilsonMetadata: Any
)
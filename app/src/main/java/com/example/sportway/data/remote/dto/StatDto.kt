package com.example.sportway.data.remote.dto

import com.example.sportway.domain.model.Stat

data class StatDto(
    val awayColor: String,
    val awayValue: Int,
    val format: Any,
    val homeColor: String,
    val homeValue: Int,
    val priority: Int,
    val title: Title,
    val id: Any? = null,
)

fun StatDto.toStat(): Stat {
    return Stat(
        title = title.original,
        awayValue = awayValue,
        homeValue = homeValue
    )
}
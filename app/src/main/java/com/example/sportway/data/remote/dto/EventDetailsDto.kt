package com.example.sportway.data.remote.dto

import com.example.sportway.domain.model.EventDetails

data class EventDetailsDto(
    val data: EventDetailsData
)

fun EventDetailsDto.toEventDetails(): EventDetails {
    with(data) {
        return EventDetails(
            tournament = tournament.name.original,
            stats = stats.map { it.toStat() }
        )
    }
}
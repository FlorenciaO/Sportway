package com.example.sportway.data.remote.dto

data class EventsResponseDto(
    val data: EventsResponseData
)

fun EventsResponseDto.toEventDtoList(): List<EventDto> {
    return data.items
}
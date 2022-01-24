package com.example.sportway.data.remote.dto

data class EventsResponseData(
    val items: List<EventDto>,
    val pagination: Any,
    val sections: Any
)
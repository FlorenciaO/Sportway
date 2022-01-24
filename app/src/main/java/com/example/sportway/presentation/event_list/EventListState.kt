package com.example.sportway.presentation.event_list

import com.example.sportway.common.ErrorCode
import com.example.sportway.domain.model.Event

data class EventListState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val error: ErrorCode? = null
)

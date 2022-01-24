package com.example.sportway.presentation.event_details

import com.example.sportway.common.ErrorCode
import com.example.sportway.domain.model.Event

data class EventDetailsState(
    val isLoading: Boolean = false,
    val event: Event? = null,
    val error: ErrorCode? = null
)

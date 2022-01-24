package com.example.sportway.presentation.event_list

import com.example.sportway.domain.model.Event

// if we had more ui events, we should add a new class extended the sealed class
// e.g. moveEventToPosition (if it were a requirement)
sealed class EventListUIEvent {
    data class onEventClicked(val event: Event): EventListUIEvent()
}

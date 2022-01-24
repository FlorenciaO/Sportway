package com.example.sportway.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventDetails(
    val tournament: String,
    val stats: List<Stat>
): Parcelable

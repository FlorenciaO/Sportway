package com.example.sportway.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stat(
    val awayValue: Int,
    val homeValue: Int,
    val title: String
): Parcelable
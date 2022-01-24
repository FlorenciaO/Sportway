package com.example.sportway.data.remote.dto

import com.example.sportway.common.Constants.DATE_INPUT_PATTERN
import com.example.sportway.common.Constants.DATE_PATTERN
import com.example.sportway.common.Constants.HOUR_PATTERN
import com.example.sportway.common.DateFormatUtil
import com.example.sportway.domain.model.Event

data class EventDto(
    val __belong: Any,
    val __children: Any,
    val _createDate: String,
    val _deleted: Boolean,
    val _externalId: String,
    val _externalProvider: String,
    val _id: String,
    val _model: String,
    val _seed: Boolean,
    val _updateDate: String,
    val awayScore: Int,
    val awayTeam: TeamDto,
    val cellType: Int,
    val endDate: String,
    val eventStatus: Any,
    val homeScore: Int,
    val homeTeam: TeamDto,
    val id: String,
    val location: Location,
    val matchDay: MatchDayDto,
    val matchTime: Int,
    val startDate: String,
    val stats: List<StatDto>,
    val statusCategory: String,
    val statusDate: String,
    val type: String,
    val videos: Any,
    val programmingData: Any,
    val homePenalties: Any? = null,
    val awayPenalties: Any? = null
)

fun EventDto.toEvent(): Event {
    val status = when (statusCategory.uppercase()) {
        "FINISHED" -> "Finalizado"
        "LIVE" -> "En Juego"
        "FUTURE" -> "Pendiente"
        else -> ""
    }

    return Event(
        id = id,
        status = status,
        awayScore = awayScore,
        homeScore = homeScore,
        awayTeam = awayTeam.name,
        homeTeam = homeTeam.name,
        location = location.original,
        matchDay = DateFormatUtil.formatDateTo(matchDay.start, DATE_INPUT_PATTERN, DATE_PATTERN),
        matchHour = DateFormatUtil.formatDateTo(matchDay.start, DATE_INPUT_PATTERN, HOUR_PATTERN)
    )
}
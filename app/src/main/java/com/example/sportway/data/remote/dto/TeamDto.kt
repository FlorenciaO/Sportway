package com.example.sportway.data.remote.dto

import com.example.sportway.domain.model.Team

data class TeamDto(
    val id: String,
    val name: String,
    val shortName: String,
    val __belong: Any? = null,
    val _createDate: String? = null,
    val _deleted: Boolean? = null,
    val _externalId: String? = null,
    val _externalProvider: String? = null,
    val _id: String? = null,
    val _model: String? = null,
    val _seed: Boolean? = null,
    val _updateDate: String? = null,
    val country: Any? = null,
    val tournaments: Any? = null
)

fun TeamDto.toTeam(): Team {
    return Team(id, name, shortName)
}
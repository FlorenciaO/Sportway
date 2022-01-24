package com.example.sportway.data.remote.dto

data class TeamsResponseDto(
    val data: TeamsResponseData
)

fun TeamsResponseDto.toTeamDtoList(): List<TeamDto> {
    return data.items
}
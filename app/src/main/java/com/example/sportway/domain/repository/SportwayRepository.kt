package com.example.sportway.domain.repository

import com.example.sportway.data.local.AccessToken
import com.example.sportway.data.remote.dto.EventDetailsDto
import com.example.sportway.data.remote.dto.EventDto
import com.example.sportway.data.remote.dto.TeamDto
import com.example.sportway.domain.model.LoginRequest

interface SportwayRepository {
    suspend fun getAccessToken(loginRequest: LoginRequest): AccessToken
    suspend fun getEvents(accessToken: String): List<EventDto>
    suspend fun getEventDetail(accessToken: String, eventId: String): EventDetailsDto
    suspend fun getTeams(accessToken: String): List<TeamDto>
}
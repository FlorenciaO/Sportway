package com.example.sportway.data.repository

import com.example.sportway.common.LoginRequestUtil
import com.example.sportway.data.local.AccessToken
import com.example.sportway.data.local.SportwayLocalDataSource
import com.example.sportway.data.remote.SportwayApi
import com.example.sportway.data.remote.dto.*
import com.example.sportway.domain.model.LoginRequest
import com.example.sportway.domain.repository.SportwayRepository
import javax.inject.Inject

class SportwayRepositoryImpl @Inject constructor(
    private val api: SportwayApi,
    private val db: SportwayLocalDataSource
) : SportwayRepository {

    override suspend fun getAccessToken(loginRequest: LoginRequest): AccessToken {
        return db.getAccessToken() ?: getRefreshedAccessToken(loginRequest)
    }

    private suspend fun getRefreshedAccessToken(loginRequest: LoginRequest): AccessToken {
        return api.getAccessToken(LoginRequestUtil.createLoginRequestDto(loginRequest))
            .toAccessToken().also {
            db.saveAccessToken(it)
        }
    }

    override suspend fun getEvents(accessToken: String): List<EventDto> {
        return api.getEvents(accessToken).toEventDtoList()
    }

    override suspend fun getEventDetail(accessToken: String, eventId: String): EventDetailsDto {
        return api.getEventDetails(accessToken, eventId)
    }

    override suspend fun getTeams(accessToken: String): List<TeamDto> {
        return api.getTeams(accessToken).toTeamDtoList()
    }
}
package com.example.sportway.data.remote

import com.example.sportway.BuildConfig
import com.example.sportway.data.remote.dto.*
import retrofit2.http.*

interface SportwayApi {

    @POST("/api/1.0/auth/users/login/anonymous")
    @Headers("Content-Type: application/json")
    suspend fun getAccessToken(
        @Body body: LoginRequestDto,
        @Header("Authorization") basicToken: String = BuildConfig.BASIC_AUTH_TOKEN
    ): AccessTokenDto

    @GET("/api/1.0/sport/events")
    suspend fun getEvents(@Header("Authorization") accessToken: String): EventsResponseDto

    @GET("/api/1.0/sport/events/{eventId}/details")
    suspend fun getEventDetails(
        @Header("Authorization") accessToken: String,
        @Path("eventId") eventId: String
    ): EventDetailsDto

    @GET("/api/1.0/sport/teams")
    suspend fun getTeams(@Header("Authorization") accessToken: String): TeamsResponseDto
}
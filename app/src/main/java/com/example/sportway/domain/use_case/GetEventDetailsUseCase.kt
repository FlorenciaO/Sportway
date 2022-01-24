package com.example.sportway.domain.use_case

import com.example.sportway.common.ErrorCode
import com.example.sportway.common.Resource
import com.example.sportway.data.remote.dto.toEventDetails
import com.example.sportway.domain.model.EventDetails
import com.example.sportway.domain.repository.SportwayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetEventDetailsUseCase @Inject constructor(
    private val repo: SportwayRepository
) {
    operator fun invoke(accessToken: String, eventId: String): Flow<Resource<EventDetails>> = flow {
        try {
            emit(Resource.Loading())
            val eventDto = repo.getEventDetail("Bearer $accessToken", eventId)
            emit(Resource.Success(eventDto.toEventDetails()))
        } catch (e: HttpException) {
            emit(Resource.Error(ErrorCode.UNEXPECTED_ERROR))
        } catch (e: IOException) {
            emit(Resource.Error(ErrorCode.CONNECTION_ERROR))
        }
    }
}
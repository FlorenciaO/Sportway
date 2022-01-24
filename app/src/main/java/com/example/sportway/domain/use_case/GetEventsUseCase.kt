package com.example.sportway.domain.use_case

import com.example.sportway.common.ErrorCode
import com.example.sportway.common.Resource
import com.example.sportway.data.remote.dto.toEvent
import com.example.sportway.domain.model.Event
import com.example.sportway.domain.repository.SportwayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val repo: SportwayRepository
) {
    operator fun invoke(accessToken: String): Flow<Resource<List<Event>>> = flow {
        try {
            emit(Resource.Loading())
            val events = repo.getEvents("Bearer $accessToken").map { it.toEvent() }
            emit(Resource.Success(events))
        } catch (e: HttpException) {
            emit(Resource.Error(ErrorCode.UNEXPECTED_ERROR))
        } catch (e: IOException) {
            emit(Resource.Error(ErrorCode.CONNECTION_ERROR))
        }
    }
}
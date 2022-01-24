package com.example.sportway.domain.use_case

import com.example.sportway.common.ErrorCode
import com.example.sportway.common.Resource
import com.example.sportway.data.remote.dto.toEvent
import com.example.sportway.data.remote.dto.toTeam
import com.example.sportway.domain.model.Event
import com.example.sportway.domain.model.Team
import com.example.sportway.domain.repository.SportwayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTeamsUseCase @Inject constructor(
    private val repo: SportwayRepository
) {

    operator fun invoke(accessToken: String): Flow<Resource<List<Team>>> = flow {
        try {
            emit(Resource.Loading())
            val teams = repo.getTeams("Bearer $accessToken").map { it.toTeam() }
            emit(Resource.Success(teams))
        } catch (e: HttpException) {
            emit(Resource.Error(ErrorCode.UNEXPECTED_ERROR))
        } catch (e: IOException) {
            emit(Resource.Error(ErrorCode.CONNECTION_ERROR))
        }
    }
}
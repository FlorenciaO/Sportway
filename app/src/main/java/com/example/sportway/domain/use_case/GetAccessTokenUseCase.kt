package com.example.sportway.domain.use_case

import com.example.sportway.common.ErrorCode
import com.example.sportway.common.Resource
import com.example.sportway.domain.model.LoginRequest
import com.example.sportway.domain.repository.SportwayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val repo: SportwayRepository
) {
    operator fun invoke(loginRequest: LoginRequest): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val accessToken = repo.getAccessToken(loginRequest)
            emit(Resource.Success(accessToken.token))
        } catch (e: HttpException) {
            emit(Resource.Error(ErrorCode.UNEXPECTED_ERROR))
        } catch (e: IOException) {
            emit(Resource.Error(ErrorCode.CONNECTION_ERROR))
        }
    }
}
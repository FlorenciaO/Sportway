package com.example.sportway.domain.use_case

import com.example.sportway.common.ErrorCode
import com.example.sportway.common.Resource
import com.example.sportway.data.local.AccessToken
import com.example.sportway.data.repository.SportwayRepositoryImpl
import com.example.sportway.domain.model.LoginRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException


class GetAccessTokenUseCaseTest {

    @MockK
    private lateinit var mockRepository: SportwayRepositoryImpl
    @MockK
    private lateinit var mockLoginRequest: LoginRequest
    @MockK
    private lateinit var mockAccessToken: AccessToken
    @MockK
    private lateinit var httpException: HttpException
    @MockK
    private lateinit var ioException: IOException

    private lateinit var getAccessTokenUseCase: GetAccessTokenUseCase
    private val mockToken = "test token"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getAccessTokenUseCase = GetAccessTokenUseCase(mockRepository)
    }

    @Test
    fun `emit loading first`() = runBlocking {
        val result = getAccessTokenUseCase.invoke(mockLoginRequest).first()
        Assert.assertEquals(result.javaClass, Resource.Loading<String>().javaClass)
    }

    @Test
    fun `when hhtp exception returns unexpected error code`() = runBlocking {
        coEvery { mockRepository.getAccessToken(mockLoginRequest) } throws httpException
        val result = getAccessTokenUseCase.invoke(mockLoginRequest).last()
        assertSame(ErrorCode.UNEXPECTED_ERROR, result.errorCode)
    }

    @Test
    fun `when io exception returns unexpected error code`() = runBlocking {
        coEvery { mockRepository.getAccessToken(mockLoginRequest) } throws ioException
        val result = getAccessTokenUseCase.invoke(mockLoginRequest).last()
        assertSame(ErrorCode.CONNECTION_ERROR, result.errorCode)
    }

    @Test
    fun `when no exception returns access token`() = runBlocking {
        every { mockAccessToken.token } returns mockToken
        coEvery { mockRepository.getAccessToken(mockLoginRequest) } returns  mockAccessToken
        val result = getAccessTokenUseCase.invoke(mockLoginRequest).last()
        assertSame(mockToken, result.data)
    }
}
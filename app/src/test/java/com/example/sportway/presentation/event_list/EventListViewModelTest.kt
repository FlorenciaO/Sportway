package com.example.sportway.presentation.event_list

import android.content.Context
import app.cash.turbine.test
import com.example.sportway.MainCoroutineRule
import com.example.sportway.common.ErrorCode
import com.example.sportway.common.LoginRequestUtil
import com.example.sportway.common.Resource
import com.example.sportway.domain.model.Event
import com.example.sportway.domain.model.LoginRequest
import com.example.sportway.domain.use_case.GetAccessTokenUseCase
import com.example.sportway.domain.use_case.GetEventsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EventListViewModelTest {
    @MockK
    private lateinit var getAccessTokenUseCase: GetAccessTokenUseCase
    @MockK
    private lateinit var getEventsUseCase: GetEventsUseCase
    @MockK
    private lateinit var context: Context
    @MockK
    private lateinit var loginRequest: LoginRequest
    @MockK
    private lateinit var eventsMock: List<Event>

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel: EventListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `when get access token was called, show loading`() = runBlocking {
        mockkObject(LoginRequestUtil)
        every { LoginRequestUtil.getLoginRequestInfo(any()) } returns loginRequest
        coEvery { getAccessTokenUseCase(any()) } returns flowOf(Resource.Loading())
        viewModel = EventListViewModel(getEventsUseCase, getAccessTokenUseCase, context)
        viewModel.eventListState.test {
            val firstEmission = awaitItem()
            assert(firstEmission.isLoading)
        }
    }

    @Test
    fun `when get access token was failed, inform the error`() = runBlocking {
        val errorCode = ErrorCode.CONNECTION_ERROR
        mockkObject(LoginRequestUtil)
        every { LoginRequestUtil.getLoginRequestInfo(any()) } returns loginRequest
        coEvery { getAccessTokenUseCase(any()) } returns flowOf(Resource.Error(errorCode))
        viewModel = EventListViewModel(getEventsUseCase, getAccessTokenUseCase, context)
        viewModel.eventListState.test {
            val firstEmission = awaitItem()
            assertSame(errorCode, firstEmission.error)
        }
    }

    @Test
    fun `when get access token was successful, show loading again`() = runBlocking {
        val token = "1234"
        mockkObject(LoginRequestUtil)
        every { LoginRequestUtil.getLoginRequestInfo(any()) } returns loginRequest
        coEvery { getAccessTokenUseCase(any()) } returns flowOf(Resource.Success(token))
        coEvery { getEventsUseCase(any()) } returns flowOf(Resource.Loading())
        viewModel = EventListViewModel(getEventsUseCase, getAccessTokenUseCase, context)
        viewModel.eventListState.test {
            val firstEmission = awaitItem()
            assert(firstEmission.isLoading)
        }
    }

    @Test
    fun `when get events was failed, inform the error`() = runBlocking {
        val errorCode = ErrorCode.UNEXPECTED_ERROR
        val token = "1234"
        mockkObject(LoginRequestUtil)
        every { LoginRequestUtil.getLoginRequestInfo(any()) } returns loginRequest
        coEvery { getAccessTokenUseCase(any()) } returns flowOf(Resource.Success(token))
        coEvery { getEventsUseCase(any()) } returns flowOf(Resource.Error(errorCode))
        viewModel = EventListViewModel(getEventsUseCase, getAccessTokenUseCase, context)
        viewModel.eventListState.test {
            val firstEmission = awaitItem()
            assertSame(errorCode, firstEmission.error)
        }
    }

    @Test
    fun `get events successfully`() = runBlocking {
        val token = "1234"
        mockkObject(LoginRequestUtil)
        every { LoginRequestUtil.getLoginRequestInfo(any()) } returns loginRequest
        coEvery { getAccessTokenUseCase(any()) } returns flowOf(Resource.Success(token))
        coEvery { getEventsUseCase(any()) } returns flowOf(Resource.Success(eventsMock))
        viewModel = EventListViewModel(getEventsUseCase, getAccessTokenUseCase, context)
        viewModel.eventListState.test {
            val firstEmission = awaitItem()
            assertSame(eventsMock, firstEmission.events)
        }
    }


}
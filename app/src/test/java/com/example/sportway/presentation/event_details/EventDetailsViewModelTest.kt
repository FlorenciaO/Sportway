package com.example.sportway.presentation.event_details

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.sportway.MainCoroutineRule
import com.example.sportway.common.ErrorCode
import com.example.sportway.common.LoginRequestUtil
import com.example.sportway.common.Resource
import com.example.sportway.domain.model.Event
import com.example.sportway.domain.model.EventDetails
import com.example.sportway.domain.model.LoginRequest
import com.example.sportway.domain.model.Team
import com.example.sportway.domain.use_case.GetAccessTokenUseCase
import com.example.sportway.domain.use_case.GetEventDetailsUseCase
import com.example.sportway.domain.use_case.GetTeamsUseCase
import com.example.sportway.presentation.team_list.TeamListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EventDetailsViewModelTest {
    @MockK
    private lateinit var getAccessTokenUseCase: GetAccessTokenUseCase
    @MockK
    private lateinit var getEventDetailsUseCase: GetEventDetailsUseCase
    @MockK
    private lateinit var context: Context
    @MockK
    private lateinit var savedStateHandle: SavedStateHandle
    @MockK
    private lateinit var loginRequest: LoginRequest
    @MockK
    private lateinit var eventDetailsMock: EventDetails
    @MockK
    private lateinit var eventMock: Event

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel: EventDetailsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `when get access token was called, show loading`() = runBlocking {
        mockkObject(LoginRequestUtil)
        every { LoginRequestUtil.getLoginRequestInfo(any()) } returns loginRequest
        coEvery { getAccessTokenUseCase(any()) } returns flowOf(Resource.Loading())
        every { savedStateHandle.get<Event>(any()) } returns eventMock
        viewModel = EventDetailsViewModel(getEventDetailsUseCase, getAccessTokenUseCase, context, savedStateHandle)
        viewModel.eventDetailsState.test {
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
        every { savedStateHandle.get<Event>(any()) } returns eventMock
        viewModel = EventDetailsViewModel(getEventDetailsUseCase, getAccessTokenUseCase, context, savedStateHandle)
        viewModel.eventDetailsState.test {
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
        coEvery { getEventDetailsUseCase(any(), any()) } returns flowOf(Resource.Loading())
        every { savedStateHandle.get<Event>(any()) } returns eventMock
        viewModel = EventDetailsViewModel(getEventDetailsUseCase, getAccessTokenUseCase, context, savedStateHandle)
        viewModel.eventDetailsState.test {
            val firstEmission = awaitItem()
            assert(firstEmission.isLoading)
        }
    }

    @Test
    fun `when get events details was failed, inform the error`() = runBlocking {
        val errorCode = ErrorCode.UNEXPECTED_ERROR
        val token = "1234"
        mockkObject(LoginRequestUtil)
        every { LoginRequestUtil.getLoginRequestInfo(any()) } returns loginRequest
        coEvery { getAccessTokenUseCase(any()) } returns flowOf(Resource.Success(token))
        coEvery { getEventDetailsUseCase(any(), any()) } returns flowOf(Resource.Error(errorCode))
        every { savedStateHandle.get<Event>(any()) } returns eventMock
        viewModel = EventDetailsViewModel(getEventDetailsUseCase, getAccessTokenUseCase, context, savedStateHandle)
        viewModel.eventDetailsState.test {
            val firstEmission = awaitItem()
            assertSame(errorCode, firstEmission.error)
        }
    }

    @Test
    fun `get event details successfully`() = runBlocking {
        val token = "1234"
        mockkObject(LoginRequestUtil)
        every { savedStateHandle.get<Event>(any()) } returns eventMock
        every { LoginRequestUtil.getLoginRequestInfo(any()) } returns loginRequest
        coEvery { getAccessTokenUseCase(any()) } returns flowOf(Resource.Success(token))
        coEvery { getEventDetailsUseCase(any(), any())  } returns flowOf(Resource.Success(eventDetailsMock))
        viewModel = EventDetailsViewModel(getEventDetailsUseCase, getAccessTokenUseCase, context, savedStateHandle)
        viewModel.eventDetailsState.test {
            val firstEmission = awaitItem()
            assertNotNull(firstEmission.event?.details)
        }
    }
}
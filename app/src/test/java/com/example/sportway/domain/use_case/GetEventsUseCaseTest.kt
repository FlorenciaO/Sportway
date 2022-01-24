package com.example.sportway.domain.use_case

import com.example.sportway.common.ErrorCode
import com.example.sportway.common.Resource
import com.example.sportway.data.remote.dto.EventDto
import com.example.sportway.data.remote.dto.toEvent
import com.example.sportway.domain.model.Event
import com.example.sportway.domain.model.EventDetails
import com.example.sportway.domain.repository.SportwayRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

class GetEventsUseCaseTest {

    @MockK
    private lateinit var mockRepository: SportwayRepository
    @MockK
    private lateinit var httpException: HttpException
    @MockK
    private lateinit var ioException: IOException
    @MockK
    private lateinit var mockEvent: Event
    @MockK
    private lateinit var mockEventDto: EventDto

    private lateinit var getEventsUseCase: GetEventsUseCase
    private val mockToken = "1234"

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        getEventsUseCase = GetEventsUseCase(mockRepository)
    }

    @Test
    fun `emit loading first`() = runBlocking {
        val result = getEventsUseCase.invoke(mockToken).first()
        assertEquals(result.javaClass, Resource.Loading<EventDetails>().javaClass)
    }

    @Test
    fun `when hhtp exception returns unexpected error code`() = runBlocking {
        coEvery { mockRepository.getEvents(any()) } throws httpException
        val result = getEventsUseCase.invoke(mockToken).last()
        assertSame(ErrorCode.UNEXPECTED_ERROR, result.errorCode)
    }

    @Test
    fun `when io exception returns unexpected error code`() = runBlocking {
        coEvery { mockRepository.getEvents(any()) } throws ioException
        val result = getEventsUseCase.invoke(mockToken).last()
        assertSame(result.errorCode, ErrorCode.CONNECTION_ERROR)
    }

    @Test
    fun `when no exception returns list of events`() = runBlocking {
        mockkStatic("com.example.sportway.data.remote.dto.EventDtoKt")
        every { any<EventDto>().toEvent() } returns mockEvent
        coEvery { mockRepository.getEvents(any()) } returns listOf(mockEventDto)
        val result = getEventsUseCase.invoke(mockToken).last()
        assertEquals(listOf(mockEvent).size, result.data?.size)
    }
}
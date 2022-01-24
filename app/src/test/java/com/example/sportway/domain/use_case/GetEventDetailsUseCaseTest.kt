package com.example.sportway.domain.use_case

import com.example.sportway.common.ErrorCode
import com.example.sportway.common.Resource
import com.example.sportway.data.remote.dto.EventDetailsDto
import com.example.sportway.data.remote.dto.toEventDetails
import com.example.sportway.domain.model.EventDetails
import com.example.sportway.domain.repository.SportwayRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import junit.framework.Assert.assertSame
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException


class GetEventDetailsUseCaseTest {

    @MockK
    private lateinit var mockRepository: SportwayRepository
    @MockK
    private lateinit var mockEventDetails: EventDetails
    @MockK
    private lateinit var httpException: HttpException
    @MockK
    private lateinit var ioException: IOException

    private val mockToken = "1234"
    private val mockEventId = "1234"
    private lateinit var getEventDetailsUseCase: GetEventDetailsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        getEventDetailsUseCase = GetEventDetailsUseCase(mockRepository)
    }

    @Test
    fun `emit loading first`() = runBlocking {
        val result = getEventDetailsUseCase.invoke(mockToken, mockEventId).first()
        Assert.assertEquals(result.javaClass, Resource.Loading<EventDetails>().javaClass)
    }

    @Test
    fun `when hhtp exception returns unexpected error code`() = runBlocking {
        coEvery { mockRepository.getEventDetail(any(), any()) } throws httpException
        val result = getEventDetailsUseCase.invoke(mockToken, mockEventId).last()
        assertSame(ErrorCode.UNEXPECTED_ERROR, result.errorCode)
    }

    @Test
    fun `when io exception returns unexpected error code`() = runBlocking {
        coEvery { mockRepository.getEventDetail(any(), any()) } throws ioException
        val result = getEventDetailsUseCase.invoke(mockToken, mockEventId).last()
        assertSame(result.errorCode, ErrorCode.CONNECTION_ERROR)
    }

    @Test
    fun `when no exception returns event details`() = runBlocking {
        mockkStatic("com.example.sportway.data.remote.dto.EventDetailsDtoKt")
        every { any<EventDetailsDto>().toEventDetails() } returns mockEventDetails
        val result = getEventDetailsUseCase.invoke(mockToken, mockEventId).last()
        assertSame(mockEventDetails, result.data)
    }
}

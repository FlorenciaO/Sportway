package com.example.sportway.domain.use_case

import com.example.sportway.common.ErrorCode
import com.example.sportway.common.Resource
import com.example.sportway.data.remote.dto.TeamDto
import com.example.sportway.data.remote.dto.toTeam
import com.example.sportway.domain.model.EventDetails
import com.example.sportway.domain.model.Team
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

class GetTeamsUseCaseTest {

    @MockK
    private lateinit var mockRepository: SportwayRepository
    @MockK
    private lateinit var httpException: HttpException
    @MockK
    private lateinit var ioException: IOException
    @MockK
    private lateinit var mockTeam: Team
    @MockK
    private lateinit var mockTeamDto: TeamDto

    private lateinit var getTeamsUseCase: GetTeamsUseCase
    private val mockToken = "1234"

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        getTeamsUseCase = GetTeamsUseCase(mockRepository)
    }

    @Test
    fun `emit loading first`() = runBlocking {
        val result = getTeamsUseCase.invoke(mockToken).first()
        assertEquals(result.javaClass, Resource.Loading<EventDetails>().javaClass)
    }

    @Test
    fun `when hhtp exception returns unexpected error code`() = runBlocking {
        coEvery { mockRepository.getTeams(any()) } throws httpException
        val result = getTeamsUseCase.invoke(mockToken).last()
        assertSame(ErrorCode.UNEXPECTED_ERROR, result.errorCode)
    }

    @Test
    fun `when io exception returns unexpected error code`() = runBlocking {
        coEvery { mockRepository.getTeams(any()) } throws ioException
        val result = getTeamsUseCase.invoke(mockToken).last()
        assertSame(result.errorCode, ErrorCode.CONNECTION_ERROR)
    }

    @Test
    fun `when no exception returns sucess`() = runBlocking {
        mockkStatic("com.example.sportway.data.remote.dto.TeamDtoKt")
        every { any<TeamDto>().toTeam() } returns mockTeam
        coEvery { mockRepository.getTeams(any()) } returns listOf(mockTeamDto)
        val result = getTeamsUseCase.invoke(mockToken).last()
        assertEquals(listOf(mockTeam).size, result.data?.size)
    }
}
package com.example.sportway.presentation.team_list

import com.example.sportway.common.ErrorCode
import com.example.sportway.domain.model.Team

data class TeamListState(
    val isLoading: Boolean = false,
    val teams: List<Team> = emptyList(),
    val error: ErrorCode? = null
)

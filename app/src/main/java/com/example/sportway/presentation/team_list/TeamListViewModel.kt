package com.example.sportway.presentation.team_list

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportway.common.ErrorCode
import com.example.sportway.common.LoginRequestUtil
import com.example.sportway.common.Resource
import com.example.sportway.domain.use_case.GetAccessTokenUseCase
import com.example.sportway.domain.use_case.GetTeamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class TeamListViewModel @Inject constructor(
    private val getTeamsUseCase: GetTeamsUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    @ApplicationContext val context: Context
): ViewModel() {

    private val _teamListState = MutableStateFlow(TeamListState())
    val teamListState = _teamListState.asStateFlow()

    init {
        getTeams()
    }

    private fun getTeams() {
        getAccessTokenUseCase(LoginRequestUtil.getLoginRequestInfo(context)).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val accessToken: String = result.data ?: ""
                    getTeamsUseCase(accessToken).onEach {
                        when(it) {
                            is Resource.Success -> {
                                _teamListState.value = TeamListState(teams = it.data ?: emptyList())
                            }
                            is Resource.Error -> informErrorCode(result.errorCode)
                            is Resource.Loading -> showLoading()
                        }
                    }.launchIn(viewModelScope)
                }
                is Resource.Error -> informErrorCode(result.errorCode)
                is Resource.Loading -> showLoading()
            }
        }.launchIn(viewModelScope)
    }

    private fun showLoading() {
        _teamListState.value = TeamListState(isLoading = true)
    }

    private fun informErrorCode(errorCode: ErrorCode?) {
        _teamListState.value = TeamListState(
            error = errorCode ?: ErrorCode.UNEXPECTED_ERROR
        )
    }
}
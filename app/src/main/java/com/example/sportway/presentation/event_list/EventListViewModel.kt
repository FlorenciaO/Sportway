package com.example.sportway.presentation.event_list

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportway.common.ErrorCode
import com.example.sportway.common.LoginRequestUtil
import com.example.sportway.common.Resource
import com.example.sportway.domain.use_case.GetAccessTokenUseCase
import com.example.sportway.domain.use_case.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class EventListViewModel
@Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    @ApplicationContext val context: Context
): ViewModel() {

    private val _eventListState = MutableStateFlow(EventListState())
    val eventListState = _eventListState.asStateFlow()

    init {
        getEvents()
    }

    private fun getEvents() {
        getAccessTokenUseCase(LoginRequestUtil.getLoginRequestInfo(context)).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val accessToken: String = result.data ?: ""
                    getEventsUseCase(accessToken).onEach {
                        when(it) {
                            is Resource.Success -> {
                                _eventListState.value = EventListState(events = it.data ?: emptyList())
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
        _eventListState.value = EventListState(isLoading = true)
    }

    private fun informErrorCode(errorCode: ErrorCode?) {
        _eventListState.value = EventListState(
            error = errorCode ?: ErrorCode.UNEXPECTED_ERROR
        )
    }

    fun onUIEvent(uiEvent: EventListUIEvent) {
        when(uiEvent) {
            is EventListUIEvent.onEventClicked -> {
                _eventListState.value = EventListState(navigateTo = uiEvent.event)
            }
        }
    }
}
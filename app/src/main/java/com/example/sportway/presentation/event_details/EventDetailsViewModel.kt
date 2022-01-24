package com.example.sportway.presentation.event_details

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportway.common.Constants.PARAM_EVENT
import com.example.sportway.common.ErrorCode
import com.example.sportway.common.LoginRequestUtil
import com.example.sportway.common.Resource
import com.example.sportway.domain.model.Event
import com.example.sportway.domain.use_case.GetAccessTokenUseCase
import com.example.sportway.domain.use_case.GetEventDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class EventDetailsViewModel @Inject constructor(
    private val getEventDetailsUseCase: GetEventDetailsUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    @ApplicationContext val context: Context,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _eventDetailsState = MutableStateFlow(EventDetailsState())
    val eventDetailsState = _eventDetailsState.asStateFlow()

    init {
        savedStateHandle.get<Event>(PARAM_EVENT)?.let {
            getEvent(it)
        }
    }

    private fun getEvent(event: Event) {
        getAccessTokenUseCase(LoginRequestUtil.getLoginRequestInfo(context)).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val accessToken: String = result.data ?: ""
                    getEventDetailsUseCase(accessToken, event.id).onEach {
                        when(it) {
                            is Resource.Success -> {
                                event.details = it.data
                                _eventDetailsState.value = EventDetailsState(event = event)
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
        _eventDetailsState.value = EventDetailsState(isLoading = true)
    }

    private fun informErrorCode(errorCode: ErrorCode?) {
        _eventDetailsState.value = EventDetailsState(
            error = errorCode ?: ErrorCode.UNEXPECTED_ERROR
        )
    }
}
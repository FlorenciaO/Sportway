package com.example.sportway.presentation.main

import com.example.sportway.common.ErrorCode

interface UiEventListener {
    fun showLoading()
    fun showError(errorCode: ErrorCode)
    fun finishLoading()
}
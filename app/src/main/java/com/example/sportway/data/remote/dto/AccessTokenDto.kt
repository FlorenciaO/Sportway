package com.example.sportway.data.remote.dto

import com.example.sportway.common.Constants
import com.example.sportway.common.DateFormatUtil
import com.example.sportway.data.local.AccessToken

data class AccessTokenDto(
    val data: AccessTokenData
)

fun AccessTokenDto.toAccessToken(): AccessToken {
    val expiresInMillis = DateFormatUtil.formatDateToMillis(data.expiresIn,
        Constants.DATE_INPUT_PATTERN
    )
    return AccessToken(data.accessToken, expiresInMillis)
}
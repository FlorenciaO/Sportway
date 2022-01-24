package com.example.sportway.common

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import com.example.sportway.common.Constants.APP_VERSION
import com.example.sportway.data.remote.dto.*
import com.example.sportway.domain.model.LoginRequest
import java.util.*

object LoginRequestUtil {
    private const val DEVICE_PLATFORM = "android"
    private const val DEVICE_NAME = "device_name"

    @SuppressLint("HardwareIds")
    private fun getDeviceId(context: Context): String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    private fun getDeviceName(context: Context): String {
        val deviceName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)
            Settings.Global.DEVICE_NAME
        else
            DEVICE_NAME
        return Settings.Global.getString(context.contentResolver, deviceName)
    }

    private fun getDeviceModel(): String = Build.MODEL

    private fun getOSVersion(): String = Build.VERSION.RELEASE

    private fun getDeviceScreenDimensions(context: Context): Pair<String, String> {
        context.resources.displayMetrics.apply {
            return Pair(widthPixels.toString(), heightPixels.toString())
        }
    }

    private fun getDeviceLanguage(): String {
        return Locale.getDefault().language
    }

    fun getLoginRequestInfo(context: Context): LoginRequest {
        return LoginRequest(
            appVersion = APP_VERSION,
            deviceId = getDeviceId(context),
            deviceName = getDeviceName(context),
            deviceModel = getDeviceModel(),
            deviceVersion = getOSVersion(),
            devicePlatform = DEVICE_PLATFORM,
            dimensions = getDeviceScreenDimensions(context),
            userProfileLanguage = getDeviceLanguage()
        )
    }

    fun createLoginRequestDto(loginRequest: LoginRequest): LoginRequestDto {
        loginRequest.apply {
            return LoginRequestDto(
                app = App(appVersion),
                device = Device(
                    deviceId,
                    dimensions.second,
                    deviceModel,
                    deviceName,
                    devicePlatform,
                    deviceVersion,
                    dimensions.first
                ),
                user = User(Profile(userProfileLanguage))
            )
        }
    }
}
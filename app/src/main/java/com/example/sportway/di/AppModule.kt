package com.example.sportway.di

import android.content.Context
import com.example.sportway.common.Constants
import com.example.sportway.data.local.SportwayLocalDataSource
import com.example.sportway.data.local.SporwayLocalDataSourceImpl
import com.example.sportway.data.remote.SportwayApi
import com.example.sportway.data.repository.SportwayRepositoryImpl
import com.example.sportway.domain.repository.SportwayRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSportwayApi(): SportwayApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_NUNCHEE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SportwayApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSportwayDb(@ApplicationContext app: Context): SportwayLocalDataSource {
        return SporwayLocalDataSourceImpl(app)
    }

    @Provides
    @Singleton
    fun provideSportwayRepository(api: SportwayApi, db: SportwayLocalDataSource): SportwayRepository {
        return SportwayRepositoryImpl(api, db)
    }
}
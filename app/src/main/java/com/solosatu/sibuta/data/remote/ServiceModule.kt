package com.solosatu.sibuta.data.remote

import com.solosatu.sibuta.data.remote.auth.AuthService
import com.solosatu.sibuta.data.remote.meeting.MeetingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun getAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun getMeetingService(retrofit: Retrofit): MeetingService =
        retrofit.create(MeetingService::class.java)

}
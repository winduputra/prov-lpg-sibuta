package com.solosatu.sibuta.data.repository

import com.solosatu.sibuta.data.repository.auth.AuthRepository
import com.solosatu.sibuta.data.repository.auth.AuthRepositoryImpl
import com.solosatu.sibuta.data.repository.meeting.MeetingRepository
import com.solosatu.sibuta.data.repository.meeting.MeetingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun authRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun meetingRepository(impl: MeetingRepositoryImpl): MeetingRepository

}
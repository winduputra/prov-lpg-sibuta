package com.solosatu.sibuta.data.remote

import com.solosatu.sibuta.data.remote.auth.persistence.AuthPersistence
import com.solosatu.sibuta.data.remote.auth.persistence.AuthPersistenceImpl
import com.solosatu.sibuta.data.remote.meeting.persistence.MeetingPersistence
import com.solosatu.sibuta.data.remote.meeting.persistence.MeetingPersistenceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PersistenceModule {

    @Singleton
    @Binds
    abstract fun authServicePersistence(impl: AuthPersistenceImpl): AuthPersistence


    @Singleton
    @Binds
    abstract fun meetingServicePersistence(impl: MeetingPersistenceImpl): MeetingPersistence

}
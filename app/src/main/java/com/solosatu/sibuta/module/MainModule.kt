package com.solosatu.sibuta.module

import android.content.Context
import com.solosatu.sibuta.helper.sahredPreference.AccountHelper
import com.solosatu.sibuta.helper.sahredPreference.AppHelper
import com.solosatu.sibuta.helper.sahredPreference.SecurityHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideSecurityHelper(@ApplicationContext context: Context) = SecurityHelper(context)

    @Provides
    @Singleton
    fun provideAccountHelper(@ApplicationContext context: Context) = AccountHelper(context)

    @Provides
    @Singleton
    fun provideAppHelper(@ApplicationContext context: Context) = AppHelper(context)

}

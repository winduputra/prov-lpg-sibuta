package com.solosatu.sibuta.data.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.solosatu.sibuta.BuildConfig
import com.solosatu.sibuta.helper.jsonIgnoreKeys
import com.solosatu.sibuta.helper.sahredPreference.AccountHelper
import com.solosatu.sibuta.helper.sahredPreference.AppHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Network {

    @Provides
    @Singleton
    fun getRetrofit(okhttpClient: OkHttpClient): Retrofit {
//        val gson = GsonBuilder()
//            .setLenient()
//            .create()

        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(Json.jsonIgnoreKeys.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun getChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .collector(
                ChuckerCollector(
                    context,
                    retentionPeriod = RetentionManager.Period.ONE_HOUR,
                    showNotification = true
                )
            )
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }

    @Provides
    @Singleton
    fun getOkhttp(
        chuckerInterceptor: ChuckerInterceptor,
        accountHelper: AccountHelper,
        appHelper: AppHelper
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        if (!BuildConfig.IS_RELEASE) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }

//        val connectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//            .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_3)
//            .cipherSuites(
//                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
//                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
//                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
//                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
//                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
//            ).build()
//
//        okHttpClientBuilder.connectionSpecs(listOf(connectionSpec))

        okHttpClientBuilder.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(40, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(40, TimeUnit.SECONDS)

        okHttpClientBuilder.addInterceptor { chain ->
            val mOriRequest: Request = chain.request()
            val token = accountHelper.tokenAuth
            val request: Request.Builder = mOriRequest.newBuilder()

            if (token.isNotEmpty()) {
                request.addHeader("Authorization", "Bearer $token")
            }

            request
                .addHeader("Accept", "application/json")
                .method(mOriRequest.method, mOriRequest.body)



            chain.proceed(request.build())
        }

        okHttpClientBuilder.addInterceptor(chuckerInterceptor)

        return okHttpClientBuilder.build()
    }

}

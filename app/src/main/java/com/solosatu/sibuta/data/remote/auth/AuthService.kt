package com.solosatu.sibuta.data.remote.auth

import com.solosatu.sibuta.data.remote.BaseResponse
import com.solosatu.sibuta.data.remote.auth.model.response.LoginResponse
import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun login(
        @Body payload: JsonObject
    ): Response<BaseResponse<LoginResponse>>

}
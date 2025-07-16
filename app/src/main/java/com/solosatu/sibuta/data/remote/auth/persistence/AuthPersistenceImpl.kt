package com.solosatu.sibuta.data.remote.auth.persistence

import com.solosatu.sibuta.data.ResultData
import com.solosatu.sibuta.data.remote.auth.AuthService
import com.solosatu.sibuta.data.remote.auth.model.response.LoginResponse
import com.solosatu.sibuta.data.remote.responseHandler
import com.solosatu.sibuta.data.remote.safeApiCall
import kotlinx.serialization.json.JsonObject
import javax.inject.Inject

class AuthPersistenceImpl @Inject constructor(
    private val service: AuthService
) : AuthPersistence {
    override suspend fun login(payload: JsonObject): ResultData<LoginResponse> = safeApiCall(
        call = { responseHandler(service.login(payload)) },
        errorMessage = "Failed to login"
    )
}
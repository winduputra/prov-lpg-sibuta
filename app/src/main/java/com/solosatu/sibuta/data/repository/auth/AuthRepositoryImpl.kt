package com.solosatu.sibuta.data.repository.auth

import com.solosatu.sibuta.data.ResultData
import com.solosatu.sibuta.data.remote.auth.model.request.LoginRequestPayload
import com.solosatu.sibuta.data.remote.auth.persistence.AuthPersistence
import com.solosatu.sibuta.data.succeeded
import com.solosatu.sibuta.domain.auth.LoginDomain
import com.solosatu.sibuta.domain.auth.asDomain
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val persistence: AuthPersistence
) : AuthRepository {
    override suspend fun login(payload: LoginRequestPayload): ResultData<LoginDomain> {
        val request = persistence.login(payload.toJsonObject())
        return if (request.succeeded) {
            request as ResultData.Success
            ResultData.Success(request.data.asDomain)
        } else {
            request as ResultData.Failed
        }
    }
}
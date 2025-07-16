package com.solosatu.sibuta.data.repository.auth

import com.solosatu.sibuta.data.ResultData
import com.solosatu.sibuta.data.remote.auth.model.request.LoginRequestPayload
import com.solosatu.sibuta.domain.auth.LoginDomain

interface AuthRepository {

    suspend fun login(payload: LoginRequestPayload): ResultData<LoginDomain>

}
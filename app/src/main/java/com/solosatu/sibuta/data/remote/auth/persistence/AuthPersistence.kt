package com.solosatu.sibuta.data.remote.auth.persistence

import com.solosatu.sibuta.data.ResultData
import com.solosatu.sibuta.data.remote.auth.model.response.LoginResponse
import kotlinx.serialization.json.JsonObject

interface AuthPersistence {

    suspend fun login(payload: JsonObject): ResultData<LoginResponse>

}
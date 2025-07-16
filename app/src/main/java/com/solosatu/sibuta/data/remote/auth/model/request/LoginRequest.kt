package com.solosatu.sibuta.data.remote.auth.model.request

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

interface LoginRequestPayload {
    fun toJsonObject(): JsonObject
}

data class LoginRequest(
    val username: String,
    val password: String
) : LoginRequestPayload {
    override fun toJsonObject(): JsonObject = buildJsonObject {
        put("username", username)
        put("password", password)
    }

}

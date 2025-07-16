package com.solosatu.sibuta.domain.auth

import android.util.Log
import com.solosatu.sibuta.data.remote.auth.model.response.LoginResponse
import com.solosatu.sibuta.helper.jsonIgnoreKeys
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class LoginDomain(
    val id: Int,
    val name: String
) {
    override fun toString(): String {
        return Json.encodeToString(this)
    }

    companion object {
        fun fromJsonString(data: String): LoginDomain? = try {
            Json.jsonIgnoreKeys.decodeFromString(data)
        } catch (e: Exception) {
            Log.d("decode-user", "failed to decode, message : ${e.message}")
            null
        }
    }
}

val LoginResponse.asDomain
    get() = LoginDomain(
        id = id ?: 0,
        name = name ?: ""
    )

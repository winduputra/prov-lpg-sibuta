package com.solosatu.sibuta.data.remote.auth.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("name")
    val name: String? = null
)

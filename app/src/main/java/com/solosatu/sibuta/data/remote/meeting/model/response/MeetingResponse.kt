package com.solosatu.sibuta.data.remote.meeting.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeetingResponse(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("name")
    val name: String? = null
)

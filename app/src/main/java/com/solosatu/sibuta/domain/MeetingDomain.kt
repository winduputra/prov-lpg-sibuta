package com.solosatu.sibuta.domain

import com.solosatu.sibuta.data.remote.meeting.model.response.MeetingResponse

data class MeetingDomain(
    val id: Int,
    val name: String
)

val MeetingResponse.asDomain
    get() = MeetingDomain(
        id = id ?: 0,
        name = name ?: ""
    )

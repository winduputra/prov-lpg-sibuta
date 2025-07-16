package com.solosatu.sibuta.data.remote.meeting.model.request

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

interface RescheduleMeetRequestPayload {
    fun toJsonObject(): JsonObject
}

data class RescheduleMeetRequest(
    val id: Int,
    val date: String,
    val time: String
) : RescheduleMeetRequestPayload {
    override fun toJsonObject(): JsonObject = buildJsonObject {
        put("date", date)
        put("time", time)
    }

}

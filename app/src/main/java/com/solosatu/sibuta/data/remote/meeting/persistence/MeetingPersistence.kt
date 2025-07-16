package com.solosatu.sibuta.data.remote.meeting.persistence

import com.solosatu.sibuta.data.ResultData
import com.solosatu.sibuta.data.remote.meeting.model.response.MeetingResponse
import kotlinx.serialization.json.JsonObject

interface MeetingPersistence {

    suspend fun getMeetings(): ResultData<List<MeetingResponse>>
    suspend fun acceptMeeting(payload: Int): ResultData<MeetingResponse>
    suspend fun rejectMeeting(payload: Int): ResultData<MeetingResponse>
    suspend fun rescheduleMeeting(payload1: Int, payload2: JsonObject): ResultData<MeetingResponse>

}
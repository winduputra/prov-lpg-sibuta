package com.solosatu.sibuta.data.remote.meeting.persistence

import com.solosatu.sibuta.data.ResultData
import com.solosatu.sibuta.data.remote.meeting.MeetingService
import com.solosatu.sibuta.data.remote.meeting.model.response.MeetingResponse
import com.solosatu.sibuta.data.remote.responseHandler
import com.solosatu.sibuta.data.remote.safeApiCall
import kotlinx.serialization.json.JsonObject
import javax.inject.Inject

class MeetingPersistenceImpl @Inject constructor(
    private val service: MeetingService
) : MeetingPersistence {
    override suspend fun getMeetings(): ResultData<List<MeetingResponse>> = safeApiCall(
        call = { responseHandler(service.getMeetings()) },
        errorMessage = "Failed to get meetings"
    )

    override suspend fun acceptMeeting(payload: Int): ResultData<MeetingResponse> = safeApiCall(
        call = { responseHandler(service.acceptMeeting(payload)) },
        errorMessage = "Failed to accept meeting"
    )

    override suspend fun rejectMeeting(payload: Int): ResultData<MeetingResponse> = safeApiCall(
        call = { responseHandler(service.rejectMeeting(payload)) },
        errorMessage = "Failed to reject meeting"
    )

    override suspend fun rescheduleMeeting(
        payload1: Int,
        payload2: JsonObject
    ): ResultData<MeetingResponse> = safeApiCall(
        call = { responseHandler(service.rescheduleMeeting(payload1, payload2)) },
        errorMessage = "Failed to reschedule meeting"
    )
}
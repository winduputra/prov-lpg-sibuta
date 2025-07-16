package com.solosatu.sibuta.data.repository.meeting

import com.solosatu.sibuta.data.ResultData
import com.solosatu.sibuta.data.remote.meeting.model.request.RescheduleMeetRequest
import com.solosatu.sibuta.data.remote.meeting.model.request.RescheduleMeetRequestPayload
import com.solosatu.sibuta.data.remote.meeting.persistence.MeetingPersistence
import com.solosatu.sibuta.data.succeeded
import com.solosatu.sibuta.domain.MeetingDomain
import com.solosatu.sibuta.domain.asDomain
import javax.inject.Inject

class MeetingRepositoryImpl @Inject constructor(
    private val persistence: MeetingPersistence
) : MeetingRepository {
    override suspend fun getMeetings(): ResultData<List<MeetingDomain>> {
        val request = persistence.getMeetings()
        return if (request.succeeded) {
            request as ResultData.Success
            ResultData.Success(request.data.asSequence().map { it.asDomain }.toList())
        } else {
            request as ResultData.Failed
        }
    }

    override suspend fun acceptMeeting(payload: Int): ResultData<MeetingDomain> {
        val request = persistence.acceptMeeting(payload)
        return if (request.succeeded) {
            request as ResultData.Success
            ResultData.Success(request.data.asDomain)
        } else {
            request as ResultData.Failed
        }
    }

    override suspend fun rejectMeeting(payload: Int): ResultData<MeetingDomain> {
        val request = persistence.rejectMeeting(payload)
        return if (request.succeeded) {
            request as ResultData.Success
            ResultData.Success(request.data.asDomain)
        } else {
            request as ResultData.Failed
        }
    }

    override suspend fun rescheduleMeeting(payload: RescheduleMeetRequestPayload): ResultData<MeetingDomain> {
        val request = persistence.rescheduleMeeting(
            (payload as RescheduleMeetRequest).id,
            payload.toJsonObject()
        )
        return if (request.succeeded) {
            request as ResultData.Success
            ResultData.Success(request.data.asDomain)
        } else {
            request as ResultData.Failed
        }
    }
}
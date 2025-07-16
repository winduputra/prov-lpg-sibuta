package com.solosatu.sibuta.data.repository.meeting

import com.solosatu.sibuta.data.ResultData
import com.solosatu.sibuta.data.remote.meeting.model.request.RescheduleMeetRequestPayload
import com.solosatu.sibuta.domain.MeetingDomain

interface MeetingRepository {

    suspend fun getMeetings(): ResultData<List<MeetingDomain>>
    suspend fun acceptMeeting(payload: Int): ResultData<MeetingDomain>
    suspend fun rejectMeeting(payload: Int): ResultData<MeetingDomain>
    suspend fun rescheduleMeeting(payload: RescheduleMeetRequestPayload): ResultData<MeetingDomain>

}
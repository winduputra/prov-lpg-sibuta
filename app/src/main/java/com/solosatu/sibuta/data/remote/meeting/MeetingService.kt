package com.solosatu.sibuta.data.remote.meeting

import com.solosatu.sibuta.data.remote.BaseResponse
import com.solosatu.sibuta.data.remote.meeting.model.response.MeetingResponse
import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MeetingService {

    @GET("category/list")
    suspend fun getMeetings(): Response<BaseResponse<List<MeetingResponse>>>

    @POST("auth/login")
    suspend fun acceptMeeting(
        @Path("id") id: Int,
    ): Response<BaseResponse<MeetingResponse>>

    @POST("auth/login")
    suspend fun rejectMeeting(
        @Path("id") id: Int,
    ): Response<BaseResponse<MeetingResponse>>

    @POST("auth/login")
    suspend fun rescheduleMeeting(
        @Path("id") id: Int,
        @Body payload: JsonObject
    ): Response<BaseResponse<MeetingResponse>>

}
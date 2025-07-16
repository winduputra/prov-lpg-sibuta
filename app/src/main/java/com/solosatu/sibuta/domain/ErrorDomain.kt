package com.solosatu.sibuta.domain

import com.solosatu.sibuta.data.remote.BaseResponse

data class ErrorDomain(
    val message: String,
    var errorCode: Int = 0,
)

fun <T> BaseResponse<T>.asErrorDomain() =
    ErrorDomain(
        message ?: "",
        errorCode
    )

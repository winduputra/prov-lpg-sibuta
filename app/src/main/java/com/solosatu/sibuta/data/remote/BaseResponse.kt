package com.solosatu.sibuta.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
open class BaseResponse<T> {
    val success: Boolean? = null
    var message: String? = null
    var errorCode: Int = 0
    open val data: T? = null

    @SerialName("current_page")
    val currentPage: Int? = null

    @SerialName("last_page")
    val lastPage: Int? = null
    val total: Int? = null
}

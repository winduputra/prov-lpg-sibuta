package com.solosatu.sibuta.data

import com.solosatu.sibuta.data.remote.BaseResponse
import com.solosatu.sibuta.domain.ErrorDomain
import com.solosatu.sibuta.domain.asErrorDomain
import retrofit2.HttpException

sealed class ResultData<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultData<T>()
    data class Failed(
        private val _errorResponse: BaseResponse<Nothing>? = null,
        val exception: Exception? = null
    ) : ResultData<Nothing>() {
        val errorResponse = (_errorResponse?.asErrorDomain() ?: ErrorDomain("", 0)).apply {
            if (exception is HttpException) errorCode = exception.code()
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> """
                success with data : $data
            """.trimIndent()

            is Failed -> """
                failed, response: $errorResponse
                may caused by ${exception?.localizedMessage}
            """.trimIndent()
        }
    }
}

/**
 * `true` if [ResultData] is of type [ResultData.Success] & holds non-null
 * [ResultData.Success.data].
 */
val ResultData<*>.succeeded
    get() = this is ResultData.Success && data != null

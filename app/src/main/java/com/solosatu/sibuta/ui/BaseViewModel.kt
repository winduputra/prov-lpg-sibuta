package com.solosatu.sibuta.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solosatu.sibuta.data.ResultData
import com.solosatu.sibuta.data.succeeded
import com.solosatu.sibuta.domain.PaginationDomain
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    protected fun <T : Any> callRequest(
        call: suspend () -> ResultData<T>,
        success: (data: T) -> Unit = {},
        failed: (message: String) -> Unit = { _ -> }
    ) {

        viewModelScope.launch {

            val result = call()
            if (result.succeeded) {
                result as ResultData.Success

                success(result.data)
            } else {
                result as ResultData.Failed
                val message = StringBuilder()

                message.append(with(result.errorResponse.message) {
                    if (isEmpty())
                        result.exception?.message ?: "Unknown Error"
                    else if (result.exception != null) "Code : ${result.errorResponse.errorCode}, ${result.errorResponse.message}"
                    else result.errorResponse.message
                })

                failed(message.toString())
            }
        }
    }

    protected fun <T : Any> callRequestPagination(
        call: suspend () -> ResultData<PaginationDomain>,
        success: (data: List<T>, isLast: Boolean) -> Unit = { _, _ -> },
        failed: (message: String) -> Unit = { _ -> }
    ) {
        viewModelScope.launch {
            val result = call()
            if (result.succeeded) {
                result as ResultData.Success
                val data = (result.data.data ?: emptyList<T>()) as List<T>
                success(data, result.data.isLastPage)
            } else {
                result as ResultData.Failed
                val message = StringBuilder()

                message.append(with(result.errorResponse.message) {
                    if (isEmpty())
                        result.exception?.message ?: "Unknown Error"
                    else "Code : ${result.errorResponse.errorCode}, ${result.errorResponse.message}"
                })

                failed(message.toString())
            }
        }
    }

}

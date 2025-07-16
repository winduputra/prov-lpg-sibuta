package com.solosatu.sibuta.domain

import com.solosatu.sibuta.data.remote.BaseResponse


data class PaginationDomain(
    val message: String,
    var data: Any?,
    val currentPage: Int,
    val lastPage: Int,
    val total: Int,
    val isLastPage: Boolean = currentPage >= lastPage
)

fun <T> BaseResponse<T>.asPaginationDomain() =
    PaginationDomain(
        message ?: "",
        data = data,
        currentPage ?: 0,
        lastPage ?: 0,
        total ?: 0
    )

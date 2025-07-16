package com.solosatu.sibuta.ui

data class RemoteViewState<T>(
    var isLoading: Boolean = false,
    var data: T? = null,
    var errorMessage: String = ""
) {
    fun setLoad(value: Boolean): RemoteViewState<T> {
        isLoading = value

        return this
    }

    fun setSuccess(item: T): RemoteViewState<T> {
        setLoad(false)
        data = item

        return this
    }

    fun setFailed(message: String): RemoteViewState<T> {
        setLoad(false)
        errorMessage = message

        return this
    }

    fun removeError(): RemoteViewState<T> {
        errorMessage = ""

        return this
    }
}

/*
 * Copyright 2018 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.solosatu.sibuta.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.solosatu.sibuta.data.ResultData
import com.solosatu.sibuta.helper.jsonIgnoreKeys
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.ref.WeakReference

/**
 * Wrap a suspending API [call] in try/catch. In case an exception is
 * thrown, a [ResultData.Failed] is created based on the [errorMessage].
 */
suspend fun <T : Any> safeApiCall(
    call: suspend () -> ResultData<T>,
    errorMessage: String
): ResultData<T> {
    return try {
        call()
    } catch (e: Exception) {
        // An exception was thrown when calling the API so we're converting this to an IOException
        e.printStackTrace()
        Log.d("network-failed", "message: ${e.message}")
        ResultData.Failed(exception = IOException(errorMessage + " : " + e.message, e))
    }
}


/**
 * Network util
 *
 * check the current state of device is connecting to, if not connect to
 * any type of [NetworkCapabilities] return false
 */
fun isNetworkConnected(mContext: Context): Boolean {
    val context = WeakReference(mContext)
    val result: Boolean
    val connectivityManager =
        context.get()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val activeNetwork =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

    result = when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
        else -> false
    }

    return result
}

fun <T : Any> responseHandler(
    response: Response<BaseResponse<T>>,
    alterResponse: T? = null
): ResultData<T> {

    return if (response.isSuccessful) {
        val isSuccess =
            if (response.body()?.success != null) (response.body()?.success ?: false) else true
        if (isSuccess) successResponseHandler(alterResponse ?: response.body()?.data)
        else ResultData.Failed(_errorResponse = response.body() as BaseResponse<Nothing>)
    } else errorResponseHandler(response)
}

fun <T : Any> responsePagingHandler(response: Response<BaseResponse<T>>): ResultData<BaseResponse<T>> {
    return if (response.isSuccessful)
        successResponseHandler(response.body())
    else errorResponseHandler(response)
}

fun <T : Any> errorResponseHandler(response: Response<T>): ResultData.Failed {
    val errorResponse = response.errorBody()?.string()
    val data = errorMessage(errorResponse)
    return ResultData.Failed(data, HttpException(response))
}

private fun <T : Any> successResponseHandler(data: T?): ResultData<T> {
    return if (data != null) ResultData.Success(data)
    else ResultData.Failed(exception = IOException("Response Empty"))
}

fun errorMessage(response: String? = null): BaseResponse<Nothing>? {
    val fileResponse = try {
        Json.jsonIgnoreKeys.decodeFromString<BaseResponse<Nothing>>(response ?: "")
    } catch (e: Exception) {
        Log.d("error-parsing", "error ${e.localizedMessage}")
        null
    }
    Log.d("error-response", "parsing error : $fileResponse")
    return fileResponse
}

fun createMultiPartBody(
    context: Context,
    fileSource: Uri,
    formDataName: String
): MultipartBody.Part? {
    val fileName = getFileName(fileSource, context)
    val contentType = context.contentResolver.getType(fileSource)
    val mediaType = contentType?.toMediaTypeOrNull()
    val inputStream = context.contentResolver.openInputStream(fileSource)
    val requestBody = inputStream?.readBytes()?.toRequestBody(mediaType)
    val multipartBody = requestBody?.let {
        MultipartBody.Part.createFormData(formDataName, fileName, it)
    }

    inputStream?.close()

    return multipartBody
}

private fun getFileName(uri: Uri?, applicationContext: Context): String? {
    return try {
        if (uri != null) {
            applicationContext.contentResolver?.query(uri, null, null, null, null)
                ?.let { cursor ->
                    cursor.run {
                        if (moveToFirst()) getString(
                            getColumnIndex(OpenableColumns.DISPLAY_NAME).coerceAtLeast(
                                0
                            )
                        )
                        else null
                    }.also { cursor.close() }
                }
        } else
            null
    } catch (e: Exception) {
        null
    }
}

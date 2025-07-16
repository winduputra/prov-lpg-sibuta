package com.solosatu.sibuta.helper.sahredPreference

import android.content.Context
import com.solosatu.sibuta.R
import com.solosatu.sibuta.domain.auth.LoginDomain

class AccountHelper(
    mContext: Context
) : BaseSharedPreference(mContext, mContext.getString(R.string.preference_login)) {

    fun setAuthToken(token: String) = saveContentString(R.string.preference_login_token, token)

    val tokenAuth
        get() = getContentString(R.string.preference_login_token)

    val isLogin
        get() = tokenAuth.isNotEmpty() && userData != null

    val userData: LoginDomain?
        get() {
            val stringData = getContentString(R.string.preference_login_user).ifEmpty { null }

            if (stringData.isNullOrEmpty()) return null

            return LoginDomain.fromJsonString(stringData)
        }

    fun setDeviceToken(token: String) = saveContentString(R.string.preference_device_token, token)
    val tokenDevice
        get() = getContentString(R.string.preference_device_token)

    fun saveUserData(data: String) {
        saveContentString(R.string.preference_login_user, data)
    }


    fun logOut() {
        removeData(
            listOf(
                R.string.preference_login_token,
                R.string.preference_login_user
            )
        )
    }

}

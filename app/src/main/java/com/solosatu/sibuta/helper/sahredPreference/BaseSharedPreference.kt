package com.solosatu.sibuta.helper.sahredPreference

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

open class BaseSharedPreference(
    private val mContext: Context,
    spName: String
) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface BaseSharedPreferenceEntryPoint {
        fun getSecurityHelper(): SecurityHelper
    }

    private var mSecuritySaveUtil: SecurityHelper =
        EntryPointAccessors.fromApplication(mContext, BaseSharedPreferenceEntryPoint::class.java)
            .getSecurityHelper()

    private val mSharedPreference = SecurityHelper.getSharedPreference(mContext, spName)
    private fun getResourceString(resource: Int): String {
        return mSecuritySaveUtil.encryptString(mContext.getString(resource)).trim()
    }

    protected fun saveContentString(mKeyName: Int, mData: String) {
        val stringResource = getResourceString(mKeyName)
        if (mData == "") {
            mSharedPreference.edit {
                putString(stringResource, mData)
            }
        } else {
            mSharedPreference.edit {
                putString(stringResource, mSecuritySaveUtil.encryptString(mData))
            }
        }
    }

    protected fun getContentString(mKeyName: Int): String {
        val key = getResourceString(mKeyName)
        val mDataContent = mSharedPreference.getString(key, "") ?: ""
        return if (mDataContent == "")
            mDataContent
        else
            try {
                mSecuritySaveUtil.decryptString(mDataContent)
            } catch (e: Exception) {
                mDataContent
            }
    }

    protected fun clearData() {
        mSharedPreference.edit {
            clear()
        }
    }

    protected fun removeData(keys: List<Int>) {
        mSharedPreference.edit {
            keys.forEach { key ->
                remove(getResourceString(key))
            }
        }
    }

}

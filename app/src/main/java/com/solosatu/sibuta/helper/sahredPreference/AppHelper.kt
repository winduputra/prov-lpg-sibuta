package com.solosatu.sibuta.helper.sahredPreference

import android.content.Context
import com.solosatu.sibuta.R

class AppHelper(
    context: Context
) : BaseSharedPreference(
    context, context.getString(R.string.preference_app)
) {

    val isIntroDone: Boolean
        get() {
            val value = getContentString(R.string.preference_intro)
            return if (value.isNotEmpty())
                value.toBoolean()
            else false
        }

    fun setIntroDone() {
        saveContentString(R.string.preference_intro, "true")
    }

}

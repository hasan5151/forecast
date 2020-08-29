package com.beeline.forecast.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.koin.core.KoinComponent


class Prefs  (val context: Context) : KoinComponent {
    private val PREFS_FILENAME = "prefs"
  // private val ISINITIAL = "is_initial"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

/*    var isInitial : Boolean
        get() = prefs.getBoolean(ISINITIAL,true)
        set(value) = prefs.edit().putBoolean(ISINITIAL,value).apply()*/

    fun getDefault() : SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun getUnit(): String= getDefault().getString("degreeType","metric")!!
    fun getLastUpdate(): String= getDefault().getString("lastUpdate","15")!!
}
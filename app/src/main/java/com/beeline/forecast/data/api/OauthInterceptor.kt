package com.beeline.forecast.data.api

import com.beeline.forecast.BuildConfig
import com.beeline.forecast.utils.Prefs
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Every request has appid, units and lang. So we add them here.
 */
class OauthInterceptor(private val prefs : Prefs) :
    Interceptor {

    @Throws(java.io.IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("appid", BuildConfig.APIKEY)
            .addQueryParameter("units", prefs.getUnit())
            .addQueryParameter("lang", "ru")
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
     }
}
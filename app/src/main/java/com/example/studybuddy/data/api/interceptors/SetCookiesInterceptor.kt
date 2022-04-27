package com.example.studybuddy.data.api.interceptors

import android.content.SharedPreferences
import android.util.Log
import com.example.studybuddy.data.preferences.AppPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/*This class is used to fetch our session id from our login request!
* We add the session id to our AppPreferences
* Shared preferences
* */
class SetCookiesInterceptor :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var builder: Request.Builder = chain.request().newBuilder()
        val cookie = AppPreferences.sessionId
        if (cookie != null) {
            builder.addHeader("Cookie",cookie)
        }
            Log.v("OkHttp","Adding Header:"+cookie)
        return chain.proceed(builder.build())
    }
}
package com.example.studybuddy.data.api.interceptors

import android.content.SharedPreferences
import android.util.Log
import com.example.studybuddy.data.preferences.AppPreferences
import com.example.studybuddy.screens.LoginScreen
import okhttp3.Interceptor
import okhttp3.Response

/*This class is used to fetch our session id from our login request!
* We add the session id to our sharedPreferences, which are passed in the constructor of the class!
* Shared preferences
* */
class GetCookiesInterceptor :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
       Log.i("TEST OKHTTP","TESt")
        val originalResponse:okhttp3.Response = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()){
            AppPreferences.sessionId = originalResponse.headers("Set-Cookie")[0]
            Log.v("okHttp",originalResponse.headers("Set-Cookie")[0])
        }
        return originalResponse
    }
}
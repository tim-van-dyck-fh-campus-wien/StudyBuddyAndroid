package com.example.studybuddy.data.api

import com.example.studybuddy.data.api.ApiConstants
import retrofit2.http.GET
import retrofit2.Call

interface AuthenticationApi {
    //Retrofit creates the necessary code for us!
    @GET("auth")
    suspend fun getTest():String //Is a async function
}
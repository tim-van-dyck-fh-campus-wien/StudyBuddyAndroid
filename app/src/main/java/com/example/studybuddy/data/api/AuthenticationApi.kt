package com.example.studybuddy.data.api

import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.model.test
import retrofit2.http.GET
import retrofit2.Call

//API Service Class
interface AuthenticationApi {
    //Retrofit creates the necessary code for us!
    @GET("auth")
    suspend fun getTest():test//Is a async function
}
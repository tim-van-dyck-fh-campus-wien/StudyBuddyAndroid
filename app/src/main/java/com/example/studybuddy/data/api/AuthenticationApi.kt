package com.example.studybuddy.data.api

import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.model.LoginData
import com.example.studybuddy.data.api.model.RegisterData
import com.example.studybuddy.data.api.model.SingleGroupId
import com.example.studybuddy.data.api.model.test
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

//API Service Class
interface AuthenticationApi {
    //Retrofit creates the necessary code for us!
    @GET("auth")
    suspend fun getTest():test//Is a async function

    @POST("auth/register")
    suspend fun register(@Body registerData: RegisterData):Response<Unit>

    @POST("auth/login")
    suspend fun login(@Body loginData:LoginData):Response<Unit>

    @GET("group/authorizationCheck")
    suspend fun checkIfStudentIsAdminOfGroup(@Body singleGroupId: SingleGroupId)
}
package com.example.studybuddy.data.api

import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.model.*
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
    suspend fun login(@Body loginData:LoginData):Response<BasicStudent>

    @GET("group/authorizationCheck")
    suspend fun checkIfStudentIsAdminOfGroup(@Body singleGroupId: SingleGroupId):Response<Unit>
    @GET("auth/checkAuthentication")
    suspend fun isUserLoggedIn():Response<BasicStudent>

    @GET("auth/logout")
    suspend fun logout():Response<Unit>
    @POST("auth/updateStudentData")
    suspend fun updateStudentData(@Body updatedProfileData: ProfileData):Response<Unit>
}
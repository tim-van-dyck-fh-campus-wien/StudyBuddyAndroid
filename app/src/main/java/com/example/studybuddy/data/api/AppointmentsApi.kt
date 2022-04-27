package com.example.studybuddy.data.api

import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

//API Service Class
interface AppointmentsApi {
    //Retrofit creates the necessary code for us!
    @POST("appointments/addAppointment")
    suspend fun createNewAppointment(@Body createAppointment: CreateAppointment):Response<Unit>//Is a async function

    @DELETE("appointments/deleteAppointment")
    suspend fun deleteAppointment(@Body deleteAppointment: deleteAppointment):Response<Unit>
}
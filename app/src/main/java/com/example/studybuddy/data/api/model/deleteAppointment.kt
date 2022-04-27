package com.example.studybuddy.data.api.model

import com.google.gson.annotations.SerializedName

data class deleteAppointment(
    val groupId:String,
    @SerializedName("_id")
    val appointmentId:String
)

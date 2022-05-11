package com.example.studybuddy.data.api.model

data class CreateStudyGroup(
    val name:String,
val location:String,
//admin raus, läuft über userID
val description:String,
val topic:String,
val icon:String,
)

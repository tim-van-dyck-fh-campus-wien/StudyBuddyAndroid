package com.example.studybuddy.models

data class CreateStudyGroup(
    val name:String,
val location:String,
val admin:String,
//admin raus, läuft über userID
val description:String,
val topic:String,
val icon:String,
)

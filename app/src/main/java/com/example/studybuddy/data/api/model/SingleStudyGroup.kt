package com.example.studybuddy.data.api.model

data class SingleStudyGroup(
    val name:String,
    val location:String,
    val description:String,
    val topic:String,
    val admin:BasicStudent,
    val members:List<BasicStudent>,
    val appointments:List<Appointment>,
    val _id:String,
    val messages:List<Message>,
    val joinRequests:List<JoinRequest>,
    val icon:String,
    val hide:Boolean
)

package com.example.studybuddy.data.api.model

data class JoinRequest(
    val groupId:String, //needed to know where to send request to
    val text:String,
    //added since this information is added in BE
    val sender_id:String,

    val _id:String)


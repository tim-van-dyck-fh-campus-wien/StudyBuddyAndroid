package com.example.studybuddy.data.api.model

data class JoinRequestsReceivedForAdmin(
    val groupId:String, //needed to know where to send request to
    val text:String,
    //added since this information is added in BE
    val sender_id:BasicStudent,

    val _id:String)


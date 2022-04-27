package com.example.studybuddy.data.api.model

import com.google.gson.annotations.SerializedName

data class DeleteMemberFromGroupData(
    val groupId:String,
    @SerializedName("newMemberId")
    val memberId:String
)

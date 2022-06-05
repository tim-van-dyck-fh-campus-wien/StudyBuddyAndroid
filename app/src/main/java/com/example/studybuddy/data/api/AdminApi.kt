package com.example.studybuddy.data.api

import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

//API Service Class
interface AdminApi {
    //Retrofit creates the necessary code for us!
    @POST("studyGroups/updateGroupData")
    suspend fun  updateGroupData(@Body changeableGroupData: ChangeableGroupData) :Response<Unit>

    @POST("auth//group/authorizationCheck")
    suspend fun  isUserAdmin(@Body groupId : SingleGroupId): Response<Unit>

    @POST("studyGroups/getJoinRequests")
    suspend fun getJoinRequests(@Body groupId: SingleGroupId): Response<List<JoinRequestsReceivedForAdmin>>

    @POST("studyGroups/joinRequests")
    suspend fun acceptOrDeclineJoinRequest(@Body handleRequest: AcceptDeclineJR): Response<Unit>
}
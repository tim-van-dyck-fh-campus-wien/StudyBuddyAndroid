package com.example.studybuddy.data.api

import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

//API Service Class
interface StudyGroupApi {
    //Retrofit creates the necessary code for us!
    @POST("studyGroups/create")
    suspend fun createStudyGroup(@Body createStudyGroup: CreateStudyGroup):Response<SingleStudyGroup>//Is a async function

    @GET("studyGroups/{district}")
    suspend fun getFilteredStudyGroups(@Path("district") district:String):Response<List<SingleStudyGroup>>

    @GET("studyGroups")
    suspend fun getAllStudyGroups():Response<List<SingleStudyGroup>>

    @GET("studyGroups/groups/myGroups")
    suspend fun getMyGroups():Response<List<SingleStudyGroup>>

    //Use this if the logged in student is NO Admin!
    //Function for admins in ManageStudyGroupApi
    @DELETE("studyGroups/leaveStudyGroup")
    suspend fun leaveStudyGroupNoAdmin(@Body leaveStudyGroupNotAdmin: LeaveStudyGroupNotAdmin):Response<String>

    @POST("studyGroups/joinRequestToGroup")
    suspend fun issueJoinRequestToGroup(@Body joinRequest: JoinRequest):Response<Unit>

    @POST("studyGroups/isStudentAbleToSendJoinRequest")
    //suspend fun canStudentSendJoinRequest(@Body singleGroupId: SingleGroupId):Response<Unit>
    suspend fun canStudentSendJoinRequest(@Body groupId: SingleGroupId) : Response<Unit>
    @GET("images/groupImgList")
    suspend fun getAvailableGroupImages():Response<List<String>>

}
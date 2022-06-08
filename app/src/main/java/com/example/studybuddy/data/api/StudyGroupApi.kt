package com.example.studybuddy.data.api

import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.model.*
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

//API Service Class
interface StudyGroupApi {
    //Retrofit creates the necessary code for us!
    @POST("studyGroups/create")
    suspend fun createStudyGroup(@Body createStudyGroup: CreateStudyGroup):Response<SingleStudyGroup>// return this value to get the ID for detailed View (returns only ID though --> no extra model needed in Backend)

    @GET("studyGroups/{district}")
    suspend fun getFilteredStudyGroups(@Path("district") district:String):Response<List<SingleStudyGroup>>

    @GET("studyGroups")
    suspend fun getAllStudyGroups():Response<List<SingleStudyGroup>>

    @GET("studyGroups/groups/myGroups")
    suspend fun getMyGroups():Response<List<SingleStudyGroup>>

    @POST("studyGroups/joinRequestToGroup")
    suspend fun issueJoinRequestToGroup(@Body joinRequest: JoinRequest):Response<Unit>

    @POST("studyGroups/isStudentAbleToSendJoinRequest")
    suspend fun canStudentSendJoinRequest(@Body groupId: SingleGroupId) : Response<Unit>
    @GET("images/groupImgList")
    suspend fun getAvailableGroupImages():Response<List<String>>

    @POST("studyGroups/groups/singleGroup")
    suspend fun getSingleStudyGroup(@Body groupId: SingleGroupId) : Response<SingleStudyGroup>

    @POST("message/newmessage")
    suspend fun sendMessageToGroup(@Body message: Message) : Response<Unit>

    @POST("message/group/mess")
    suspend fun getMessagesOfGroup(@Body groupId: SingleGroupId) : Response<List<Message>>
}
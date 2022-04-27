package com.example.studybuddy.data.api

import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

//API Service Class
interface ManageStudyGroupApi {
    //Retrofit creates the necessary code for us!
    @DELETE("studyGroups/deleteMember")
    suspend fun deleteMemberFromGroup(@Body deleteMemberFromGroupData: DeleteMemberFromGroupData):Response<String>//Is a async function

    @DELETE("studyGroups/leaveStudyGroup")
    suspend fun leaveStudyGroupIsAdmin(@Body leaveStudyGroupAdmin: LeaveStudyGroupAdmin):Response<String>

    @POST("studyGroups/getJoinRequests")
    suspend fun getListOfJoinRequest(@Body singleGroupId: SingleGroupId):Response<ListOfJoinRequests>

    @POST("studyGroups/joinRequests")
    suspend fun acceptDeclineJoinRequest(@Body acceptDeclineJR: AcceptDeclineJR):Response<Unit>

    @POST("studyGroups/updateGroupData")
    suspend fun updateGroupData(@Body changeableGroupData: ChangeableGroupData)

    @POST("studyGroups/hideThisGroup")
    suspend fun hideShowStudyGroup(@Body hideShowGroup: HideShowGroup):Response<Unit>

    @DELETE("studyGroups/deleteThisStudyGroup")
    suspend fun deleteStudyGroup(@Body singleGroupId: SingleGroupId):Response<Unit>

}
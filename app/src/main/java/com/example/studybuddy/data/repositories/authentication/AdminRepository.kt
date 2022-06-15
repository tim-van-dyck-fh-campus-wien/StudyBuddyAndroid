package com.example.studybuddy.data.repositories.authentication

import com.example.studybuddy.data.api.AdminApi
import com.example.studybuddy.data.api.StudyGroupApi
import com.example.studybuddy.data.api.model.*
import retrofit2.Response


class AdminRepository constructor (
    private val adminApi:AdminApi) {
    //async function...
    suspend fun isUserAdmin(singleGroupId: SingleGroupId) = adminApi.isUserAdmin(singleGroupId)
    suspend fun updateGroupData(changeableGroupData: ChangeableGroupData) = adminApi.updateGroupData(changeableGroupData)
    suspend fun getJoinRequests(singleGroupId: SingleGroupId) = adminApi.getJoinRequests(singleGroupId)
    suspend fun acceptOrDeclineJoinRequest(handleRequest: AcceptDeclineJR) = adminApi.acceptOrDeclineJoinRequest(handleRequest)
    suspend fun deleteMember(deleteMemberFromGroupData: DeleteMemberFromGroupData) = adminApi.deleteMember(deleteMemberFromGroupData)
}

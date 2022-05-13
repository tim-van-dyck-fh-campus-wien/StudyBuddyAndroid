package com.example.studybuddy.data.repositories.authentication

import com.example.studybuddy.data.api.StudyGroupApi
import com.example.studybuddy.data.api.model.CreateStudyGroup
import com.example.studybuddy.data.api.model.SingleGroupId
import com.example.studybuddy.data.api.model.SingleStudyGroup
import retrofit2.Response
import javax.inject.Inject

//Pass the API via dependency injection for loose coupling

class StudyGroupRepository @Inject constructor (
    private val studyGroupApi:StudyGroupApi) {
    //async function...
    suspend fun getAllStudyGroups() = studyGroupApi.getAllStudyGroups()
    suspend fun canStudentSendJoinRequest(groupId:SingleGroupId) = studyGroupApi.canStudentSendJoinRequest(groupId)
    suspend fun getAvailabelGroupImages() = studyGroupApi.getAvailableGroupImages()
    suspend fun createStudyGroup(group:CreateStudyGroup) = studyGroupApi.createStudyGroup(group)
    suspend fun getMyGroups() = studyGroupApi.getMyGroups()
    suspend fun getSingleStudyGroup(groupId: SingleGroupId) = studyGroupApi.getSingleStudyGroup(groupId)
}

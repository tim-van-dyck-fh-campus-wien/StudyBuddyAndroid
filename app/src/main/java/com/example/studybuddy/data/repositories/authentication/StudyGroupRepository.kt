package com.example.studybuddy.data.repositories.authentication

import com.example.studybuddy.data.api.StudyGroupApi
import com.example.studybuddy.data.api.model.SingleGroupId
import javax.inject.Inject

//Pass the API via dependency injection for loose coupling

class StudyGroupRepository @Inject constructor (
    private val studyGroupApi:StudyGroupApi) {
    //async function...
    suspend fun getAllStudyGroups() = studyGroupApi.getAllStudyGroups()
    suspend fun canStudentSendJoinRequest(groupId:SingleGroupId) = studyGroupApi.canStudentSendJoinRequest(groupId)
}

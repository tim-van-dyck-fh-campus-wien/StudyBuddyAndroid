package com.example.studybuddy.data.repositories.authentication

import android.util.Log
import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.AuthenticationApi
import com.example.studybuddy.data.api.StudyGroupApi
import com.example.studybuddy.data.api.model.LoginData
import com.example.studybuddy.data.api.model.RegisterData
import com.example.studybuddy.data.api.model.SingleGroupId
import com.example.studybuddy.data.api.model.test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import javax.inject.Inject

//Pass the API via dependency injection for loose coupling

class StudyGroupRepository @Inject constructor (
    private val studyGroupApi:StudyGroupApi) {
    //async function...
    suspend fun getAllStudyGroups() = studyGroupApi.getAllStudyGroups()
}

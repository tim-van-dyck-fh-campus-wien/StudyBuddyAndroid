package com.example.studybuddy.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.api.model.*
import com.example.studybuddy.data.repositories.authentication.StudyGroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StudyGroupViewModel @Inject constructor(
    private val repository:StudyGroupRepository
) : ViewModel(){
    val errorMessage = MutableLiveData<String>()
    var studyGroupsSearchList =MutableLiveData<List<SingleStudyGroup>>()
    //Test for List of StudyGroups - works fine
    var testList = listOf<SingleStudyGroup>()
    var myGroupList = listOf<SingleStudyGroup>()
    var singleGroup = getDummyGroup()
    var filteredStudyGroupList = MutableLiveData<List<SingleStudyGroup>>()


    var canSendRQ:Boolean=false

    fun getAllStudyGroups() {
        Log.i("Test","test")
        viewModelScope.launch {
            val response = repository.getAllStudyGroups()
            //response.body()?.forEach { it -> Log.i("StudyGroupAPI", "$it") }
            //Log.i("StudyGroupAPI", "${response.body()}")

            //added for testList purposes (see below)
            if(response.isSuccessful || !response.body().isNullOrEmpty()){
                val forList = response.body()
                forList?.forEach{it -> Log.i("StudyGroupAPI", "$it")}
                //could not get a nonNull Value for response with studyGroupsSearchList, works fine with testList
                if (!forList.isNullOrEmpty()){
                    testList = forList
                }
                //left this here, but doesn't update
                studyGroupsSearchList.postValue(forList)

                Log.i("StudyGroupAPI","The List updated ${studyGroupsSearchList.value}")
                Log.i("StudyGroupAPI", "trying with testList ${testList}")
                //studyGroupsSearchList.postValue(response.body())
                //Log.i("StudyGroupAPI",response.body().toString())
            }else{
                onError("Error: ${response.message()}")
            }
        }
    }

    fun getFilteredStudyGroups(district:String) {
        viewModelScope.launch {
            val response = repository.getFilteredStudyGroups(district = district)
            //response.body()?.forEach { it -> Log.i("StudyGroupAPI", "$it") }
            //Log.i("StudyGroupAPI", "${response.body()}")
            if (response.isSuccessful || !response.body().isNullOrEmpty()) {
                val forList = response.body()
                filteredStudyGroupList.postValue(forList)
                Log.i("StudyGroupAPI", "The filtered list ${filteredStudyGroupList.value}")

            } else {
                onError("Error: ${response.message()}")
            }
        }
    }


    fun canStudentSendJoinRequest(studyGroupId: SingleGroupId, callback:(Boolean) -> Unit = {}){
        Log.i("StudyGroupAPI", "message is $studyGroupId")
        viewModelScope.launch {

            val response = repository.canStudentSendJoinRequest(studyGroupId)
            //Log.i("StudyGroupAPI", "checking if not member ${response.code()}")
            Log.i("StudyGroupAPI", "API member response ${response}")
            //response.body()?.forEach { it -> Log.i("StudyGroupAPI", "$it") }
            //Log.i("StudyGroupAPI", "${response.body()}")

            //added for testList purposes (see below)
            if(response.code() == 200){
                callback(true)
            } else if (response.code() == 400) {
                callback(false)
            } else{
                onError("Error: ${response.message()}")
            }
        }
    }

    fun getOnlyMyGroups() {
        viewModelScope.launch {
            val response = repository.getMyGroups()
            Log.i("StudyGroupAPI", "API myGroups $response")
            if (response.isSuccessful || !response.body().isNullOrEmpty()) {
                val myGroups = response.body()
                Log.i("StudyGroupAPI", "getOnlyMyGroups  - Response: ${response.body()}")
                //myGroups?.forEach { it -> Log.i("StudyGroupAPI", "$it") }
                if (!myGroups.isNullOrEmpty()) {
                    myGroupList = myGroups
                } else{
                onError("Error: ${response.message()}")
            }
            }
        }
    }


    fun detailedViewOfSingleStudyGroup(groupId: SingleGroupId) : SingleStudyGroup{
        Log.i("StudyGroupAPI", "current ID $groupId")
        viewModelScope.launch {
            val response = repository.getSingleStudyGroup(groupId = groupId)
            if (response.message() == "OK" || response.code() == 200  ){
          // Log.i("StudyGroupAPI", " Single Group in detailed View ${response.body()}")
                singleGroup = response.body()!! }
            else {

                /*TODO: add behaviour for error to eliminate dummy group*/
            onError("Error: ${response.message()}")
        }}
        return singleGroup
    }


    private fun onError(message:String){
            Log.w("StudyGroupVM,Error",message)
    }

    fun sendJoinRequest(joinRequest: JoinRequest, callbackJoin:(Boolean) -> Unit = {}){
            Log.i("StudyGroupAPI", "join request is $joinRequest")
            viewModelScope.launch {
                val response = repository.sendJoinRequest(joinRequest = joinRequest)
                Log.i("StudyGroupAPI", "Join REQ response ${response}")
                if(response.code() == 200){
                    callbackJoin(true)
                } else
                    onError("Error: ${response.message()}")
                }
            }


        }





/*
class AuthenticationViewModel constructor(
    private val repository:AuthenticationRepository = AuthenticationRepository()
) : ViewModel(){
    fun loadTest():String{
        //launch an async coroutine
        viewModelScope.launch{
            val result = repository.getTest()
            Log.i("test",result.text)
        }
        return "asdf"
    }
}*/
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
    var testList = listOf<JoinRequestsReceivedForAdmin>()
    var myGroupList = MutableLiveData<List<SingleStudyGroup>>()
    var singleGroup = getDummyGroup()
    var filteredStudyGroupList = MutableLiveData<List<SingleStudyGroup>>()
    var joinRequests = MutableLiveData<List<JoinRequestsReceivedForAdmin>>()

    //added, because somehow, the first call seems to return an empty list
    /*init {
        getAllStudyGroups()
        getOnlyMyGroups()
    }*/

    fun getAllStudyGroups() {
        Log.i("Test","test")
        viewModelScope.launch {
            val response = repository.getAllStudyGroups()
            if(response.isSuccessful || !response.body().isNullOrEmpty()){
                val forList = response.body()
                forList?.forEach{it -> Log.i("StudyGroupAPI", "$it")}
                studyGroupsSearchList.postValue(forList)
                Log.i("StudyGroupAPI","The List updated ${studyGroupsSearchList.value}")
            }else{
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
                myGroupList.postValue(myGroups)
                Log.i("StudyGroupAPI", "My group list updated ${myGroupList.value}")
                    //myGroupList = myGroups
                } else{
                    onError("Error: ${response.message()}")
                }
            }
        }



    fun getFilteredStudyGroups(district:String, filteredGroupsCallback:(Boolean)->Unit = {}) {
        viewModelScope.launch {
            val response = repository.getFilteredStudyGroups(district = district)
            if (response.isSuccessful || !response.body().isNullOrEmpty()) {
                val forList = response.body()
                filteredStudyGroupList.postValue(forList)
                filteredGroupsCallback(true)
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
            when {
                response.code() == 200 -> {
                    callback(true)
                }
                response.code() == 400 -> {
                    callback(false)
                }
                else -> {
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
                Log.i("StudyGroupAPI", "Join REQ response $response")
                if(response.code() == 200){
                    callbackJoin(true)
                } else
                    onError("Error: ${response.message()}")
                }
            }

    fun isUserAdmin(singleGroupId: SingleGroupId, callbackAdmin:(Boolean) -> Unit = {}){
        Log.i("StudyGroupAPI", "Group to check is $singleGroupId")
        viewModelScope.launch {
            val response = repository.isUserAdmin(singleGroupId = singleGroupId)
            Log.i("StudyGroupAPI", "Join REQ response $response")
            if(response.code() == 200){
                callbackAdmin(true)
            } else if (response.code() == 400){
                callbackAdmin(false)
            }
            else
                onError("Error: ${response.message()}")
        }
    }

    fun updateGroupData(changeableGroupData: ChangeableGroupData, callbackChangedData: (Boolean) -> Unit){
        Log.i("StudyGroupAPI", "Content is $changeableGroupData")
        viewModelScope.launch {
            val response = repository.updateGroupData(changeableGroupData = changeableGroupData)
            Log.i("StudyGroupAPI", "Update Group Data response $response")
            when {
                response.code() == 200 -> {
                    callbackChangedData(true)
                }
                response.code() == 400 -> {
                    callbackChangedData(false)
                }
                else -> onError("Error: ${response.message()}")
            }
        }
    }

    fun getJoinRequests(singleGroupId: SingleGroupId, callbackJoinRQs:(ListOfJoinRequests) -> Unit = {}) {
        Log.i("StudyGroupAPI", "Content is getting pending Join Requests for Group $singleGroupId")
        viewModelScope.launch {
            Log.i("StudyGroupAPI", "inside coroutine for join requests list ")
            val response = repository.getJoinRequests(singleGroupId)

            //Log.i("StudyGroupAPI", "Join REQ list response ${response.body()}")
            if (response.isSuccessful || !response.body().isNullOrEmpty()) {
                Log.i("StudyGroupAPI", "Join REQ list response ${response.body()}")
                val requests = response.body()
                joinRequests.postValue(requests)
                if (requests != null) {
                    testList = requests
                }
                Log.i("StudyGroupAPI", "Join testlist after post value ${testList}")
                Log.i("StudyGroupAPI", "Join joinRequests after post value ${joinRequests.value}")
                //callbackJoinRQs(response.body()!!)
            } else {
                onError("Error: ${response.message()}")
            }
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
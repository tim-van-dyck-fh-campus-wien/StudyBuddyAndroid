package com.example.studybuddy.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.api.model.*
import com.example.studybuddy.data.repositories.authentication.StudyGroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
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
    var singleGroup = MutableLiveData<SingleStudyGroup>()
    var filteredStudyGroupList = MutableLiveData<List<SingleStudyGroup>>()
    var joinRequests = MutableLiveData<List<JoinRequestsReceivedForAdmin>>()
    var messages = MutableLiveData<List<Message>>()
    var members = MutableLiveData<List<BasicStudent>>()


    fun getAllStudyGroups() {
        Log.i("Test","test")
        viewModelScope.launch {
            try {
                val response = repository.getAllStudyGroups()
                if (response.isSuccessful || !response.body().isNullOrEmpty()) {
                    val forList = response.body()
                    forList?.forEach { it -> Log.i("StudyGroupAPI", "$it") }
                    studyGroupsSearchList.postValue(forList)
                    Log.i("StudyGroupAPI", "The List updated ${studyGroupsSearchList.value}")
                } else {
                    onError("Error: ${response.message()}")
                }
            }catch(e:Exception){
                Log.i("error",e.toString())
            }
        }
    }

    fun getOnlyMyGroups() {
        viewModelScope.launch {
            try {
                val response = repository.getMyGroups()
                Log.i("StudyGroupAPI", "API myGroups $response")
                if (response.isSuccessful || !response.body().isNullOrEmpty()) {
                    val myGroups = response.body()
                    Log.i("StudyGroupAPI", "getOnlyMyGroups  - Response: ${response.body()}")
                    //myGroups?.forEach { it -> Log.i("StudyGroupAPI", "$it") }
                    myGroupList.postValue(myGroups)
                    Log.i("StudyGroupAPI", "My group list updated ${myGroupList.value}")
                    //myGroupList = myGroups
                } else {
                    onError("Error: ${response.message()}")
                }
            }catch(e:Exception){
                Log.i("Error",e.toString())
            }
        }
        }



    fun getFilteredStudyGroups(district:String, filteredGroupsCallback:(Boolean)->Unit = {}) {
        viewModelScope.launch {
          try {
              val response = repository.getFilteredStudyGroups(district = district)
              if (response.isSuccessful || !response.body().isNullOrEmpty()) {
                  val forList = response.body()
                  filteredStudyGroupList.postValue(forList)
                  filteredGroupsCallback(true)
                  Log.i("StudyGroupAPI", "The filtered list ${filteredStudyGroupList.value}")
              } else {
                  onError("Error: ${response.message()}")
              }
          }catch(e:Exception){
              Log.i("Error",e.toString())
          }
          }
    }


    fun canStudentSendJoinRequest(studyGroupId: SingleGroupId, callback:(Boolean) -> Unit = {}){
        Log.i("StudyGroupAPI", "message is $studyGroupId")
        viewModelScope.launch {
            try{
            val response = repository.canStudentSendJoinRequest(studyGroupId)
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
            }}catch(e:Exception){
                Log.i("Error",e.toString())
            }
        }
    }



    fun detailedViewOfSingleStudyGroup(groupId: SingleGroupId){
        Log.i("StudyGroupAPI", "current ID $groupId")
        viewModelScope.launch {
          try{
            val response = repository.getSingleStudyGroup(groupId = groupId)
            if (response.message() == "OK" || response.code() == 200  ){
                singleGroup.postValue(response.body()!!)
                messages.postValue(response.body()!!.messages)
                members.postValue(response.body()!!.members)
                Log.i("StudyGroupAPI", "Messages from detailed view ${messages.value}")
                Log.i("StudyGroupAPI", "SingleGroup from detailed view ${singleGroup.value}")
                Log.i("StudyGroupAPI", "Members from detailed view ${members.value}")
            }
            else {
                /*TODO: add behaviour for error to eliminate dummy group*/
            onError("Error: ${response.message()}")
        }}catch(e:Exception){
            Log.i("Error",e.toString())
        }}

    }


    private fun onError(message:String){
            Log.w("StudyGroupVM,Error",message)
    }

    fun sendJoinRequest(joinRequest: JoinRequest, callbackJoin:(Boolean) -> Unit = {}){
            Log.i("StudyGroupAPI", "join request is $joinRequest")
            viewModelScope.launch {
              try {
                  val response = repository.sendJoinRequest(joinRequest = joinRequest)
                  Log.i("StudyGroupAPI", "Join REQ response $response")
                  if (response.code() == 200) {
                      callbackJoin(true)
                  } else
                      onError("Error: ${response.message()}")
              }catch(e:Exception){
                  Log.i("Error",e.toString())
              }
                }
            }

    fun sendMessageToGroup(message: Message, callbackMessage:(Boolean) -> Unit = {}){
        Log.i("StudyGroupAPI", "Message is $message")
        viewModelScope.launch {
            try {
                val response = repository.sendMessageToGroup(message = message)
                Log.i("StudyGroupAPI", "Message Response $response")
                if (response.code() == 200) {
                    callbackMessage(true)
                } else
                    onError("Error: ${response.message()}")
            }catch(e:Exception){
                Log.i("Error",e.toString())
            }
        }
    }

    fun getMessagesOfGroup(singleGroupId: SingleGroupId){
        viewModelScope.launch {
            try {
                val response = repository.getMessagesOfGroup(singleGroupId = singleGroupId)
                Log.i("StudyGroupAPi", "Get Message Response $response")
                if (response.code() == 200 || response.body().isNullOrEmpty()){
                    messages.postValue(response.body())
                    Log.i("StudyGroupAPI", "Messages are in response ${response.body()}")
                    var list = messages.value
                    list?.forEach { it -> Log.i("StudyGroupAPI", "$it") }
                }
             else
                onError("Error: ${response.message()}")
            }catch(e:Exception){
                Log.i("Error",e.toString())
            }

        }
    }
}







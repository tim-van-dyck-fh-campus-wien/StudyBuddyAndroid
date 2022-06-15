package com.example.studybuddy.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.api.model.*
import com.example.studybuddy.data.preferences.AppPreferences
import com.example.studybuddy.data.repositories.authentication.AdminRepository
import com.example.studybuddy.data.repositories.authentication.StudyGroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository:AdminRepository
) : ViewModel(){
    val errorMessage = MutableLiveData<String>()
    //var studyGroupsSearchList =MutableLiveData<List<SingleStudyGroup>>()
    //Test for List of StudyGroups - works fine
    //var testList = listOf<JoinRequestsReceivedForAdmin>()
    //var myGroupList = MutableLiveData<List<SingleStudyGroup>>()
    //var singleGroup = getDummyGroup()
    //var filteredStudyGroupList = MutableLiveData<List<SingleStudyGroup>>()
    var joinRequests = MutableLiveData<List<JoinRequestsReceivedForAdmin>>()

    //added, because somehow, the first call seems to return an empty list
    /*init {
        getAllStudyGroups()
        getOnlyMyGroups()
    }*/


    private fun onError(message:String){
            Log.w("StudyGroupVM,Error",message)
    }

    fun isUserAdmin(singleGroupId: SingleGroupId, callbackAdmin:(Boolean) -> Unit = {}){

            Log.i("StudyGroupAPI", "Group to check is $singleGroupId")
            viewModelScope.launch {
                try {
                    val response = repository.isUserAdmin(singleGroupId = singleGroupId)
                    Log.i("StudyGroupAPI", "User is admin response $response")
                    when {
                        response.code() == 200 -> {
                            callbackAdmin(true)
                        }
                        response.code() == 400 -> {
                            callbackAdmin(false)
                        }
                        else -> onError("Error: ${response.message()}")
                    }
                }catch(e:Exception){
                    Log.i("error",e.toString())
                }
    }}

    fun updateGroupData(changeableGroupData: ChangeableGroupData, callbackChangedData: (Boolean) -> Unit){

            Log.i("StudyGroupAPI", "Content is $changeableGroupData")
            viewModelScope.launch {
                try {
                    val response =
                        repository.updateGroupData(changeableGroupData = changeableGroupData)
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
                }catch(e:Exception){
                    Log.i("error",e.toString())
                }
            }

    }

    fun getJoinRequests(singleGroupId: SingleGroupId, callbackJoinRQs:(List<JoinRequestsReceivedForAdmin>) -> Unit = {}) {

        Log.i("StudyGroupAPI", "Content is getting pending Join Requests for Group $singleGroupId")
        viewModelScope.launch {
           try {
               Log.i("StudyGroupAPI", "inside coroutine for join requests list ")
               val response = repository.getJoinRequests(singleGroupId)

               //Log.i("StudyGroupAPI", "Join REQ list response ${response.body()}")
               if (response.isSuccessful || !response.body().isNullOrEmpty()) {
                   Log.i("StudyGroupAPI", "Join REQ list response ${response}")
                   val requests = response.body()
                   Log.i("StudyGroupAPI", "join req list from response $requests")
                   //if (requests != null) {
                   // testList = requests
                   //  Log.i("StudyGroupAPI", "Join testlist after post value ${testList}")
                   if (requests != null) {
                       callbackJoinRQs(requests)
                   }
                   joinRequests.postValue(requests)
                   Log.i(
                       "StudyGroupAPI",
                       "Join joinRequests after post value ${joinRequests.value}"
                   )
                   //}
                   //Log.i("StudyGroupAPI", "Join testlist after post value ${testList}")
                   Log.i(
                       "StudyGroupAPI",
                       "Join joinRequests after post value ${joinRequests.value}"
                   )
                   //callbackJoinRQs(response.body()!!)
               } else {
                   onError("Error: ${response.message()}")
               }
           }catch(e:Exception){
               Log.i("error",e.toString())
           }
        }
    }
    fun acceptOrDeclineJoinRequest(handleRequest: AcceptDeclineJR, callbackResponse: (Boolean) -> Unit){
        Log.i("StudyGroupAPI", "Reaction to JoinRequest is $handleRequest")
        viewModelScope.launch {
            try {
                val response = repository.acceptOrDeclineJoinRequest(handleRequest = handleRequest)
                Log.i("StudyGroupAPI", "Accept or Decline Join Request response $response")
                when {
                    response.code() == 200 -> {
                        callbackResponse(true)

                    }
                    else -> onError("Error: ${response.message()}")
                }
            }catch(e:Exception){
                Log.i("error",e.toString())
            }
        }
    }
    fun deleteMember(deleteMemberFromGroupData: DeleteMemberFromGroupData, callbackDeleteMember: (Boolean) -> Unit){
        Log.i("StudyGroupAPI", "Delete this member is $deleteMemberFromGroupData")
        viewModelScope.launch {
            try {
                val response = repository.deleteMember(deleteMemberFromGroupData = deleteMemberFromGroupData)
                if (response.isSuccessful){
                    callbackDeleteMember(true)
                } else { onError("Error: ${response.message()}") }
            } catch(e:Exception){
                Log.i("error",e.toString())
            }
        }
    }
}



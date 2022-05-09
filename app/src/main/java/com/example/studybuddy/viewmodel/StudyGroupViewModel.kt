package com.example.studybuddy.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.api.model.*
import com.example.studybuddy.data.repositories.authentication.AuthenticationRepository
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

    private fun onError(message:String){

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
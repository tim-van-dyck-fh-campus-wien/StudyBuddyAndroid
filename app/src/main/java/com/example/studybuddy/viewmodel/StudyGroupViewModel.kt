package com.example.studybuddy.viewmodel

import android.util.Log
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
    val studyGroupsSearchList =MutableLiveData<List<SingleStudyGroup>>()
    fun getAllStudyGroups(){
        Log.i("Test","test")
        viewModelScope.launch {
            val response = repository.getAllStudyGroups()
            if(response.isSuccessful){
                studyGroupsSearchList.postValue(response.body())
                Log.i("StudyGroupAPI",response.body().toString())
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
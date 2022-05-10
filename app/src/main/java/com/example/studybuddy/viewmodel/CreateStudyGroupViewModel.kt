package com.example.studybuddy.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.api.model.*
import com.example.studybuddy.data.repositories.authentication.StudyGroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateStudyGroupViewModel @Inject constructor(
    private val repository:StudyGroupRepository
) : ViewModel(){
    var availableGroupImages = MutableLiveData<List<String>>()
    val selectedIconUrl = mutableStateOf("")
    //Test for List of StudyGroups - works fine

    fun getAvailableGroupimages(){
        viewModelScope.launch {
            val response = repository.getAvailabelGroupImages()
            if(response.code()==200){
                availableGroupImages.postValue( response.body())
                var firstUrl = response.body()!!.get(0)
                if(firstUrl!=null){
                    selectedIconUrl.value = firstUrl
                }
            }else{
                onError("Error:${response.message()}")
            }
        }
    }
    fun setSelectedIcon(url:String){
        selectedIconUrl.value=url
        Log.i("Icon Selected",url)
    }
    fun isUrlSelected(url:String):Boolean{
        if(url==selectedIconUrl.value){
            return true
        }
        return false
    }

    private fun onError(message:String){
            Log.w("StudyGroupVM,Error",message)
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
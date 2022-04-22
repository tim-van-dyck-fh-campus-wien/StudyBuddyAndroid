package com.example.studybuddy.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.repositories.authentication.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
/*
@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository:AuthenticationRepository
) : ViewModel(){
    fun loadTest():String{
        //launch an async coroutine
        viewModelScope.launch{
            val result = repository.getTest()
            Log.i("test",result)
        }
        return "asdf"
    }
}*/
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
}
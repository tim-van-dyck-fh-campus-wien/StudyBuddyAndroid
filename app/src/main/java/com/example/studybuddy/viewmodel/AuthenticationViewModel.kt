package com.example.studybuddy.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.api.model.LoginData
import com.example.studybuddy.data.api.model.RegisterData
import com.example.studybuddy.data.api.model.SingleGroupId
import com.example.studybuddy.data.repositories.authentication.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository:AuthenticationRepository
) : ViewModel(){
    val errorMessage = MutableLiveData<String>()

    val isLoggedIn = MutableLiveData<Boolean>()


    fun loadTest():String{
        //launch an async coroutine
        viewModelScope.launch{
            val result = repository.getTest()
            Log.i("test",result)
        }
        return "asdf"
    }

    fun register(registerData: RegisterData=
                     RegisterData("hans","test", "hansi", "hans123@stud.fh-campuswien.ac.at", "campus09", "c20104750555", "1070",2030)){
        viewModelScope.launch {
           val response = repository.register(registerData)
            if(response.isSuccessful){//If registering worked...

            }else{
                onError("Error: ${response.message()}")
            }
        }
    }

    fun login(loginData: LoginData,failure:(String)->Unit={},success:()->Unit={}){
        Log.i("LOGIIN","WORKS")
        viewModelScope.launch{
            val response = repository.login(loginData)
            if(response.isSuccessful){//If registering worked...
                Log.i("login",response.code().toString())
                success()
            }else{
                failure(response.message())
                onError("Error: ${response.message()}")
            }
        }
    }

    fun isAdminOfGroup(singleGroupId: SingleGroupId){
        viewModelScope.launch {
            val response = repository.studentIsAdminOfGroup(singleGroupId)
            if(response.isSuccessful){//If registering worked...

            }else{
                onError("Error: ${response.message()}")
            }
        }
    }
    fun isStudentLoggedIn(sucess:()->Unit = {}, failure:()->Unit = {}){
        viewModelScope.launch{
            val response = repository.isUserLoggedIn()
            if(response.isSuccessful){
                Log.i("AuthAPI-LoggedIn",response.code().toString())
                sucess()
            }else{
                failure()
                Log.w("AuthAPI-LoggedIn",response.code().toString())
            }
        }
    }
    private fun onError(message:String){
        Log.w("Authentication",message)
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
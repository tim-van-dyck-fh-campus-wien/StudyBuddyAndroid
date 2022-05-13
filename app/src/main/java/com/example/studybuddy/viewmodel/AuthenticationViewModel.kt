package com.example.studybuddy.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studybuddy.data.api.model.*
import com.example.studybuddy.data.preferences.AppPreferences
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
    var profileData= BasicStudent("","","","","","",false)


    fun loadTest():String{
        //launch an async coroutine
        viewModelScope.launch{
            val result = repository.getTest()
            Log.i("test",result)
        }
        return "asdf"
    }

    fun register(registerData: RegisterData=
                     RegisterData("hans","test", "hansi", "hans123@stud.fh-campuswien.ac.at", "campus09", "c20104750555", "1070",2030), failure:(String)->Unit={},success:()->Unit={}){
        viewModelScope.launch {
           val response = repository.register(registerData)
            if(response.isSuccessful){//If registering worked...
                success()
            }else{
                failure(response.message())
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
                val dat = response.body()
                if(dat!=null){
                    profileData=dat
                }
                success()
            }else{
                failure(response.message())
                onError("Error: ${response.message()}")
            }
        }
    }
    fun logout(success: () -> Unit={},failure: (String) -> Unit={}){
        viewModelScope.launch {
            val response = repository.logout()
            if(response.isSuccessful){
                AppPreferences.sessionId=""
                isLoggedIn.postValue(false)
                success()
            }else{
                failure("${response.code()}:${response.message()}")
                onError("Error: ${response.message()}:${response.code()}")

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
                val dat = response.body()
                if(dat!=null){
                    profileData=dat
                }
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

    fun updateProfileData(success:()->Unit={}, failure: (String) -> Unit={}, updatedProfileData: ProfileData){
        viewModelScope.launch{
            val response = repository.updateProfileData(updatedProfileData = updatedProfileData)
            if(response.code()==200){
                isStudentLoggedIn()
                success()
            }else{
                failure("${response.code()}:${response.message()}")
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
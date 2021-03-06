package com.example.studybuddy.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studybuddy.data.api.model.*
import com.example.studybuddy.data.preferences.AppPreferences
import com.example.studybuddy.data.repositories.authentication.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
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
                     RegisterData("hans","test", "hansi", "hans123@stud.fh-campuswien.ac.at", "campus09", "c20104750555", "1070",2030), failure: (String)->Unit={}, success: ()->Unit={}){
        viewModelScope.launch {
          try {
              val response = repository.register(registerData)
              if (response.isSuccessful) {//If registering worked...
                  success()
              } else if (response.code() == 500) {
                  failure("Some error occurred")
              } else if (response.code() == 408) {
                  failure("Username is already taken, choose another one!")
              } else if (response.code() == 406) {
                  failure("Couldn't save student. Make sure to fill out all the fields!")
              } else {
                  failure(response.message())
                  onError("Error: ${response.message()}")
              }
          }catch(e:Exception){
              Log.i("error",e.toString())
          }
        }
    }

    fun login(loginData: LoginData,failure:(String)->Unit={},success:(Boolean)->Unit={}){
        Log.i("LOGIIN","WORKS")
        viewModelScope.launch{
            try {
                val response = repository.login(loginData)
                if (response.isSuccessful) {//If registering worked...
                    Log.i("login", response.code().toString())
                    val dat = response.body()
                    if (dat != null) {
                        profileData = dat
                    }
                    success(true)
                } else {
                    failure(response.message())
                    onError("Error: ${response.message()}")
                }

            } catch(e:Exception){//if the connection failed
                Log.i("error",e.toString())
            }
        }
    }
    fun logout(success: () -> Unit={},failure: (String) -> Unit={}){
        viewModelScope.launch {
            try{
                val response = repository.logout()
                if(response.isSuccessful){
                    AppPreferences.sessionId=""
                    isLoggedIn.postValue(false)
                    success()
                }else{
                    failure("${response.code()}:${response.message()}")
                    onError("Error: ${response.message()}:${response.code()}")

                }
            }catch(e:Exception){
                Log.i("error",e.toString())
            }


        }
    }

    fun isAdminOfGroup(singleGroupId: SingleGroupId){
        viewModelScope.launch {
            try{
                val response = repository.studentIsAdminOfGroup(singleGroupId)
                if(response.isSuccessful){//If registering worked...

                }else{
                    onError("Error: ${response.message()}")
                }
            }catch(e:Exception){
                Log.i("error",e.toString())
            }

        }
    }
    fun isStudentLoggedIn(sucess:()->Unit = {}, failure:()->Unit = {}){
        viewModelScope.launch{
            try{
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
            }catch(e:Exception){
                Log.i("error",e.toString())
            }

        }
    }
    private fun onError(message:String){
        Log.w("Authentication",message)
    }

    fun updateProfileData(success:()->Unit={}, failure: (String) -> Unit={}, updatedProfileData: ProfileData){
        viewModelScope.launch{
            try {
                val response = repository.updateProfileData(updatedProfileData = updatedProfileData)
                if (response.code() == 200) {
                    isStudentLoggedIn()
                    success()
                } else {
                    failure("${response.code()}:${response.message()}")
                }
            }catch(e:Exception){
                Log.i("error",e.toString())
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
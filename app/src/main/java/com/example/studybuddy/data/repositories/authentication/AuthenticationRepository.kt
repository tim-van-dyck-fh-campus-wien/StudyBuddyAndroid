package com.example.studybuddy.data.repositories.authentication

import android.util.Log
import com.example.studybuddy.data.api.AuthenticationApi
import java.lang.Exception
import javax.inject.Inject

//Pass the API via dependency injection for loose coupling
class AuthenticationRepository @Inject constructor (
    private val authenticationApi:AuthenticationApi) {
    //async function...
    suspend fun getTest():String{
        val response = try{
            authenticationApi.getTest()
        }catch(e:Exception){
            Log.i("error",e.toString())
            return e.toString()
        }
        return response
    }
}
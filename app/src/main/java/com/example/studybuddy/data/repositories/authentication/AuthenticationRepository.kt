package com.example.studybuddy.data.repositories.authentication

import android.util.Log
import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.AuthenticationApi
import com.example.studybuddy.data.api.model.LoginData
import com.example.studybuddy.data.api.model.RegisterData
import com.example.studybuddy.data.api.model.SingleGroupId
import com.example.studybuddy.data.api.model.test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import javax.inject.Inject

//Pass the API via dependency injection for loose coupling

class AuthenticationRepository constructor (
    private val authenticationApi:AuthenticationApi) {
    //async function...
    suspend fun getTest():String{
        val response = try{
            authenticationApi.getTest()
        }catch(e:Exception){
            Log.i("error",e.toString())
            return e.toString()
        }
        return response.text
    }
    suspend fun register(registerData: RegisterData) = authenticationApi.register(registerData)
    suspend fun login(loginData: LoginData)= authenticationApi.login(loginData)
    suspend fun studentIsAdminOfGroup(singleGroupId: SingleGroupId)=authenticationApi.checkIfStudentIsAdminOfGroup(singleGroupId)
    suspend fun isUserLoggedIn()=authenticationApi.isUserLoggedIn()
    /*suspend fun register(registerData: RegisterData):Boolean{
       val response = authenticationApi.register(registerData = registerData)
        if(response.isSuccessful){
            Log.i("authAPI/register",response.body().toString())
            return true
        }
        return false
    }
    suspend fun login(loginData: LoginData):Boolean{
        val response = authenticationApi.login(loginData = loginData)
        if(response.isSuccessful){
            Log.i("authAPI/login",response.body().toString())
            return true
        }
        return false
    }
    suspend fun studentIsAdminOfGroup(singleGroupId: SingleGroupId):Boolean{
        val response = authenticationApi.checkIfStudentIsAdminOfGroup(singleGroupId)
        if(response.isSuccessful){
            Log.i("authAPI/studentIsAdmin",response.body().toString())
            return true
        }
        return false
    }*/
}
/*
class AuthenticationRepository  constructor (
    private val authenticationApi:AuthenticationApi = Retrofit.Builder().addConverterFactory(
        GsonConverterFactory.create())
        .baseUrl(ApiConstants.BASE_URL)
        .build()
        .create(AuthenticationApi::class.java) ) {
    //async function...
    suspend fun getTest(): test {
        val response = try{
            authenticationApi.getTest()
        }catch(e:Exception){
            Log.i("error",e.toString())
            return test("e.toString()")
        }
        Log.i("testAuth",response.text)
        return response
    }
}*/
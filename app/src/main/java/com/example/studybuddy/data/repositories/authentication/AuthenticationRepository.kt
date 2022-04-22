package com.example.studybuddy.data.repositories.authentication

import android.util.Log
import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.AuthenticationApi
import com.example.studybuddy.data.api.model.test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import javax.inject.Inject

//Pass the API via dependency injection for loose coupling
/*
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
}*/
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
}
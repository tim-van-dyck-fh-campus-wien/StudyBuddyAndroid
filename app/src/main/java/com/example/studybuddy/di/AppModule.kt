package com.example.studybuddy.di

import android.content.Context
import com.example.studybuddy.BaseApplication
import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.AuthenticationApi
import com.example.studybuddy.data.api.StudyGroupApi
import com.example.studybuddy.data.api.interceptors.GetCookiesInterceptor
import com.example.studybuddy.data.api.interceptors.SetCookiesInterceptor
import com.example.studybuddy.data.repositories.authentication.AuthenticationRepository
import com.example.studybuddy.data.repositories.authentication.StudyGroupRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
//The modules contain the dependencys!
//Global module for whole application
//to be injected into the constructor of a class as a singleton!
//Dependencys at this level live as long as the app lives
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context):BaseApplication{
        return app as BaseApplication
    }


    /**
     * Goes together (I guess) for each API call
     */
    @Singleton
    @Provides //the repo for the API call
    fun provideAuthenticationRepository(api:AuthenticationApi) = AuthenticationRepository(api)

    @Singleton
    @Provides //the API to the retrofit instance, thus be able to use the service
    fun provideAuthenticationApi():AuthenticationApi{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiConstants.BASE_URL)
            .client(buildOkHttpWithInterceptors())
            .build()
            .create(AuthenticationApi::class.java)
    }

    private fun buildOkHttpWithInterceptors():okhttp3.OkHttpClient{
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(SetCookiesInterceptor())
            .addInterceptor(GetCookiesInterceptor())
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
        return okHttpClient
    }

    @Singleton
    @Provides //the API to the retrofit instance, thus be able to use the service
    fun provideStudyGroupApi():StudyGroupApi{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiConstants.BASE_URL)
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(buildOkHttpWithInterceptors())
            .build()
            .create(StudyGroupApi::class.java)
    }

    @Singleton
    @Provides //the repo for the API call
    fun provideStudyGroupRepository(api:StudyGroupApi) = StudyGroupRepository(api)
}
package com.example.studybuddy.di

import android.content.Context
import com.example.studybuddy.BaseApplication
import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.AuthenticationApi
import com.example.studybuddy.data.repositories.authentication.AuthenticationRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Singleton
    @Provides
    fun provideAuthenticationRepository(api:AuthenticationApi) = AuthenticationRepository(api)

    @Singleton
    @Provides
    fun provideAuthenticationApi():AuthenticationApi{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiConstants.BASE_URL)
            .build()
            .create(AuthenticationApi::class.java)
    }
}
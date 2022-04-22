package com.example.studybuddy.di

import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.AuthenticationApi
import com.example.studybuddy.data.repositories.authentication.AuthenticationRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//to be injected into the constructor of a class as a singleton!
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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
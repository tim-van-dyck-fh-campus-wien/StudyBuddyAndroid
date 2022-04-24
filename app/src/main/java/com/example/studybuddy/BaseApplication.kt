package com.example.studybuddy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


//Needed for Dependency injection with Hilt!
@HiltAndroidApp
class BaseApplication : Application(){
}
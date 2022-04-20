package com.example.studybuddy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.screens.*

@Composable
fun MainNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenNames.LoginScreen.name  ){
        composable(ScreenNames.LoginScreen.name){
            LoginScreen(navController = navController)
        }
        composable(ScreenNames.HomeScreen.name){
            HomeScreen(navController = navController)
        }
        composable(ScreenNames.FindStudyGroups.name){
            FindStudyGroupsScreen(navController = navController)
        }
        composable(ScreenNames.CreateStudyGroups.name){
            CreateStudyGroupsScreen(navController = navController)
        }
        composable(ScreenNames.ProfileScreen.name){
            ProfileScreen(navController = navController)
        }
        composable(ScreenNames.ViewStudyGroup.name){
            ViewStudyGroupScreen(navController = navController)
        }

    }
}
package com.example.studybuddy.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studybuddy.screens.*
import com.example.studybuddy.viewmodel.AuthenticationViewModel

@Composable
fun MainNavigation(){
    val navController = rememberNavController()
    val authenticationViewModel:AuthenticationViewModel = viewModel()
    NavHost(navController = navController, startDestination = ScreenNames.LoginScreen.name  ){
    //changed start destination to homeScreen to see the preview:
    //NavHost(navController = navController, startDestination = ScreenNames.HomeScreen.name  ){
            composable(ScreenNames.LoginScreen.name){
                LoginScreen(navController = navController, authenticationViewModel)
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

        /*composable(ScreenNames.ViewStudyGroupScreen.name){
            ViewStudyGroupScreen(navController = navController )
        }

         */


          composable(ScreenNames.ViewStudyGroupScreen.name + "/{studyGroup}",
              arguments=listOf(navArgument("studyGroup") {
                  type = NavType.StringType
              }))
          {
              //to enable navigation to a specific group & display its info in ViewStudyGroupScreen
                  backStackEntry ->
              Log.d("navigation", "cur backStackEntry $backStackEntry")
              ViewStudyGroupScreen(navController = navController, studyGroupID = backStackEntry.arguments?.getString("studyGroup"))
          }



        }
    }
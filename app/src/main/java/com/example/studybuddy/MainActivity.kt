package com.example.studybuddy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.data.api.model.LoginData
import com.example.studybuddy.data.preferences.AppPreferences
import com.example.studybuddy.navigation.MainNavigation
import com.example.studybuddy.navigation.ScreenNames
import com.example.studybuddy.ui.theme.StudyBuddyTheme
import com.example.studybuddy.viewmodel.AuthenticationViewModel
import com.example.studybuddy.viewmodel.StudyGroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Needed to initialize our AppPreferences object.
        //Needed for session management!
        AppPreferences.setup(applicationContext)

        setContent {

            StudyBuddyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainNavigation()
                    //Greeting(name = "asdf")
                }
            }
        }
    }
}
@Composable
fun checkLogin(){

}

@Composable
fun backendTests() {
    val authenticationViewModel: AuthenticationViewModel = viewModel()
    val studyGroupViewModel: StudyGroupViewModel = viewModel()
    val loginData = LoginData("tvd2204", "campus09129")
    authenticationViewModel.login(loginData)
    //val test = studyGroupViewModel.getAllStudyGroups()
    authenticationViewModel.isStudentLoggedIn()
    studyGroupViewModel.getAllStudyGroups()
}

@Composable
fun Greeting(name: String) {
    backendTests()
    Text(text = "Hello $name!")
}

private fun initializeAppPreferences(){
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StudyBuddyTheme {
        Greeting("Android")
    }
}

@Composable
fun DisplayBottomBar(navController: NavController,
                     content: @Composable () -> Unit = {}){
    //val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            ) {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // HomeScreen
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "HomeScreen") },
                    selected = currentRoute == ScreenNames.HomeScreen.name,
                    onClick = {
                        Log.d("nav", "HomeScreen clicked")
                        navController.navigate(ScreenNames.HomeScreen.name)
                    },
                )

                // ViewStudyGroupsScreen
                /*BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.List, contentDescription = "ViewStudyGroupsScreen") },
                    selected = currentRoute == ScreenNames.ViewStudyGroup.name,
                    onClick = {
                        Log.d("nav", "ViewStudyGroupsScreen clicked")
                        navController.navigate(ScreenNames.ViewStudyGroup.name)
                    },
                )*/

                // FindStudyGroupsScreen
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.Search, contentDescription = "FindStudyGroupsScreen") },
                    selected = currentRoute == ScreenNames.FindStudyGroups.name,
                    onClick = {
                        Log.d("nav", "FindStudyGroupsScreen clicked")
                        navController.navigate(ScreenNames.FindStudyGroups.name)
                    },
                )

                // CreateStudyGroupsScreen
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "CreateStudyGroupsScreen") },
                    selected = currentRoute == ScreenNames.CreateStudyGroups.name,
                    onClick = {
                        Log.d("nav", "CreateStudyGroupsScreen clicked")
                        navController.navigate(ScreenNames.CreateStudyGroups.name)
                    },
                )

                // ProfileScreen
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "ProfileScreen") },
                    selected = currentRoute == ScreenNames.ProfileScreen.name,
                    onClick = {
                        Log.d("nav", "ProfileScreen clicked")
                        navController.navigate(ScreenNames.ProfileScreen.name)
                    },
                )
            }
        }
    ) { innerPadding -> // needed so that the bottom bar does not overlap screen contents
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}
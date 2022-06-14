package com.example.studybuddy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.R
import com.example.studybuddy.data.api.model.ProfileData
import com.example.studybuddy.navigation.ScreenNames
import com.example.studybuddy.viewmodel.AuthenticationViewModel
import com.example.studybuddy.widgets.TextFieldCloseOnEnter
import com.example.studybuddy.widgets.displayLogo

@Composable
fun ProfileScreen(
    navController: NavHostController,
    authenticationViewModel: AuthenticationViewModel
){
    DisplayBottomBar (navController = navController) { ProfileContent(authenticationViewModel,navController) }
}

@Composable
fun ProfileContent(authenticationViewModel: AuthenticationViewModel, navController: NavHostController){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        displayLogo()
        /*Image(
            painter = painterResource(id = R.drawable.transparent_study_buddy_backup),
            contentDescription = "StudyBuddyIcon"
        )*/
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                authenticationViewModel.logout(success = {
                    navController.navigate(ScreenNames.LoginScreen.name)
                })
            }) {
            Text(text = "LOGOUT")
        }
        Text(
            text = "Edit your Profile",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        form(authenticationViewModel.profileData.username,authenticationViewModel.profileData.location,
            {
            authenticationViewModel.updateProfileData(success = {
            /*TODO ADD SUCCESS ACTION*/ navController.navigate(ScreenNames.HomeScreen.name)
        }, failure = {
            /*TODO Add failure action*/
        },updatedProfileData = it)})

    }
}
@Composable
fun form(username:String,location:String, onSubmit:(ProfileData)->Unit){
    var username by remember { mutableStateOf(username) }
    var location by remember { mutableStateOf(location) }
    var password by remember { mutableStateOf("")}
    //var topic by remember { mutableStateOf("") }
    TextFieldCloseOnEnter(value = username,label = "Username",onValueChange = {username=it})
    TextFieldCloseOnEnter(value = password,label = "Password",isPasswordField = true,onValueChange = {password=it})

    TextFieldCloseOnEnter(value = location,label = "Location",onValueChange = {location=it})

    Button(
        modifier = Modifier.padding(16.dp),
        onClick = {
            onSubmit(ProfileData(username,password,location))
        }) {
        Text(text = "Save Changes")
    }
}
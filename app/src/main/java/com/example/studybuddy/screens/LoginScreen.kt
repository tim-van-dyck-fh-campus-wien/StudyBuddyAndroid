package com.example.studybuddy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.R
import com.example.studybuddy.data.api.model.LoginData
import com.example.studybuddy.navigation.ScreenNames
import com.example.studybuddy.viewmodel.AuthenticationViewModel
import com.example.studybuddy.widgets.TextFieldCloseOnEnter
import java.util.*


@Composable
fun LoginScreen(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel ,
                //onLoginClick: () -> Unit = {},
                //onRegisterInsteadClick: () -> Unit = {}
    ){
    //If the student is already logged in, go to the home screen!
   //authenticationViewModel.isStudentLoggedIn(sucess = {
    //    navController.navigate(ScreenNames.HomeScreen.name)
    //})
    
    Column (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Image(painter = painterResource(id = R.drawable.transparent_study_buddy), contentDescription = "StudyBuddyIcon")

        Text(
            text = "Login",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        var openDialog = remember { mutableStateOf(false) }

        /*OutlinedTextField(
            value = username,
            onValueChange = { value -> username = value },
            label = { Text(text = "Username") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { value -> password = value },
            label = { Text(text = "Password") }
        )*/

        TextFieldCloseOnEnter(username, "Username"){
                value -> username = value
        }

        TextFieldCloseOnEnter(password, "Password", true){
                value -> password = value
        }

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                if (username.isNotEmpty() and password.isNotEmpty()) {
                    val loginData = LoginData(username,password)
                    authenticationViewModel.login(loginData = loginData,failure = {
                        openDialog.value = true
                    }){
                        // success
                        if (it) {
                            navController.navigate(ScreenNames.HomeScreen.name)
                            //username = ""
                            //password = ""
                        }
                    }


                }
            }) {
            Text(text = "Login")
        }

        Button(
            modifier = Modifier.padding(0.dp),
            onClick = {
                // open register page
                navController.navigate(ScreenNames.RegisterScreen.name)
                username = ""
                password = ""
            }) {
            Text(text = "Register instead")
        }

        if(openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Invalid username or password")
                },
                text = {
                    Text("Please try again.")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        }) {
                        Text("OK")
                    }
                },
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //LoginScreen()
}
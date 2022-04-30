package com.example.studybuddy.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.navigation.ScreenNames
import com.example.studybuddy.viewmodel.AuthenticationViewModel


@Composable
fun RegisterScreen(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel ,
    //onRegisterClick: () -> Unit = {}
    //onLoginInsteadClick: () -> Unit = {},
){

    Column (modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(
            text = "Register",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var location by remember { mutableStateOf("") }
        var firstname by remember { mutableStateOf("") }
        var lastname by remember { mutableStateOf("") }
        var studentId by remember { mutableStateOf("") }
        var yearOfFinish by remember { mutableStateOf("") }     // muss string sein, weil sonst das OutlinedTextField nicht funktioniert?

        OutlinedTextField(
            value = username,
            onValueChange = { value -> username = value },
            label = { Text(text = "Username") }
        )

        OutlinedTextField(
            value = email,
            onValueChange = { value -> email = value },
            label = { Text(text = "Email Address") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { value -> password = value },
            label = { Text(text = "Password") }
        )

       OutlinedTextField(
            value = location,
            onValueChange = { value -> location = value },
            label = { Text(text = "Location (Zipcode)") }
        )

        OutlinedTextField(
            value = firstname,
            onValueChange = { value -> firstname = value },
            label = { Text(text = "First Name") }
        )

        OutlinedTextField(
            value = lastname,
            onValueChange = { value -> lastname = value },
            label = { Text(text = "Last Name") }
        )

        OutlinedTextField(
            value = studentId,
            onValueChange = { value -> studentId = value },
            label = { Text(text = "Identification Number (cXXX...)") }
        )

        OutlinedTextField(
            value = yearOfFinish,
            onValueChange = { value -> yearOfFinish = value },
            label = { Text(text = "I will finish my studies by ...") }
        )

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                if (username.isNotEmpty() and password.isNotEmpty()) {
                    // todo: register
                    navController.navigate(ScreenNames.HomeScreen.name)
                    username = ""
                    email = ""
                    password = ""
                    location = ""
                    firstname = ""
                    lastname = ""
                    studentId = ""
                    yearOfFinish = ""
                }
            }) {
            Text(text = "Register")
        }

        Button(
            modifier = Modifier.padding(0.dp),
            onClick = {
                // open login page
                navController.navigate(ScreenNames.LoginScreen.name)
                username = ""
                email = ""
                password = ""
                location = ""
                firstname = ""
                lastname = ""
                studentId = ""
                yearOfFinish = ""
            }) {
            Text(text = "Login instead")
        }
    }
}
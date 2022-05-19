package com.example.studybuddy.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.data.api.model.LoginData
import com.example.studybuddy.data.api.model.RegisterData
import com.example.studybuddy.navigation.ScreenNames
import com.example.studybuddy.viewmodel.AuthenticationViewModel
import com.example.studybuddy.widgets.TextFieldCloseOnEnter


@Composable
fun RegisterScreen(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel ,
    //onRegisterClick: () -> Unit = {}
    //onLoginInsteadClick: () -> Unit = {},
){

    Column (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
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
        //var yearOfFinish by remember { mutableStateOf("") }     // muss string sein, weil sonst das OutlinedTextField nicht funktioniert?
        var yearOfFinish by remember { mutableStateOf(0) }

        var openDialog = remember { mutableStateOf(false) }
        var responseMessage = remember { mutableStateOf("")}

        /*OutlinedTextField(
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
        )*/

        TextFieldCloseOnEnter(username, "Username"){
                value -> username = value
        }

        TextFieldCloseOnEnter(email, "Email Address"){
                value -> email = value
        }

        TextFieldCloseOnEnter(password, "Password", true){
                value -> password = value
        }

        TextFieldCloseOnEnter(location, "Location (Zipcode)"){
                value -> location = value
        }

        TextFieldCloseOnEnter(firstname, "First Name"){
                value -> firstname = value
        }

        TextFieldCloseOnEnter(lastname, "Last Name"){
                value -> lastname = value
        }

        TextFieldCloseOnEnter(studentId, "Identification Number (cXXX...)"){
                value -> studentId = value
        }

        TextFieldCloseOnEnter(yearOfFinish.toString(), "I will finish my studies by ..."){
                value -> yearOfFinish = value.toInt()
        }

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                if (/*username.isNotEmpty() and password.isNotEmpty() and */!email.endsWith("@stud.fh-campuswien.ac.at")) {
                    openDialog.value = true
                    responseMessage.value = "Email must be a valid FH Campus Wien email address."
                }
                else {
                    val registerData = RegisterData(firstname, lastname, username, email, password, studentId, location, yearOfFinish)
                    // register
                    authenticationViewModel.register(registerData = registerData, failure = {
                        openDialog.value = true
                        responseMessage.value = it
                    }){
                        //success
                        navController.navigate(ScreenNames.HomeScreen.name)
                        username = ""
                        email = ""
                        password = ""
                        location = ""
                        firstname = ""
                        lastname = ""
                        studentId = ""
                        yearOfFinish = 0
                    }
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
                yearOfFinish = 0
            }) {
            Text(text = "Login instead")
        }

        if(openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    openDialog.value = false
                },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text(text = responseMessage.value)
                },
                confirmButton = {
                    Button(

                        onClick = {
                            openDialog.value = false
                        }) {
                        Text("OK")
                    }
                },
                /*dismissButton = {
                    Button(

                        onClick = {
                            openDialog.value = false
                        }) {
                        Text("This is the dismiss Button")
                    }
                }*/
            )
        }
    }
}
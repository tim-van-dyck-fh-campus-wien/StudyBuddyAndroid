package com.example.studybuddy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.R

@Composable
fun CreateStudyGroupsScreen(navController: NavHostController){
    DisplayBottomBar (navController = navController) { CreateStudyGroupsContent() }
}

@Composable
fun CreateStudyGroupsContent(){
    Column {
        Image(
            painter = painterResource(id = R.drawable.transparent_study_buddy),
            contentDescription = "StudyBuddyIcon"
        )
        Text(text = "CreateStudyGroupsScreen")
    }
}
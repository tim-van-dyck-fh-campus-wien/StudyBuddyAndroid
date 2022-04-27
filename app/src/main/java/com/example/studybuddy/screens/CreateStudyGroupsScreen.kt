package com.example.studybuddy.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.studybuddy.DisplayBottomBar

@Composable
fun CreateStudyGroupsScreen(navController: NavHostController){
    DisplayBottomBar (navController = navController) { CreateStudyGroupsContent() }
}

@Composable
fun CreateStudyGroupsContent(){
    Text(text = "CreateStudyGroupsScreen")
}
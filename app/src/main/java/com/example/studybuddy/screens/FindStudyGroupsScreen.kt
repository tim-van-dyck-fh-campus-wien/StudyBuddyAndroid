package com.example.studybuddy.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.studybuddy.DisplayBottomBar

@Composable
fun FindStudyGroupsScreen(navController: NavHostController){
    DisplayBottomBar (navController = navController) { FindStudyGroupsContent() }
}

@Composable
fun FindStudyGroupsContent(){
    Text(text = "FindStudyGroupsScreen")
}
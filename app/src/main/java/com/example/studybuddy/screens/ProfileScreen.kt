package com.example.studybuddy.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.studybuddy.DisplayBottomBar

@Composable
fun ProfileScreen(navController: NavHostController){
    DisplayBottomBar (navController = navController) { ProfileContent() }
}

@Composable
fun ProfileContent(){
    Text(text = "ProfileScreen")
}
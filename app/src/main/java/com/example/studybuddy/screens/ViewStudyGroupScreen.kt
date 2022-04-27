package com.example.studybuddy.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.DisplayBottomBar

@Composable
fun ViewStudyGroupScreen(navController: NavHostController){
    DisplayBottomBar (navController = navController) { ViewStudyGroupContent() }
}

@Composable
fun ViewStudyGroupContent(){
    Text(text = "ViewStudyGroupScreen")
}
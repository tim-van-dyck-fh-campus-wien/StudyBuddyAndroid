package com.example.studybuddy.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Preview
@Composable()
fun ViewStudyGroupScreen(navController: NavHostController = rememberNavController()){
    Text(text = "Test")
}
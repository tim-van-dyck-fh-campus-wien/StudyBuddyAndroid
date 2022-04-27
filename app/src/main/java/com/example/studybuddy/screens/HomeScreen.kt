package com.example.studybuddy.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.navigation.ScreenNames

@Composable
fun HomeScreen(navController: NavHostController){
    DisplayBottomBar (navController = navController) { HomeContent() }
}

@Composable
fun HomeContent(){
    Text(text = "HomeScreen")
}


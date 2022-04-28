package com.example.studybuddy.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.data.api.model.SingleStudyGroup
import com.example.studybuddy.data.api.model.filterGroup
import com.example.studybuddy.data.api.model.getDummyGroups



@Composable
fun ViewStudyGroupScreen(
    navController: NavHostController = rememberNavController(),
    studyGroupID: String? = "00001"){
    DisplayBottomBar (navController = navController) { ViewStudyGroupContent(studyGroup = filterGroup(studyGroupID = studyGroupID)) }
}

@Composable
fun ViewStudyGroupContent(studyGroup:SingleStudyGroup = getDummyGroups()[0]){
    Text(text = "ViewStudyGroupScreen ${studyGroup._id}, ${studyGroup.name}")
}
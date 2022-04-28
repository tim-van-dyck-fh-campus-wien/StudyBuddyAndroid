package com.example.studybuddy.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.data.dummies.DummyModel
import com.example.studybuddy.data.dummies.getDummyGroups
import com.example.studybuddy.navigation.ScreenNames
import com.example.studybuddy.widgets.DisplayArrowContent
import com.example.studybuddy.widgets.JoinButton
import com.example.studybuddy.widgets.StudyGroupRow

@Composable
fun HomeScreen(navController: NavHostController){
    DisplayBottomBar (navController = navController) { HomeContent() }
}

@Preview(showBackground = true)
@Composable
fun HomeContent(studyGroupList :List<DummyModel> = getDummyGroups(),
                navController: NavHostController = rememberNavController()){
    Column {
        Text(text = "HomeScreen")
        LazyColumn{
            items(studyGroupList){
                studyGroup ->
                StudyGroupRow(studyGroup = studyGroup,
                    onItemClick = {studyGroupId ->
                        navController.navigate(route= ScreenNames.ViewStudyGroup.name +"/$studyGroupId")}
                ) {
                    //test with join button, actually not needed here
                    JoinButton()
                }
            }
        }
       // StudyGroupRow()
    }

}


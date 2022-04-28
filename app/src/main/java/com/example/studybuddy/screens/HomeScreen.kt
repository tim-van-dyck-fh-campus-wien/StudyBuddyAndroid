package com.example.studybuddy.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.data.dummies.DummyModel
import com.example.studybuddy.data.dummies.getDummyGroups
import com.example.studybuddy.navigation.ScreenNames
import com.example.studybuddy.widgets.JoinButton
import com.example.studybuddy.widgets.StudyGroupRow

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()){
    DisplayBottomBar (navController = navController) {
        HomeContent(navController = navController, studyGroupList = getDummyGroups())
    }
}


@Composable
fun HomeContent(studyGroupList :List<DummyModel>,
                navController: NavHostController = rememberNavController()){
        Text(text = "HomeScreen")
        LazyColumn{
            items(studyGroupList){
                studyGroup ->
                StudyGroupRow(studyGroup = studyGroup,
                    onItemClick = {
                            studyGroupSingle ->
                            Log.d("navigation", "cur studyGroupID = $studyGroupSingle")
                            //navController.navigate(route= ScreenNames.ViewStudyGroupScreen.name)

                            navController.navigate(route= ScreenNames.ViewStudyGroupScreen.name +"/$studyGroupSingle")
                    }
                ) {
                    //test with join button, actually not needed here
                    //JoinButton()
                }
            }
        }
       // StudyGroupRow()


}


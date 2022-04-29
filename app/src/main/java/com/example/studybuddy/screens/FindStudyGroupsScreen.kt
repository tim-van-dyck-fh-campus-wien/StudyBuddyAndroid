package com.example.studybuddy.screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.data.api.model.getDummyGroups
import com.example.studybuddy.viewmodel.StudyGroupViewModel
import com.example.studybuddy.widgets.GroupButton
import com.example.studybuddy.widgets.StudyGroupRow

@Composable
fun FindStudyGroupsScreen(
    navController: NavHostController = rememberNavController(),
    studyGroupViewModel: StudyGroupViewModel
){
    DisplayBottomBar (navController = navController) {
        FindStudyGroupsContent(navController = navController, studyGroupViewModel = studyGroupViewModel)
    }
}


@Composable
fun FindStudyGroupsContent(
    navController: NavHostController,
    studyGroupViewModel: StudyGroupViewModel
){
    Text("FindStudyGroupsScreen")
    //Todo: make it actually communicate with the backend ^^
    val studyGroupList = studyGroupViewModel.studyGroupsSearchList.value
    Log.d("join", "${studyGroupViewModel.studyGroupsSearchList.value}")
    val studyGroupListDummy = getDummyGroups()
    if (studyGroupList != null){
    LazyColumn{
        items(studyGroupList){
            studyGroup ->
            StudyGroupRow(studyGroup = studyGroup){
                GroupButton(studyGroup = studyGroup, onButtonClicked =
                {
                    group ->
                    Log.d("join", "send join RQ to group $group ")
                },
                    text = "Send Join Request"
                ){}
            }
        }
    }
    }

}





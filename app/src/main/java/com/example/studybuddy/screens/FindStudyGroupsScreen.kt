package com.example.studybuddy.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.R
import com.example.studybuddy.data.api.model.SingleStudyGroup
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
    Column {
        Image(
            painter = painterResource(id = R.drawable.transparent_study_buddy),
            contentDescription = "StudyBuddyIcon"
        )
        //val studyGroupList = studyGroupViewModel.studyGroupsSearchList.value
        studyGroupViewModel.getAllStudyGroups()
        val studyGroupList = studyGroupViewModel.testList
        if (studyGroupList.isNullOrEmpty()){
            Log.d("join", "List is empty")
        } else {
            studyGroupList.forEach{it -> Log.d("join", "List content ${it}")}
            //val studyGroupListDummy = getDummyGroups()
            LazyColumn {
                items(studyGroupList) { studyGroup ->
                    StudyGroupRow(studyGroup = studyGroup) {
                        //Todo: Implement if clause, checking with backend, if student is already a member of the group
                        GroupButton(
                            studyGroup = studyGroup, onButtonClicked =
                            { group ->
                                Log.d("join", "send join RQ to group $group ")
                            },
                            text = "Send Join Request"
                        ) {}
                    }
                }
            }
        }

    }
}





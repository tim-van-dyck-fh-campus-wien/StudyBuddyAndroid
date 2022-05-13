package com.example.studybuddy.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.R
import com.example.studybuddy.data.api.model.SingleGroupId
import com.example.studybuddy.viewmodel.StudyGroupViewModel
import com.example.studybuddy.widgets.GroupButton
import com.example.studybuddy.widgets.StudyGroupRow

@Composable
fun FindStudyGroupsScreen(
    navController: NavHostController = rememberNavController(),
    studyGroupViewModel: StudyGroupViewModel
){
    DisplayBottomBar (navController = navController) {
        studyGroupViewModel.getAllStudyGroups()

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

        val studyGroupList = studyGroupViewModel.testList
        if (studyGroupList.isNullOrEmpty()){
            Log.d("join", "List is empty")
        } else {
            //studyGroupList.forEach{it -> Log.d("join", "List content ${it}")}
            //val studyGroupListDummy = getDummyGroups()
            LazyColumn {
                items(studyGroupList) { studyGroup ->
                    studyGroupViewModel.canStudentSendJoinRequest(SingleGroupId(studyGroup._id))
                    var joinButton = false
                    if (studyGroupViewModel.canSendRQ){
                        joinButton = true
                    } else if (!studyGroupViewModel.canSendRQ){
                        joinButton = false
                    }
                    StudyGroupRow(studyGroup = studyGroup) {
                        studyGroupViewModel.canStudentSendJoinRequest(SingleGroupId(studyGroup._id))
                        //Todo: improve Request Check - make canStudentSendJoinRequest return boolean
                        if (studyGroupViewModel.canSendRQ)
                            GroupButton(
                                studyGroup = studyGroup, onButtonClicked =
                                { group ->
                                    Log.d("join", "send join RQ to group $group ")
                                },
                                text = "Send Join Request"
                            ) {}
                        else if (!studyGroupViewModel.canSendRQ) {
                            Text("You are a member of this group")
                        }

                    }
                }
            }
        }

    }
}







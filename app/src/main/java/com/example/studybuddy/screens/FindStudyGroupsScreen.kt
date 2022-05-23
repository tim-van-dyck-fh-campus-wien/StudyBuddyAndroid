package com.example.studybuddy.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.R
import com.example.studybuddy.data.api.model.JoinRequest
import com.example.studybuddy.data.api.model.SingleGroupId
import com.example.studybuddy.viewmodel.AuthenticationViewModel
import com.example.studybuddy.viewmodel.StudyGroupViewModel
import com.example.studybuddy.widgets.GroupButton
import com.example.studybuddy.widgets.StudyGroupRow

@Composable
fun FindStudyGroupsScreen(
    navController: NavHostController = rememberNavController(),
    studyGroupViewModel: StudyGroupViewModel,
    authenticationViewModel: AuthenticationViewModel,

    ){
    DisplayBottomBar (navController = navController) {
       // studyGroupViewModel.getAllStudyGroups()
        var district = authenticationViewModel.profileData.location
        studyGroupViewModel.getFilteredStudyGroups(district = district)
        FindStudyGroupsContent(navController = navController, studyGroupViewModel = studyGroupViewModel)
    }
}


@Composable
fun FindStudyGroupsContent(
    navController: NavHostController,
    studyGroupViewModel: StudyGroupViewModel
) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.transparent_study_buddy),
            contentDescription = "StudyBuddyIcon"
        )
        //val studyGroupList = studyGroupViewModel.studyGroupsSearchList.value

        //val studyGroupList = studyGroupViewModel.testList
        if (studyGroupViewModel.filteredStudyGroupList.value.isNullOrEmpty()) {
            Log.d("join", "List is empty")
        } else {
            //studyGroupList.forEach{it -> Log.d("join", "List content ${it}")}
            //val studyGroupListDummy = getDummyGroups()
            LazyColumn {
                items(studyGroupViewModel.filteredStudyGroupList.value!!) { studyGroup ->
                    var joinButton by remember { mutableStateOf(false) }
                    //var clicked by remember{ mutableStateOf(false)}
                    studyGroupViewModel.canStudentSendJoinRequest(SingleGroupId(studyGroup._id),
                        callback = {
                            joinButton = it
                        })
                    StudyGroupRow(studyGroup = studyGroup) {
                        //Todo: improve Request Check - make canStudentSendJoinRequest return boolean
                        when (joinButton) {
                            true -> GroupButton(
                                studyGroup = studyGroup, onButtonClicked =
                                { group ->
                                    val joinRequest = JoinRequest(
                                        groupId = group,
                                        text = "Hi, I'd like to join your group! Have a great day.",
                                        sender_id = "",
                                        _id = ""
                                    )
                                    studyGroupViewModel.sendJoinRequest(joinRequest = joinRequest,
                                        callbackJoin = {
                                            if (it) {
                                                joinButton = !joinButton
                                            }
                                        })

                                    Log.d("join", "send join RQ to group $group ")
                                },
                                text = "Send Join Request"
                            ) {}
                            false -> {
                                Text(
                                    modifier = Modifier.padding(horizontal = 5.dp),
                                    text = "You're either a member of this group or have a pending request.",
                                    style = MaterialTheme.typography.caption,
                                    color = Color.Green
                                )
                            }
                        } /*else if (clicked){
                            Text(
                                modifier = Modifier.padding(horizontal = 5.dp),
                                text = "Your Join Request has been issued.",
                                style = MaterialTheme.typography.caption,
                                color = Color.Green
                            )
                        }*/

                    }
                }
            }
        }

    }

}






package com.example.studybuddy.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
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
import com.example.studybuddy.widgets.displayLogo

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
        FindStudyGroupsContent( studyGroupViewModel = studyGroupViewModel, district = district)
    }
}


@Composable
fun FindStudyGroupsContent(
    //navController: NavHostController,
    studyGroupViewModel: StudyGroupViewModel,
    district: String
) {
    Column {
        displayLogo()

        var allGroups by remember { mutableStateOf(false) }
        var showFilter by remember { mutableStateOf(false) }
        Surface(
            color = MaterialTheme.colors.background
        ) {
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .heightIn(min = 80.dp),
                shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                elevation = 6.dp
            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(5.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 17.dp)
                            .width(200.dp)
                    ) {
                        Button(
                            modifier = Modifier.padding(0.dp),
                            onClick = {
                                showFilter = !showFilter
                                Log.d("findStudyGroups", "showFilter is $showFilter")

                            }) {
                                if (!showFilter) {
                                    Text(text = "Change Location Filter")
                                }
                                if (showFilter) {Text(text = "Close Filter Menu")
                                }
                        }
                    }
                }
            }
        }
        when (showFilter) {
            false -> studyGroupViewModel.getFilteredStudyGroups(district = district)
            true -> FilterOptions(filter = true, setDistrict = {
                Log.d("findStudyGroups", "current district $it")
                allGroups = if (it == "no filter"){
                    studyGroupViewModel.getAllStudyGroups()
                    true
                } else {
                    studyGroupViewModel.getFilteredStudyGroups(it)
                    false
                }
            } )
        }
        when (allGroups) {
            false -> {
                Log.d("findStudyGroups", "allGroups is $allGroups, showing filtered result")
                Log.d("findStudyGroups", "filtered list: ${studyGroupViewModel.filteredStudyGroupList.value}")
                if (studyGroupViewModel.filteredStudyGroupList.value.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        text = "You see this, because we couldn't load any groups. Either try reload or be the first to create a group for this district.",
                        style = MaterialTheme.typography.body1
                    )
                    Log.d("join", "List is empty")
                } else {
                    LazyColumn {
                        items(studyGroupViewModel.filteredStudyGroupList.value!!) { studyGroup ->
                            var joinButton by remember { mutableStateOf(false) }
                            studyGroupViewModel.canStudentSendJoinRequest(SingleGroupId(studyGroup._id),
                                callback = {
                                    joinButton = it
                                })
                            StudyGroupRow(studyGroup = studyGroup) {
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
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            true -> {
                Log.d("findStudyGroups", "allGroups is $allGroups, showing all existing studygroups")
                if (studyGroupViewModel.studyGroupsSearchList.value.isNullOrEmpty()) {
                    Log.d("findStudyGroups", "StudyGroupslistList is empty")
                } else {
                    LazyColumn {
                        items(studyGroupViewModel.studyGroupsSearchList.value!!) { studyGroup ->
                            var joinButton by remember { mutableStateOf(false) }
                            studyGroupViewModel.canStudentSendJoinRequest(SingleGroupId(studyGroup._id),
                                callback = {
                                    joinButton = it
                                })
                            StudyGroupRow(studyGroup = studyGroup) {
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
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}


// creates the Radio Buttons to use for filtering districts
@Composable
fun FilterOptions(filter:Boolean, setDistrict:(String) -> Unit) {
    val filterOptions = listOf(
        "no filter", "1010", "1020", "1030", "1040", "1050", "1060", "1070", "1080", "1090", "1100",
        "1110", "1120", "1130", "1140", "1150", "1160", "1170", "1180", "1190", "1200", "1210", "1220", "1230"
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(filterOptions[23]) }
    Surface(
        color = MaterialTheme.colors.background
    ) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
            elevation = 6.dp
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(5.dp)
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 17.dp)
                        .width(200.dp)
                ) {
                    items(filterOptions) {
                            text ->
                        Row(
                            Modifier
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = { onOptionSelected(text) }
                                )
                                .padding(16.dp)
                        ) {
                            val context = LocalContext.current
                            RadioButton(
                                selected = (text == selectedOption),
                                modifier = Modifier.padding(all = Dp(value = 8F)),
                                onClick = {
                                    onOptionSelected(text)
                                    setDistrict(text)
                                    Toast.makeText(context, text, Toast.LENGTH_LONG)
                                        .show() //displays selection to user
                                }
                            )
                            Text(
                                text = text,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


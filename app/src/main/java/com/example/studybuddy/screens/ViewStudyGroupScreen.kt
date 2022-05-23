package com.example.studybuddy.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.model.ChangeableGroupData
import com.example.studybuddy.data.api.model.SingleGroupId
import com.example.studybuddy.data.api.model.SingleStudyGroup
import com.example.studybuddy.data.api.model.getDummyGroup
import com.example.studybuddy.navigation.ScreenNames
import com.example.studybuddy.viewmodel.StudyGroupViewModel
import com.example.studybuddy.widgets.*


@Composable
fun ViewStudyGroupScreen(
    navController: NavHostController = rememberNavController(),
    studyGroupID: String? = "00001",
    studyGroupViewModel: StudyGroupViewModel
){
    var currentGroup = getDummyGroup()
    // get the group for detailed StudyGroup Info, if null or something goes wrong, it will be the dummy group
    currentGroup = studyGroupID?.let {
        SingleGroupId(
            it
        )
    }?.let { studyGroupViewModel.detailedViewOfSingleStudyGroup(it) }!!

    DisplayBottomBar (navController = navController) {
        ViewStudyGroupContent(studyGroup = currentGroup, navController = navController, studyGroupViewModel = studyGroupViewModel) }
}

//@Preview
@Composable
fun ViewStudyGroupContent(
    studyGroup: SingleStudyGroup,
    navController: NavHostController,
    studyGroupViewModel: StudyGroupViewModel
) {
    var admin by remember { mutableStateOf(false) }
    studyGroupViewModel.isUserAdmin(singleGroupId = SingleGroupId(studyGroup._id), callbackAdmin = {
        admin = it
    })
    var displayAdminStuff by remember { mutableStateOf(false) }
    Log.d("ViewStudyGroup", "admin = $admin")
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Surface(
            color = MaterialTheme.colors.background
        ) {

            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .heightIn(min = 130.dp, max = 700.dp),

                shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                elevation = 6.dp
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(5.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 17.dp)
                            .width(250.dp)
                    ) {
                        // this displays generally visible StudyGroup Info
                        DisplayGeneralGroupTextInfo(studyGroup = studyGroup)
                        Surface() {
                            Column() {
                                Text(
                                    modifier = Modifier.padding(horizontal = 5.dp),
                                    text = "Current Group Members",
                                    style = MaterialTheme.typography.subtitle2
                                )
                                DisplayGroupMembers(studyGroup = studyGroup)
                                if (admin) {
                                    Button(
                                        modifier = Modifier.padding(10.dp),
                                        onClick = {
                                            displayAdminStuff = !displayAdminStuff
                                        }) {
                                        if (displayAdminStuff) {
                                            Text("Cancel Update")
                                        } else if (!displayAdminStuff) {
                                            Text(text = "Update Group Data")
                                        }
                                    }
                                }
                            }
                        }
                    }

                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(vertical = 20.dp)
                        ) {
                            //DisplayStudyGroupIcon(studyGroup = studyGroup)
                            //groupIcon(ApiConstants.IMG_BASE_URL+studyGroup.icon)
                            groupIconWithElevation(
                                url = ApiConstants.IMG_BASE_URL + studyGroup.icon,
                                iconSize = 120
                            )


                        }
                    }


                }

            }

            if (displayAdminStuff) {
                DesignForWidgets() {
                    form(onSubmit = { groupname, description, topic, location ->
                        studyGroupViewModel.updateGroupData(changeableGroupData = ChangeableGroupData(
                            groupName = groupname,
                            description = description,
                            topic = topic,
                            location = location, groupId = studyGroup._id
                        ),
                            callbackChangedData = {
                                displayAdminStuff = !it             //if successful, it closes
                                navController.navigate(route = ScreenNames.ViewStudyGroupScreen.name + "/${studyGroup._id}")
                            })
                    })
                }

            }
            Row {
                Surface() {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        //.heightIn(min = 50.dp),

                        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                        elevation = 6.dp
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .padding(horizontal = 15.dp, vertical = 17.dp)
                            ) {
                                Text(
                                    modifier = Modifier.padding(horizontal = 5.dp),
                                    text = "Group Messages",
                                    style = MaterialTheme.typography.subtitle2
                                )
                                DisplayMessaging(studyGroup = studyGroup)
                                Row() {
                                    DisplayInputTextFieldAndSendButton(studyGroup = studyGroup)
                                }
                            }
                        }
                    }

                }
            }

        }
    }


@Composable
fun DesignForWidgets(content: @Composable () -> Unit = {}){
    Row {
        Surface() {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                //.heightIn(min = 50.dp),

                shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                elevation = 6.dp
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(5.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 17.dp)
                    ) {
                        content()
                    }
                }
            }
        }
    }
}

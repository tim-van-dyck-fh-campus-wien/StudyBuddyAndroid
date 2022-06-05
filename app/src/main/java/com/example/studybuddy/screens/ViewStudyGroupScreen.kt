package com.example.studybuddy.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.model.*
import com.example.studybuddy.navigation.ScreenNames
import com.example.studybuddy.viewmodel.AdminViewModel
import com.example.studybuddy.viewmodel.StudyGroupViewModel
import com.example.studybuddy.widgets.*
import io.reactivex.Single


@Composable
fun ViewStudyGroupScreen(
    navController: NavHostController = rememberNavController(),
    studyGroupID: String? = "00001",
    studyGroupViewModel: StudyGroupViewModel,
    adminViewModel: AdminViewModel
){
    var currentGroup = getDummyGroup()
    //var joinRQs = listOf<JoinRequestsReceivedForAdmin>()
    // get the group for detailed StudyGroup Info, if null or something goes wrong, it will be the dummy group
    currentGroup = studyGroupID?.let {
        SingleGroupId(
            it
        )
    }?.let { studyGroupViewModel.detailedViewOfSingleStudyGroup(it)}!!
    var admin by remember { mutableStateOf(false) }
    var joinRQs by remember{ mutableStateOf(listOf<JoinRequestsReceivedForAdmin>())}
    //studyGroupViewModel.detailedViewOfSingleStudyGroup(SingleGroupId(currentGroup._id))
    adminViewModel.isUserAdmin(singleGroupId = SingleGroupId(currentGroup._id), callbackAdmin = {
        admin = it
    })
    if(admin){adminViewModel.getJoinRequests(singleGroupId = SingleGroupId(currentGroup._id))
    {list -> joinRQs = list}
    }
   // Log.i("ViewStudyGroupScreen", "list of joinRQs = $joinRQs")
    DisplayBottomBar (navController = navController) {
        ViewStudyGroupContent(admin = admin, studyGroup = currentGroup, navController = navController, studyGroupViewModel = studyGroupViewModel, adminViewModel = adminViewModel, joinRequests = joinRQs) }
}

//@Preview
@Composable
fun ViewStudyGroupContent(
    studyGroup: SingleStudyGroup,
    navController: NavHostController,
    adminViewModel: AdminViewModel,
    admin: Boolean,
    joinRequests: List<JoinRequestsReceivedForAdmin>,
    studyGroupViewModel: StudyGroupViewModel
) {
    //studyGroupViewModel.getJoinRequests(singleGroupId = SingleGroupId(studyGroup._id))
    var displayAdminStuff by remember { mutableStateOf(false) }
    var studyGroupLocal by remember { mutableStateOf(listOf<Message>())    }
    studyGroupLocal = studyGroup.messages
    //var studyGroupLocalMessages = studyGroup.messages
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
                        Surface(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            Column(modifier = Modifier.heightIn(min = 200.dp, max=450.dp)) {
                                Text(
                                    modifier = Modifier.padding(horizontal = 5.dp),
                                    text = "Current Group Members",
                                    style = MaterialTheme.typography.subtitle2
                                )
                                /*Todo: Change this to its own widget, where I can choose to get the info displayed*/
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
                    adminViewModel.updateGroupData(changeableGroupData = ChangeableGroupData(
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
        DesignForWidgets {
            Text(
                modifier = Modifier.padding(horizontal = 5.dp),
                text = "Group Messages",
                style = MaterialTheme.typography.subtitle2
            )
            DisplayMessaging(messages = studyGroupLocal)
            Row() {
                DisplayInputTextFieldAndSendButton(studyGroup = studyGroup){
                    message ->
                    //Todo : update messages in message display as well
                    studyGroupViewModel.sendMessageToGroup(message = message, callbackMessage = {studyGroupLocal = it})
                }
            }
        }
        var showRequests by remember { mutableStateOf(false) }
        var showButtons by remember { mutableStateOf(true)}
        if (admin) {
            when (showRequests) {
                true -> {
                    DesignForWidgets() {
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
                        Button(onClick = { showRequests = !showRequests }) {
                            if (!showRequests) {
                                Text(text = "Check Join Requests")
                            }
                            if (showRequests) {
                                Text(text = "Close Join Requests")
                            }
                        }}}
                        Surface(modifier = Modifier.heightIn(min = 50.dp, max = 150.dp)) {
                            LazyColumn {
                                items(joinRequests) { rq ->
                                    Log.i("myStudyGroups", "show request $rq")
                                    DisplayJoinRQ(joinRequestsReceivedForAdmin = rq)
                                    Row(
                                        horizontalArrangement = Arrangement.End,
                                        modifier = Modifier
                                            .padding(horizontal = 15.dp, vertical = 17.dp)
                                    ) {
                                        if (showButtons) {
                                            Button(
                                                onClick = {
                                                    val request = AcceptDeclineJR(
                                                        groupId = studyGroup._id,
                                                        joinRequestId = rq._id,
                                                        accept = true
                                                    );
                                                    adminViewModel.acceptOrDeclineJoinRequest(
                                                        request
                                                    ) {
                                                        if (it) {
                                                            showButtons = false
                                                            //studyGroupViewModel.detailedViewOfSingleStudyGroup(groupId = SingleGroupId(studyGroup._id))
                                                        }
                                                    }
                                                },
                                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                                            ) {
                                                Text(text = "Accept")
                                            }
                                            Button(
                                                onClick = {
                                                    val request = AcceptDeclineJR(
                                                        groupId = studyGroup._id,
                                                        joinRequestId = rq._id,
                                                        accept = false
                                                    );
                                                    adminViewModel.acceptOrDeclineJoinRequest(
                                                        request
                                                    ) {
                                                        if (it) {
                                                            showButtons = false
                                                        }
                                                    }
                                                },
                                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                                            ) {
                                                Text(text = "Decline")
                                            }
                                        } else if (!showButtons) {
                                            Text(
                                                modifier = Modifier.padding(horizontal = 5.dp),
                                                text = "Your Response has been processed.",
                                                style = MaterialTheme.typography.caption
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                false -> DesignForWidgets() {
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
                    Button(onClick = { showRequests = !showRequests }) {
                        if (!showRequests) {
                            Text(text = "Check Join Requests")
                        }
                        if (showRequests) {
                            Text(text = "Close Join Requests")
                        }
                    }

                }}}
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




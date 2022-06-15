package com.example.studybuddy.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
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
    adminViewModel: AdminViewModel,
    username :String
){

    // get the group for detailed StudyGroup Info, if null or something goes wrong, it will be the dummy group
    var id = studyGroupID?.let {
        SingleGroupId(
            it
        )
    }
    var admin by remember { mutableStateOf(false) }
    var joinRQs by remember{ mutableStateOf(listOf<JoinRequestsReceivedForAdmin>())}
    Log.i("ViewStudyGroupScreen", "current ID in here is $id")
    if (id != null) {
        studyGroupViewModel.detailedViewOfSingleStudyGroup(id)
        adminViewModel.isUserAdmin(singleGroupId = id, callbackAdmin = {
            admin = it
        })
        if(admin){adminViewModel.getJoinRequests(singleGroupId = id)
        {list -> joinRQs = list}
        }
    }


    Scaffold(
        //modifier = Modifier.background(color = Color.Cyan),
        topBar = {
            TopAppBar( elevation = 3.dp){
                Row (verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.clickable {
                            navController.popBackStack()        //goes back to last screen
                        })
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
        }
    ) {
   // Log.i("ViewStudyGroupScreen", "list of joinRQs = $joinRQs")
    DisplayBottomBar (navController = navController) {
        ViewStudyGroupContent(admin = admin, username = username, navController = navController, studyGroupViewModel = studyGroupViewModel, adminViewModel = adminViewModel, joinRequests = joinRQs)
    }
    }}
//}

//@Preview
@Composable
fun ViewStudyGroupContent(
    //studyGroup: SingleStudyGroup,
    navController: NavHostController,
    adminViewModel: AdminViewModel,
    admin: Boolean,
    joinRequests: List<JoinRequestsReceivedForAdmin>,
    studyGroupViewModel: StudyGroupViewModel, username:String
) {
    //studyGroupViewModel.getJoinRequests(singleGroupId = SingleGroupId(studyGroup._id))
    var displayAdminStuff by remember { mutableStateOf(false) }
    var displayGroupMembers by remember { mutableStateOf(false) }
    var displayGroupMessages by remember { mutableStateOf(false) }



    if (studyGroupViewModel.singleGroup.value == null) {
        Text("Sorry, something went wrong")
    } else if (studyGroupViewModel.singleGroup.value != null){
        var studyGroup = studyGroupViewModel.singleGroup.value!!
        Log.d("ViewStudyGroup", "admin = $admin")
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
        ) {
            Surface(
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    DesignForWidgets() {
                        Text(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = studyGroup.name,
                            style = MaterialTheme.typography.h3,
                            fontStyle = FontStyle.Normal
                        )
                    }
                }

            }

            DesignForWidgets() {
                // this displays generally visible StudyGroup Info
                DisplayGeneralGroupTextInfo(studyGroup = studyGroup, showHeading = false)
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 20.dp)
                ) {
                    groupIconWithElevation(
                        url = ApiConstants.IMG_BASE_URL + studyGroup.icon,
                        iconSize = 120
                    )
                }
            }


            DesignForWidgets() {

                /**Display Group Members*/
                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .width(250.dp),
                    onClick = {
                        displayGroupMembers = !displayGroupMembers
                    }) {
                    if (displayGroupMembers) {
                        Text("Hide Group Members")
                    } else if (!displayGroupMembers) {
                        Text(text = "Show Group Members")
                    }
                }

                val studyGroupLocal by studyGroupViewModel.members.observeAsState()
                if (displayGroupMembers) {
                    DesignForWidgets() {
                        Text(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = "Current Group Members",
                            style = MaterialTheme.typography.subtitle2
                        )
                        DisplayGroupMembers(
                            studyGroupID = SingleGroupId(studyGroup._id),
                            studyGroupMembers = studyGroupLocal!!,
                            admin = admin,
                            adminViewModel = adminViewModel,
                            onDelete = {
                                //var index = studyGroupViewModel.members.indexOf(it)
                                var list = studyGroupViewModel.members.value?.toMutableList()
                                var index = list?.indexOf(it)
                                if (list != null) {
                                    if (index != null) {
                                        list.removeAt(index)
                                    }
                                }
                                if (list != null) {
                                    studyGroupViewModel.members.value = list.toList()
                                }
                                Log.i("ViewStudyGroupScreen", "StudyGroup Member List Updated ${studyGroupViewModel.members.value}")
                            },
                            content = {}
                        )
                    }
                }
                /**Display Group Messages*/
                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .width(250.dp),
                    onClick = {
                        displayGroupMessages = !displayGroupMessages
                    }) {
                    if (displayGroupMessages) {
                        Text("Hide Group Messages")
                    } else if (!displayGroupMessages) {
                        Text(text = "Show Group Messages")
                    }
                }
                var success by remember { mutableStateOf(false) }
                val list by studyGroupViewModel.messages.observeAsState()
                if (displayGroupMessages) {
                    DesignForWidgets {
                        Text(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = "Group Messages",
                            style = MaterialTheme.typography.subtitle2
                        )
                        if (list == null) {
                            Log.d("ViewStudyGroupScreen", "Messaging is empty")
                        } else {
                            DisplayMessaging(messages = list)
                            Row() {
                                DisplayInputTextFieldAndSendButton(
                                    studyGroup = studyGroup,
                                    username = username
                                ) { message ->
                                    studyGroupViewModel.sendMessageToGroup(
                                        message = message,
                                        callbackMessage = {
                                            success = it
                                            if (it) {
                                                studyGroupViewModel.messages.value =
                                                    studyGroupViewModel.messages.value?.plus(
                                                        message
                                                    ) ?: listOf(message)
                                                Log.i(
                                                    "ViewStudygroupscreen",
                                                    "updated messages ${list}"
                                                )
                                            }
                                            Log.i(
                                                "viewStudyGroupScreen",
                                                "success send message = $success"
                                            )
                                        })
                                    if (success) {
                                        success = !success
                                    }
                                }
                            }
                        }
                    }
                }

                /** Display Admin Stuff*/
                if (admin) {

                    //Group Data Update
                    Button(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(250.dp),
                        onClick = {
                            displayAdminStuff = !displayAdminStuff
                        }) {
                        if (displayAdminStuff) {
                            Text("Cancel Update")
                        } else if (!displayAdminStuff) {
                            Text(text = "Update Group Data")
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
                                        displayAdminStuff =
                                            !it             //if successful, it closes
                                        navController.navigate(route = ScreenNames.ViewStudyGroupScreen.name + "/${studyGroup._id}")
                                    })
                            })
                        }
                    }
                    //Join Requests
                    var showRequests by remember { mutableStateOf(false) }
                    var showButtons by remember { mutableStateOf(true) }
                    Button(modifier = Modifier
                        .padding(10.dp)
                        .width(250.dp),
                        onClick = { showRequests = !showRequests }) {
                        if (!showRequests) {
                            Text(text = "Check Join Requests")
                        }
                        if (showRequests) {
                            Text(text = "Close Join Requests")
                        }
                    }
                    when (showRequests) {
                        true -> {
                            DesignForWidgets() {
                                Surface(modifier = Modifier.heightIn(min = 50.dp, max = 150.dp)) {
                                    if (joinRequests.isNullOrEmpty()) {

                                        Text("No open JoinRequests")

                                    } else {
                                        LazyColumn {
                                            items(joinRequests) { rq ->
                                                Log.i("myStudyGroups", "show request $rq")
                                                DisplayJoinRQ(joinRequestsReceivedForAdmin = rq)
                                                Row(
                                                    horizontalArrangement = Arrangement.End,
                                                    modifier = Modifier
                                                        .padding(
                                                            horizontal = 15.dp,
                                                            vertical = 17.dp
                                                        )
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
                                                                        studyGroupViewModel.detailedViewOfSingleStudyGroup(groupId = SingleGroupId(studyGroup._id))
                                                                    }
                                                                }
                                                            },
                                                            colors = ButtonDefaults.buttonColors(
                                                                backgroundColor = Color.Green
                                                            )
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
                                                            colors = ButtonDefaults.buttonColors(
                                                                backgroundColor = Color.Red
                                                            )
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
                        }
                    }
                }

            }
        }
    }
}









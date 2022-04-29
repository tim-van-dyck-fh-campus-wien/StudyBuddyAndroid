package com.example.studybuddy.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.data.api.model.SingleStudyGroup
import com.example.studybuddy.data.api.model.filterGroup
import com.example.studybuddy.data.api.model.getDummyGroups
import com.example.studybuddy.widgets.*


@Composable
fun ViewStudyGroupScreen(
    navController: NavHostController = rememberNavController(),
    studyGroupID: String? = "00001"){
    DisplayBottomBar (navController = navController) { ViewStudyGroupContent(studyGroup = filterGroup(studyGroupID = studyGroupID)) }
}

@Preview
@Composable
fun ViewStudyGroupContent(studyGroup:SingleStudyGroup = getDummyGroups()[1]) {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState()) ){
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
                            }
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Top
                    ) {
                        DisplayStudyGroupIcon(studyGroup = studyGroup)
                    }
                }


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
                            Row (){
                                DisplayInputTextFieldAndSendButton(studyGroup = studyGroup)
                            }
                        }
                    }
                }

            }
        }

    }
}

package com.example.studybuddy.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.R

import com.example.studybuddy.navigation.ScreenNames
import com.example.studybuddy.viewmodel.StudyGroupViewModel
import com.example.studybuddy.widgets.DesignForWidgets
import com.example.studybuddy.widgets.StudyGroupRow
import com.example.studybuddy.widgets.displayLogo

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    studyGroupViewModel: StudyGroupViewModel
){
    DisplayBottomBar (navController = navController) {
        studyGroupViewModel.getOnlyMyGroups()
        Log.i("HomeScreen", "My StudyGroups are ${studyGroupViewModel.myGroupList.value}")
        HomeContent(navController = navController, studyGroupViewModel = studyGroupViewModel)
    }
}


@Composable
fun HomeContent(navController: NavHostController = rememberNavController(),
                studyGroupViewModel: StudyGroupViewModel
                ){
    Column {
        /* Image(
            painter = painterResource(id = R.drawable.transparent_study_buddy_backup),
            contentDescription = "StudyBuddyIcon"
        )*/
        displayLogo()
        //studyGroupViewModel.getOnlyMyGroups()
        DesignForWidgets() {
            if (studyGroupViewModel.myGroupList.value.isNullOrEmpty()) {
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = "You see this, because we couldn't load your groups. Either try reload or create new groups to find study buddies.",
                    style = MaterialTheme.typography.body1
                )
                Log.d("HomeScreen", "List is empty")
            } else {
            LazyColumn {
                    items(studyGroupViewModel.myGroupList.value!!) { studyGroup ->
                        Log.i("myStudyGroups", "show group $studyGroup")
                        StudyGroupRow(
                            studyGroup = studyGroup,
                            onItemClick = { studyGroupSingle ->
                                Log.d("navigation", "cur studyGroupID = ${studyGroupSingle}")
                                navController.navigate(route = ScreenNames.ViewStudyGroupScreen.name + "/${studyGroupSingle}")
                            },
                            {},
                        )
                    }
                }
            }
        }
    }
}
       // StudyGroupRow()





package com.example.studybuddy.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
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
            LazyColumn {
                if (studyGroupViewModel.myGroupList.value.isNullOrEmpty()) {
                    Log.d("HomeScreen", "List is empty")
                } else {
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





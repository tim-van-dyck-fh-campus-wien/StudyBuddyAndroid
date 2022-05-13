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
import com.example.studybuddy.data.api.model.SingleStudyGroup

import com.example.studybuddy.navigation.ScreenNames
import com.example.studybuddy.viewmodel.StudyGroupViewModel
import com.example.studybuddy.widgets.StudyGroupRow

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    studyGroupViewModel: StudyGroupViewModel
){
    DisplayBottomBar (navController = navController) {
        studyGroupViewModel.getOnlyMyGroups()
        HomeContent(navController = navController, studyGroupList = studyGroupViewModel.myGroupList)
    }
}


@Composable
fun HomeContent(studyGroupList :List<SingleStudyGroup>,
                navController: NavHostController = rememberNavController()){
    Column {
        Image(
            painter = painterResource(id = R.drawable.transparent_study_buddy),
            contentDescription = "StudyBuddyIcon"
        )
        LazyColumn {
            items(studyGroupList) { studyGroup ->
                Log.i("myStudyGroups", "show group $studyGroup")
                StudyGroupRow(
                    studyGroup = studyGroup,
                    onItemClick = { studyGroupSingle ->
                        Log.d("navigation", "cur studyGroupID = ${studyGroupSingle}")
                        navController.navigate(route = ScreenNames.ViewStudyGroupScreen.name + "/${studyGroupSingle}")
                    },
                    {
                    },
                )
            }
        }
    }
       // StudyGroupRow()


}


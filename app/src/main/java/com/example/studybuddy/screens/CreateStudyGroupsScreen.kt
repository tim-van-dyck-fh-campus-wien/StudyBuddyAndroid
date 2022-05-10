package com.example.studybuddy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.R
import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.viewmodel.CreateStudyGroupViewModel
import com.example.studybuddy.widgets.groupIcon

@Composable
fun CreateStudyGroupsScreen(
    navController: NavHostController,
    studyGroupViewModel: CreateStudyGroupViewModel
){
    DisplayBottomBar (navController = navController) { CreateStudyGroupsContent(studyGroupViewModel = studyGroupViewModel) }

}

@Composable
fun CreateStudyGroupsContent(studyGroupViewModel: CreateStudyGroupViewModel){
    studyGroupViewModel.getAvailableGroupimages()
    Column {
        Image(
            painter = painterResource(id = R.drawable.transparent_study_buddy),
            contentDescription = "StudyBuddyIcon"
        )
        displayGroupImages(urls = studyGroupViewModel.availableGroupImages.value,selectedIcon = studyGroupViewModel.selectedIconUrl.value){
            studyGroupViewModel.setSelectedIcon(it)
        }
    }
}
@Composable
fun displayGroupImages(urls: List<String>?,selectedIcon:String, iconSelected:(String)->(Unit)={}){
    if(urls!=null){
        LazyRow {
            items(urls) { url ->
                var isIconSelected by remember{ mutableStateOf(false) }
                if(selectedIcon==url){
                    isIconSelected=true
                }else{
                    isIconSelected=false
                }
                groupIconCard(url = url, isIconSelected = isIconSelected,iconSelected = iconSelected )
            }
        }
    }
}
@Composable fun groupIconCard(url:String, isIconSelected:Boolean,iconSelected:(String)->Unit={}){
    var cardColor = Color.White
    if(isIconSelected){
        cardColor = Color.Magenta
    }
    Card(elevation = 7.dp,backgroundColor = cardColor,modifier = Modifier
        .padding(4.dp)
        .clickable(onClick = {
            iconSelected(url)
        })) {
        groupIcon(url = ApiConstants.IMG_BASE_URL+url)
    }
}

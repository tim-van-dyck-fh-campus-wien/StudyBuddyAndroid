package com.example.studybuddy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.R
import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.model.LoginData
import com.example.studybuddy.navigation.ScreenNames
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
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.transparent_study_buddy),
            contentDescription = "StudyBuddyIcon"
        )
        Text("Please select an icon for your study group:")
        displayGroupImages(urls = studyGroupViewModel.availableGroupImages.value,selectedIcon = studyGroupViewModel.selectedIconUrl.value){
            studyGroupViewModel.setSelectedIcon(it)
        }
        form()
    }
}
@Composable
fun displayGroupImages(urls: List<String>?,selectedIcon:String, iconSelected:(String)->(Unit)={}){
    if(urls!=null){
        LazyRow {
            items(urls) { url ->
                var isIconSelected by remember{ mutableStateOf(false) }
                isIconSelected = selectedIcon==url
                groupIconCard(url = url, isIconSelected = isIconSelected,iconSelected = iconSelected )
            }
        }
    }
}
@Composable fun groupIconCard(url:String, isIconSelected:Boolean,iconSelected:(String)->Unit={}){
    var cardColor = MaterialTheme.colors.background
    if(isIconSelected){
        cardColor = MaterialTheme.colors.primary
    }
    Card(elevation = 7.dp,backgroundColor = cardColor,modifier = Modifier
        .padding(4.dp)
        .clickable(onClick = {
            iconSelected(url)
        })) {
        groupIcon(url = ApiConstants.IMG_BASE_URL+url)
    }
}
@Composable
fun form(){
    var groupname by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    OutlinedTextField(
        value = groupname,
        onValueChange = { value -> groupname = value },
        label = { Text(text = "Group Name") }
    )

    OutlinedTextField(
        value = description,
        onValueChange = { value -> description = value },
        label = { Text(text = "Group Description") }
    )

    OutlinedTextField(
        value = location,
        onValueChange = { value -> location = value },
        label = { Text(text = "District") }
    )
    Button(
        modifier = Modifier.padding(16.dp),
        onClick = {
           /*todo add behaviour*/
        }) {
        Text(text = "Submit")
    }
}

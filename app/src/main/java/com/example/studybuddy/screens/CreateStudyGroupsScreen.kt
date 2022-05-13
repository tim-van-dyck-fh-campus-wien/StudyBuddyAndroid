package com.example.studybuddy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.studybuddy.DisplayBottomBar
import com.example.studybuddy.R
import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.navigation.ScreenNames
import com.example.studybuddy.viewmodel.CreateStudyGroupViewModel
import com.example.studybuddy.viewmodel.StudyGroupViewModel
import com.example.studybuddy.widgets.TextFieldCloseOnEnter
import com.example.studybuddy.widgets.groupIcon

@Composable
fun CreateStudyGroupsScreen(
    navController: NavHostController,
    createStudyGroupViewModel: CreateStudyGroupViewModel,
    studyGroupViewModel: StudyGroupViewModel
){
    DisplayBottomBar (navController = navController) { CreateStudyGroupsContent(
        createStudyGroupViewModel = createStudyGroupViewModel,
        navController=navController
    ) }

}

@Composable
fun CreateStudyGroupsContent(
    createStudyGroupViewModel: CreateStudyGroupViewModel,
    navController: NavHostController,
){
    createStudyGroupViewModel.getAvailableGroupimages()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.transparent_study_buddy),
            contentDescription = "StudyBuddyIcon"
        )
        Text("Please select an icon for your study group:")
        displayGroupImages(urls = createStudyGroupViewModel.availableGroupImages.value,selectedIcon = createStudyGroupViewModel.selectedIconUrl.value){
            createStudyGroupViewModel.setSelectedIcon(it)
        }
        /*called when submit button is clicked!*/
        form(onSubmit = {groupname, description, topic, location ->
            createStudyGroupViewModel.createStudyGroup(groupname = groupname,
                description = description,
                topic = topic,
                location=location,
                onSuccess ={
                    navController.navigate(route = ScreenNames.ViewStudyGroupScreen.name + "/${createStudyGroupViewModel.studyGroupId._id}")
                } )
        })
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
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun form(groupname:String="",description:String="",topic:String="",location:String="",onSubmit:(groupname:String,description:String,topic:String,location:String)->Unit){
    var groupname by remember { mutableStateOf(groupname) }
    var description by remember { mutableStateOf(description) }
    var topic by remember { mutableStateOf(topic) }

    var location by remember { mutableStateOf(location) }
    TextFieldCloseOnEnter(value=groupname,label="Group Name",maxLength = 20,onValueChange = {groupname=it})
    TextFieldCloseOnEnter(value=description,label="Group Description",maxLength = 150,onValueChange = {description=it})
    TextFieldCloseOnEnter(value=topic,label="Group Topic",maxLength = 50,onValueChange = {topic=it})
    TextFieldCloseOnEnter(value=location,label="ZIP of district",maxLength = 4,onValueChange = {location=it})
    Button(
        modifier = Modifier.padding(16.dp),
        onClick = {
           onSubmit(groupname, description, topic, location)
        }) {
        Text(text = "Submit")
    }
}


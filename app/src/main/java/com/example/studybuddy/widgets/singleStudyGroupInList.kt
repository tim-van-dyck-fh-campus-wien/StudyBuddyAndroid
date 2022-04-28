package com.example.studybuddy.widgets

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.studybuddy.data.api.model.SingleStudyGroup
import com.example.studybuddy.data.dummies.DummyModel
import com.example.studybuddy.data.dummies.getDummyGroups


/**
 * This function shows a simple display of a Study Group in a List of Study Groups
 * Intended for Group Lists
 */
@Preview
@Composable
fun StudyGroupRow(
            studyGroup: DummyModel = getDummyGroups()[0],
            onItemClick: (String) -> Unit = {},
            content: @Composable () -> Unit = {}
            //test content for JoinStudyGroup

            //onJoinButtonClick: (String) -> Unit = {},

) {
    var showContent by remember {
        mutableStateOf(false)
    }

    Column() {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .heightIn(min = 130.dp)
                    .clickable {
                        Log.d("navigation", "cur StudyGroup is: ${studyGroup._id}")
                        onItemClick(studyGroup._id)  //damit ich single study group besuchen kann (?) - funktioniert jetzt evtl anders
                    },
                shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                elevation = 6.dp
            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(5.dp)
                        ) {

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 17.dp)
                            .width(200.dp)


                    ) {

                        Text(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = studyGroup.name,
                            style = MaterialTheme.typography.h5,
                            fontStyle = FontStyle.Italic
                        )
                        Divider(modifier = Modifier.padding(5.dp))
                        Text(modifier = Modifier.padding(horizontal = 5.dp),text = studyGroup.topic, style = MaterialTheme.typography.caption)
                        Divider(modifier = Modifier.padding(5.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = "Located in: ${studyGroup.location}",
                            style = MaterialTheme.typography.caption
                        )

                        // show Content Icon is changed here, as well as Boolean value
                        when (showContent) {
                            true -> Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = "Arrow Up",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable(onClick = { showContent = !showContent })
                            )
                            false -> Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Arrow Down",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable(onClick = { showContent = !showContent })
                            )

                        }
                        // actual Content is defined here, depending on current state of showContent
                        // todo: rewrite into modular version with extra button to join to use join button flexibly where needed
                        // see Work in Progress below, needs testing
                       /** Column(modifier = Modifier.width(200.dp)) {
                            AnimatedVisibility(
                                visible = showContent,
                                enter = expandVertically(expandFrom = Alignment.Top)
                            ) {
                                Column() {
                                    Divider(modifier = Modifier.padding(5.dp))

                                    Text(
                                        modifier = Modifier.padding(horizontal = 5.dp),
                                        text = "How we describe ourselves: ${studyGroup.description}",
                                        style = MaterialTheme.typography.caption)
                                    Divider(modifier = Modifier.padding(5.dp))

                                    Button(
                                        modifier = Modifier.padding(horizontal = 5.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                                        onClick = { onJoinButtonClick(studyGroup._id) }) {
                                        Text(text = "Send Join Request")
                                    }

                                    Divider(modifier = Modifier.padding(5.dp))
                                }

                            }
                        }*/

                       // content()
                        //rewrite this in content when called
                        DisplayArrowContent(showContent = showContent, studyGroup = studyGroup){
                            content()

                            //JoinButton(studyGroup = studyGroup, /*todo: onButtonClicked*/ )
                        }
                        }
                    Column(modifier = Modifier
                        .padding(5.dp)){
                        DisplayStudyGroupIcon(studyGroup = studyGroup)
                    }
                    }



                }

            }

        }
    }

@Composable
fun DisplayStudyGroupIcon(studyGroup: DummyModel = getDummyGroups()[0]){
    // PICTURE
    Surface(

        modifier = Modifier
            .size(120.dp)
            .padding(12.dp),
        //.fillMaxHeight(),
        shape = RoundedCornerShape(corner= CornerSize(6.dp)),
        color = Color.LightGray,
        elevation = 6.dp,

    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                //.data(studyGroup.icon)
                .data(Icon(modifier = Modifier
                    .padding(5.dp),imageVector = Icons.Default.Edit, contentDescription = "IconDummy"))
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(RoundedCornerShape(corner= CornerSize(6.dp)))
        )

    }
}


@Composable
fun DisplayArrowContent(studyGroup: DummyModel = getDummyGroups()[0], showContent:Boolean,
                        //content for Button to Join Group - Button should be used, where List is called from
                        //search, but is not needed from List of my Study Groups
                        content: @Composable () -> Unit = {}
                        ) {
    Column(modifier = Modifier.width(200.dp)) {
        AnimatedVisibility(
            visible = showContent,
            enter = expandVertically(expandFrom = Alignment.Top)
        ) {
            Column() {
                Divider(modifier = Modifier.padding(5.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = "How we describe ourselves: ${studyGroup.description}",
                    style = MaterialTheme.typography.caption
                )
                Divider(modifier = Modifier.padding(5.dp))

                content()

                //Divider(modifier = Modifier.padding(5.dp))
            }

        }
    }
}
    @Composable
    fun JoinButton(
        studyGroup: DummyModel = getDummyGroups()[0],
        onButtonClicked: (String) -> Unit = {},
    ) {
        Button(
            modifier = Modifier.padding(horizontal = 5.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            onClick = { onButtonClicked(studyGroup._id) }) {
            Text(text = "Send Join Request")
        }
    }



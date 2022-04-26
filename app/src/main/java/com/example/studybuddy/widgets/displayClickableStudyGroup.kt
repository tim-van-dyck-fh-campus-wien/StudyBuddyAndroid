package com.example.studybuddy.widgets

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
            onJoinButtonClick: (String) -> Unit = {},

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
                        onItemClick(studyGroup._id)  //damit ich single study group besuchen kann (?) - funktioniert jetzt evtl anders
                    },
                shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                elevation = 6.dp
            ) {
                Row(/*verticalAlignment = Alignment.Top,*/ modifier = Modifier.padding(5.dp)) {

                    Column(
                        modifier = Modifier
                            .padding(25.dp)
                            .width(200.dp)
                    ) {

                        Text(
                            text = studyGroup.name,
                            style = MaterialTheme.typography.h5,
                            fontStyle = FontStyle.Italic
                        )
                        Text(text = studyGroup.topic, style = MaterialTheme.typography.caption)
                        Text(
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
                        Column(modifier = Modifier.width(200.dp)) {
                            AnimatedVisibility(
                                visible = showContent,
                                enter = expandVertically(expandFrom = Alignment.Top)
                            ) {
                                Column() {
                                    Divider(modifier = Modifier.padding(5.dp))

                                        Text(
                                        text = "How we describe ourselves: ${studyGroup.description}",
                                        style = MaterialTheme.typography.caption)
                                    Divider(modifier = Modifier.padding(5.dp))

                                        Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                                            onClick = { onJoinButtonClick(studyGroup._id) }) {
                                            Text(text = "Send Join Request")
                                        }

                                        Divider(modifier = Modifier.padding(5.dp))
                                    }

                                }
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
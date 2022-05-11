package com.example.studybuddy.widgets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.studybuddy.data.api.ApiConstants
import com.example.studybuddy.data.api.model.SingleStudyGroup
import com.example.studybuddy.data.api.model.getDummyGroups

@Composable
fun DisplayGeneralGroupTextInfo (studyGroup: SingleStudyGroup){
    Text(
        modifier = Modifier.padding(horizontal = 5.dp),
        text = studyGroup.name,
        style = MaterialTheme.typography.h5,
        fontStyle = FontStyle.Italic
    )
    Divider(modifier = Modifier.padding(5.dp))
    Text(modifier = Modifier.padding(horizontal = 7.dp),
        text = studyGroup.topic,
        style = MaterialTheme.typography.body2
    )
    Divider(modifier = Modifier.padding(5.dp))
    Text(
        modifier = Modifier.padding(horizontal = 7.dp),
        text = "Located in: ${studyGroup.location}",
        style = MaterialTheme.typography.body2
    )
    Divider(modifier = Modifier.padding(5.dp))
}

@Composable
fun DisplayStudyGroupIcon(
    studyGroup: SingleStudyGroup,
    //studyGroup: DummyModel = getDummyGroups()[0]
){
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
                .data(
                    Icon(modifier = Modifier
                    .padding(5.dp),imageVector = Icons.Default.Edit, contentDescription = "IconDummy")
                )
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(corner = CornerSize(6.dp)))
                .size(120.dp)
        )
    }
}
@Composable
fun groupIconWithElevation(url:String,iconSize:Int=85,modifier: Modifier=Modifier.size(iconSize.dp)){
    Surface(
        modifier = modifier,
        //.fillMaxHeight(),
        shape = RoundedCornerShape(corner= CornerSize(6.dp)),
        color=Color.White,
        elevation = 6.dp
    ){
        Surface(Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primaryVariant
                    )
                )
            ),color=Color.Transparent){
            groupIcon(url = url,size=iconSize)
        }

    }
}
@Composable
//Watch out -> must concatinate the Image base url in some cases, see createStudyGroup
fun groupIcon(url: String,size:Int=85){

    AsyncImage(model = url, contentDescription = "a study group icon",
        contentScale = ContentScale.Crop, modifier = Modifier
            .size(size.dp)
            )
}
/*A Textfield where the keyboard automatically closes when hitting the done button */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldCloseOnEnter(value:String="",label:String="",maxLength:Int=500,onValueChange:(String)->Unit={}){
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = value,
        onValueChange = { if(value.length<=maxLength) onValueChange(it) },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {keyboardController?.hide()})
    )
}

//@Preview
@Composable
fun DisplayGroupMembers(studyGroup: SingleStudyGroup = getDummyGroups()[0]){
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .widthIn(25.dp),
        //.fillMaxHeight(),
        shape = RoundedCornerShape(corner= CornerSize(6.dp)),
        elevation = 6.dp,
    ) {
            LazyColumn() {
                items(studyGroup.members) { member ->
                    Divider(modifier = Modifier.padding(5.dp))
                    Row {
                        Text(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = "Name: ",
                            style = MaterialTheme.typography.body2
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = "${member.firstname}, ${member.lastname}",
                            style = MaterialTheme.typography.body2
                        )
                    }
                        Text(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = "Email: ${member.email}",
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
    }

@Preview
@Composable
fun DisplayMessaging(studyGroup: SingleStudyGroup = getDummyGroups()[1],
                     ) {
    Surface(
        color = MaterialTheme.colors.background
    ) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .heightIn(min = 130.dp, max = 250.dp),
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
                ) {

                    if (studyGroup.messages.isEmpty()) {
                        Divider(modifier = Modifier.padding(5.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = "No Messages yet, be the first to talk to your Group Members",
                            style = MaterialTheme.typography.body2
                        )
                        Divider(modifier = Modifier.padding(5.dp))
                    } else {

                        LazyColumn(
                            modifier =
                            Modifier
                                .padding(5.dp),
                            reverseLayout = true,
                        ) {
                            items(studyGroup.messages.reversed()) { message ->
                                Divider(modifier = Modifier.padding(5.dp))
                                Row {
                                    Text(
                                        modifier = Modifier.padding(horizontal = 5.dp),
                                        text = "From: ",
                                        style = MaterialTheme.typography.body2
                                    )
                                    Text(
                                        modifier = Modifier.padding(horizontal = 5.dp),
                                        text = message.sender_id.username,
                                        style = MaterialTheme.typography.body2
                                    )
                                }
                                Text(
                                    modifier = Modifier.padding(horizontal = 5.dp),
                                    text = message.text,
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

@Preview(showBackground = true)
@Composable
fun DisplayInputTextFieldAndSendButton(studyGroup: SingleStudyGroup = getDummyGroups()[0],
                                       content: @Composable () -> Unit = {}
                                      ){
    var text by remember { mutableStateOf(TextFieldValue("")) }
   Row {
       OutlinedTextField(
           value = text,
           leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "message Icon") },
           modifier = Modifier
               .padding(8.dp)
               .width(220.dp),
           keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
           label = { Text(text = "...") },
           placeholder = { Text(text = "Your Message") },
           onValueChange = {
               text = it
           }
       )
       Surface(modifier = Modifier
           .padding(16.dp)
           .height(55.dp),
       ) {
           Button(
               colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
               onClick = { Log.d("message", "$text, to Group: ${studyGroup._id}")}) {
               Icon(imageVector = Icons.Default.Send, contentDescription = "sendButton")
           }
       }

   }
}
@Composable
fun GroupButton(
    studyGroup: SingleStudyGroup,
    text : String,
    onButtonClicked: (String) -> Unit = {},
    content: @Composable () -> Unit
) {
    Button(
        modifier = Modifier.padding(horizontal = 5.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
        onClick = { onButtonClicked(studyGroup._id) }) {
        if (text != "") {
            Text(text = text, style = MaterialTheme.typography.button)
        } else {
            content()
        }
    }
}

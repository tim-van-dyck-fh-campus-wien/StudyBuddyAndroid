package com.example.studybuddy.widgets

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.studybuddy.R
import com.example.studybuddy.data.api.model.*
import com.example.studybuddy.viewmodel.AdminViewModel
import io.reactivex.Single

@Composable
fun DisplayGeneralGroupTextInfo (studyGroup: SingleStudyGroup, showHeading:Boolean){
    if (showHeading){
    Text(
        modifier = Modifier.padding(horizontal = 5.dp),
        text = studyGroup.name,
        style = MaterialTheme.typography.h5,
        fontStyle = FontStyle.Italic
    )}
    if (studyGroup.topic != "") {
        Divider(modifier = Modifier.padding(5.dp))
        Text(
            modifier = Modifier.padding(horizontal = 7.dp),
            text = "Our Topics: ${studyGroup.topic}",
            style = MaterialTheme.typography.body2
        )
    }
    Divider(modifier = Modifier.padding(5.dp))
    Text(
        modifier = Modifier.padding(horizontal = 7.dp),
        text = "Located in: ${studyGroup.location}",
        style = MaterialTheme.typography.body2
    )
    Divider(modifier = Modifier.padding(5.dp))
}

/*@Composable
fun DisplayStudyGroupIcon(
    studyGroup: SingleStudyGroup,
    //studyGroup: DummyModel = getDummyGroups()[0]
){
    // PICTURE
    Surface(
        modifier = Modifier
            .size(120.dp)
            .padding(12.dp),
        shape = RoundedCornerShape(corner= CornerSize(6.dp)),
        color = Color.LightGray,
        elevation = 6.dp,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
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
}*/
@Composable
fun groupIconWithElevation(url:String,iconSize:Int=85,modifier: Modifier=Modifier.size(iconSize.dp)){
    Surface(
        modifier = modifier,
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
fun TextFieldCloseOnEnter(value:String="",label:String="",isPasswordField:Boolean=false,maxLength:Int=500,onValueChange:(String)->Unit={}){
    val keyboardController = LocalSoftwareKeyboardController.current
    if(!isPasswordField){
        OutlinedTextField(
            value = value,
            onValueChange = { if(value.length<=maxLength) onValueChange(it) },
            label = { Text(text = label) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()})
        )
    }else{
        OutlinedTextField(
            value = value,
            onValueChange = { if(value.length<=maxLength) onValueChange(it) },
            label = { Text(text = label) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()}),
            visualTransformation = PasswordVisualTransformation(),
            )
    }

}


@Composable
fun DisplayGroupMembers(studyGroupMembers: List<BasicStudent>, studyGroupID:SingleGroupId, admin: Boolean, content: @Composable () -> Unit, onDelete:(BasicStudent) -> Unit = {}, adminViewModel: AdminViewModel){
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .heightIn(min = 50.dp, max = 700.dp),
        shape = RoundedCornerShape(corner= CornerSize(6.dp)),
        elevation = 6.dp,
    ) {
            LazyColumn() {
                items(studyGroupMembers){ member ->
                    Divider(modifier = Modifier.padding(5.dp))
                    Row {
                        Column (modifier = Modifier.width(300.dp)) {
                            Row {
                                Column(modifier = Modifier.width(250.dp)) {
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

                                if (admin){
                                    content()
                                    Column( modifier = Modifier.width(50.dp),
                                        horizontalAlignment = Alignment.End) {
                                        Icon(imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete Member",
                                            modifier = Modifier.clickable {
                                                Log.i("StudyGroupReusables", "Group ID: ${studyGroupID}, member ID: ${member._id}")
                                                adminViewModel.deleteMember(deleteMemberFromGroupData = DeleteMemberFromGroupData(studyGroupID.groupId, member._id), callbackDeleteMember = {
                                                        if(it){
                                                            onDelete(member)
                                                        }
                                                })
                                            })
                                    }
                                }
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
        }
    }

//@Preview
@Composable
fun DisplayMessaging(messages: List<Message>?
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
                    if (messages == null || messages.isEmpty()) {
                        //if (messages.isEmpty()) {
                            Divider(modifier = Modifier.padding(5.dp))
                            Text(
                                modifier = Modifier.padding(horizontal = 5.dp),
                                text = "No Messages yet, be the first to talk to your Group Members",
                                style = MaterialTheme.typography.body2
                            )
                            Divider(modifier = Modifier.padding(5.dp))
                        }
                    else {

                        LazyColumn(
                            modifier =
                            Modifier
                                .padding(5.dp),
                            reverseLayout = true,
                        ) {
                            items(messages.reversed()) { message ->
                                DisplayMessage(message = message)
                            }
                        }
                    }

                }

            }

        }
    }
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DisplayInputTextFieldAndSendButton(studyGroup: SingleStudyGroup, username : String = "",
                                       content: @Composable (Message) -> Unit = {}
                                      ){
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var test by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
   Row {
       OutlinedTextField(
           value = text,
           leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "message Icon") },
           modifier = Modifier
               .padding(8.dp)
               .width(220.dp),
           label = { Text(text = "...") },
           placeholder = { Text(text = "Your Message") },
           onValueChange = {
               text = it
           },
           keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
           keyboardActions = KeyboardActions(
               onDone = {keyboardController?.hide()})
       )
       val message = Message(text = text.text, groupId = studyGroup._id, sender_id = BasicStudent("", "", "", "", username, "", false))
       var sendMessage by remember { mutableStateOf(false)       }
       if (!sendMessage) {
           Surface(
               modifier = Modifier
                   .padding(16.dp)
                   .height(55.dp),
           ) {
               Button(
                   onClick = {
                       Log.d("message", "$text, to Group: ${studyGroup._id}")
                       sendMessage = true
                   }) {
                   Icon(imageVector = Icons.Default.Send, contentDescription = "sendButton")
               }
           }
       }
       if (sendMessage) {
           content(message)
           text = test
           sendMessage = !sendMessage
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
        onClick = { onButtonClicked(studyGroup._id) }) {
        if (text != "") {
            Text(text = text, style = MaterialTheme.typography.button)
        } else {
            content()
        }
    }
}


@Composable
fun DesignForWidgets(content: @Composable () -> Unit = {}){
    Row {
        Surface() {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),

                shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                elevation = 6.dp
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(5.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 17.dp)
                    ) {
                        content()
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayMessage(message: Message){
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

@Composable
fun DisplayJoinRQ(joinRequestsReceivedForAdmin: JoinRequestsReceivedForAdmin){
    Divider(modifier = Modifier.padding(5.dp))
    Row {
        Text(
            modifier = Modifier.padding(horizontal = 5.dp),
            text = "From: ",
            style = MaterialTheme.typography.body2
        )
        Text(
            modifier = Modifier.padding(horizontal = 5.dp),
            text = joinRequestsReceivedForAdmin.sender_id.username,
            style = MaterialTheme.typography.body2
        )
    }
    Text(
        modifier = Modifier.padding(horizontal = 5.dp),
        text = joinRequestsReceivedForAdmin.text,
        style = MaterialTheme.typography.caption
    )
}


@Preview
@Composable
fun displayLogo(){
    Surface(
        modifier = Modifier
            .padding(12.dp),
        shape = RectangleShape,
        elevation = 6.dp
    ) {
        Image(
            painter = painterResource(id = R.drawable.transparent_study_buddy_backup),
            contentDescription = "StudyBuddyIcon"
        )
    }
}
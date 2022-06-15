package com.example.studybuddy.data.api.model



data class SingleStudyGroup(
    val name:String = "",
    val location:String = "",
    val description:String = "",
    val topic:String = "",
    val admin:BasicStudent,
    var members:List<BasicStudent>,
    val appointments:List<Appointment>,
    val _id:String = "",
    val messages:List<Message>,
    val joinRequests:List<JoinRequest>,
    val icon:String = "",
    val hide:Boolean
)

fun getDummyGroup(): SingleStudyGroup{
    return SingleStudyGroup(
            name = "Dummy1",
            location = "1010",
            description = "studying AI",
            topic="AI & Data Science",
            admin= BasicStudent(_id = "1", firstname = "trick", lastname = "duck", email = "trick.duck@fh-campuswien.ac.at", username = "trick", location="1090", hideData = false),
            members= listOf(
                BasicStudent(_id = "1", firstname = "trick", lastname = "duck", email = "trick.duck@fh-campuswien.ac.at", username = "trick", location="1090", hideData = false),
                BasicStudent(_id = "2", firstname = "tick", lastname = "duck", email = "tick.duck@fh-campuswien.ac.at", username = "tick", location="1030", hideData = false),
                BasicStudent(_id = "3", firstname = "track", lastname = "duck", email = "track.duck@fh-campuswien.ac.at", username = "track", location="1010", hideData = false),
            ),
            appointments= listOf(
                Appointment(date = "23.5.2021", topic = "Heuristic Search", _id = "01")
            ),
            _id="00001",
            messages= listOf(
                Message(text = "messageContent", groupId = "00001", sender_id = BasicStudent(_id = "1", firstname = "trick", lastname = "duck", email = "trick.duck@fh-campuswien.ac.at", username = "trick", location="1090", hideData = false)) ,
                Message(text = "messageContent Answer 2", groupId = "00001", sender_id = BasicStudent(_id = "2", firstname = "tick", lastname = "duck", email = "tick.duck@fh-campuswien.ac.at", username = "tick", location="1030", hideData = false)),
                Message(text = "messageContent Answer 3", groupId = "00001", sender_id = BasicStudent(_id = "2", firstname = "tick", lastname = "duck", email = "tick.duck@fh-campuswien.ac.at", username = "tick", location="1030", hideData = false)),
                Message(text = "messageContent Answer 4", groupId = "00001", sender_id = BasicStudent(_id = "2", firstname = "tick", lastname = "duck", email = "tick.duck@fh-campuswien.ac.at", username = "tick", location="1030", hideData = false)),
                Message(text = "messageContent Answer 5", groupId = "00001", sender_id = BasicStudent(_id = "2", firstname = "tick", lastname = "duck", email = "tick.duck@fh-campuswien.ac.at", username = "tick", location="1030", hideData = false)),
                Message(text = "messageContent Answer 6", groupId = "00001", sender_id = BasicStudent(_id = "2", firstname = "tick", lastname = "duck", email = "tick.duck@fh-campuswien.ac.at", username = "tick", location="1030", hideData = false)),
                Message(text = "messageContent Answer 7", groupId = "00001", sender_id = BasicStudent(_id = "2", firstname = "tick", lastname = "duck", email = "tick.duck@fh-campuswien.ac.at", username = "tick", location="1030", hideData = false)),
                Message(text = "messageContent Answer 8", groupId = "00001", sender_id = BasicStudent(_id = "2", firstname = "tick", lastname = "duck", email = "tick.duck@fh-campuswien.ac.at", username = "tick", location="1030", hideData = false)),
                Message(text = "messageContent Answer 9", groupId = "00001", sender_id = BasicStudent(_id = "2", firstname = "tick", lastname = "duck", email = "tick.duck@fh-campuswien.ac.at", username = "tick", location="1030", hideData = false)),

            ),
            joinRequests= listOf(
                JoinRequest(groupId = "00001", text = "join request", sender_id = "4", _id = "23")
            ),
            icon="/group/calculator.png",
            hide=false,
        )
       /* SingleStudyGroup(
            name = "Dummy2",
            location = "1090",
            description = "learning all about Mobile App Development on Android",
            topic="MAD",
            admin=                 BasicStudent(_id = "3", firstname = "track", lastname = "duck", email = "track.duck@fh-campuswien.ac.at", username = "track", location="1010", hideData = false),

            members= listOf(
                BasicStudent(_id = "3", firstname = "track", lastname = "duck", email = "track.duck@fh-campuswien.ac.at", username = "track", location="1010", hideData = false),
                BasicStudent(_id = "1", firstname = "trick", lastname = "duck", email = "trick.duck@fh-campuswien.ac.at", username = "trick", location="1090", hideData = false),
            ),
            appointments= listOf(
                Appointment(date = "23.5.2021", topic = "Heuristic Search", _id = "01")
            ),
            _id="0000",
            messages= listOf(),
            joinRequests= listOf(
                JoinRequest(groupId = "00001", text = "join request", sender_id = "4", _id = "25")
            ),
            icon="/group/calculator.png",
            hide=false,
        )
    )*/
}

/*fun filterGroup(studyGroupID: String?) : SingleStudyGroup {
    return getDummyGroups().filter {studyGroup -> studyGroup._id == studyGroupID}[0]
}*/

package com.example.studybuddy.data.dummies

import com.example.studybuddy.data.api.model.Appointment
import com.example.studybuddy.data.api.model.BasicStudent
import com.example.studybuddy.data.api.model.JoinRequest
import com.example.studybuddy.data.api.model.Message

data class DummyModel(
    val name:String,
    val location:String,
    val description:String,
    val topic:String,
    val admin: BasicStudent,
    val members:List<BasicStudent>,
    val appointments:List<Appointment>,
    val _id:String,
    val messages:List<Message>,
    val joinRequests:List<JoinRequest>,
    val icon:String,
    val hide:Boolean
)

fun getDummyGroups(): List<DummyModel>{
    return listOf(
        DummyModel(
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
             ),
             joinRequests= listOf(
                 JoinRequest(groupId = "00001", text = "join request", sender_id = "4", _id = "23")
             ),
             icon="https://d30y9cdsu7xlg0.cloudfront.net/png/409803-200.png",
             hide=false,
        ),
        DummyModel(
            name = "Dummy1",
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
            messages= listOf(
                Message(text = "messageContent", groupId = "00001", sender_id = BasicStudent(_id = "1", firstname = "trick", lastname = "duck", email = "trick.duck@fh-campuswien.ac.at", username = "trick", location="1090", hideData = false)) ,

            ),
            joinRequests= listOf(
                JoinRequest(groupId = "00001", text = "join request", sender_id = "4", _id = "25")
            ),
            icon="https://d30y9cdsu7xlg0.cloudfront.net/png/409803-200.png",
            hide=false,
        )
    )
}
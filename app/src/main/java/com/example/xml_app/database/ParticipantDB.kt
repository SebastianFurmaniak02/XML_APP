package com.example.xml_app.database
data class ParticipantDB(
    val id: Int?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val gender: String,
    val studentStatus: Int,
    val skillLevel: Int
)
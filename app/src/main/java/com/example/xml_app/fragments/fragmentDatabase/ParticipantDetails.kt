package com.example.xml_app.fragments.fragmentDatabase

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.xml_app.R
import com.example.xml_app.database.DatabaseHandler
import com.example.xml_app.database.ParticipantDB

class ParticipantDetails : AppCompatActivity() {
    private lateinit var db: DatabaseHandler
    private lateinit var participant: ParticipantDB


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.participant_details)

        db = DatabaseHandler(this)
        val participantID = intent.getStringExtra("PARTICIPANT_ID")
        val record = participantID?.let { db.getRecord(it) }

        if (record != null) {
            participant = record
        } else {
            Toast.makeText(this, "Participant doesn't exist.", Toast.LENGTH_SHORT).show()
            finish()
        }


        findViewById<TextView>(R.id.textViewParticipantFirstName).text = "First name: " + participant.firstName
        findViewById<TextView>(R.id.textViewParticipantLastName).text = "Last name: " + participant.lastName
        findViewById<TextView>(R.id.textViewParticipantEmail).text = "Email: " + participant.email
        findViewById<TextView>(R.id.textViewParticipantGender).text = "Gender: " + participant.gender
        findViewById<TextView>(R.id.textViewParticipantStudentStatus).text = "Student status: " + when {
                participant.studentStatus == 1 -> "Yes"
                else -> "No"
            }
        findViewById<TextView>(R.id.textViewParticipantSkillLevel).text = "Skill level: " + participant.skillLevel.toString()
        findViewById<Button>(R.id.buttonDelete).setOnClickListener {
            deleteParticipant()
        }
    }

    private fun deleteParticipant() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                db.deleteParticipant(participant.id)
                finish()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}
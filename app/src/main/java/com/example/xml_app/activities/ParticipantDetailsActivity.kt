package com.example.xml_app.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.xml_app.R
import com.example.xml_app.database.DatabaseHandler
import com.example.xml_app.database.ParticipantDB

class ParticipantDetailsActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHandler
    private lateinit var participant: ParticipantDB


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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


        findViewById<TextView>(R.id.textViewParticipantFirstName).text = participant.firstName
        findViewById<TextView>(R.id.textViewParticipantLastName).text = participant.lastName
        findViewById<TextView>(R.id.textViewParticipantEmail).text = participant.email
        findViewById<TextView>(R.id.textViewParticipantGender).text = participant.gender
        findViewById<TextView>(R.id.textViewParticipantStudentStatus).text = when {
                participant.studentStatus == 1 -> "Yes"
                else -> "No"
            }
        findViewById<TextView>(R.id.textViewParticipantSkillLevel).text = when (participant.skillLevel) {
            1 -> "Beginner"
            2 -> "Novice"
            3 -> "Intermediate"
            4 -> "Proficient"
            else -> "Advanced"
        }
        findViewById<Button>(R.id.buttonDelete).setOnClickListener {
            deleteParticipant()
        }
        findViewById<Button>(R.id.buttonBack).setOnClickListener {
            finish()
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
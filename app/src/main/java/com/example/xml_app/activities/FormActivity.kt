package com.example.xml_app.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.xml_app.R
import com.example.xml_app.database.DatabaseHandler
import com.example.xml_app.database.ParticipantDB

class FormActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var maleButton: RadioButton
    private lateinit var femaleButton: RadioButton
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var studentStatusSwitch: Switch
    private lateinit var skillLevelSeekBar: SeekBar
    private lateinit var confirmButton: Button

    private lateinit var db: DatabaseHandler
    private var seekBarProgress: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_activity)
        firstNameEditText = findViewById(R.id.editTextFirstName)
        lastNameEditText = findViewById(R.id.editTextLastName)
        emailEditText = findViewById(R.id.editTextEmail)
        maleButton = findViewById(R.id.radioButtonMale)
        femaleButton = findViewById(R.id.radioButtonFemale)
        studentStatusSwitch = findViewById(R.id.switchStudentStatus)
        skillLevelSeekBar = findViewById(R.id.seekBarSkillLevel)
        confirmButton = findViewById(R.id.buttonConfirm)
        confirmButton.setOnClickListener(this)
        findViewById<Button>(R.id.buttonBackForm).setOnClickListener {
            finish()
        }

        db = DatabaseHandler(this)

        skillLevelSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBarProgress = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    override fun onClick(v: View?) {
        if (v == confirmButton) {
            if (firstNameEditText.text.isNullOrEmpty() || lastNameEditText.text.isNullOrEmpty() || emailEditText.text.isNullOrEmpty())
                emptyEdits()
            else if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.trim()).matches())
                wrongEmail()
            else {
                addParticipant()
                confirmationSuccessful()
            }
        }
    }

    private fun addParticipant() {
        val genderSelected = when {
            maleButton.isChecked -> "Male"
            else -> "Female"
        }

        val participantDB = ParticipantDB(
            id = null,
            firstName = firstNameEditText.text.toString(),
            lastName = lastNameEditText.text.toString(),
            email = emailEditText.text.toString(),
            gender = genderSelected,
            studentStatus = when {
                studentStatusSwitch.isChecked -> 1
                else -> 2
            },
            skillLevel = seekBarProgress
        )
        db.insertData(participantDB)
    }

    private fun confirmationSuccessful() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("The form has been sent.")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun emptyEdits() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Some fields are empty.")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun wrongEmail() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Invalid email address.")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}
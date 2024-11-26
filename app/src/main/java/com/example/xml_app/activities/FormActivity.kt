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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.xml_app.R
import com.example.xml_app.database.DatabaseHandler
import com.example.xml_app.database.ParticipantDB
import com.example.xml_app.viewModel.FormViewModel

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
    private lateinit var viewModel: FormViewModel


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
        viewModel = ViewModelProvider(this)[FormViewModel::class.java]

        db = DatabaseHandler(this)

        updateAllViews()

        skillLevelSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.updateSkillLevel(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        firstNameEditText.addTextChangedListener {
            viewModel.updateFirstName(firstNameEditText.text.toString())
        }
        lastNameEditText.addTextChangedListener {
            viewModel.updateLastName(lastNameEditText.text.toString())
        }
        emailEditText.addTextChangedListener {
            viewModel.updateEmail(emailEditText.text.toString())
        }
        maleButton.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateGender(
                if (isChecked) 0
                else 1
            )
        }
        studentStatusSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateStudentStatus(isChecked)
        }

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
            viewModel.formScreenInfo.value.selectedIndex == 0 -> "Male"
            else -> "Female"
        }

        val participantDB = ParticipantDB(
            id = null,
            firstName = viewModel.formScreenInfo.value.firstName,
            lastName = viewModel.formScreenInfo.value.lastName,
            email = viewModel.formScreenInfo.value.email,
            gender = genderSelected,
            studentStatus = when {
                viewModel.formScreenInfo.value.studentStatus -> 1
                else -> 0
            },
            skillLevel = when (viewModel.formScreenInfo.value.skillLevel) {
                0 -> "Beginner"
                1 -> "Novice"
                2 -> "Intermediate"
                3 -> "Proficient"
                else -> "Advanced"
            }
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

    private fun updateAllViews() {
        firstNameEditText.setText(viewModel.formScreenInfo.value.firstName)
        lastNameEditText.setText(viewModel.formScreenInfo.value.lastName)
        emailEditText.setText(viewModel.formScreenInfo.value.email)
        maleButton.isChecked = (viewModel.formScreenInfo.value.selectedIndex == 0)
        studentStatusSwitch.isChecked = viewModel.formScreenInfo.value.studentStatus
        skillLevelSeekBar.progress = viewModel.formScreenInfo.value.skillLevel
    }
}


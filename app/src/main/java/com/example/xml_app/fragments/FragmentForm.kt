package com.example.xml_app.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.xml_app.R
import com.example.xml_app.database.DatabaseHandler
import com.example.xml_app.database.ParticipantDB

@SuppressLint("UseSwitchCompatOrMaterialCode")
class FragmentForm : Fragment(), View.OnClickListener {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var maleButton: RadioButton
    private lateinit var femaleButton: RadioButton
    private lateinit var otherButton: RadioButton
    private lateinit var studentStatusSwitch: Switch
    private lateinit var skillLevelSeekBar: SeekBar
    private lateinit var confirmButton: Button

    private lateinit var db: DatabaseHandler
    private var seekBarProgress: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstNameEditText = requireView().findViewById(R.id.editTextFirstName)
        lastNameEditText = requireView().findViewById(R.id.editTextLastName)
        emailEditText = requireView().findViewById(R.id.editTextEmail)
        maleButton = requireView().findViewById(R.id.radioButtonMale)
        femaleButton = requireView().findViewById(R.id.radioButtonFemale)
        otherButton = requireView().findViewById(R.id.radioButtonOther)
        studentStatusSwitch = requireView().findViewById(R.id.switchStudentStatus)
        skillLevelSeekBar = requireView().findViewById(R.id.seekBarSkillLevel)
        confirmButton = requireView().findViewById(R.id.buttonConfirm)
        confirmButton.setOnClickListener(this)

        db = DatabaseHandler(requireContext())

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

    @SuppressLint("SuspiciousIndentation")
    override fun onClick(v: View?) {
        if (v == confirmButton) {
            if (firstNameEditText.text == null || lastNameEditText.text == null || emailEditText.text == null)
                emptyEdits()
            else if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.trim()).matches())
                wrongEmail()
            else if (!maleButton.isChecked && !femaleButton.isChecked && !otherButton.isChecked) {
                noGender()
            }
            else {
                addParticipant()
                confirmationSuccessful()
            }
        }
    }

    private fun addParticipant() {
        val genderSelected = when {
            maleButton.isChecked -> "Male"
            femaleButton.isChecked -> "Female"
            else -> "Other"
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
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("The form has been sent.\n First name: ${firstNameEditText.text}\n Last name: ${lastNameEditText.text}\n Email: ${emailEditText.text}")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun emptyEdits() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Some fields were not completed correctly.")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun wrongEmail() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("The email was filled in incorrectly.")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun noGender() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Select gender.")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}
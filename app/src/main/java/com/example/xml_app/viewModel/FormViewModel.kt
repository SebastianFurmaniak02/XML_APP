package com.example.xml_app.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class FormScreenInfo(
    var firstName : String = "",
    var lastName : String = "",
    var email : String = "",
    var selectedIndex : Int = 0,
    var studentStatus: Boolean = false,
    var skillLevel: Int = 3,
)
class FormViewModel : ViewModel() {

    private val _formScreenInfo = MutableStateFlow(FormScreenInfo())
    val formScreenInfo: StateFlow<FormScreenInfo> get() = _formScreenInfo

    fun updateFirstName(firstName: String) {
        _formScreenInfo.value = _formScreenInfo.value.copy(firstName = firstName)
    }

    fun updateLastName(lastName: String) {
        _formScreenInfo.value = _formScreenInfo.value.copy(lastName = lastName)
    }

    fun updateEmail(email: String) {
        _formScreenInfo.value = _formScreenInfo.value.copy(email = email)
    }

    fun updateGender(index: Int) {
        _formScreenInfo.value = _formScreenInfo.value.copy(selectedIndex = index)
    }

    fun updateStudentStatus(isStudent: Boolean) {
        _formScreenInfo.value = _formScreenInfo.value.copy(studentStatus = isStudent)
    }

    fun updateSkillLevel(level: Int) {
        _formScreenInfo.value = _formScreenInfo.value.copy(skillLevel = level)
    }
}
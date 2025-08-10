package com.example.notesapplication.presentation.viewmodels

sealed class UIEvent {
    data class  UserNameChanged(val userName : String) : UIEvent()
    data class EmailChanged(val email : String) : UIEvent()
    data class PasswordChanged(val password : String) : UIEvent()

    object RegisterButtonClicked : UIEvent()
    object LoginButtonClicked : UIEvent()
}
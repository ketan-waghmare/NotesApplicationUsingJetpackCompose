package com.example.notesapplication.presentation.screens.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.domain.model.Note
import com.example.notesapplication.domain.usecase.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase
) : ViewModel() {
    var notesList = mutableStateOf<List<Note>>(emptyList())
        private set

    var isLoading = mutableStateOf(false)
        private set

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                notesList.value = getNotesUseCase()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            isLoading.value = false
        }
    }
}
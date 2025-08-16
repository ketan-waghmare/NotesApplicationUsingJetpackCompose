package com.example.notesapplication.presentation.screens.notedetails

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.logindemousingcompose.R
import com.example.notesapplication.presentation.components.ButtonComponent
import com.example.notesapplication.presentation.components.HeadingTextComponent

@Composable
fun NoteDetailScreen(
    noteId: String,
    navController: NavHostController
) {
    val viewModel: NoteDetailViewModel = hiltViewModel()
    val noteState = viewModel.noteDetailState.value
    val loading = viewModel.loading.value

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val noteDetailSuccess by viewModel.noteDetailSuccess

    LaunchedEffect(noteDetailSuccess) {
        if (noteDetailSuccess) {
            navController.popBackStack()
        }
    }

    LaunchedEffect(noteId) {
        viewModel.getNotesDetails(noteId)
    }

    LaunchedEffect(noteState.title,noteState.description) {
        title = noteState.title
        description = noteState.description
    }

    if(loading) {

    } else {

    }

    Scaffold(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)
    ) { paddingValues ->

        if(loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeadingTextComponent(value = stringResource(R.string.edit_note))
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            viewModel.onEvent(NoteDetailUIEvent.TitleChanged(it))
                        },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            viewModel.onEvent(NoteDetailUIEvent.DescriptionChanged(it))
                        },
                        label = { Text("Description")},
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                    Spacer(Modifier.height(16.dp))
                    ButtonComponent(value = "Update", onButtonClicked = {
                        viewModel.onEvent(NoteDetailUIEvent.updateNoteClicked)
                    })
                }
            }
        }

    }

}
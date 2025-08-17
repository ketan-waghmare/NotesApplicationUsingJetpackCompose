package com.example.notesapplication.presentation.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.logindemousingcompose.R
import com.example.notesapplication.domain.model.Note
import com.example.notesapplication.presentation.components.HeadingTextComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController, viewModel: HomeViewModel = hiltViewModel()
) {
    val notes = viewModel.notesList.value
    val loading = viewModel.isLoading.value

    val state = viewModel.notesList.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.loadNotes()
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is HomeUIEvent.ShowMessage ->
                    snackbarHostState.showSnackbar(event.text)
                is HomeUIEvent.DeletedWithUndo -> {
                    val res = snackbarHostState.showSnackbar(
                        message = "Note deleted",
                        actionLabel = "Undo",
                        withDismissAction = true
                    )
                    if (res == SnackbarResult.ActionPerformed) {
                        viewModel.undoDelete(event.note, event.index)
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { androidx.compose.material3.SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addnote") }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note"
                )
            }
        }
    ) { innerPadding ->

        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(8.dp)
                    .padding(innerPadding)
            ) {
                Column {
                    HeadingTextComponent(value = stringResource(R.string.notes_list))
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn {
                        items(items = notes, key = { it.id }) { note ->
                            NoteRow(
                                note = note,
                                onDelete = { viewModel.onDeleteRequest(it) },
                                onClick = { navController.navigate("note_details/${it.id}") }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
//    Card(
//        onClick = onClick,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(text = note.description, style = MaterialTheme.typography.bodyMedium)
//        }
//    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = note.title.ifBlank { "1" },
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = note.description.ifBlank { "No description" },
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteRow(
    note: Note,
    onDelete: (Note) -> Unit,
    onClick: (Note) -> Unit
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.StartToEnd,
                SwipeToDismissBoxValue.EndToStart -> {
                    onDelete(note)
                    true
                }
                else -> false
            }
        }
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    ) {
        NoteItem(note = note)  {
            onClick(note)
        }
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}

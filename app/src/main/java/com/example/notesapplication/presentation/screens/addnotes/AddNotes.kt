package com.example.notesapplication.presentation.screens.addnotes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.logindemousingcompose.R
import com.example.notesapplication.presentation.components.ButtonComponent
import com.example.notesapplication.presentation.components.HeadingTextComponent
import com.example.notesapplication.presentation.components.MyTextFieldComponent
import com.example.notesapplication.presentation.components.NormalTextComponent


@Composable
fun AddNotes(
    navController: NavController,
    addNoteViewModel: AddNoteViewModel = hiltViewModel()
) {
    val addNoteSuccess by addNoteViewModel.addNoteSuccess


    // ✅ Navigate on success
    LaunchedEffect(addNoteSuccess) {
        if (addNoteSuccess) {
            navController.popBackStack()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {

                HeadingTextComponent(value = stringResource(R.string.add_note))

                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(
                    lableValue = stringResource(R.string.title),
                    painterResource = null,
                    onTextSelected = {
                        addNoteViewModel.onEvent(CreateNoteUIEvent.TitleChanged(it))
                    }
                )

                MyTextFieldComponent(
                    lableValue = stringResource(R.string.description),
                    painterResource = null,
                    onTextSelected = {
                        addNoteViewModel.onEvent(CreateNoteUIEvent.DescriptionChanged(it))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ButtonComponent(value = stringResource(R.string.create), onButtonClicked = {
                    addNoteViewModel.onEvent(CreateNoteUIEvent.SaveNoteClicked)
                })
            }
    }


    }


}

@Preview
@Composable
fun SimpleComposablePreview() {
    val navController = rememberNavController()
    AddNotes(navController = navController)
}



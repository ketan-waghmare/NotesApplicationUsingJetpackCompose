package com.example.notesapplication.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.ui.Modifier
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.logindemousingcompose.R
import com.example.notesapplication.presentation.components.ButtonComponent
import com.example.notesapplication.presentation.components.ClickableLoginTextComponent
import com.example.notesapplication.presentation.components.DividerComponent
import com.example.notesapplication.presentation.components.HeadingTextComponent
import com.example.notesapplication.presentation.components.MyTextFieldComponent
import com.example.notesapplication.presentation.components.NormalTextComponent
import com.example.notesapplication.presentation.components.PasswordTextComponent
import com.example.notesapplication.presentation.viewmodels.AuthViewModel
import com.example.notesapplication.presentation.viewmodels.UIEvent


@Composable
fun Login(navController: NavHostController) {
    val context = LocalContext.current;
    val viewModel: AuthViewModel = hiltViewModel()
    val loginSuccess by viewModel.loginSuccess

    // ✅ Navigate on success
    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate("homescreen")
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
                NormalTextComponent(value = stringResource(R.string.login))
                HeadingTextComponent(value = stringResource(R.string.app_name))

                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(
                    lableValue = stringResource(R.string.userName),
                    painterResource = painterResource(id = R.drawable.email),
                    onTextSelected = {
                        viewModel.onEvent(UIEvent.UserNameChanged(it))
                    }
                )

                PasswordTextComponent(
                    lableValue = stringResource(R.string.password),
                    painterResource = painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        viewModel.onEvent(UIEvent.PasswordChanged(it))
                    },
                )

                Spacer(modifier = Modifier.height(10.dp))

                Spacer(modifier = Modifier.height(40.dp))
                ButtonComponent(value = stringResource(R.string.login), onButtonClicked = {
                    viewModel.onEvent(UIEvent.LoginButtonClicked)
                })

                Spacer(modifier = Modifier.height(20.dp))

                DividerComponent()

                ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                    navController.navigate("signup")
                })
            }
        }


    }
}

@Composable
fun isInPreview(): Boolean {
    return LocalInspectionMode.current
}

@Preview
@Composable
fun LoginPreview() {
    val navController = rememberNavController()
    Login(navController = navController)
}
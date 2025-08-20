package com.example.notesapplication.presentation.screens


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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
fun SignUp(navController: NavHostController, viewModel: AuthViewModel = hiltViewModel()) {

    val signupSuccess by viewModel.signupSuccess.collectAsState()
    val authState = viewModel.authUIstate.collectAsState()

    // Local UI flags — preserve across config changes
    var usernameTouched by rememberSaveable { mutableStateOf(false) }
    var emailTouched by rememberSaveable { mutableStateOf(false) }
    var passwordTouched by rememberSaveable { mutableStateOf(false) }
    var attemptedSubmit by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(signupSuccess) {
        if (signupSuccess) {
            navController.navigate("login")
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
                NormalTextComponent(value = stringResource(id = R.string.hello))
                HeadingTextComponent(value = stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(20.dp))

                val showUsernameError = authState.value.userNameError != null && (usernameTouched || attemptedSubmit)
                MyTextFieldComponent(
                    lableValue = stringResource(id = R.string.userName),
                    painterResource(R.drawable.profile),
                    onTextSelected = {
                        if (!usernameTouched) usernameTouched = true
                        viewModel.onEvent(UIEvent.UserNameChanged(it))
                    },
                    isError = showUsernameError,
                    errorText = authState.value.userNameError
                )

                val showEmailError = authState.value.emailError != null && (emailTouched || attemptedSubmit)
                MyTextFieldComponent(
                    lableValue = stringResource(id = R.string.email),
                    painterResource = painterResource(R.drawable.email),
                    onTextSelected = {
                        if(!passwordTouched) passwordTouched = true
                        viewModel.onEvent(UIEvent.EmailChanged(it))
                    },
                    isError = showEmailError,
                    errorText = authState.value.emailError

                )

                val showPasswordError = authState.value.passwordError != null && (passwordTouched || attemptedSubmit)
                PasswordTextComponent(
                    lableValue = stringResource(R.string.password),
                    painterResource = painterResource(R.drawable.lock),
                    onTextSelected = {
                        viewModel.onEvent(UIEvent.PasswordChanged(it))
                    },
                    isError = showPasswordError,
                    errorText = authState.value.passwordError
                )


                Spacer(modifier = Modifier.height(90.dp))

                ButtonComponent(value = stringResource(R.string.register), onButtonClicked = {
                    attemptedSubmit = true
                    viewModel.onEvent(UIEvent.RegisterButtonClicked)
                })

                Spacer(modifier = Modifier.height(20.dp))

                DividerComponent()

                ClickableLoginTextComponent(onTextSelected = {
                    navController.navigate("login")
                })
            }
        }


    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreviewOfSignUpScreen() {
    val navController = rememberNavController()
    SignUp(navController = navController)
}
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

@Composable
fun SignUp(navController: NavHostController, loginViewModel: AuthViewModel = hiltViewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize()
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

                MyTextFieldComponent(lableValue = stringResource(id = R.string.userName),
                    painterResource(R.drawable.profile),
                    onTextSelected = {
//                    loginViewModel.onEvent(UIEvent.FirstNameChanged(it))
                    }
                )

                MyTextFieldComponent(
                    lableValue = stringResource(id = R.string.email),
                    painterResource = painterResource(R.drawable.email),
                    onTextSelected = {
//                    loginViewModel.onEvent(UIEvent.EmailChanged(it))
                    }
                )

                PasswordTextComponent(
                    lableValue = stringResource(R.string.password),
                    painterResource = painterResource(R.drawable.lock),
                    onTextSelected = {
//                    loginViewModel.onEvent(UIEvent.PasswordChanged(it))
                    }
                )


                Spacer(modifier = Modifier.height(90.dp))

                ButtonComponent(value = stringResource(R.string.register), onButtonClicked = {
//                loginViewModel.onEvent(UIEvent.RegisterButtonClicked)
                })

                Spacer(modifier = Modifier.height(20.dp))

                DividerComponent()

                ClickableLoginTextComponent(onTextSelected ={
                    navController.navigate("login")
                })
            }
        }


    }
}


@Preview (showBackground = true)
@Composable
fun DefaultPreviewOfSignUpScreen() {
    val navController = rememberNavController()
    SignUp(navController = navController)
}
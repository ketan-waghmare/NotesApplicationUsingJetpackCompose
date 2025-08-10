package com.example.notesapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notesapplication.presentation.screens.Login
import com.example.notesapplication.presentation.screens.SignUp
import com.example.notesapplication.presentation.screens.addnotes.AddNotes
import com.example.notesapplication.presentation.screens.dashboard.HomeScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            Login(navController)
        }
        composable("signup") {
            SignUp(navController) // Create this screen
        }
        composable("homescreen") {
            HomeScreen(navController) // Create this screen
        }
        composable("addnote") {
            AddNotes(navController) // Create this screen
        }

    }
}